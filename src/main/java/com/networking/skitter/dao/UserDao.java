package com.networking.skitter.dao;
import java.util.List;
import com.networking.skitter.domain.*;

public interface UserDao {
	public User createUser(User newUser);
	public int followerCount(int userId);
	public int followingCount(int userId);
	public List<User> getAllFollowers(int userId);
	public List<User> getAllFollowings(int userId);
}
