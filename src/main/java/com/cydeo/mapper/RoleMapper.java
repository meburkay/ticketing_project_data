package com.cydeo.mapper;

import com.cydeo.dto.RoleDTO;
import com.cydeo.entity.Role;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
//We create mapper package and RoleMapper class to use as mapper for Role entities and DTOs.
@Component
public class RoleMapper {

    private ModelMapper modelMapper;

    public RoleMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Role convertToEntity(RoleDTO dto){
        return modelMapper.map(dto,Role.class);

    }

    public RoleDTO convertToDto(Role entity){
        return modelMapper.map(entity,RoleDTO.class);
    }
}
