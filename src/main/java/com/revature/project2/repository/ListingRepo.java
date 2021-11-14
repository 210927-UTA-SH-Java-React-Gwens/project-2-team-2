package com.revature.project2.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.project2.models.Listing;
import com.revature.project2.models.User;

@Repository
public interface ListingRepo extends JpaRepository <Listing, Integer> {
	public Listing findById(int id);
	
	public List<Listing> findByCategory(String category);
	
	public List<Listing> findByTitleContainingOrContentContainingOrCategoryContainingIgnoreCase(String snipit, String snipit2, String snipit3);
	
	public List<Listing> findByContentContaining(String snipit);

	public List<Listing> findFirst10ByOrderByIdDesc();
	
	public List<Listing> findByPoster(User poster);
	
	public List<Listing> findByPurchaser(User purchaser);

}
