package com.revature.test;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.project2.repository.ListingRepo;
import com.revature.project2.repository.UserRepo;
import com.revature.project2.models.*;
import com.revature.project2.services.UserService;

public class UserTest {

	private UserRepo uDao;
	private ListingRepo lDao;
	private UserService uServ;
	

	@BeforeEach
	void setup() {
		uDao = mock(UserRepo.class);
		lDao = mock(ListingRepo.class);
		uServ = new UserService(uDao,lDao);
	}
	
	@Test
	void getUserTest() {
		User user = new User(1, "testUsername", "Myemail@gmail.com", 200, "password", null, null, null, null, null);
		when(uDao.findById(1)).thenReturn(user);
		
		User response = uServ.getUser(1);
		
		assertThat(response.getId()).isEqualTo(user.getId());
		
	}
	
	@Test
	void getUserTestFail() {
		when(uDao.findById(1)).thenThrow(RuntimeException.class);
		
		User response = uServ.getUser(1);
		
		assertThat(response).isEqualTo(null);
		
	}
	
	@Test
	void createUserFailTest() {
		User user = new User(1, "testUsername", "-Myemail@gmail.com", 200, "password", null, null, null, null, null);
		
		User response = uServ.createUser(user);
		
		assertThat(response).isEqualTo(null);
		
	}
	
	@Test
	void getUserbyUsernameTest() {
		User user = new User(1, "testUsername", "Myemail@gmail.com", 200, "password", null, null, null, null, null);
		when(uDao.findByUsername(user.getUsername())).thenReturn(user);
		
		User response = uServ.findUserByUsername("testUsername");
		
		assertThat(response.getId()).isEqualTo(user.getId());
		
	}
	
	@Test
	void getAllUsersTest() {
		List<User> users = new ArrayList<User>();
		users.add(new User(1, "testUsername", "Myemail@gmail.com", 200, "password", null, null, null, null, null));
		users.add(new User(2, "testUsername", "Myemail@gmail.com", 200, "password", null, null, null, null, null));
		users.add(new User(3, "testUsername", "Myemail@gmail.com", 200, "password", null, null, null, null, null));
		when(uDao.findAll()).thenReturn(users);
		
		List<User> responses = uServ.getAllUsers();
		
		assertThat(responses.size()).isGreaterThan(0);
		
	}
	
	@Test
	void getAllUsersTestFail() {
		when(uDao.findAll()).thenThrow(RuntimeException.class);
		
		List<User> responses = uServ.getAllUsers();
		
		assertThat(responses.size()).isEqualTo(0);
		
	}
	
	@Test
	void loginTest() {
		
		User user = new User(1, "testUsername", "Myemail@gmail.com", 200, "password", null, null, null, null, null);
		when(uDao.findByUsername(user.getUsername())).thenReturn(user);
		
		User response = uServ.login(user);
		
		assertThat(response).isNotEqualTo(null);
		
	}
	
	@Test
	void loginTestFail() {
		
		User user = new User(1, "testUsername", "Myemail@gmail.com", 200, "password", null, null, null, null, null);
		User userDb = new User(2, "testUsername", "Myemail@gmail.com", 200, "passwordwronger", null, null, null, null, null);
		when(uDao.findByUsername(user.getUsername())).thenReturn(userDb);
		
		User response = uServ.login(user);
		
		assertThat(response).isEqualTo(null);
		
	}
	
	@Test
	void loginTestErrorFail() {
		
		User user = new User();
		when(uDao.findByUsername(user.getUsername())).thenReturn(null);
		
		User response = uServ.login(user);
		
		assertThat(response).isEqualTo(null);
		
	}
	
	@Test
	void updateUsernameTest() {
		
		User user = new User(1, "testUsername2", "Myemail@gmail.com", 200, "password", null, null, null, null, null);
		User userdB = new User(1, "testUsername", "Myemail@gmail.com", 200, "password", null, null, null, null, null);
		when(uDao.findById(user.getId())).thenReturn(user);
		when(uDao.save(any())).thenReturn(userdB);
		
		User response = uServ.updateUserUsername(user);
		
		assertThat(response).isNotEqualTo(null);
		assertThat(response.getUsername()).isEqualTo(user.getUsername());
		
	}
	@Test
	void updateUsernameTestFail() {
		
		User user = new User();
		when(uDao.findById(user.getId())).thenReturn(null);
		when(uDao.save(any())).thenReturn(user);
		
		User response = uServ.updateUserUsername(user);
		
		assertThat(response).isEqualTo(null);

		
	}
	
