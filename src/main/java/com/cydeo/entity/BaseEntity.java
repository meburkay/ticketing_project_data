package com.cydeo.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

//When we use @Data in the future there might be error ı do not know why. Because of that we use getter and setter individually.
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //We add this for arranging the delete operation. We don't want to delete anything from db. Because of that we create this. The deleted users will become true and the ui part will see only the isDeleted = false ones.
    private Boolean isDeleted = false;
    private LocalDateTime insertDateTime;
    private Long insertUserId;
    private LocalDateTime lastUpdateDateTime;
    private Long lastUpdateUserId;

}
