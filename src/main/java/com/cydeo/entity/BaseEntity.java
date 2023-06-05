package com.cydeo.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * There are 2 way to create table, 1st with hybernate using @Entity, and 2nd with schema2.sql
 * create data in datatable, Entity is for table creation
 */

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@MappedSuperclass
@EntityListeners(BaseEntityListener.class)
public class BaseEntity implements Serializable {

    //nullable = false, means keep the same value, when you update some data, the insertDateTime becoming overwrite and null
    @Column(nullable = false, updatable = false)
    public LocalDateTime insertDateTime;
    @Column(nullable = false, updatable = false)
    public Long insertUserId; //who inserted data
    //    dont put  updatable = false in lastUpdateDateTime, bc we need to see the updated changes
    @Column(nullable = false)
    public LocalDateTime lastUpdateDateTime;
    @Column(nullable = false)
    public Long lastUpdateUserId;
    //define primary key, use @Id annotation
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //postgress will generate id/primary key
    private Long id;
    //dont delete data in database, change the flag to true only, it only deleted in ui, but data is kept in database
    private Boolean isDeleted = false;
}
