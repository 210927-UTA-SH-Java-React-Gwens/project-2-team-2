package com.revature.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.bind.annotation.RequestParam;

import com.revature.project2.models.Listing;
import com.revature.project2.models.User;
import com.revature.project2.repository.ListingRepo;
import com.revature.project2.repository.UserRepo;
import com.revature.project2.services.ListingService;
import com.revature.project2.services.UserService;

public class ListingTest {

	private UserRepo uDao;
	private ListingRepo lDao;
	private ListingService lServ;
	

	@BeforeEach
	void setup() {
		uDao = mock(UserRepo.class);
		lDao = mock(ListingRepo.class);
		lServ = new ListingService(lDao,uDao);
	}
	
	
	@Test
	void getListingTest() {
		Listing list = new Listing(1, 400, null, "My listing", "content", null, null, null, null, "Electric");
		when(lDao.findById(list.getId())).thenReturn(list);
		
		Listing response = lServ.getListingById(1);
		
		assertThat(response.getId()).isEqualTo(list.getId());
		
	}
	
	@Test
	void createListingTest() {
		Listing list = new Listing(1, 400, null, "My listing", "content", null, null, null, null, "Electric");
		when(lDao.save(any())).thenReturn(list);
		
		Listing response = lServ.createListing(list);
		
		assertThat(response.getId()).isEqualTo(list.getId());
		
	}
	
	@Test
	void getListingByCategoryTest() {
		List<Listing> lists = new ArrayList<Listing>();
		Listing list = new Listing(1, 400, null, "My listing", "content", null, null, null, null, "Electric");
		lists.add(list);
		lists.add(list);
		lists.add(list);
		when(lDao.findByCategory("category")).thenReturn(lists);
		
		List<Listing> responses = lServ.getListingsByCategory("category");
		
		assertThat(responses.size()).isGreaterThan(0);
		
	}
	
	@Test
	void getRecentListingTest() {
		List<Listing> lists = new ArrayList<Listing>();
		Listing list = new Listing(1, 400, null, "My listing", "content", null, null, null, null, "Electric");
		lists.add(list);
		lists.add(list);
		lists.add(list);
		when(lDao.findFirst10ByOrderByIdDesc()).thenReturn(lists);
		
		List<Listing> responses = lServ.getRecentListings();
		
		assertThat(responses.size()).isGreaterThan(0);
		
	}
	
	@Test
	void getListingBySearchTest() {
		List<Listing> lists = new ArrayList<Listing>();
		Listing list = new Listing(1, 400, null, "My listing", "content", null, null, null, null, "Electric");
		lists.add(list);
		lists.add(list);
		lists.add(list);
		when(lDao.findByTitleContainingOrContentContainingOrCategoryContainingIgnoreCase("query","query","query")).thenReturn(lists);
		
		List<Listing> responses = lServ.getListingsBySearchQuery("query");
		
		assertThat(responses.size()).isGreaterThan(0);
		
	}
	
	@Test
	void getListingByPosterTest() {
		List<Listing> lists = new ArrayList<Listing>();
		User user = new User(1, "testUsername", "Myemail@gmail.com", 200, "password", null, null, null, null, null);
		Listing list = new Listing(1, 400, null, "My listing", "content", null, null, null, null, "Electric");
		lists.add(list);
		lists.add(list);
		lists.add(list);
		when(uDao.findByUsername(user.getUsername())).thenReturn(user);
		when(lDao.findByPoster(user)).thenReturn(lists);
		
		List<Listing> responses = lServ.getListingsByPoster(user.getUsername());
		
		assertThat(responses.size()).isGreaterThan(0);
		
	}
	
	@Test
	void getListingByPosterTestFail() {
		List<Listing> lists = new ArrayList<Listing>();
		User user = null;
		Listing list = new Listing(1, 400, null, "My listing", "content", null, null, null, null, "Electric");
		lists.add(list);

		when(uDao.findByUsername("username")).thenReturn(user);
		when(lDao.findByPoster(user)).thenReturn(lists);
		
		List<Listing> responses = lServ.getListingsByPoster("username");
		
		assertThat(responses).isEqualTo(null);
		
	}
	
	
	@Test
	void getListingByPurchasesTest() {
		List<Listing> lists = new ArrayList<Listing>();
		User user = new User(1, "testUsername", "Myemail@gmail.com", 200, "password", null, null, null, null, null);
		Listing list = new Listing(1, 400, null, "My listing", "content", null, null, null, null, "Electric");
		lists.add(list);
		lists.add(list);
		lists.add(list);
		when(uDao.findByUsername(user.getUsername())).thenReturn(user);
		when(lDao.findByPurchaser(user)).thenReturn(lists);
		
		List<Listing> responses = lServ.getListingsByPurchaser(user.getUsername());
		
		assertThat(responses.size()).isGreaterThan(0);
		
	}
	
