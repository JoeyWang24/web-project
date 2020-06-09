package com.Share.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * FollowId entity. 
 */

public class FollowId implements java.io.Serializable {

	// Fields
	private User user;
	private User fans;

	// Constructors


	public FollowId() {
	}


	public FollowId(User user, User fans) {
		this.user = user;
		this.fans = fans;
	}

	// Property accessors

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public User getFans() {
		return this.fans;
	}

	public void setFans(User fans) {
		this.fans = fans;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof FollowId))
			return false;
		FollowId castOther = (FollowId) other;

		return ((this.getUser() == castOther.getUser()) || (this.getUser() != null
				&& castOther.getUser() != null && this.getUser().equals(
				castOther.getUser())))
				&& ((this.getFans() == castOther.getFans()) || (this
						.getFans() != null && castOther.getFans() != null && this
						.getFans().equals(castOther.getFans())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getUser() == null ? 0 : this.getUser().hashCode());
		result = 37 * result
				+ (getFans() == null ? 0 : this.getFans().hashCode());
		return result;
	}

}