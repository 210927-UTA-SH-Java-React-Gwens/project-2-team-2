package com.revature.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

import static org.mockito.ArgumentMatchers.any;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import com.revature.project2.models.Listing;
import com.revature.project2.models.ListingImage;
import com.revature.project2.repository.ListingImageRepo;
import com.revature.project2.repository.ListingRepo;
import com.revature.project2.repository.UserRepo;
import com.revature.project2.services.ListingImageService;
import com.revature.project2.services.ListingService;

public class ListingImages {

	private ListingImageRepo lIDao;
	private ListingRepo lDao;
	private ListingImageService lIServ;
	

	@BeforeEach
	void setup() {
		lIDao = mock(ListingImageRepo.class);
		lDao = mock(ListingRepo.class);
		lIServ = new ListingImageService(lIDao,lDao);
	}
	
	@Test
	void findIndicesByListingIdTest() {
		Listing list = new Listing(1, 400, null, "My listing", "content", null, null, null, null, "Electric");
		List<ListingImage> listingImages = new ArrayList<ListingImage>();
		listingImages.add(new ListingImage(1,"Any image".getBytes(),2,"jpg",list));
		ArrayList<Integer> index = new ArrayList<Integer>();
		index.add(1);
		when(lDao.findById(list.getId())).thenReturn(list);
		when(lIDao.findByListing(list)).thenReturn(listingImages);
		
		List<Integer> indexes = lIServ.findIndicesByListingId(1);
		
		
		assertThat(indexes.size()).isGreaterThan(0);
		
	}
	
	@Test
	void findIndicesByListingIdTestFail() {
		Listing list = new Listing(1, 400, null, "My listing", "content", null, null, null, null, "Electric");
		List<ListingImage> listingImages = new ArrayList<ListingImage>();
		listingImages.add(new ListingImage(1,"Any image".getBytes(),2,"jpg",list));
		ArrayList<Integer> index = new ArrayList<Integer>();
		index.add(1);
		when(lDao.findById(list.getId())).thenReturn(null);
		when(lIDao.findByListing(list)).thenReturn(listingImages);
		
		List<Integer> indexes = lIServ.findIndicesByListingId(1);
		
		
		assertThat(indexes).isEqualTo(null);
	}
	
	@Test
	void findImageByListingIdAndIndexTest() {
		Listing list = new Listing(1, 400, null, "My listing", "content", null, null, null, null, "Electric");
		List<ListingImage> listingImages = new ArrayList<ListingImage>();
		listingImages.add(new ListingImage(1,"Any image".getBytes(),2,"jpg",list));
		ArrayList<Integer> index = new ArrayList<Integer>();
		index.add(1);
		when(lDao.findById(list.getId())).thenReturn(list);
		when(lIDao.findByListingAndIndex(list,1)).thenReturn(new ListingImage(1,"Any image".getBytes(),2,"jpg",list));
		
		ListingImage lI = lIServ.findImageByListingIdAndIndex(1,1);
		
		
		assertThat(lI).isNotEqualTo(null);
		
	}
	
	@Test
	void findImageByListingIdAndIndexTestFail() {
		Listing list = null;
		List<ListingImage> listingImages = new ArrayList<ListingImage>();
		listingImages.add(new ListingImage(1,"Any image".getBytes(),2,"jpg",list));
		ArrayList<Integer> index = new ArrayList<Integer>();
		index.add(1);
		when(lDao.findById(0)).thenReturn(list);
		when(lIDao.findByListingAndIndex(list,1)).thenReturn(new ListingImage(1,"Any image".getBytes(),2,"jpg",list));
		
		ListingImage lI = lIServ.findImageByListingIdAndIndex(1,1);
		
		
		assertThat(lI).isEqualTo(null);
		
	}
	
	@Test
	void addImageTest() {
		Listing list = new Listing(1, 400, null, "My listing", "content", null, null, null, new ArrayList<>(), "Electric");
		List<ListingImage> listingImages = new ArrayList<ListingImage>();
		listingImages.add(new ListingImage(1,"Any image".getBytes(),2,"jpg",list));
		ArrayList<Integer> index = new ArrayList<Integer>();
		index.add(1);
		when(lDao.findById(list.getId())).thenReturn(list);
		when(lIDao.findByListingAndIndex(list,0)).thenReturn(new ListingImage(1,"Any image".getBytes(),2,"jpg",list));
		when(lIDao.findFirstByOrderByIndexDesc()).thenReturn(new ListingImage(2,"Any image".getBytes(),2,"jpg",list));
		
		when(lDao.save(any())).thenReturn(list);
		when(lIDao.save(any())).thenReturn(new ListingImage(1,"Any image".getBytes(),2,"jpg",list));
		
		int lI = lIServ.addImage(1,new MockMultipartFile("Hello","Im", "testing", "anyimage".getBytes()));
		
		assertThat(lI).isGreaterThan(0);
		
	}
	
	
}
