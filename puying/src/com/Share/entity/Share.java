package com.Share.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

/**
 * Share entity. 
 */
public class Share implements java.io.Serializable {

	// Fields

	private Integer id;
	private User user;
	private String content;
	private Timestamp createTime;
	private Integer status;

	//状态为公开
	public static Integer STATUS_PUBLIC = 0;
	//状态为屏蔽
	public static Integer STATUS_PRIVATE = 1;
	
	//该分享的点赞
	private Set<Like> likes = new HashSet(0);
	
	//该分享的图片
	private Set<ShareImg> shareImgs = new HashSet(0);
	
	//该分享的评论
	private Set<Comment> comments = new HashSet(0);
	
	//该分享的收藏
	private Set<Collect> collects = new HashSet(0);

	// Constructors


	public Share() {
	}


	public Share(User user, String content) {
		this.user = user;
		this.content = content;
	}


	public Share(User user, String content, Timestamp createTime,
			Integer status, Set likes, Set shareImgs, Set comments, Set collects) {
		this.user = user;
		this.content = content;
		this.createTime = createTime;
		this.status = status;
		this.likes = likes;
		this.shareImgs = shareImgs;
		this.comments = comments;
		this.collects = collects;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public static Integer getStatusPublic() {
		return STATUS_PUBLIC;
	}

	public static void setStatusPublic(Integer statusPublic) {
		STATUS_PUBLIC = statusPublic;
	}

	public static Integer getStatusPrivate() {
		return STATUS_PRIVATE;
	}

	public static void setStatusPrivate(Integer statusPrivate) {
		STATUS_PRIVATE = statusPrivate;
	}

	public Set<Like> getLikes() {
		return likes;
	}

	public void setLikes(Set<Like> likes) {
		this.likes = likes;
	}

	public Set<ShareImg> getShareImgs() {
		return shareImgs;
	}

	public void setShareImgs(Set<ShareImg> shareImgs) {
		this.shareImgs = shareImgs;
	}

	public Set<Comment> getComments() {
		return comments;
	}

	public void setComments(Set<Comment> comments) {
		this.comments = comments;
	}

	public Set<Collect> getCollects() {
		return collects;
	}

	public void setCollects(Set<Collect> collects) {
		this.collects = collects;
	}

	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}

}