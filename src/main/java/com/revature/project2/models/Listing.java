package com.revature.project2.models;

import java.util.ArrayList;
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
@Table(name = "listings")
public class Listing {

	@Id
	@Column(name = "listing_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int listingId;

	@Column(name = "price")
	private int price;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "poster_id")
	private User poster;

	@Column(name = "title")
	private String title;

	@Column(name = "content")
	private String content;

	@Column(name = "posted")
	private Date posted = new Date();

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "purchaser_id")
	private User purchaser = null;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "watch_junction", joinColumns = { @JoinColumn(name = "listing_id") }, inverseJoinColumns = {
			@JoinColumn(name = "watcher_id") })
	private List<User> watchers = new ArrayList<User>();

	@OneToMany(mappedBy = "id", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<ListingImage> images = new ArrayList<ListingImage>();

	@Column(name = "category")
	private String category;

	public Listing() {
		super();
	}

	public Listing(int listingId, int price, User poster, User purchaser, List<User> watchers,
			List<ListingImage> images, String category) {
		super();
		this.listingId = listingId;
		this.price = price;
		this.poster = poster;
		this.purchaser = purchaser;
		this.watchers = watchers;
		this.images = images;
		this.category = category;
	}

	public Listing(int price, String title, String content, String category) {
		super();
		this.price = price;
		this.title = title;
		this.content = content;
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
	
	public List<ListingImage> getImages() {
		return this.images;
	}
	
	public void setImages(List<ListingImage> images) {
		this.images = images;
	}
	
	public void addImage(ListingImage image) {
		this.images.add(image);
	}

	@Override
	public String toString() {
		return "Listing [listingId=" + listingId + ", price=" + price + ", poster=" + poster + ", title=" + title
				+ ", content=" + content + ", posted=" + posted + ", purchaser=" + purchaser + ", watchers=" + watchers
				+ ", images=" + images + ", category=" + category + "]";
	}

}
