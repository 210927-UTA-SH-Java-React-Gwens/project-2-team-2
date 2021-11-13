package com.revature.project2.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.project2.models.Listing;
import com.revature.project2.models.ListingImage;

@Repository
public interface ListingImageRepo extends JpaRepository <ListingImage, Integer>{
	public List<ListingImage> findByListing(Listing li_id);
	public ListingImage findByListingAndIndex(Listing l, int i);
}
