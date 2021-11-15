package com.revature.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.revature.project2.models.Message;
import com.revature.project2.models.User;
import com.revature.project2.repository.ListingRepo;
import com.revature.project2.repository.MessageRepo;
import com.revature.project2.repository.UserRepo;
import com.revature.project2.services.MessageService;
import com.revature.project2.services.UserService;

public class MessagesTest {


	private UserRepo uDao;
	private MessageRepo mDao;
	private MessageService mServ;
	

	@BeforeEach
	void setup() {
		uDao = mock(UserRepo.class);
		mDao = mock(MessageRepo.class);
		mServ = new MessageService(mDao,uDao);
	}
	
	
	@Test
	void createMessageTest() {
		Message m = new Message(null, "message content", new User(), new User());
		when(mDao.save(any())).thenReturn(m);
		
		Message response = mServ.createMessage(m);
		
		assertThat(response).isNotEqualTo(null);
		
	}
	
	@Test
	void createMessageTestFail() {
		when(mDao.save(any())).thenThrow(RuntimeException.class);
		
		Message response = mServ.createMessage(new Message());
		
		assertThat(response).isEqualTo(null);
		
	}
	
	
	@Test
	void getAllReceiversTest() {
		List<Message> messages = new ArrayList<Message>();
		Message m = new Message(null, "message content", new User(), new User());
		messages.add(m);
		User user = new User(1, "testUsername", "Myemail@gmail.com", 200, "password", null, null, null, null, null);
		when(mDao.findBySender(any())).thenReturn(messages);
		when(uDao.findById(1)).thenReturn(user);
		
		List<User> response = mServ.getAllRecievers(1);
		
		assertThat(response.size()).isNotEqualTo(0);
		
	}
	
	@Test
	void getConversationTest() {
		List<Message> messages = new ArrayList<Message>();

		Message m = new Message(null, "message content", new User(1, "testUsername", "Myemail@gmail.com", 200, "password", null, null, null, null, null), new User(1, "testUsername", "Myemail@gmail.com", 200, "password", null, null, null, null, null));
		messages.add(m);		when(mDao.save(any())).thenReturn(m);
		
		when(mDao.findConversation(1,2)).thenReturn(messages);
		
		List<Message> response = mServ.getConversation(1,2);
		
		assertThat(response).isNotEqualTo(null);
		
	}
	

}
