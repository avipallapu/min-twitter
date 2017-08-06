package com.challenge;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.challenge.service.UserService;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private CustomAuthenticationProvider authProvider;
	
	private final static String CLIENT_URL = "http://localhost:4200";
	
//	@Bean
//	public WebMvcConfigurer corsConfigurer() {
//		return new WebMvcConfigurerAdapter() {
//			@Override
//			public void addCorsMappings(CorsRegistry registry) {
//				registry.addMapping("api/**").allowedOrigins(CLIENT_URL);
//			}
//		};
//	}
	
//	protected void configure(HttpSecurity http) throws Exception {
//      http.authorizeRequests()
//          .antMatchers("/h2-console/*").permitAll();
//
//      http.csrf().disable();
//      http.headers().frameOptions().disable();
//	}
//	
	@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authProvider);
    }
	
	// USE this while submitting 
	@Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
        	.antMatchers("/h2-console/*").permitAll()
        	.anyRequest().fullyAuthenticated();
        
        http.httpBasic();
        http.csrf().disable();
        http.headers().frameOptions().disable();
    }
	
	@Bean
	public PasswordEncoder passwordEncoder() {

	    PasswordEncoder encoder = new BCryptPasswordEncoder();
	    return encoder;
	}
	
}