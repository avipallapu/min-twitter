package com.challenge;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private CustomAuthenticationProvider authProvider;

	@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authProvider);
    }
	
	@Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
        	.antMatchers("/h2-console/*").permitAll()
        	.anyRequest().fullyAuthenticated();
        
        http.httpBasic();
        http.csrf().disable();
        http.headers().frameOptions().disable();
    }
	
	/*
	 * This encoder should be used for protecting the password saved in database
	 * For demo purposes, this has been commented out
	 * @Bean
	public PasswordEncoder passwordEncoder() {

	    PasswordEncoder encoder = new BCryptPasswordEncoder();
	    return encoder;
	}*/
	
}