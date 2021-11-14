package com.revature.project2.controllers;

import java.io.ByteArrayInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.util.List;

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

import com.revature.project2.models.ListingImage;
import com.revature.project2.services.ListingImageService;

@RestController
@CrossOrigin
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
	
	@GetMapping("")
	public ResponseEntity<byte[]> getImage(@RequestParam int listing, @RequestParam int index) {
		ListingImage li = liServ.findImageByListingIdAndIndex(listing, index);

		if (li == null)
			return new ResponseEntity<byte[]>(new byte[0], HttpStatus.NOT_FOUND);

		return ResponseEntity.ok()
				.contentType(MediaType.parseMediaType(li.getFtype()))
				.body(li.getImg());
	}
	
}
