package com.revature.project2.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.project2.models.Listing;
import com.revature.project2.models.User;
import com.revature.project2.repository.ListingRepo;

@Service
public class HomeService {
	
	private ListingRepo lDao;
	
	@Autowired
	public HomeService(ListingRepo l) {
		this.lDao = l;
	}
	
	public List<Listing> getRecentListings() {
		try {
			return lDao.findFirst10ByOrderByIdDesc();
		} 
		catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<Listing>();
		}	
	}

}
