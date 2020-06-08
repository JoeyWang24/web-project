package com.Share.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * LikeId entity. 
 */

public class LikeId implements java.io.Serializable {

	// Fields

	private User user;
	private Share share;

	// Constructors


	public LikeId() {
	}


	public LikeId(User user, Share share) {
		this.user = user;
		this.share = share;
	}

	// Property accessors

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Share getShare() {
		return this.share;
	}

	public void setShare(Share share) {
		this.share = share;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof LikeId))
			return false;
		LikeId castOther = (LikeId) other;

		return ((this.getUser() == castOther.getUser()) || (this.getUser() != null
				&& castOther.getUser() != null && this.getUser().equals(
				castOther.getUser())))
				&& ((this.getShare() == castOther.getShare()) || (this
						.getShare() != null && castOther.getShare() != null && this
						.getShare().equals(castOther.getShare())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getUser() == null ? 0 : this.getUser().hashCode());
		result = 37 * result
				+ (getShare() == null ? 0 : this.getShare().hashCode());
		return result;
	}

}