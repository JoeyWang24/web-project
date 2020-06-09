package com.Share.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * CollectId entity. 
 */

public class CollectId implements java.io.Serializable {

	// Fields

	private Share share;
	private User user;

	// Constructors


	public CollectId() {
	}


	public CollectId(Share share, User user) {
		this.share = share;
		this.user = user;
	}

	// Property accessors

	public Share getShare() {
		return this.share;
	}

	public void setShare(Share share) {
		this.share = share;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof CollectId))
			return false;
		CollectId castOther = (CollectId) other;

		return ((this.getShare() == castOther.getShare()) || (this.getShare() != null
				&& castOther.getShare() != null && this.getShare().equals(
				castOther.getShare())))
				&& ((this.getUser() == castOther.getUser()) || (this.getUser() != null
						&& castOther.getUser() != null && this.getUser()
						.equals(castOther.getUser())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getShare() == null ? 0 : this.getShare().hashCode());
		result = 37 * result
				+ (getUser() == null ? 0 : this.getUser().hashCode());
		return result;
	}

}