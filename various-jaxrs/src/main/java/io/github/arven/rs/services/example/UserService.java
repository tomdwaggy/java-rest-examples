/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.arven.rs.services.example;

import java.util.Arrays;
import javax.inject.Inject;
import javax.inject.Named;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * @author Brian Becker
 */
@Named
public class UserService implements UserDetailsService {
    
    @Inject private MicroBlogService blogService;

    @Override
    public UserDetails loadUserByUsername(String string) throws UsernameNotFoundException {
        Person data = blogService.getUser(string);
        if(data != null) {
            return new User(data.getId(), data.getPassword(), Arrays.asList(new SimpleGrantedAuthority("User")));
        } else {
            throw new UsernameNotFoundException("Name not found!");
        }
    }    
    
}