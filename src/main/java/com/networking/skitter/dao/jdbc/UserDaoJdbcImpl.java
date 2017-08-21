package com.networking.skitter.dao.jdbc;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.networking.skitter.dao.UserDao;
import com.networking.skitter.domain.Gender;
import com.networking.skitter.domain.User;

/**
 * DAO for User using JdbcTemplate
 * @author Lei
 */
@Repository("UserDaoJdbcImpl")
public class UserDaoJdbcImpl implements UserDao {
	@Autowired
	private DataSource dataSource;
	private JdbcTemplate dbTemplate;
	private SimpleJdbcInsert jdbcInsert;
	
	@PostConstruct
	public void setup() {
		dbTemplate = new JdbcTemplate(dataSource);
		jdbcInsert = new SimpleJdbcInsert(dataSource);
		jdbcInsert.setTableName("skitter_account");
		jdbcInsert.setGeneratedKeyName("account_id");
	}
	
	@Override
	public User createUser(User newUser) {
		Map<String, Object> args = new HashMap<>();
		args.put("account_name", newUser.getAccountName());
		args.put("gender", newUser.getGender() == Gender.Male ? 0 : 1);
		args.put("birthday", newUser.getBirthday());
		args.put("password", newUser.getPassword());
		args.put("email", newUser.getEmail());
		Number userId = jdbcInsert.executeAndReturnKey(args);
		newUser.setAccountId(userId.intValue());
		return newUser;
	}

	@Override
	public int followerCount(int userId) {
		return dbTemplate.queryForObject("select count(*) from relationship where followee = ?", new Object [] {userId}, Integer.class);
	}

	@Override
	public int followingCount(int userId) {
		return dbTemplate.queryForObject("select count(*) from relationship where follower = ?", new Object [] {userId}, Integer.class);
	}

	private User toUser(Map<String, Object> row) {
		User user = new User();
		user.setAccountId((int) row.get("account_id"));
		user.setAccountName((String) row.get("account_name"));
		user.setBirthday((Date) row.get("birthday"));
		user.setEmail((String) row.get("email"));
		user.setGender((int) row.get("gender") == 0 ? Gender.Male : Gender.Female);
		user.setPassword((String) row.get("password"));
		return user;
	}

	@Override
	public List<User> getAllFollowers(int userId) {
		List<Map<String, Object>> results = dbTemplate.queryForList(
				"select U.* from skitter_account U join relationship R on U.account_id = R.follower where R.followee = ?",
				userId);
		
		List<User> users = new ArrayList<>();
		for (Map<String, Object> row : results) {
			users.add(toUser(row));
		}
		
		return users;
	}

	@Override
	public List<User> getAllFollowings(int userId) {
		List<Map<String, Object>> results = dbTemplate.queryForList(
				"select U.* from skitter_account U join relationship R on U.account_id = R.followee where R.follower = ?",
				userId);
		
		List<User> users = new ArrayList<>();
		for (Map<String, Object> row : results) {
			users.add(toUser(row));
		}
		
		return users;
	}

	@SuppressWarnings("unchecked")
	@Override
	public User getUserById(int userId) {
		List<Map<String, Object>> users = dbTemplate.queryForList("select * from skitter_account where account_id = ?", new Object [] {userId});
		if (users.isEmpty()) {
			return null;
		} else {
			return toUser(users.get(0));
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public User getUserByName(String name) {
		List<Map<String, Object>> users = dbTemplate.queryForList("select * from skitter_account where account_name = ?", new Object [] {name});
		if (users.isEmpty()) {
			return null;
		} else {
			return toUser(users.get(0));
		}
	}

	@Override
	public boolean follow(int userId, String followerName) {
		User follower = getUserByName(followerName);
		if (follower == null) {
			throw new IllegalArgumentException("User does not exist, name=" + followerName);
		}
		String sql = String.format("insert into relationship values (%d, %d)", userId, follower.getAccountId());
		dbTemplate.execute(sql);
		return true;
	}
}
