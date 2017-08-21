package com.networking.skitter.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.networking.skitter.dao.UserDao;
import com.networking.skitter.domain.User;

@Service
@Transactional
public class UserService {
	@Autowired
	@Qualifier("UserDaoJdbcImpl")
	private UserDao userDao;

	public User getUserById(int userId) {
		return userDao.getUserById(userId);
	}
	
	public User getUserByName(String name) {
		return userDao.getUserByName(name);
	}
	
	public User createUser(User newUser){
		return userDao.createUser(newUser);
	}
	
	public int followerCount(int userId){
		return userDao.followerCount(userId);
	}
	
	public int followingCount(int userId){
		return userDao.followingCount(userId);
	}
	
	public List<User> getAllFollowers(int userId){
		return userDao.getAllFollowers(userId);
	}
	
	public List<User> getAllFollowing(int userId){
		return userDao.getAllFollowings(userId);
	}
	
	public boolean follow(int userId, String followerName) {
		return userDao.follow(userId, followerName);
	}
}
