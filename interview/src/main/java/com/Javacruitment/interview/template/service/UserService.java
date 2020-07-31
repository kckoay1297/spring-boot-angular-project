package com.Javacruitment.interview.template.service;

import com.Javacruitment.interview.template.dao.UserDao;
import com.Javacruitment.interview.template.exceptions.UserNotFoundException;
import com.Javacruitment.interview.template.model.UserEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class UserService {

	@Autowired
	private UserDao userDao;

	public List<UserEntity> getAllUsers() {
		return userDao.findAll();
	}

	public List<UserEntity> findByUsername(String username) throws UserNotFoundException{
		return userDao.findByUsername(username);
	}
	
	public UserEntity getUser(long id) throws UserNotFoundException {
		return userDao.findOrDie(id);
	}

	public void checkUserExists(long id) throws UserNotFoundException {
		userDao.checkExists(id);
	}
	
	public UserEntity createUser(UserEntity user){
		return userDao.create(user);
	}
	
	public UserEntity deleteUser(long id) throws UserNotFoundException {
		return userDao.delete(id);
	}
}
