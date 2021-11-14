package com.revature.project2.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.revature.project2.models.Message;
import com.revature.project2.models.User;
import com.revature.project2.services.MessageService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value="/message")
public class MessageController {
	
	private MessageService mServ;

	@Autowired
	public MessageController(MessageService m) {
		this.mServ = m;
	}
	
	@GetMapping("/messages")
	public List<Message> getListingMessages(@RequestParam int sender_id, int reciever_id){
		return mServ.getConversation(sender_id, reciever_id);
	}
	
	@CrossOrigin(origins = "*")
	@GetMapping("/recievers")
	public List<User> getRecipients(@RequestParam("id") int user_id){
		return mServ.getAllRecievers(user_id);
	}
	
	@PostMapping("/create")
	public Message createMessage(@RequestBody Message u) {
		System.out.println(u);
		return mServ.createMessage(u);
	}
	
	
}
