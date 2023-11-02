package com.cydeo.entity;

import com.cydeo.enums.Gender;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "users")
//@Where(clause = "is_deleted=false")//this is very important. When we add this it add this where clause to every method when taking data from db consist of the jpa in build methods too. When we retrieving data from database about user we only take the is_deleted = false ones.
public class User extends BaseEntity {

    private String firstName;
    private String lastName;
    private String userName;
    private String passWord;
    private boolean enabled;
    private String phone;

    //We need role at the user table only. We can not put user to role table because when we do that spring have to create third table to define it and it is unlogical.
    @ManyToOne
    private Role role;

    @Enumerated(EnumType.STRING)
    private Gender gender;


}
