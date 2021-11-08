package com.revature.project2.models;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
//changes
@Entity
@Table(name="listings")
public class Listing {
	public enum Category { Collectibles, Electronics, Clothing, Sports, Music, Movies, Home, Toys, Other };

	@Id
	@Column(name="listing_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int listingId;
	
	
	@Column(name="price")
	private int price;
	
	@ManyToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	@JoinColumn(name="poster_id")
	private User poster;
	
	@Column(name="title")
	private String title;
	
	@Column(name="content")
	private String content;
	
	@Column(name = "posted")
	private Date posted;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "purchaser_id")
	private User purchaser = null;
	

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "watch_junction", joinColumns = { @JoinColumn(name = "listing_id") }, inverseJoinColumns = {
			@JoinColumn(name = "watcher_id") })
	private List<User> watchers;

	@OneToMany(mappedBy="id", cascade=CascadeType.ALL)
	@JsonIgnore
	private List<ListingImage> images;
	
	@Enumerated(EnumType.STRING)
	private Category category;
	
	public Listing() {
		super();
	}
	
	public Listing(int listingId, int price, User poster, User purchaser, List<User> watchers,
			List<ListingImage> images, Category category) {
		super();
		this.listingId = listingId;
		this.price = price;
		this.poster = poster;
		this.purchaser = purchaser;
		this.watchers = watchers;
		this.images = images;
		this.category = category;
	}

	public int getListingId() {
		return listingId;
	}


	public void setListingId(int listingId) {
		this.listingId = listingId;
	}


	public int getPrice() {
		return price;
	}


	public void setPrice(int price) {
		this.price = price;
	}


	public User getPoster() {
		return poster;
	}


	public void setPoster(User poster) {
		this.poster = poster;
	}


	public User getPurchaser() {
		return purchaser;
	}


	public void setPurchaser(User purchaser) {
		this.purchaser = purchaser;
	}


	public List<User> getWatchers() {
		return watchers;
	}


	public void setWatchers(List<User> watchers) {
		this.watchers = watchers;
	}


	@Override
	public String toString() {
		return "Listing [listingId=" + listingId + ", price=" + price + ", poster=" + poster + ", purchaser="
				+ purchaser + ", watchers=" + watchers + "]";
	}
}
