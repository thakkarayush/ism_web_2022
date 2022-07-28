package com.controller;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.bean.LoginBean;
import com.bean.UserBean;
import com.dao.UserDao;

@Controller
public class SessionController {

	
	@Autowired //ioc => getBean()
	UserDao userDao;   
	// jsp load method - url

	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public String signup(UserBean user, Model model) {

		model.addAttribute("user", user);
		return "Signup"; // Signup -> jsp name
	}

	//
	@RequestMapping(value = "/saveuser", method = RequestMethod.POST)
	public String saveUser(@ModelAttribute("user") @Valid UserBean user, BindingResult result, Model model) {
		System.out.println("SaveUser called.....");
		System.out.println(result);

		if (result.hasErrors()) {// true == error
			model.addAttribute("user", user);
			return "Signup";
		} else {
			 user.setUserType("customer");
			 userDao.addUser(user);
			 model.addAttribute("msg","Signup done..");
			 return "Login";
			 		
		}
	}

	@GetMapping("/login")
	public String login() {
		return "Login";
	}

	@PostMapping("/login")
	public String authenticate(LoginBean login, Model model, HttpSession session) {
		UserBean user = userDao.authenticate(login);
		if (user == null) {
			model.addAttribute("msg", "InvalidCredentials");
			return "Login";
		} else if (user.getUserType().contentEquals("customer")) {
			session.setAttribute("user", user);
			return "Home";
		} else if (user.getUserType().contentEquals("admin")) {
			session.setAttribute("user", user);
			return "Dashboard";
		} else {
			model.addAttribute("msg", "Please Contact Admin");
			return "Login";
		}
	}
	
	@GetMapping("/search")
	public String search(UserBean userBena) {
		return "SearchUser";
	}
	
	@PostMapping("/searchuser")
	public String searchUser(@RequestParam("name") String name, Model model) {
		List<UserBean> users=userDao.getSearchedUser(name);
		model.addAttribute("users",users);
		return "SearchedList";
	}
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/login";
	}
}
