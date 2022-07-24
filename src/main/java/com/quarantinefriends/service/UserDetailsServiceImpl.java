package com.quarantinefriends.service;

import com.quarantinefriends.configuration.UserDetailsImpl;
import com.quarantinefriends.dao.UserDao;
import com.quarantinefriends.dto.UserDTO;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDTO user = userDao.findByUsername(username);

        if(user == null){
            throw new UsernameNotFoundException("Username not found!");
        }
        return new UserDetailsImpl(user);
    }
}
