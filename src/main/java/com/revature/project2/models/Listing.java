package com.revature.project2.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "price")
	private int price;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "poster_id")
	private User poster;

	@Column(name = "title")
	private String title;

	@Column(name = "content", columnDefinition="TEXT")
	private String content;

	@Column(name = "posted")
	private Date posted = new Date();

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "purchaser_id")
	private User purchaser = null;

	@ManyToMany(mappedBy="bookmarks")
	@JsonIgnore
	private List<User> watchers = new ArrayList<User>();

	@OneToMany(mappedBy = "id", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<ListingImage> images = new ArrayList<ListingImage>();

	@Column(name = "category")
	private String category;

	
	
	public Listing() {
		super();
	}

	/**
	 * All-args constructor
	 * @param id
	 * @param price
	 * @param poster
	 * @param title
	 * @param content
	 * @param posted
	 * @param purchaser
	 * @param watchers
	 * @param images
	 * @param category
	 */
	public Listing(int id, int price, User poster, String title, String content, Date posted, User purchaser,
			List<User> watchers, List<ListingImage> images, String category) {
		super();
		this.id = id;
		this.price = price;
		this.poster = poster;
		this.title = title;
		this.content = content;
		this.posted = posted;
		this.purchaser = purchaser;
		this.watchers = watchers;
		this.images = images;
		this.category = category;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getPosted() {
		return posted;
	}

	public void setPosted(Date posted) {
		this.posted = posted;
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
		return images;
	}

	public void setImages(List<ListingImage> images) {
		this.images = images;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	/**
	 * @param image
	 */
	public void addImage(ListingImage image) {
		this.images.add(image);
	}

	@Override
	public String toString() {
		return "Listing [id=" + id + ", price=" + price + ", poster=" + poster.getUsername() + ", title=" + title
				+ ", content=" + content + ", posted=" + posted
				+ ", purchaser=" + ((purchaser != null)
					? purchaser.getUsername()
					: "none") + ", watchers=" + watchers
				+ ", images=" + images.stream()
					.map(e -> String.valueOf(e.getId()))
					.collect(Collectors.joining(", "))
				+ ", category=" + category + "]";
	}

	
	
	
}
