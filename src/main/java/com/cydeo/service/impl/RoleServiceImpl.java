package com.cydeo.service.impl;

import com.cydeo.dto.RoleDTO;
import com.cydeo.entity.Role;
import com.cydeo.mapper.RoleMapper;
import com.cydeo.repository.RoleRepository;
import com.cydeo.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {

    //We did the RoleRepository injection to reach to DB from the RoleRepository.
    private final RoleRepository roleRepository;
    //We add this dependency injection to map role entities and dtos.
    private final RoleMapper roleMapper;

    public RoleServiceImpl(RoleRepository roleRepository, RoleMapper roleMapper) {
        this.roleRepository = roleRepository;
        this.roleMapper = roleMapper;
    }

    @Override
    public List<RoleDTO> listAllRoles() {

        //We take all the role entities from db but we must convert it with mappers to RoleDto because we must return RoleDTO for ui. Because of that we add to pom an mapper and used it.
        List<Role> roleList = roleRepository.findAll();

//        return roleList.stream().map(role -> roleMapper.convertToDto(role)).collect(Collectors.toList());
//        return roleList.stream().map(roleMapper::convertToDto).collect(Collectors.toList());

        return roleRepository.findAll().stream().map(roleMapper::convertToDto).collect(Collectors.toList());


    }

    @Override
    public RoleDTO findById(Long id) {
        return null;
    }
}
