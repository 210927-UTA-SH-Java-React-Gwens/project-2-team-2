package com.revature.project2.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.revature.project2.models.Listing;
import com.revature.project2.models.ListingImage;
import com.revature.project2.services.ListingService;

@RestController
@CrossOrigin
@RequestMapping(value = "/listing")
public class ListingController {

	private ListingService lServ;

	@Autowired
	public ListingController(ListingService l) {
		this.lServ = l;
	}
	
	@GetMapping("/recent")
	public ResponseEntity<List<Listing>> getRecentListings() {
		List<Listing> recent = lServ.getRecentListings();
		return new ResponseEntity<List<Listing>>(recent,
				recent == null
					? HttpStatus.INTERNAL_SERVER_ERROR
					: recent.size() == 0
						? HttpStatus.NO_CONTENT
						: HttpStatus.OK);
	}

	/**
	 * @param id
	 * @return
	 */
	@GetMapping(value = "")
	public ResponseEntity<Listing> getListingById(@RequestParam int id) {
		Listing l = lServ.getListingById(id);
		return new ResponseEntity<Listing>(l, l == null ? HttpStatus.NOT_FOUND : HttpStatus.OK);
	}

	/**
	 * 
	 * @param title    Title of the new listing
	 * @param desc     Description of the new listing
	 * @param category Category of the new listing
	 * @param price    Price of the new listing represented by an int - 1 = $.01,
	 *                 100 = $1
	 * @param images   All of the images for the new listing
	 * @param session  Current HTTP session to verify a loged-in user is sending the
	 *                 request
	 * @return Response entity containing ID of created listing and HTTP response
	 *         code
	 */
	@PostMapping(value = "/new", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	public Listing createListing(
			@RequestParam String title,
			@RequestParam String desc,
			@RequestParam String category,
			@RequestParam int price,
			@RequestParam List<MultipartFile> images,
			HttpSession session) {

		System.out.println("---- NEW LISTING REQUEST ----");
		System.out.println("Title: " + title);
		System.out.println("Description: " + desc);
		System.out.println("Category: " + category);
		System.out.println(String.format("Price: $%d.%02d", price / 100, price % 100));
		System.out.println("Files:");
		for (MultipartFile file : images)
			System.out.println("  " + file.getOriginalFilename());
		System.out.println("-----------------------------");
		
		Listing listing = new Listing(0, price, null, title, desc, new Date(), null, null, new ArrayList<ListingImage>(), category);
		
		ListingImage limg;
		for (int i = 0; i < images.size(); i++) {
			try {
				limg = new ListingImage(listing, i, images.get(i));
				listing.addImage(limg);
			} catch (IOException e) {
				return new Listing();
			}
		}
		
		return lServ.createListing(listing);
	}
}
