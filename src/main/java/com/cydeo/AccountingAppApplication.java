package com.cydeo;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

//@EnableCaching

/**
 * If class is not your class, and create bean in this class, bc @SpringBootApplication has @Configuration, it is treating as configuration class
 */
@SpringBootApplication
@EnableFeignClients
@EnableCaching
public class AccountingAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(AccountingAppApplication.class, args);
    }
    //convert the entity/db to dto/view
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
