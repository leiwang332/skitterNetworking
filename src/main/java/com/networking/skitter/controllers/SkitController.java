package com.networking.skitter.controllers;

import java.util.Date;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.networking.skitter.domain.Skit;
import com.networking.skitter.domain.User;
import com.networking.skitter.service.SkitService;
import com.networking.skitter.service.UserService;

@Controller
public class SkitController {
	@Autowired
	private UserService userService;
	@Autowired
	private SkitService skitService;

	@Inject
	public SkitController(UserService userService, SkitService skitService) {
		this.userService = userService;
		this.skitService = skitService;
	}

	@RequestMapping(value = "/skit", method = RequestMethod.GET)
	public String listSkitsForUser(@RequestParam("User") int userId, Model model) {
		model.addAttribute(skitService.getSkitsByUser(userId));
		return "skitsForUser/list";
	}

	@RequestMapping(value = "/postSkit", method = RequestMethod.POST)
	public String postSkit(ModelMap model, 
			@RequestParam("userId") int userId,
			@RequestParam("skitText") String skitText,
			RedirectAttributes redirectAttributes) {
		try {
			User user = userService.getUserById(userId);
			if (user == null) {
				throw new IllegalArgumentException("Invalid userId: " + userId);
			}
			
			Skit skit = new Skit();
			skit.setAccountId(userId);
			skit.setText(skitText);
			skit.setPostDate(new Date());
			skit = skitService.createSkit(skit);
			
		    redirectAttributes.addFlashAttribute("user", user);
		} catch (IllegalArgumentException e) {
			model.addAttribute("errMsg", e.getMessage());
		}
		return "redirect:home";
	}	
}
