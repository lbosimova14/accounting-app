/**
 * UserPrincipal class is conversion from my User Entity to Spring User
 * Repository(DB) -> My User -> UserPrincipal -> Spring User -> UserDetail (Service)
 * some curtain filed we need to provide to Spring User: username, password, authorities, accountNonExpired, accountNonLocked, credeialsNonExpired, enabled (registering time if enable true then it sends to email verification)
 *
 * implements UserDetails class,
 *
 *
 */

package com.cydeo.entity.common;