package com.Share.service;

import com.Share.entity.Comment;
import com.alibaba.fastjson.JSONArray;

public interface ICommentService<T> extends IBaseService<T> {
	
	/**
	 * 根据分享id查找评论列表
	 * @param shareId
	 * @return
	 */
	public JSONArray findByShareId(Integer shareId);
}
