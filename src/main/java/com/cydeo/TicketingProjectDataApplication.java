package com.cydeo;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TicketingProjectDataApplication {

    public static void main(String[] args) {
        SpringApplication.run(TicketingProjectDataApplication.class, args);
    }

    //To use the third party modelmapper we have to create bean by @Bean annotation because we do not have this class we will take and use it and we can not use stereotype because of that.
    @Bean
    public ModelMapper mapper(){
        return new ModelMapper();
    }

}
