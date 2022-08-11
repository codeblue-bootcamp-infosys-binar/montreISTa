package com.codeblue.montreISTA.security;

import com.codeblue.montreISTA.entity.Role;
import com.codeblue.montreISTA.entity.User;
import com.codeblue.montreISTA.repository.RoleRepository;
import com.codeblue.montreISTA.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

        User user = userRepository.findByUsername(userName)
                .orElseThrow(() -> new UsernameNotFoundException("Not Found " + userName));
        List<Role> userRoles = roleRepository.findByUsersUserUserId(user.getUserId());
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        userRoles.forEach(role ->{
            authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
        });

        return new MyUserDetails(user, authorities);
    }
}
