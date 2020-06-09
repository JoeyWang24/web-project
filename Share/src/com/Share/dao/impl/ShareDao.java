package com.Share.dao.impl;

import org.springframework.stereotype.Repository;

import com.Share.dao.IShareDao;
import com.Share.entity.Share;
import com.Share.entity.ShareCondition;
import com.Share.util.Pagination;

import java.util.ArrayList;
import java.util.Calendar;

@Repository
public class ShareDao extends BaseDao<Share> implements IShareDao<Share> {

    @Override
    public Pagination<Share> findByPage(ShareCondition shareCondition) {
        StringBuffer hql = new StringBuffer();
        ArrayList<Object> params = new ArrayList<>();

        hql.append("from Share s where 1=1 ");

        //条件：用户id
        if (shareCondition.getUserId() != null) {
            hql.append("and s.user.id=? ");
            params.add(shareCondition.getUserId());
        }
        //条件：分享内容含有condition字符串
        if (shareCondition.getCondition() != null && !"".equals(shareCondition.getCondition())) {
            hql.append("and s.content like ? ");
            params.add("%"+shareCondition.getCondition()+"%");
        }
        //条件：不传userId和condition->显示主页（显示当前用户的分享以及当前用户所关注的人的分享）
        if (shareCondition.getUserId() == null && (shareCondition.getCondition() == null || "".equals(shareCondition.getCondition())) && shareCondition.getCurrentUserId() != null) {
            hql.append("and s.user.id=? or s.user.id in (select f.id.user.id from Follow f where f.id.fans.id=?) ");
            params.add(shareCondition.getCurrentUserId());
            params.add(shareCondition.getCurrentUserId());
        }
        //条件：公开分享，用户没有被屏蔽，或我的(隐私)分享
        if (shareCondition.getCurrentUserId() != null) {
            hql.append("and (s.status=0 and s.user.status=0 or s.user.id=?) ");
            params.add(shareCondition.getCurrentUserId());
        }

        //根据时间倒序排序
        hql.append("order by s.createTime desc");

        return findPagination(hql.toString(), params.toArray(), shareCondition.getPageNo(), shareCondition.getPageSize());
    }

    @Override
    public Pagination<Share> findByLikeRankingAndPage(ShareCondition shareCondition) {
        StringBuffer hql = new StringBuffer();
        ArrayList<Object> params = new ArrayList<>();

        hql.append("from Share where 1=1 ");

        
        hql.append("and likes.size > 0 ");
        //条件：时间筛选（全部，今天，最近一周）
        if (shareCondition.getLastNDays() != null) {
            Calendar calendar = Calendar.getInstance();
            //前N天
            calendar.add(Calendar.DATE, 1 - shareCondition.getLastNDays());
            //设置时分秒为0
            calendar.set(Calendar.AM_PM, Calendar.AM);
            calendar.set(Calendar.HOUR,0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);

            hql.append("and createTime > ? ");
            params.add(calendar.getTime());
        }
        //条件：公开分享，用户没有被屏蔽
        hql.append("and (status=0 and user.status=0) ");

        //根据点赞数倒序、时间倒序排序
        hql.append("order by likes.size desc, createTime desc");

        return findPagination(hql.toString(), params.toArray(), shareCondition.getPageNo(), shareCondition.getPageSize());
    }

}
