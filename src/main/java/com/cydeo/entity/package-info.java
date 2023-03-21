
/**
 * <p>
 * @Entity:
 * Entities in JPA are nothing but POJOs representing data that can be persisted to the
 * database
 * An entity represents a table stored in a database
 * Every instance of an entity represents a row in the table
 * Entity classes must not be declared final
 * @Id:
 * Each JPA entity must have a primary key which uniquely identifies it. @Id annotation defines the primary key
 * @Column annotation is used to mention the details of a column in the table
 * @Transient annotation is used to make a field non-persistent
 * @Enumerated annotation is used to persist Java Enum type. If you dont configure the annotation with EnumType.STRING then it will read value as integer (0, 1)
 * @MappedSuperclass annotation is used to allow an entity to inherit properties from a base class
 * </p>
 */


package com.cydeo.entity;