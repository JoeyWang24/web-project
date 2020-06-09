package com.Share.dao.impl;

import org.springframework.stereotype.Repository;

import com.Share.dao.ICommentDao;
import com.Share.entity.Comment;
import com.Share.entity.ShareCondition;
import com.Share.util.Pagination;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CommentDao extends BaseDao<Comment> implements ICommentDao<Comment> {

    @Override
    public List<Comment> findByShareId(Integer shareId) {
        String hql = "from Comment c "
                + "where c.share.id=? ";
        return this.find(hql, new Object[]{shareId});
    }

    @Override
    public Pagination<Object[]> findCommentBySqlPage(ShareCondition shareCondition) {
        StringBuffer sql = new StringBuffer();
        ArrayList<Object> params = new ArrayList<>();

        sql.append("select distinct(s.id),s.content, s.create_time, s.status, s.user_id, u.username, u.img_path from share s ");
        sql.append("join comment c on c.share_id=s.id ");
        sql.append("join user u on s.user_id=u.id ");
        sql.append("where 1=1 ");
        //条件：用户id
        if (shareCondition.getUserId() != null) {
            sql.append("and c.user_id=? ");
            params.add(shareCondition.getUserId());
        }
        //条件：公开分享，用户没有被屏蔽，或我的(隐私)分享
        if (shareCondition.getCurrentUserId() != null) {
            sql.append("and (s.status=0 and u.status=0 or s.user_id=?) ");
            params.add(shareCondition.getCurrentUserId());
        }

        String countSql = "select count(*) from ( " + sql.toString() + " ) t";

        //根据时间倒序排序
        sql.append("order by s.create_time desc");

        Pagination<Object[]> sqlPagination = findSqlPagination(sql.toString(), countSql, params.toArray(), shareCondition.getPageNo(), shareCondition.getPageSize());

        return sqlPagination;
    }

}
