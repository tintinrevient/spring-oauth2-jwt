package com.hncy.platform.authorizationserver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.hncy.platform.authorizationserver.repository.UserRepository;
import com.hncy.platform.authorizationserver.domain.User;
import com.hncy.platform.authorizationserver.domain.UserPrincipal;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    public UserService() {
        super();
    }

    @Override
    public UserDetails loadUserByUsername(final String username) {
        final User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return new UserPrincipal(user);
    }

}