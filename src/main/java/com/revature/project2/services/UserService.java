package com.revature.project2.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.project2.models.User;
import com.revature.project2.repository.UserRepo;
import com.revature.project2.util.EmailManager;
import com.revature.project2.util.VerificationCodeManager;

@Service
public class UserService {

	private UserRepo uDao;
	
	@Autowired
	public UserService(UserRepo u) {
		this.uDao = u;
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
	
	
}
