package com.revature.project2.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.revature.project2.models.ListingImage;
import com.revature.project2.services.ListingImageService;

@RestController
@RequestMapping(value="/listing-image")
public class ListingImageController {

	private ListingImageService liServ;

	@Autowired
	public ListingImageController(ListingImageService li) {
		this.liServ = li;
	}
	
	@GetMapping("/cnt")
	public List<Integer> getImageIndices(@RequestParam int listingId) {
		return liServ.findIndicesByListingId(listingId);
	}
	
	/**
	 * Get a listing image
	 * @param listing 
	 * @param index 
	 * @return
	 */
	@GetMapping("")
	public ListingImage getListingImage(@RequestParam int listing, @RequestParam int index) {
		return liServ.findImageByListingIdAndIndex(listing, index);
	}
	
}
