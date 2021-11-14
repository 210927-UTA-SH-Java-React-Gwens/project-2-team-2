package com.revature.project2.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.revature.project2.models.Listing;
import com.revature.project2.models.ListingImage;
import com.revature.project2.repository.ListingImageRepo;
import com.revature.project2.repository.ListingRepo;

@Service
public class ListingImageService {
	private ListingImageRepo liDao;
	private ListingRepo lDao;

	@Autowired
	public ListingImageService(ListingImageRepo li, ListingRepo lr) {
		this.liDao = li;
		this.lDao = lr;
	}

	public List<Integer> findIndicesByListingId(int listingId) {
		Listing listing = lDao.findById(listingId);
		if (listing == null)
			return null;
		List<Integer> indices = new ArrayList<Integer>();
		for (ListingImage li : liDao.findByListing(listing))
			indices.add(li.getIndex());
		return indices;
	}

	public ListingImage findImageByListingIdAndIndex(int listingId, int index) {
		Listing listing = lDao.findById(listingId);
		if (listing == null)
			return null;
		return liDao.findByListingAndIndex(listing, index);
	}
	
	public int addImage(int listingId, MultipartFile image) {
		Listing listing = lDao.findById(listingId);
		
		int nextIndex;
		if (liDao.findByListingAndIndex(listing, 0) == null)
			nextIndex = 0;
		
		else {
			ListingImage maxIndex = liDao.findFirstByOrderByIndexDesc();
			nextIndex = (maxIndex == null) ? 0 : (maxIndex.getIndex() + 1);
		}
		
		ListingImage limg = new ListingImage(listing, nextIndex, image);
		listing.addImage(limg);
		liDao.save(limg);
		lDao.save(listing);
		return limg.getIndex();
	}

}
