package com.revature.project2.models;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Type;

@Entity
@Table(name = "messages")
public class Message {
	@Id
	@Column(name="message_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "time_sent", nullable = false)
	@Type(type="timestamp")
	private Date time = new Date();

	@Column(name = "content", nullable = false)
	private String content;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "sender_id")
	User sender;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "receiver_id")
	User receiver;
	
	
	public Message() {
		super();
	}
	
	public Message(Date time, String content, User sender, User receiver) {
		super();
		this.time = time;
		this.content = content;
		this.sender = sender;
		this.receiver = receiver;
	}
	
	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public User getSender() {
		return sender;
	}

	public void setSender(User sender) {
		this.sender = sender;
	}

	public User getReceiver() {
		return receiver;
	}

	public void setReceiver(User receiver) {
		this.receiver = receiver;
	}
	
	public String toString() {
		return "Sent: " + time.toString()
			 + "\nFrom: " + sender.getUsername()
			 +"\nTo: " + receiver.getUsername()
			 +"\nMessage: \"" + content + "\"";
	}
}