	@Test
	void updateEmailTest() {
		
		User user = new User(1, "testUsername", "Myemail@gmail.com", 200, "password", null, null, null, null, null);
		User userdB = new User(1, "testUsername", "Myemail2@gmail.com", 200, "password", null, null, null, null, null);
		when(uDao.findById(user.getId())).thenReturn(user);
		when(uDao.save(any())).thenReturn(userdB);
		
		User response = uServ.updateUserEmail(user);
		
		assertThat(response).isNotEqualTo(null);
		assertThat(response.getEmail()).isEqualTo(user.getEmail());
		
	}
	
	@Test
	void updateEmailTestFail() {
		
		User user = new User();
		when(uDao.findById(user.getId())).thenReturn(null);
		when(uDao.save(any())).thenReturn(user);
		
		User response = uServ.updateUserEmail(user);
		
		assertThat(response).isEqualTo(null);
		
	}
	
	@Test
	void updateUserPasswordTest() {
		
		User user = new User(1, "testUsername", "Myemail@gmail.com", 200, "password", null, null, null, null, null);
		User userdB = new User(1, "testUsername", "Myemail@gmail.com", 200, "password2", null, null, null, null, null);
		when(uDao.findById(user.getId())).thenReturn(user);
		when(uDao.save(any())).thenReturn(userdB);
		
		User response = uServ.updateUserPassword(user);
		
		assertThat(response).isNotEqualTo(null);
		assertThat(response.getPassword()).isEqualTo(user.getPassword());
		
	}
	
	@Test
	void updateUserPasswordTestFail() {
		
		User user = new User();
		when(uDao.findById(user.getId())).thenReturn(null);
		when(uDao.save(any())).thenReturn(user);
		
		User response = uServ.updateUserPassword(user);
		
		assertThat(response).isEqualTo(null);
		
	}
	

	
	@Test
	void addFundsTest() {
		
		User user = new User(1, "testUsername", "Myemail@gmail.com", 200, "password", null, null, null, null, null);
		User userdB = new User(1, "testUsername", "Myemail@gmail.com", 400, "password", null, null, null, null, null);
		when(uDao.findById(user.getId())).thenReturn(user);
		when(uDao.save(any())).thenReturn(user);
		
		User response = uServ.addFunds(user);
		
		assertThat(response).isNotEqualTo(null);
		assertThat(response.getFunds()).isEqualTo(userdB.getFunds());
		
	}
	
	@Test
	void addFundsTestFail() {
		
		User user = new User(1, "testUsername", "Myemail@gmail.com", 200, "password", null, null, null, null, null);
		User userdB = new User(1, "testUsername", "Myemail@gmail.com", 400, "password", null, null, null, null, null);
		when(uDao.findById(user.getId())).thenReturn(null);
		when(uDao.save(any())).thenReturn(user);
		
		User response = uServ.addFunds(user);
		
		assertThat(response).isEqualTo(null);
		
	}

	
	
	@Test
	void verifyMailTest() {
		String [] parameters = {"username","12345"};
		User user = new User(1, "username", "Myemail@gmail.com", 200, "password", null, null, null, null, null);
		User userdB = new User(1, "username", "-12345-Myemail@gmail.com", 200, "password", null, null, null, null, null);

		
		when(uDao.findByUsername(user.getUsername())).thenReturn(userdB);
		when(uDao.save(any())).thenReturn(userdB);
		
		User response = uServ.verifyUsersEmail(parameters[0],parameters[1]);
		
		assertThat(response.getEmail()).isEqualTo(userdB.getEmail());

	}
	
