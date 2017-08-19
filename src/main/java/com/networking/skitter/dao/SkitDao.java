package com.networking.skitter.dao;

import java.util.List;
import com.networking.skitter.domain.*;

public interface SkitDao {
	Skit createSkit(Skit skit);
	List<Skit> getSkitsByUser(int userId); // list the skits of one user
	int skitCount(int userId); // the number of skits a user posts
	List<Skit> getAllSkits(int userId); // list all the skits of a user's followings
}
