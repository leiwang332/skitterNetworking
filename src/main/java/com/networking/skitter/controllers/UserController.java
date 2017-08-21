package com.networking.skitter.controllers;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.networking.skitter.domain.Gender;
import com.networking.skitter.domain.User;
import com.networking.skitter.service.UserService;

@Controller
public class UserController {
	@Autowired
	private UserService userService;

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String loginGet() {
		return "login";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String loginPost(
			ModelMap model,
			@RequestParam("username") String username,
			@RequestParam("password") String password,
			RedirectAttributes redirectAttributes) {
	
		User user = userService.getUserByName(username);
		
		if (user != null && user.getPassword().equals(password)) {
		    redirectAttributes.addFlashAttribute("user", user);
		    return "redirect:home";
		} else {
		    model.addAttribute("failed", true);
		    return "login";
		}
	}

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String register() {
		return "register";
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String register(ModelMap model, @RequestParam("username") String username,
			@RequestParam("password") String password, @RequestParam("email") String email,
			@RequestParam("gender") String gender, @RequestParam("birthday") String birthday,
			RedirectAttributes redirectAttributes) {
		User user = new User();
		user.setAccountName(username);
		user.setPassword(password);
		try {
			user.setBirthday(new SimpleDateFormat("yyyy-MM-dd").parse(birthday));
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
		user.setEmail(email);
		user.setGender(Gender.valueOf(gender));
		user = userService.createUser(user);
	    redirectAttributes.addFlashAttribute("user", user);

		return "redirect:home";
	}

	@RequestMapping(value = "/follows", method = RequestMethod.GET)
	public String showFollowersGet(ModelMap model, @RequestParam("followers") boolean followers,
			@RequestParam("userId") int userId, @RequestParam("username") String username) {
		List<User> users;
		if (followers) {
			users = userService.getAllFollowers(userId);
			model.addAttribute("isFollower", true);
		} else {
			users = userService.getAllFollowing(userId);
			model.addAttribute("isFollowing", true);
		}
		model.addAttribute("users", users);
		model.addAttribute("username", username);
		return "follows";
	}

	@RequestMapping(value = "/addfollower", method = RequestMethod.POST)
	public String addFollower(ModelMap model, 
			@RequestParam("followerName") String followerName,
			@RequestParam("userId") int userId,
			RedirectAttributes redirectAttributes) {
		try {
			User user = userService.getUserById(userId);
			if (user == null) {
				throw new IllegalArgumentException("Invalid userId: " + userId);
			}
			userService.follow(userId, followerName);
		    redirectAttributes.addFlashAttribute("user", user);
		} catch (IllegalArgumentException e) {
			model.addAttribute("errMsg", e.getMessage());
		}
		return "redirect:home";
	}	
}
