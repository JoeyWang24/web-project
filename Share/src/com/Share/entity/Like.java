package com.Share.entity;

import java.sql.Timestamp;

/**
 * Like entity. 
 */

public class Like implements java.io.Serializable {

	// Fields

	private LikeId id;
	private Timestamp createTime;

	// Constructors


	public Like() {
	}


	public Like(LikeId id) {
		this.id = id;
	}


	public Like(LikeId id, Timestamp createTime) {
		this.id = id;
		this.createTime = createTime;
	}

	// Property accessors

	public LikeId getId() {
		return this.id;
	}

	public void setId(LikeId id) {
		this.id = id;
	}

	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

}