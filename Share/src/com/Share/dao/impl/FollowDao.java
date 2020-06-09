package com.Share.dao.impl;

import java.util.List;

import org.omg.CORBA.OBJ_ADAPTER;
import org.springframework.stereotype.Repository;

import com.Share.dao.IFollowDao;
import com.Share.entity.Follow;

@Repository
public class FollowDao extends BaseDao<Follow> implements IFollowDao<Follow> {

	@Override
	public List<Follow> getFansListByUserId(Integer userId,Integer limit) {
		String hql="from Follow f "
				+ "where f.id.user.id=?";
		if(limit!=null){
			return this.findByMax(hql, new Object[]{userId},limit);
		}else {
			return this.find(hql, new Object[]{userId});
		}
	}

	@Override
	public List<Follow> getFollowingListByFansId(Integer fansId,Integer limit) {
		String hql="from Follow f "
				+ "where f.id.fans.id=?";
		if(limit!=null){
			return this.findByMax(hql, new Object[]{fansId},limit);
		}else {
			return this.find(hql, new Object[]{fansId});
		}
	}

}
