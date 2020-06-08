package com.Share.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * ShareImg entity. 
 */

public class ShareImg implements java.io.Serializable {

	// Fields

	private Integer id;
	private Share share;
	private String imgPath;
	private String smallImgPath;

	// Constructors


	public ShareImg() {
	}


	public ShareImg(Share share, String imgPath,String smallImagePath) {
		this.share = share;
		this.imgPath = imgPath;
		this.smallImgPath=smallImagePath;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Share getShare() {
		return this.share;
	}

	public void setShare(Share share) {
		this.share = share;
	}

	public String getImgPath() {
		return this.imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}
	
	public String getSmallImgPath() {
		return smallImgPath;
	}
	
	public void setSmallImgPath(String smallImgPath) {
		this.smallImgPath = smallImgPath;
	}

}