	@Test
	void getListingByPurchasesTestFail() {
		List<Listing> lists = new ArrayList<Listing>();
		User user = null;
		Listing list = new Listing(1, 400, null, "My listing", "content", null, null, null, null, "Electric");
		lists.add(list);
		lists.add(list);
		lists.add(list);
		when(uDao.findByUsername("username")).thenReturn(user);
		when(lDao.findByPurchaser(user)).thenReturn(lists);
		
		List<Listing> responses = lServ.getListingsByPurchaser("username");
		
		assertThat(responses).isEqualTo(null);
		
	}
	
	@Test
	void getListingBySearchParamTest() {
		List<Listing> lists = new ArrayList<Listing>();
		User user = new User(1, "testUsername", "Myemail@gmail.com", 200, "password", null, null, null, null, null);
		Listing list = new Listing(1, 400, null, "My listing", "content", null, null, null, null, "Electric");
		lists.add(list);
		lists.add(list);
		lists.add(list);
		when(uDao.findByUsername(user.getUsername())).thenReturn(user);
		when(lDao.findByPurchaser(user)).thenReturn(lists);
		Optional<String> user2  = Optional.of("firstUser");
		Optional<Integer> id = Optional.of(1);
		
		
		List<Listing> responses = lServ.getListingsBySearchParams(user2,user2, user2, id);
		
		assertThat(responses.size()).isEqualTo(0);
		
	}
	
	@Test
	void purchaseListingsTest() {
		User user = new User(1, "testUsername", "Myemail@gmail.com", 5200, "password", null, null, new ArrayList<Listing>(), null, null);
		Listing list = new Listing(1, 400, null, "My listing", "content", null, null, null, null, "Electric");
		when(uDao.findByUsername(user.getUsername())).thenReturn(user);
		when(lDao.findById(list.getId())).thenReturn(list);
		when(uDao.save(any())).thenReturn(user);
		when(lDao.save(any())).thenReturn(list);
		
		int response = lServ.purchaseListing("testUsername",1);
		
		assertThat(response).isEqualTo(1);
		
	}
	
	@Test
	void purchaseListingsTestFailFunds() {
		List<Listing> lists = new ArrayList<Listing>();
		User user = new User(1, "testUsername", "Myemail@gmail.com", 200, "password", null, null, null, null, null);
		Listing list = new Listing(1, 400, null, "My listing", "content", null, null, null, null, "Electric");
		when(uDao.findByUsername(user.getUsername())).thenReturn(user);
		when(lDao.findById(list.getId())).thenReturn(list);
		
		int response = lServ.purchaseListing("testUsername",1);
		
		assertThat(response).isEqualTo(0);
		
	}
	
	@Test
	void purchaseListingsTestFailPurchased() {
		User user = new User(1, "testUsername", "Myemail@gmail.com", 5200, "password", null, null, new ArrayList<Listing>(), null, null);
		Listing list = new Listing(1, 400, null, "My listing", "content", null, new User(), null, null, "Electric");
		when(uDao.findByUsername(user.getUsername())).thenReturn(user);
		when(lDao.findById(list.getId())).thenReturn(list);
		when(uDao.save(any())).thenReturn(user);
		when(lDao.save(any())).thenReturn(list);
		
		int response = lServ.purchaseListing("testUsername",1);
		
		assertThat(response).isEqualTo(-2);
		
	}
	
	@Test
	void deleteListing() {
		User user = new User(1, "testUsername", "Myemail@gmail.com", 5200, "password", null, null, new ArrayList<Listing>(), null, null);
		Listing list = new Listing(1, 400, user, "My listing", "content", null, user, null, null, "Electric");
		when(uDao.findByUsername(user.getUsername())).thenReturn(user);
		when(lDao.findById(list.getId())).thenReturn(list);
		when(uDao.save(any())).thenReturn(user);
		
		
		
		
		int response = lServ.deleteListing("testUsername",1);
		
		assertThat(response).isEqualTo(1);
		
	}
	
}
