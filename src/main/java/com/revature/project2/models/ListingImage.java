package com.revature.project2.models;

import java.io.IOException;
import java.util.Arrays;

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

import org.springframework.web.multipart.MultipartFile;

@Entity
@Table(name="listing_images")
public class ListingImage {
	@Id
	@Column(name="li_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "img", nullable = false)
	private byte[] img;
	
	@Column(name = "index", nullable = false)
	private int index;
	

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "listing_id")
	private Listing listing;


	public ListingImage() {
		super();
	}


	public ListingImage(byte[] img, Listing listing) {
		super();
		this.img = img;
		this.listing = listing;
	}
	
	public ListingImage(Listing listing, int index, MultipartFile file) throws IOException {
		super();
		this.listing = listing;
		this.index = index;
		this.img = file.getBytes();
	}

	public byte[] getImg() {
		return img;
	}


	public void setImg(byte[] img) {
		this.img = img;
	}


	public Listing getListing() {
		return listing;
	}

	
	public void setListing(Listing listing) {
		this.listing = listing;
	}

	
	@Override
	public String toString() {
		return "ListingImage [img=" + Arrays.toString(img) + ", index=" + index + ", listing=" + listing + "]";
	}
	
}
