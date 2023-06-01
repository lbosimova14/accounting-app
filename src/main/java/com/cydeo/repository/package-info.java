/**
 * The Repository level, retrieves the data from database. it contact with database and Entity. It called DAO layer ->  Data Access Object
 * Spring is providing crud JpaRepository interface. ( extends JpaRepository<User, Long>) 2 thing is need: Entity name(User) and Primary key(Long)
 *
 *
 * Spring Data JPA allows you to execute different kinds of queries to retrieve data from database. You can either use the method name to derive a query directly,
 * or manually define your own JPQL/Native query using the @Query annotation.
 *
 * Derived Queries --> Spring Data comes with the Query Builder mechanism for JPA and it’s capable of
 * interpreting a query method name(derived query-the query derived from the
 * method name) and converting it into an SQL statement. This is possible as long as you follow the naming conventions of this
 * mechanism.
 * eg: List<String> findByLastName(String lastName);
 *
 * JPQL --> The Java Persistence Query Language(JPQL) is a platform-independent object oriented query language defined as part of the JPA specification. JPQL
 * is used to make queries against entities stored in a relational database. It is heavily inspired by SQL, and its queries resemble SQL queries in syntax, but
 * operate against JPA entity objects rather than directly with database tables.
 *
 * eg: @Query("SELECT e FROM Employee e WHERE e.email='amcnee1@google.es'") Employee getEmployeeDetail();
 */
package com.cydeo.repository;