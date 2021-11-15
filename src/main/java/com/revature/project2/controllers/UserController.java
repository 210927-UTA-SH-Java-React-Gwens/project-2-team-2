package com.revature.project2.controllers;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.revature.project2.models.Listing;
import com.revature.project2.models.User;
import com.revature.project2.services.UserService;
import com.revature.project2.util.VerificationCodeManager;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value="/user")
public class UserController {

	private UserService uServ;

	@Autowired
	public UserController(UserService u) {
		this.uServ = u;
	}
	
	@GetMapping("/")
	public List<User> getAll(){
		return uServ.getAllUsers();
	}
	
	@PostMapping("/create-user")
	public User createUser(@RequestBody User u) {
		System.out.println(u);
		return uServ.createUser(u);
	}
	
	@CrossOrigin(origins = "*")
	@GetMapping("/get-user")
	public User getUser(@RequestParam("id") int id) {
		System.out.println(id);
		return uServ.getUser(id);
	}
	
	@GetMapping("/{uname}")
	public User getUser(@PathVariable("uname") String username) {
		return uServ.findUserByUsername(username);
	}
	
	@PostMapping("/login")
	public User login(@RequestBody User u) {
		System.out.println(u);
		return uServ.login(u);
	}
	
	@PostMapping("/update-username")
	public User updateUsername(@RequestBody User u) {
		System.out.println(u);
		return uServ.updateUserUsername(u);
	}
	
	@PostMapping("/update-email")
	public User updateEmail(@RequestBody User u) {
		System.out.println(u);
		return uServ.updateUserEmail(u);
	}
	
	@PostMapping("/update-password")
	public User updatePassword(@RequestBody User u) {
		System.out.println(u);
		return uServ.updateUserPassword(u);
	}
	
	@PostMapping("/add-funds")
	public User addFunds(@RequestBody User u) {
		System.out.println(u);
		return uServ.addFunds(u);
	}
	
	@PostMapping("/verify")
	public User emailMe(@RequestBody Map<String, String> json) {
		//System.out.println(json.get("username"));
		//System.out.println(json.get("code"));
		return uServ.verifyUsersEmail(json.get("username"), json.get("code"));
	}
	
	@PostMapping("/watch")
	public ResponseEntity<Integer> addBookmark(@RequestBody Map<String, ?> body) {
		int status = uServ.addBookmark((String)body.get("user"), (Integer)body.get("listing"));
		return new ResponseEntity<Integer>((Integer)body.get("listing"),
			status == -1 ? HttpStatus.BAD_REQUEST :
				status == 0 ? HttpStatus.CONFLICT :
					HttpStatus.CREATED
		);
	}
	
	@GetMapping("/{uname}/watching")
	public ResponseEntity<List<Listing>> getUserBookmarks(@PathVariable("uname") String user) {
		List<Listing> bookmarks = uServ.getBookmarks(user);
		if (bookmarks == null)
			return new ResponseEntity<List<Listing>>(bookmarks, HttpStatus.BAD_REQUEST);
		
		return new ResponseEntity<List<Listing>>(bookmarks,
				(bookmarks.size() == 0) ? HttpStatus.NO_CONTENT : HttpStatus.OK);
	}
	
	@DeleteMapping("/unwatch")
	public ResponseEntity<Integer> removeBookmark(@RequestBody Map<String, ?> body) {
		int status = uServ.removeBookmark((String)body.get("user"), (Integer)body.get("listing"));
		return new ResponseEntity<Integer>((Integer)body.get("listing"),
				status == -1 ? HttpStatus.BAD_REQUEST :
					status == 0 ? HttpStatus.NOT_FOUND :
						HttpStatus.NO_CONTENT
				);
	}

}
