package com.thesis.gamamicroservices.userservice.security;

import com.thesis.gamamicroservices.userservice.model.Account;
import com.thesis.gamamicroservices.userservice.model.User;
import com.thesis.gamamicroservices.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByAccount_Email(username);
        if (user.isPresent()) {
            return new org.springframework.security.core.userdetails.User(user.get().getAccount().getEmail(), user.get().getAccount().getPassword(), getAuthority(user.get().getAccount()));
        } else {
            throw new UsernameNotFoundException("Username not found.");
        }
    }

    private Set<SimpleGrantedAuthority> getAuthority(Account acc) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + acc.getRole()));
        return authorities;
    }
}
