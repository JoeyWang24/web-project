package com.Share.service.impl;

import java.util.List;
import java.util.Set;

import com.Share.dao.IFollowDao;
import com.Share.entity.Follow;
import com.Share.entity.User;
import com.Share.service.IFollowService;
import com.Share.util.Pagination;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class FollowService extends BaseService<Follow> implements IFollowService<Follow> {
	
	@Override
	public JSONObject getFansList(User user,Integer limit,Integer page) {
		List<Follow> fansList=null;
		Pagination<Follow> pagination=null;
		if(page==null){
			fansList = followDao.getFansListByUserId(user.getId(),limit);
		}else {
			String hql="from Follow f "
					+ "where f.id.user.id=?";
			pagination=followDao.findPagination(hql, new Object[]{user.getId()}, page, limit);
			fansList=pagination.getData();
		}
		JSONArray jFansList=new JSONArray();
		//需要获取粉丝的用户名，头像，粉丝数量，关注数量，性别
		for (Follow follow : fansList) {
			JSONObject jFans=new JSONObject();
			
			User fans=follow.getId().getFans();
			jFans.put("id", fans.getId());
			jFans.put("username", fans.getUsername());
			jFans.put("userImg", fans.getImgPath());
			jFans.put("gender", fans.getGender());
			jFans.put("fansCount", fans.getFanses().size());
			jFans.put("followCount", fans.getUsers().size());
			jFans.put("shareCount", fans.getShares().size());
			jFansList.add(jFans);
		}
		JSONObject result=new JSONObject();
		result.put("fansList", jFansList);
		if (page!=null&&pagination!=null) {
			result.put("page", page);
			result.put("rows", limit);
			result.put("count", pagination.getCount());
		}
		return result;
	}

	@Override
	public JSONObject getFollowingList(User fans,Integer limit,Integer page) {
		List<Follow> followList=null;
		Pagination<Follow> pagination=null;
		if (page==null) {
			followList = followDao.getFollowingListByFansId(fans.getId(),limit);
		}else {
			String hql="from Follow f "
					+ "where f.id.fans.id=?";
			pagination=followDao.findPagination(hql, new Object[]{fans.getId()}, page, limit);
			followList=pagination.getData();
		}
		JSONArray jUserList=new JSONArray();
		//需要获取粉丝的用户名，头像，粉丝数量，关注数量，性别
		for (Follow follow : followList) {
			JSONObject jUser=new JSONObject();
			
			User user=follow.getId().getUser();
			jUser.put("id", user.getId());
			jUser.put("username", user.getUsername());
			jUser.put("userImg", user.getImgPath());
			jUser.put("gender", user.getGender());
			jUser.put("fansCount", user.getFanses().size());
			jUser.put("followCount", user.getUsers().size());
			jUser.put("shareCount", user.getShares().size());
			jUserList.add(jUser);
		}
		
		JSONObject result=new JSONObject();
		result.put("followingList", jUserList);
		if (page!=null&&pagination!=null) {
			result.put("page", page);
			result.put("rows", limit);
			result.put("count", pagination.getCount());
		}
		
		return result;
	}

	@Override
	public boolean isFollow(int id1, int id2) {
		if(id1==id2){
			return false;
		}
		User user1 = userDao.get(id1);
		Set<Follow> users = user1.getUsers();
		for (Follow follow : users) {
			if (follow.getId().getUser().getId().equals(id2)) {
				return true;
			}
		}
		return false;
	}

}
