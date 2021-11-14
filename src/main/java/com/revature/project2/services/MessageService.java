package com.revature.project2.services;

import java.util.ArrayList;
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
	
	public Message createMessage (Message u) {
		try {
			mDao.save(u);
			return u;
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
