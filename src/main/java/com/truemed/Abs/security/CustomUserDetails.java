package com.truemed.Abs.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.truemed.Abs.Entity.CustomUser;

public class CustomUserDetails implements UserDetails {

    private final CustomUser user;

    public CustomUserDetails(CustomUser user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return Collections.emptyList(); // Add roles if needed
    	return null;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

//    @Override
//    public String getUsername() {
//        return user.getEmail(); // Spring uses this as username (even if loaded via mobile)
//    }
    @Override
    public String getUsername() {
        return user.getEmail() != null ? user.getEmail() : user.getMobile();
    }


    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return true; }

    public String getMobile() {
        return user.getMobile();
    }

    public CustomUser getUser() {
        return user;
    }
}

