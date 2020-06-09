package com.Share.dao;

import java.util.List;

import com.Share.entity.Comment;
import com.Share.entity.ShareCondition;
import com.Share.util.Pagination;

public interface ICommentDao<T> extends IBaseDao<T> {
	/**
	 * 根据分享id查找评论列表
	 * @param shareId
	 * @return
	 */
	public List<Comment> findByShareId(Integer shareId);

	/**
	 * 查找评论的分享
	 * @param shareCondition
	 * @return
	 */
    Pagination<Object[]> findCommentBySqlPage(ShareCondition shareCondition);
}
