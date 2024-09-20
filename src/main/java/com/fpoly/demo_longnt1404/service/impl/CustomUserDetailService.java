package com.fpoly.demo_longnt1404.service.impl;

import com.fpoly.demo_longnt1404.model.CustomUserDetails;
import com.fpoly.demo_longnt1404.model.Role;
import com.fpoly.demo_longnt1404.model.User;
import com.fpoly.demo_longnt1404.service.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;

@Service
public class CustomUserDetailService implements UserDetailsService {

    private final UserService userService;

    public CustomUserDetailService(UserService userService) {
        this.userService = userService;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Check user by username in database
        User account = userService.findByUsername(username);
        System.out.println("account: " + account);

        // If user not found, throw exception
        if (account == null) {
            throw new UsernameNotFoundException("User not found");
        }
        // If user found, return CustomUserDetails
        else {
            // Get all roles of user
            Collection<GrantedAuthority> authorities = new HashSet<>();
            // Add roles to authorities collection
            for (Role role : account.getRoles()) {
                authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
            }
            System.out.println("authorities: " + authorities);
            // Return CustomUserDetails
            return new CustomUserDetails(authorities, account.getUsername(), account.getPassword(), account.getFullname(), account.getEmail(), account.getEnabled(), account.getAccountNonExpired(), account.getAccountNonLocked(), account.getCredentialsNonExpired());
        }
    }
}
