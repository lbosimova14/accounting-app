package com.cydeo.entity;

import lombok.*;

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

    //define primary key, use @Id annotation
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, updatable = false)
    public LocalDateTime insertDateTime;
    @Column(nullable = false, updatable = false)
    public Long insertUserId;
    @Column(nullable = false)
    public LocalDateTime lastUpdateDateTime;
    @Column(nullable = false)
    public Long lastUpdateUserId;

    private Boolean isDeleted = false;
}
