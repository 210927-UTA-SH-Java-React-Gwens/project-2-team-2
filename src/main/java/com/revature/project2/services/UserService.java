package com.revature.project2.services;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.project2.models.Listing;
import com.revature.project2.models.User;
import com.revature.project2.repository.ListingRepo;
import com.revature.project2.repository.UserRepo;
import com.revature.project2.util.EmailManager;
import com.revature.project2.util.VerificationCodeManager;

@Service
public class UserService {

	@PersistenceContext
	private EntityManager entityManager;
	
	private UserRepo uDao;
	private ListingRepo lDao;
	
	@Autowired
	public UserService(UserRepo u, ListingRepo l) {
		this.uDao = u;
		this.lDao = l;
	}
	
	public User createUser (User u) {
		try {
			EmailManager em = new EmailManager();
			String code;
			
			if(u.getEmail().contains("-"))
				throw new Exception("Not valid email");
			else 
				code = VerificationCodeManager.generateNewCode();
			
			em.sendVerificationCodeMail(u.getEmail(), code);
			
			u.setEmail(code + u.getEmail());
			
			uDao.save(u);
			return u;
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public User findUserByUsername(String username) {
		return uDao.findByUsername(username);
	}
	
	public List<User> getAllUsers(){
		try {
			return uDao.findAll();
		} catch (Exception e) {
			return new ArrayList<User>();
		}
	}
	
	public User getUser(int id) {
	
		try {
			return uDao.findById(id);
		}catch (Exception e) {
			return null;
		}
		
	}
	
	public User login(User user) {
		
		try {
			User userDB = uDao.findByUsername(user.getUsername());
			if(userDB.getPassword().equals(user.getPassword()))
				return userDB;
			else
				return null;
			
		}catch (Exception e) {
			return null;
		}
		
	}
	
	public User updateUserUsername(User user) {
		User userDB = null;
		try {
			userDB = uDao.findById(user.getId());
			userDB.setUsername(user.getUsername());
			uDao.save(userDB);
		}catch(Exception e) {
			return null;
		}
		
		return userDB;
	}
	
	public User updateUserEmail(User user) {
		User userDB = null;
		try {
			userDB = uDao.findById(user.getId());
			userDB.setEmail(user.getEmail());
			uDao.save(userDB);
		}catch(Exception e) {
			return null;
		}
		
		return userDB;
	}
	
	public User updateUserPassword(User user) {
		User userDB = null;
		try {
			userDB = uDao.findById(user.getId());
			userDB.setPassword(user.getPassword());
			uDao.save(userDB);
		}catch(Exception e) {
			return null;
		}
		
		return userDB;
	}
	
	public User addFunds(User user) {
		User userDB = null;
		try {
			userDB = uDao.findById(user.getId());
			userDB.setFunds(user.getFunds() + userDB.getFunds());
			uDao.save(userDB);
		}catch(Exception e) {
			return null;
		}
		
		return userDB;
	}
	
	public User verifyUsersEmail(String username, String code) {
		String [] values;
		User userDB = null;
		try {
			userDB = uDao.findByUsername(username);
			System.out.print("arrived"+userDB.toString());
			if(!userDB.getEmail().contains("-"))
				throw new Exception("User account is already active");
			else 
				values = VerificationCodeManager.getCodeFromEmail(userDB.getEmail());
			System.out.print("arrived2"+values[1]+values[2]);
			if(values[1].equals(code))
				userDB.setEmail(values[2]);
			else
				throw new Exception("Incorrect activation code");
			
			uDao.save(userDB);
		}catch(Exception e) {
			userDB = null;
			e.printStackTrace();
		}
		
		return userDB;
		
	}
	
	public List<Listing> getBookmarks(String uname) {
		User u = uDao.findByUsername(uname);
		if (u == null)
			return null;
		return uDao.findBookmarks(u);
	}
	
	/**
	 * Add a bookmark to a user's account
	 * @param username Username of user to add bookmark for
	 * @param listingId ID of the listing to bookmark
	 * @return -1 for bad request, 0 for bookmark already exists, 1 for success
	 */
	public int addBookmark(String username, int listingId) {
		User u = uDao.findByUsername(username);
		Listing l = lDao.findById(listingId);
		if (u == null || l == null)
			return -1;
		if (u.addBookmark(l)) {
			uDao.save(u);
			return 1;
		}
		return 0;
	}

	/**
	 * Remove a bookmark from a user's account
	 * @param user User to remove bookmark for
	 * @param listingId ID of listing to un-unwatch
	 * @return -1 for bad request, 0 for bookmark does not exist, 1 for bookmark removed
	 */
	public int removeBookmark(String user, int listingId) {
		User u = uDao.findByUsername(user);
		Listing l = lDao.findById(listingId);
		if (u == null || l == null)
			return -1;
		if (u.removeBookmark(l)) {
			uDao.save(u);
			return 1;
		}
		return 0;
	}
	
	
}
