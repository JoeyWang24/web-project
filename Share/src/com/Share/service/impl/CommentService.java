package com.Share.service.impl;

import com.Share.entity.Comment;
import com.Share.entity.User;
import com.Share.service.ICommentService;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Transactional
@Service
public class CommentService extends BaseService<Comment> implements
        ICommentService<Comment> {

    @Override
    public JSONArray findByShareId(Integer shareId) {
        List<Comment> commentList = commentDao.findByShareId(shareId);
        JSONArray jList = new JSONArray();

        if (commentList != null) {
            for (Comment comment : commentList) {
                if (comment.getComment() == null) {
                    jList.add(getFirstComment(comment));
                }
            }
        }

        return jList;
    }

    /**
     * 得到第一层的Comment的JSONObject
     *
     * @param comment
     * @return
     */
    private JSONObject getFirstComment(Comment comment) {

        JSONObject jComment = toComment(comment);

        //获取后代
        JSONArray childs = new JSONArray();
        getChilds(comment, childs);

        jComment.put("childs", childs);

        return jComment;
    }

    /**
     * 获取后代节点，保存到JSONArray中
     *
     * @param comment
     * @param childs
     */
    private void getChilds(Comment comment, JSONArray childs) {
    	String hql="from Comment c where c.comment.id=?";
    	List<Comment> comments = commentDao.find(hql,new Object[]{comment.getId()});
        for (Comment c : comments) {
            childs.add(toComment(c));
            getChilds(c, childs);
        }
    }

    /**
     * 将一个Comment实体转为JSONObject
     *
     * @param comment
     * @return
     */
    private JSONObject toComment(Comment comment) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        JSONObject jComment = new JSONObject();
        jComment.put("id", comment.getId());
        jComment.put("content", comment.getContent());
        jComment.put("createTime", sdf.format(new Date(comment.getCreateTime().getTime())));
        User user = comment.getUser();
        jComment.put("userId", user.getId());
        jComment.put("username", user.getUsername());
        jComment.put("userImg", user.getImgPath());
        jComment.put("shareId", comment.getShare().getId());
        if (comment.getComment() != null) {
            Comment parent = comment.getComment();
            jComment.put("pId", parent.getId());
            User pUser = parent.getUser();
            jComment.put("pUserId", pUser.getId());
            jComment.put("pUsername", pUser.getUsername());
            jComment.put("pUserImg", pUser.getImgPath());
        }
        return jComment;
    }

}
