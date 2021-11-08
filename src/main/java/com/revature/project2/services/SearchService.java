package com.revature.project2.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.project2.models.Listing;
import com.revature.project2.repository.ListingRepo;
import com.revature.project2.repository.UserRepo;

@Service
public class SearchService {
	private ListingRepo lDao;
	private UserRepo uDao;
	
	@Autowired
	public SearchService(ListingRepo lr, UserRepo ur) {
		this.lDao = lr;
		this.uDao = ur;
	}
	
	public List<Listing> getListingsByCategory(String category) {
		return lDao.findByCategory(category);
	}
	
	public List<Listing> getListingsBySearchQuery(String query) {
		List<Listing> fullSearch = new ArrayList<Listing>(lDao.findByTitleContaining(query));
		fullSearch.addAll(lDao.findByContentContaining(query));
		return fullSearch;
	}

}
