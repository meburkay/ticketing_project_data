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
    //this annotations created because when we do operation update it overwrite the data. But when we use this annotations when update they did not affected from it. insert time and insert userid must be initialize for once. it must not change. Because of that we put updatable = false here otherwise it reseted.
    @Column(nullable = false,updatable = false)
    private LocalDateTime insertDateTime;
    @Column(nullable = false,updatable = false)
    private Long insertUserId;
    @Column(nullable = false)
    private LocalDateTime lastUpdateDateTime;
    @Column(nullable = false)
    private Long lastUpdateUserId;

    //this method executed automatically when we save by this annotation
    @PrePersist
    private void onPrePersist(){
        this.insertDateTime = LocalDateTime.now();
        this.lastUpdateDateTime=LocalDateTime.now();
        this.insertUserId=1L;
        this.lastUpdateUserId=1L;
    }


    //this method executed automatically when we update data by this annotation.
    @PreUpdate
    private void onPreUpdate(){
        this.lastUpdateDateTime=LocalDateTime.now();
        this.lastUpdateUserId=1L;
    }



}
