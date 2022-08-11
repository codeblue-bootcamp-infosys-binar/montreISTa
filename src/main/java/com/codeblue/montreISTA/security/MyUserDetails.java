package com.codeblue.montreISTA.security;

import com.codeblue.montreISTA.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class MyUserDetails implements UserDetails {

    private Long userId;
    private String userName;
    private String password;
    private String email;
    private List<SimpleGrantedAuthority> authorities;

    public MyUserDetails(User user, List<SimpleGrantedAuthority> authorities){
        this.userId = user.getUserId();
        this.userName = user.getUsername();
        this.email = user.getEmail();
        this.authorities = authorities;
        this.password = user.getPassword();
    }

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
        return userName;
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
