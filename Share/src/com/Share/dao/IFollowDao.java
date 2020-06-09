package com.Share.dao;

import java.util.List;

import com.Share.entity.Follow;

public interface IFollowDao<T> extends IBaseDao<T> {
	
	/**
	 * 根据userId获取粉丝列表
	 * @param id
	 * @return
	 */
	public List<Follow> getFansListByUserId(Integer userId,Integer limit);
	
	/**
	 * 根据fansId获取关注列表
	 * @param fansId
	 * @return
	 */
	public List<Follow> getFollowingListByFansId(Integer fansId,Integer limit);
		
}
