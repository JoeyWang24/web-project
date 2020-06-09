package com.Share.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

/**
 * Comment entity.
 */

public class Comment implements java.io.Serializable {

	// Fields

	private Integer id;
	private User user;
	private Comment comment;     //父评论
	private Share share;
	private String content;
	private Timestamp createTime;

	private Set<Comment> comments = new HashSet(0);

	// Constructors


	public Comment() {
	}


	public Comment(User user, Comment comment, Share share, String content) {
		this.user = user;
		this.comment = comment;
		this.share = share;
		this.content = content;
	}


	public Comment(User user, Comment comment, Share share, String content,
			Timestamp createTime, Set comments) {
		this.user = user;
		this.comment = comment;
		this.share = share;
		this.content = content;
		this.createTime = createTime;
		this.comments = comments;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Comment getComment() {
		return this.comment;
	}

	public void setComment(Comment comment) {
		this.comment = comment;
	}

	public Share getShare() {
		return this.share;
	}

	public void setShare(Share share) {
		this.share = share;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Set<Comment> getComments() {
		return this.comments;
	}

	public void setComments(Set<Comment> comments) {
		this.comments = comments;
	}

}