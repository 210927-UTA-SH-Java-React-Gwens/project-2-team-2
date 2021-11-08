package com.revature.project2.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.revature.project2.models.User;
import com.revature.project2.services.UserService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value="/user")
public class UserController {

	private UserService uServ;

	@Autowired
	public UserController(UserService u) {
		this.uServ = u;
	}
	
	@GetMapping("/")
	public List<User> getAll(){
		return uServ.getAllUsers();
	}
	
	@PostMapping("/create-user")
	public User createUser(@RequestBody User u) {
		System.out.println(u);
		return uServ.createUser(u);
	}
	
	@CrossOrigin(origins = "*")
	@GetMapping("/get-user")
	public User getUser(@RequestParam("id") int id) {
		System.out.println(id);
		return uServ.getUser(id);
	}
	
	@PostMapping("/login")
	public User login(@RequestBody User u) {
		System.out.println(u);
		return uServ.login(u);
	}
	
}