	@Test
	void verifyMailTestFail() {
		String [] parameters = {"username","123456"};
		User user = new User(1, "username", "-12345-Myemail@gmail.com", 200, "password", null, null, null, null, null);
		User userdB = new User(1, "username", "Myemail@gmail.com", 200, "password", null, null, null, null, null);

		
		when(uDao.findByUsername(user.getUsername())).thenReturn(user);
		when(uDao.save(any())).thenReturn(userdB);
		
		User response = uServ.verifyUsersEmail(parameters[0],parameters[1]);
		
		assertThat(response).isEqualTo(null);

	}
	
	@Test
	void verifyMailTestFailError() {
		String [] parameters = {"username","12345"};
		User user = new User(1, "username", "Myemail@gmail.com", 200, "password", null, null, null, null, null);

		
		when(uDao.findByUsername(user.getUsername())).thenReturn(user);
		when(uDao.save(any())).thenReturn(user);
		
		User response = uServ.verifyUsersEmail(parameters[0],parameters[1]);
		
		assertThat(response).isEqualTo(null);
		
	}
	
	@Test
	void getBookMarkTest() {
		User user = new User(1, "username", "Myemail@gmail.com", 200, "password", null, null, null, null, null);
		List<Listing> bookmarked=new ArrayList<>();
		bookmarked.add(new Listing());
		bookmarked.add(new Listing());
		
		when(uDao.findByUsername(user.getUsername())).thenReturn(user);
		when(uDao.findBookmarks(user)).thenReturn(bookmarked);
		
		List<Listing> response = uServ.getBookmarks(user.getUsername());
		
		assertThat(response.size()).isGreaterThan(0);
		
	}
	
	@Test
	void getBookMarkTestFail() {
		User user = null;
		List<Listing> bookmarked=new ArrayList<>();
		bookmarked.add(new Listing());
		bookmarked.add(new Listing());
		
		when(uDao.findByUsername("username")).thenReturn(user);
		when(uDao.findBookmarks(user)).thenReturn(bookmarked);
		
		List<Listing> response = uServ.getBookmarks(null);
		
		assertThat(response).isEqualTo(null);
		
	}
	
	@Test
	void addBookMarkTest() {
		User user = new User(1, "username", "Myemail@gmail.com", 200, "password", null, new ArrayList<Listing>(), null, null, null);
		Listing list = new Listing(1, 400, null, "My listing", "content", null, null, null, null, "Electric");

		List<Listing> bookmarked=new ArrayList<>();
		bookmarked.add(list);
		
		
		when(uDao.findByUsername(user.getUsername())).thenReturn(user);
		when(lDao.findById(list.getId())).thenReturn(list);
		when(uDao.save(any())).thenReturn(user);
		
		int response = uServ.addBookmark(user.getUsername(),list.getId());
		
		assertThat(response).isEqualTo(1);
		
	}
	
	@Test
	void removeBookMarkTestFail() {
		Listing list = new Listing(1, 400, null, "My listing", "content", null, null, null, null, "Electric");
		
		User userBookmarked = new User(1, "username", "Myemail@gmail.com", 200, "password", null, new ArrayList<Listing>(), null, null, null);

		
		when(uDao.findByUsername(userBookmarked.getUsername())).thenReturn(userBookmarked);
		when(lDao.findById(list.getId())).thenReturn(list);
		when(uDao.save(any())).thenReturn(userBookmarked);
		
		int response = uServ.removeBookmark(userBookmarked.getUsername(),list.getId());
		
		assertThat(response).isEqualTo(0);
		
	}
	
	@Test
	void removeBookMarkTest() {
		Listing list = new Listing(1, 400, null, "My listing", "content", null, null, null, null, "Electric");

		List<Listing> bookmarked=new ArrayList<>();
		bookmarked.add(list);
		
		User userBookmarked = new User(1, "username", "Myemail@gmail.com", 200, "password", null, bookmarked, null, null, null);

		
		when(uDao.findByUsername(userBookmarked.getUsername())).thenReturn(userBookmarked);
		when(lDao.findById(list.getId())).thenReturn(list);
		when(uDao.save(any())).thenReturn(userBookmarked);
		
		int response = uServ.removeBookmark(userBookmarked.getUsername(),list.getId());
		
		assertThat(response).isEqualTo(1);
		
	}
	
	



}