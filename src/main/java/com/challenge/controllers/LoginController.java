package com.challenge.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.challenge.domain.User;
import com.challenge.service.UserService;

@RestController
@RequestMapping("api")
@CrossOrigin(origins = "*")
public class LoginController {

    private final UserService userService;
	
    @Autowired
    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(
            value = "/login",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin(origins = "*")
    public ResponseEntity<?> login(@RequestBody User user) {
        if (StringUtils.isEmpty(user.getUsername()) || StringUtils.isEmpty(user.getPassword())) {
            return new ResponseEntity<>(new User(), HttpStatus.OK);
        }
        if (userService.findUserByUsernameAndPassword(user.getUsername(), user.getPassword())==null){
            return new ResponseEntity<>(new User(), HttpStatus.OK);
        }
        return new ResponseEntity<>(userService.findUserByUsername(user.getUsername()), HttpStatus.OK);
    }

	public void fillData() {
		userService.fillData();
	}
}
