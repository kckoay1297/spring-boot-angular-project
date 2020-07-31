package com.Javacruitment.interview.template.controller;

import java.util.Arrays;
import java.util.List;

import com.Javacruitment.interview.template.exceptions.UserNotFoundException;
import com.Javacruitment.interview.template.model.UserEntity;
import com.Javacruitment.interview.template.service.UserService;
import lombok.AllArgsConstructor;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@PropertySource("classpath:user.properties")
@ConfigurationProperties
@RestController
@RequestMapping(UserController.BASE_URL)
@AllArgsConstructor
class UserController {

	static final String BASE_URL = "/api/v1/users";

	@Autowired
	private UserService userService;

	@Autowired
	private Environment env;
	
	Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@GetMapping
	public ResponseEntity<List<UserEntity>> getAllUsers() {
		try {
			List<UserEntity> allUsers = userService.getAllUsers();
			return new ResponseEntity<>(allUsers, HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}

	@GetMapping("/search")
	public ResponseEntity<List<UserEntity>> getUsersByUsername(@RequestParam(required=true) String username) {
		try {
			List<UserEntity> allUsers = userService.findByUsername(username);
			return new ResponseEntity<>(allUsers, HttpStatus.OK);
		}catch(UserNotFoundException ue) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}catch(Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@GetMapping("/{userId}")
	public ResponseEntity<UserEntity> getUser(@PathVariable Long userId) throws UserNotFoundException {
		try {
			return new ResponseEntity<>(userService.getUser(userId), HttpStatus.OK);
		}catch(UserNotFoundException userNotFoundException) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}catch(Exception e) {
			e.printStackTrace();

			logger.error(e.getMessage());
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/create")
	public ResponseEntity<UserEntity> createUser(@RequestBody UserEntity userEntity) {
		try {
			String blacklist = env.getProperty("blacklist.user.name");
			if(StringUtils.isNotBlank(blacklist)) {
				String[] blacklistArray = blacklist.split(",");
				if(Arrays.asList(blacklistArray).contains(userEntity.getUsername())) {
					return new ResponseEntity<>(null, HttpStatus.CONFLICT);
				}
			}
			
			return new ResponseEntity<>(userService.createUser(userEntity), HttpStatus.OK);
		}catch(IllegalArgumentException illegalArgumentException) {
			return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
		}catch(Exception e) {
			logger.error(e.getMessage());
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/delete")
	public ResponseEntity<UserEntity> deleteUser(@RequestParam(required=true) long userId) throws UserNotFoundException {
		try {
			return new ResponseEntity<>(userService.deleteUser(userId), HttpStatus.OK);
		}catch(UserNotFoundException UserNotFoundException) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}catch(Exception e) {
			e.printStackTrace();

			logger.error(e.getMessage());
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(method = RequestMethod.HEAD, value = "/{userId}")
	public ResponseEntity<Boolean> checkExists(@PathVariable Long userId) throws UserNotFoundException {
		try {
			userService.checkUserExists(userId);
			return new ResponseEntity<>(true, HttpStatus.OK);
		}catch (Exception e) {
			e.printStackTrace();

			logger.error(e.getMessage());
			return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
