package com.revature.project2.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.revature.project2.models.Listing;
import com.revature.project2.services.SearchService;

@RestController
@RequestMapping(value = "/search")
public class SearchController {
	private SearchService sServ;

	@Autowired
	public SearchController(SearchService sServ) {
		super();
		this.sServ = sServ;
	}



	@GetMapping("")
	public List<Listing> getListingsByCategory(@RequestParam String category) {
		return sServ.getListingsByCategory(category);
	}
}
