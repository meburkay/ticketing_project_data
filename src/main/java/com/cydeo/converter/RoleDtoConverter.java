package com.cydeo.converter;

import com.cydeo.dto.RoleDTO;
import com.cydeo.service.RoleService;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@ConfigurationPropertiesBinding
public class RoleDtoConverter implements Converter<String, RoleDTO> {

    RoleService roleService;

    //We add here @Lazy annotation. Because we say that we want you to inject RoleService when we use it not at the start of the build. Because if it try to inject it at the start because the bean is not ready it go in to a cycle and give error. When we use lazy annotation it did not inject it directly and wait for the usage of the converter which is when we save the data at the ui part.
    public RoleDtoConverter(@Lazy RoleService roleService) {
        this.roleService = roleService;
    }

    @Override
    public RoleDTO convert(String source) {

        if (source == null || source.equals("")) {  //  Select  -> ""
            return null;
        }

        return roleService.findById(Long.parseLong(source));

    }

}
