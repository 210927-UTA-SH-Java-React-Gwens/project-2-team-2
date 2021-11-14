package com.revature.project2.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.revature.project2.models.Message;
import com.revature.project2.models.User;

@Repository
public interface MessageRepo extends JpaRepository <Message, Integer> {

	@Query("SELECT m FROM Message m WHERE (m.sender.id=?1 AND m.receiver.id=?2) OR (m.sender.id=?2 AND m.receiver.id=?1) ORDER BY m.time DESC")
	List<Message> findConversation(int user1Id, int user2Id);
	
	public List<User> findDistinctRecieverBySender (int sender);
}
