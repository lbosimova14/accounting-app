package com.cydeo.service.implementation;

import com.cydeo.dto.InvoiceDto;
import com.cydeo.dto.InvoiceProductDto;
import com.cydeo.entity.ClientVendor;
import com.cydeo.entity.Company;
import com.cydeo.entity.Invoice;
import com.cydeo.enums.InvoiceStatus;
import com.cydeo.enums.InvoiceType;
import com.cydeo.mapper.MapperUtil;
import com.cydeo.repository.InvoiceRepository;
import com.cydeo.service.InvoiceProductService;
import com.cydeo.service.InvoiceService;
import com.cydeo.service.SecurityService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InvoiceServiceImpl implements InvoiceService {

  private final   InvoiceRepository invoiceRepository;
  private final MapperUtil mapperUtil;

  private final SecurityService securityService;

  private final InvoiceProductService invoiceProductService;

    public InvoiceServiceImpl(InvoiceRepository invoiceRepository, MapperUtil mapperUtil, SecurityService securityService, @Lazy InvoiceProductService invoiceProductService) {
        this.invoiceRepository = invoiceRepository;
        this.mapperUtil = mapperUtil;
        this.securityService = securityService;
        this.invoiceProductService = invoiceProductService;
    }

    @Override
    public InvoiceDto findInvoiceById(Long id) {
        return mapperUtil.convert(invoiceRepository.findInvoiceById(id), new InvoiceDto());
    }

    @Override
    public List<InvoiceDto> getAllInvoicesOfCompany(InvoiceType invoiceType){
        Company company = mapperUtil.convert(securityService.getLoggedInUser().getCompany(), new Company());
        return invoiceRepository.findInvoicesByCompanyAndInvoiceType(company, invoiceType)
                .stream()
                .sorted(Comparator.comparing(Invoice::getInvoiceNo))
                .map(each -> mapperUtil.convert(each, new InvoiceDto()))
                .peek(this::calculateInvoiceDetails)
                .collect(Collectors.toList());
    }

    private void calculateInvoiceDetails(InvoiceDto invoiceDto) {
        invoiceDto.setPrice(getTotalPriceOfInvoice(invoiceDto.getId()));
        invoiceDto.setTax(getTotalTaxOfInvoice(invoiceDto.getId()));
        invoiceDto.setTotal(getTotalPriceOfInvoice(invoiceDto.getId()).add(getTotalTaxOfInvoice(invoiceDto.getId())));
    }

    @Override
    public BigDecimal getTotalPriceOfInvoice(Long id){
        Invoice invoice = invoiceRepository.findInvoiceById(id);
        List<InvoiceProductDto> invoiceProductsOfInvoice = invoiceProductService.getInvoiceProductsOfInvoice(invoice.getId());
        return invoiceProductsOfInvoice.stream()
                .map(p -> p.getPrice()
                        .multiply(BigDecimal.valueOf((long) p.getQuantity())))
                .reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
    }

    @Override
    public BigDecimal getTotalTaxOfInvoice(Long id){
        Invoice invoice = invoiceRepository.findInvoiceById(id);
        List<InvoiceProductDto> invoiceProductsOfInvoice = invoiceProductService.getInvoiceProductsOfInvoice(invoice.getId());
        return invoiceProductsOfInvoice.stream()
                .map(p -> p.getPrice()
                        .multiply(BigDecimal.valueOf(p.getQuantity() * p.getTax() /100d))
                        .setScale(2, RoundingMode.HALF_UP))
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }


    @Override
    public InvoiceDto update(Long invoiceId, InvoiceDto invoiceDto) {
        Invoice invoice = invoiceRepository.findInvoiceById(invoiceId);
        invoice.setClientVendor(mapperUtil.convert(invoiceDto.getClientVendor(), new ClientVendor()));
        invoiceRepository.save(invoice);
        return mapperUtil.convert(invoice, invoiceDto);
    }

    @Override
    public void approve(Long invoiceId) {
        Invoice invoice = invoiceRepository.findInvoiceById(invoiceId);
        invoiceProductService.completeApprovalProcedures(invoiceId, invoice.getInvoiceType());
        invoice.setInvoiceStatus(InvoiceStatus.APPROVED);
        invoice.setDate(LocalDate.now());
        invoiceRepository.save(invoice);
        mapperUtil.convert(invoice, new InvoiceDto());
    }

    @Override
    public void delete(Long invoiceId) {
        Invoice invoice = invoiceRepository.findInvoiceById(invoiceId);
        invoiceProductService.getInvoiceProductsOfInvoice(invoiceId)
                .forEach( invoiceProductDto -> invoiceProductService.delete(invoiceProductDto.getId()));
        invoice.setIsDeleted(true);
        invoiceRepository.save(invoice);
    }

    @Override
    public InvoiceDto printInvoice(Long id) {
        InvoiceDto invoiceDto = mapperUtil.convert(invoiceRepository.findInvoiceById(id), new InvoiceDto());
        calculateInvoiceDetails(invoiceDto);
        return invoiceDto;
    }

    @Override
    public InvoiceDto getNewInvoice(InvoiceType invoiceType){
        InvoiceDto invoiceDto = new InvoiceDto();
        invoiceDto.setInvoiceNo(generateInvoiceNo(invoiceType));
        invoiceDto.setDate(LocalDate.now());
        return invoiceDto;
    }


    private String generateInvoiceNo(InvoiceType invoiceType){
        Company company = mapperUtil.convert(securityService.getLoggedInUser().getCompany(), new Company());
        List<Invoice> invoices = invoiceRepository.findInvoicesByCompanyAndInvoiceType(company, invoiceType);
        if (invoices.size() == 0) {
            return invoiceType.name().charAt(0) + "-001";
        }
        Invoice lastCreatedInvoiceOfTheCompany = invoices.stream()
                .max(Comparator.comparing(Invoice::getInsertDateTime)).get();
        int newOrder = Integer.parseInt(lastCreatedInvoiceOfTheCompany.getInvoiceNo().substring(2)) + 1;
        return invoiceType.name().charAt(0) + "-" + String.format("%03d", newOrder);
    }

    @Override
    public InvoiceDto save(InvoiceDto invoiceDto, InvoiceType invoiceType) {
        invoiceDto.setCompany(securityService.getLoggedInUser().getCompany());
        invoiceDto.setInvoiceType(invoiceType);
        invoiceDto.setInvoiceStatus(InvoiceStatus.AWAITING_APPROVAL);
        Invoice invoice = mapperUtil.convert(invoiceDto, new Invoice());
        return mapperUtil.convert(invoiceRepository.save(invoice), new InvoiceDto());
    }

    @Override
    public List<InvoiceDto> getAllInvoicesByInvoiceStatus(InvoiceStatus status) {
        Company company = mapperUtil.convert(securityService.getLoggedInUser().getCompany(), new Company());
        List<Invoice> invoices = invoiceRepository.findInvoicesByCompanyAndInvoiceStatus(company, InvoiceStatus.APPROVED);
        return invoices
                .stream()
                .map(invoice -> mapperUtil.convert(invoice, new InvoiceDto()))
                .collect(Collectors.toList());
    }

    @Override
    public BigDecimal getProfitLossOfInvoice(Long id) {
        Invoice invoice = invoiceRepository.findInvoiceById(id);
        List<InvoiceProductDto> invoiceProductsOfInvoice = invoiceProductService.getInvoiceProductsOfInvoice(invoice.getId());
        return invoiceProductsOfInvoice.stream()
                .map(InvoiceProductDto::getProfitLoss)
                .reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
    }

    @Override
    public List<InvoiceDto> getLastThreeInvoices() {
        Company company = mapperUtil.convert(securityService.getLoggedInUser().getCompany(), new Company());
        return invoiceRepository.findInvoicesByCompanyAndInvoiceStatusOrderByDateDesc(company, InvoiceStatus.APPROVED)
                .stream()
                .limit(3)
                .map(each -> mapperUtil.convert(each, new InvoiceDto()))
                .peek(this::calculateInvoiceDetails)
                .collect(Collectors.toList());
    }


}
