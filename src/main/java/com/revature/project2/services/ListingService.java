package com.revature.project2.services;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.project2.models.Listing;
import com.revature.project2.models.User;
import com.revature.project2.repository.ListingRepo;
import com.revature.project2.repository.UserRepo;

@Service
public class ListingService {

	private ListingRepo lDao;
	private UserRepo uDao;

	@Autowired
	public ListingService(ListingRepo l, UserRepo u) {
		this.lDao = l;
		this.uDao = u;
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

	public List<Listing> getListingsBySearchQuery(String query) {
		return lDao.findByTitleContainingOrContentContainingOrCategoryContainingIgnoreCase(query, query, query);
	}
	
	public List<Listing> getListingsByPoster(String username) {
		User u = uDao.findByUsername(username);
		if (u == null)
			return null;
		return lDao.findByPoster(u);
	}
	
	public List<Listing> getListingsByPurchaser(String username) {
		User u = uDao.findByUsername(username);
		if (u == null)
			return null;
		return lDao.findByPurchaser(u);
	}
	
	public List<Listing> getListingsBySearchParams(
			Optional<String> user,
			Optional<String> purchaser,
			Optional<String> query,
			Optional<Integer> id) {
		Set<Listing> listings = new TreeSet<Listing>(new Comparator<Listing>() {
			@Override
			public int compare(Listing a, Listing b) {
				return a.getPosted().compareTo(b.getPosted());
			}
		});
		
		List<Listing> userListings = null, queryListings = null, purchaserListings = null;
		Listing idListing = null;
		
		if (id.isPresent()) {
			idListing = getListingById(id.get().intValue());
			if (idListing == null)
				return new ArrayList<Listing>();
			listings.add(idListing);
		}
		
		if (user.isPresent()) {
			userListings = getListingsByPoster(user.get());
			if (userListings == null)
				return null;
			listings.addAll(userListings);
		}
		
		if (purchaser.isPresent()) {
			purchaserListings = getListingsByPurchaser(purchaser.get());
			if (purchaserListings == null)
				return null;
			listings.addAll(purchaserListings);
		}
		
		if (query.isPresent()) {
			queryListings = getListingsBySearchQuery(query.get());
			listings.addAll(queryListings);
		}
		
		if (userListings != null)
			listings.retainAll(userListings);
		if (purchaserListings != null)
			listings.retainAll(purchaserListings);
		if (queryListings != null)
			listings.retainAll(queryListings);
		
		if (idListing != null) {
			if (listings.contains(idListing)) {
				List<Listing> ret = new ArrayList<Listing>();
				ret.add(idListing);
				return ret;
			}
			return new ArrayList<Listing>();
		}
			
		List<Listing> ret = new ArrayList<Listing>();
		for (Listing l : listings)
			ret.add(l);
		return ret;
	}

}
