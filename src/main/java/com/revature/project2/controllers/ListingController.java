package com.revature.project2.controllers;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.revature.project2.models.Listing;
import com.revature.project2.services.ListingService;

@RestController
@RequestMapping(value="/listing")
public class ListingController {
	
	private ListingService lServ;

	@Autowired
	public ListingController(ListingService l) {
		this.lServ = l;
	}
	
	@GetMapping("")
	public Listing getListingById(@RequestParam int id) {
		return lServ.getListingById(id);
	}
	
	@PostMapping("")
	public ResponseEntity<Listing> createListing(@RequestBody Listing listing, HttpSession session) {
		if (session.getAttribute("user") == null)
			return new ResponseEntity<Listing>(new Listing(), HttpStatus.FORBIDDEN);
		return new ResponseEntity<Listing>(lServ.createListing(listing), HttpStatus.CREATED);
	}

}
