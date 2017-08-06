package com.challenge;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.challenge.domain.User;
import com.challenge.repo.UserRepository;
import com.challenge.service.UserService;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider{

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String name = authentication.getName();
        String password = authentication.getCredentials().toString();
        
        // use the credentials and authenticate against the third-party system
        User checkLogin = userService.findUserByUsernameAndPassword(name, password);
        if(null!=checkLogin){
        	logger.info("Succesful authentication!");
        	return new UsernamePasswordAuthenticationToken(name, password, new ArrayList<>());	
        }
        
        logger.info("Login fail!");
        
        return null;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}
}
