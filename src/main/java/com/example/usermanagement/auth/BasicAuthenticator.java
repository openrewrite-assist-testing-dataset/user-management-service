package com.example.usermanagement.auth;

import com.example.usermanagement.core.User;
import com.example.usermanagement.db.UserDAO;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.basic.BasicCredentials;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

public class BasicAuthenticator implements Authenticator<BasicCredentials, User> {
    
    private final UserDAO userDAO;
    private final BCryptPasswordEncoder passwordEncoder;

    public BasicAuthenticator(UserDAO userDAO) {
        this.userDAO = userDAO;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public Optional<User> authenticate(BasicCredentials credentials) throws AuthenticationException {
        Optional<User> user = userDAO.findByUsername(credentials.getUsername());
        
        if (user.isPresent() && passwordEncoder.matches(credentials.getPassword(), user.get().getPasswordHash())) {
            return user;
        }
        
        return Optional.empty();
    }
}