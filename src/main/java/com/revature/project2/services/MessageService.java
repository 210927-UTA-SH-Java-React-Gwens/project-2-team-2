package com.revature.project2.services;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.project2.models.Message;
import com.revature.project2.models.User;
import com.revature.project2.repository.MessageRepo;
import com.revature.project2.repository.UserRepo;

@Service
public class MessageService {
	
	private MessageRepo mDao;
	private UserRepo uDao;
	
	@Autowired
	public MessageService(MessageRepo m, UserRepo u) {
		this.mDao = m;
		this.uDao = u;
	}
	
	public Message createMessage (String senderUname, String receiverUname, String content, long time) {
		User sender = uDao.findByUsername(senderUname);
		User receiver = uDao.findByUsername(receiverUname);
		if (sender == null || receiver == null)
			return null;
		Date date = new Date(time);
		
		Message message = new Message(date, content, sender, receiver);
		try {
			mDao.save(message);
			return message;
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Message> getConversation(int user1Id,int user2Id) {
		return mDao.findConversation(user1Id, user2Id);
	}
	
	public List<User> getAllRecievers(int userId){
		List<Message> msgs = mDao.findBySender(uDao.findById(userId));
		List<User> users = new ArrayList<User>();
		for (Message msg : msgs)
			if (!users.contains(msg.getReceiver()))
				users.add(msg.getReceiver());
		return users;
	}
}
