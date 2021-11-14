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

import com.fasterxml.jackson.annotation.JsonIgnore;

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
	
	@Column(name = "ftype", nullable = false)
	private String ftype;
	

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "listing_id")
	@JsonIgnore
	private Listing listing;


	public ListingImage() {
		super();
	}


	public ListingImage(int id, byte[] img, int index, String ftype, Listing listing) {
		super();
		this.img = img;
		this.listing = listing;
		this.index = index;
		this.ftype = ftype;
	}
	
	public ListingImage(Listing listing, int index, MultipartFile file) {
		super();
		this.listing = listing;
		this.index = index;
		try {
			this.img = file.getInputStream().readAllBytes();
		} catch (IOException e) {
			e.printStackTrace();
			this.img = new byte[0];
		}
		this.ftype = file.getContentType();
	}


	
	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public byte[] getImg() {
		return img;
	}


	public void setImg(byte[] img) {
		this.img = img;
	}


	public int getIndex() {
		return index;
	}


	public void setIndex(int index) {
		this.index = index;
	}


	public String getFtype() {
		return ftype;
	}


	public void setFtype(String ftype) {
		this.ftype = ftype;
	}


	public Listing getListing() {
		return listing;
	}


	public void setListing(Listing listing) {
		this.listing = listing;
	}


	
	@Override
	public String toString() {
		return "ListingImage [id=" + id + ", img=" + Arrays.toString(img) + ", index=" + index + ", ftype=" + ftype
				+ ", listing=" + listing + "]";
	}
}
