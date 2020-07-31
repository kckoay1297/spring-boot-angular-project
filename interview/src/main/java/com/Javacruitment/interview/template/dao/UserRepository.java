package com.Javacruitment.interview.template.dao;

import com.Javacruitment.interview.template.model.UserEntity;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
interface UserRepository extends JpaRepository<UserEntity, Long> {
	@Query("SELECT u FROM UserEntity u WHERE u.username LIKE %:username%")
	List<UserEntity> searchByUsername(@Param("username") String username);
}
