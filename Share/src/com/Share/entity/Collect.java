package com.Share.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.sql.Timestamp;

/**
 * Collect entity.
 */

public class Collect implements java.io.Serializable {

	// Fields

	private CollectId id;
	private Timestamp createTime;

	// Constructors

	/** default constructor */
	public Collect() {
	}

	/** minimal constructor */
	public Collect(CollectId id) {
		this.id = id;
	}

	/** full constructor */
	public Collect(CollectId id, Timestamp createTime) {
		this.id = id;
		this.createTime = createTime;
	}

	// Property accessors

	public CollectId getId() {
		return this.id;
	}

	public void setId(CollectId id) {
		this.id = id;
	}

	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

}