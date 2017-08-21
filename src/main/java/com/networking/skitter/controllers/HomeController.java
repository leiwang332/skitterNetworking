package com.networking.skitter.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.networking.skitter.domain.Skit;
import com.networking.skitter.domain.User;
import com.networking.skitter.service.SkitService;
import com.networking.skitter.service.UserService;

@Controller
public class HomeController {
	@Autowired
	private UserService userService;
	@Autowired
	private SkitService skitService;
	
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String showHome(ModelMap model, @ModelAttribute("user") User user) {
		int userId = user.getAccountId();
		List<Skit> skits = skitService.getSkitsByUser(userId);
		int followerCount = userService.followerCount(userId);
		int followingCount = userService.followingCount(userId);
		
		model.addAttribute("userId", user.getAccountId());
		model.addAttribute("username", user.getAccountName());
		model.addAttribute("followerCount", followerCount);
		model.addAttribute("followingCount", followingCount);
		model.addAttribute("skits", skits);

		return "home";
	}
}
