package com.networking.skitter.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.networking.skitter.dao.SkitDao;
import com.networking.skitter.domain.Skit;

@Service
@Transactional
public class SkitService {
	@Autowired
	@Qualifier("SkitDaoJdbcImpl")
	private SkitDao skitDao;
	
	public Skit createSkit(Skit skit){
		return skitDao.createSkit(skit);
	}
	
	public List<Skit> getSkitsByUser(int userId){
		return skitDao.getSkitsByUser(userId);
	}
	
	public int skitCount(int userId){
		return skitDao.skitCount(userId);
	}
	
	public List<Skit> getAllSkits(int userId){
		return skitDao.getAllSkits(userId);
	}

}
