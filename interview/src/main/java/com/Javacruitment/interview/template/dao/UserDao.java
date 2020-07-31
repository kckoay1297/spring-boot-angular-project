package com.Javacruitment.interview.template.dao;

import java.util.List;
import java.util.Optional;

import com.Javacruitment.interview.template.exceptions.UserNotFoundException;
import com.Javacruitment.interview.template.model.UserEntity;

import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

@Repository
@AllArgsConstructor
public class UserDao {
	
	@Autowired
	private UserRepository userRepository;

	public List<UserEntity> findAll() {
		return userRepository.findAll();
	}

	public Optional<UserEntity> find(long id) {
		return userRepository.findById(id);
	}

	public List<UserEntity> findByUsername(String username) throws UserNotFoundException{
		List<UserEntity> userList = userRepository.searchByUsername(username);
		if(CollectionUtils.isEmpty(userList)) {
			throw new UserNotFoundException("User with username " + username + " does not exist.");
		}
		return userList;
	}
	
	public UserEntity findOrDie(long id) throws UserNotFoundException {
		return find(id).orElseThrow(() -> new UserNotFoundException("User with id " + id + " does not exist."));
	}

	public void checkExists(long id) throws UserNotFoundException {
		if (!userRepository.existsById(id)) {
			throw new UserNotFoundException("User with id " + id + " does not exist.");
		}
	}

	public UserEntity create(UserEntity user) {
		if (user.getId() != null) {
			throw new IllegalArgumentException("User already exists.");
		}
		
		return userRepository.save(user);
	}
	
	public UserEntity delete(long id) throws UserNotFoundException {
		find(id).orElseThrow(() -> new UserNotFoundException("User with id " + id + " does not exist."));
		UserEntity deleteUserEntity = userRepository.getOne(id);
		
		userRepository.delete(deleteUserEntity);
		return deleteUserEntity;
	}
}
