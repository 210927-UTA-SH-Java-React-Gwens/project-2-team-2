package com.revature.project2.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.revature.project2.models.Listing;
import com.revature.project2.services.HomeService;
import com.revature.project2.services.SearchService;
import com.revature.project2.services.UserService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/home")
public class HomeController {
	
	private HomeService hServ;

	@Autowired
	public HomeController(HomeService h) {
		this.hServ = h;
	}



	@GetMapping("")
	public List<Listing> getRecentListings() {
		List<Listing> recent = hServ.getRecentListings();
		System.out.println(recent);
		return recent;		
	}
}

