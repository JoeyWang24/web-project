package com.Share.service;

import com.Share.entity.User;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public interface IFollowService<T> extends IBaseService<T> {
	
	/**
	 * 获取粉丝列表
	 * @param user
	 * @return
	 */
	public JSONObject getFansList(User user,Integer limit,Integer page);
	
	/**
	 * 获取关注列表
	 * @param user
	 * @return
	 */
	public JSONObject getFollowingList(User fans,Integer limit,Integer page);
	
	/**
	 * 判断user1是否关注了user2
	 * @param id1
	 * @param id2
	 * @return
	 */
	public boolean isFollow(int  id1,int id2);
}
