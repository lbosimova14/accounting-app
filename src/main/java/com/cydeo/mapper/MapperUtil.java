package com.cydeo.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;

@Component
public class MapperUtil {

    private final ModelMapper modelMapper;

    public MapperUtil(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public <T> T convert(Object objectToBeConverted, T convertedObject) {
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        return modelMapper.map(objectToBeConverted, (Type) convertedObject.getClass());
    }

    /**
     * ABOVE is simplified version with Generic Type.
     *
     *    //convertToEntity
     *     public Role convertToEntity(RoleDTO dto){
     *
     *         return modelMapper.map(dto,Role.class);
     *     }
     *
     *     //convertToDto
     *     public RoleDTO convertToDto(Role entity){
     *
     *         return modelMapper.map(entity,RoleDTO.class);
     *     }
     */
}
