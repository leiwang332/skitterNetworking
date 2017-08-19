package com.networking.skitter.dao.jdbc;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.networking.skitter.dao.SkitDao;
import com.networking.skitter.domain.Skit;

@Repository("SkitDaoJdbcImpl")
public class SkitDaoJdbcImpl implements SkitDao {
	@Autowired
	private DataSource dataSource;
	private JdbcTemplate dbTemplate;
	private SimpleJdbcInsert jdbcInsert;

	@PostConstruct
	public void setup() {
		dbTemplate = new JdbcTemplate(dataSource);
		jdbcInsert = new SimpleJdbcInsert(dataSource);
	}
	
	@Override
	public Skit createSkit(Skit skit) {
		Map<String, Object> args = new HashMap<>();
		args.put("account_id", skit.getAccountId());
		args.put("post_date", skit.getPostDate());
		args.put("text", skit.getText());
		Number skitId = jdbcInsert.executeAndReturnKey(args);
		skit.setSkitId(skitId.intValue());
		return skit;
	}
	
	private Skit toSkit(Map<String, Object> row) {
		Skit skit = new Skit();
		skit.setSkitId((int) row.get("skit_it"));
		skit.setAccountId((int) row.get("account_id"));
		skit.setPostDate((Date) row.get("post_date"));
		skit.setText((String) row.get("text"));
		return skit;
	}
	
	@Override
	public List<Skit> getSkitsByUser(int userId) {
		List<Map<String, Object>> results = dbTemplate.queryForList(
				"select S.* from skit S inner join skitter_account U on S.account_id = U.account_id where U.account_id = *",
				userId);

		List<Skit> skits = new ArrayList<>();
		for (Map<String, Object> row : results) {
			skits.add(toSkit(row));
		}
		
		return skits;
	}

	@Override
	public int skitCount(int userId) {
		return dbTemplate.queryForObject("select count(*) from skits where account_id = ?", new Object [] {userId}, Integer.class);
	}

	@Override
	public List<Skit> getAllSkits(int userId) {
		List<Map<String, Object>> results = dbTemplate.queryForList(
				"select S.* from skit S "
				+ "inner join skitter_account U on S.account_id = U.account_id "
				+ "inner join relationship R on U.account_id = R.followee "
				+ "where R.follower = *",
				userId);

		List<Skit> skits = new ArrayList<>();
		for (Map<String, Object> row : results) {
			skits.add(toSkit(row));
		}
		
		return skits;
	}

}
