package com.quarantinefriends.configuration;

import com.quarantinefriends.dto.RoleDTO;
import com.quarantinefriends.dto.UserDTO;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserDetailsImpl implements UserDetails {
    private UserDTO user;

    public UserDetailsImpl(UserDTO user){
        this.user = user;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<RoleDTO>roles = new ArrayList<>();
        roles.add(user.getRole());
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for (RoleDTO theRole : roles) {
            authorities.add(new SimpleGrantedAuthority(theRole.getName()));
        }
        return authorities;
    }
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
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
