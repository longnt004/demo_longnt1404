package com.fpoly.demo_longnt1404.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomUserDetails implements UserDetails {
    // Collection<? extends GrantedAuthority> authorities: Collection of permissions granted to users
    private Collection<? extends GrantedAuthority> authorities;
    private String username;
    private String password;
    @Getter
    private String fullname;
    @Getter
    private String email;
    @Getter
    // enabled = true: account has been activated
    private Boolean enabled;
    // accountNonExpired = true: account has not expired
    private Boolean accountNonExpired;
    // accountNonLocked = true: account has not been locked
    private Boolean accountNonLocked;
    // credentialsNonExpired = true: logon session has not expired
    private boolean credentialsNonExpired;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
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
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
