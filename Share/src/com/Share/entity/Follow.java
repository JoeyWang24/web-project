package com.Share.entity;

import java.sql.Timestamp;

/**
 * Follow entity.
 */

public class Follow implements java.io.Serializable {

	// Fields

	private FollowId id;
	private Timestamp createTime;

	// Constructors


	public Follow() {
	}


	public Follow(FollowId id) {
		this.id = id;
	}


	public Follow(FollowId id, Timestamp createTime) {
		this.id = id;
		this.createTime = createTime;
	}

	// Property accessors

	public FollowId getId() {
		return this.id;
	}

	public void setId(FollowId id) {
		this.id = id;
	}

	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

}