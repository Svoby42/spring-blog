package com.example.springblog.security;

import com.example.springblog.entities.Role;
import com.example.springblog.entities.User;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserPrincipal implements UserDetails {

    private String id;
    private String username;
    transient private String password;
    transient private User user;
    private Set<GrantedAuthority> authoritySet;

    public static UserPrincipal createSuperUser(){
        Set<GrantedAuthority> authorities = Set.of(SecurityUtils.convertToAuthority(Role.SYSTEM_MANAGER.name()));

        return UserPrincipal.builder()
                .id(String.valueOf(UUID.randomUUID()))
                .username("system-administrator")
                .authoritySet(authorities)
                .build();
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authoritySet;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
