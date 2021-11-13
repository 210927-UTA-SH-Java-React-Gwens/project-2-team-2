package com.revature.project2.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.project2.models.Listing;
import com.revature.project2.repository.ListingRepo;

@Service
public class ListingService {
	
private ListingRepo lDao;
	
	@Autowired
	public ListingService(ListingRepo l) {
		this.lDao = l;
	}
	
	public Listing getListingById(int id) {
		return lDao.findById(id);
	}
	
	public Listing createListing(Listing listing) {
		return lDao.save(listing);
	}
	
	public List<Listing> getListingsByCategory(String category) {
		return lDao.findByCategory(category);
	}
	
	public List<Listing> getRecentListings() {
		return lDao.findFirst10ByOrderByIdDesc();
	}
}
