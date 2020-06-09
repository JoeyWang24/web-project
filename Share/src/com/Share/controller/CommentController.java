package com.Share.controller;

import java.sql.Timestamp;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.Share.entity.Comment;
import com.Share.entity.Share;
import com.Share.entity.User;
import com.Share.service.ICommentService;
import com.Share.service.IShareService;
import com.Share.util.Json;
import com.alibaba.fastjson.JSONArray;

@Controller
@Scope("prototype")
@RequestMapping("/comment")
public class CommentController {
	
	@Autowired
	IShareService<Share> shareService;
	
	@Autowired
	ICommentService<Comment> commentService;

	/**
	 * 评论分享/回复评论
	 * @param comment	评论，开始只有内容
	 * @param shareId	评论的分享的id
	 * @param parentId	父评论的id(回复评论时有值)
	 * @return
	 */
	@RequestMapping("/save")
	@ResponseBody
	public Json saveComment(Comment comment,Integer shareId,Integer parentId,HttpSession session){
		
		Json result=new Json();
		
		try {
			
			//验证参数
			if(comment.getContent()==null||comment.getContent().equals("")){
				throw new IllegalArgumentException("评论不能为空!");
			}
			
			if(comment.getContent().length()>100){
				throw new IllegalArgumentException("评论字数必须小于100!");
			}
			
			//1.调用shareService根据id获取Share
			Share share = shareService.get(shareId);
			comment.setShare(share);
			//2.调用commentService根据id获取Comment
			if (parentId==null||parentId.equals("")) {
				comment.setComment(null);
			}else {
				Comment parent = commentService.get(parentId);
				comment.setComment(parent);
			}
			//3.封装基本信息到comment中
			comment.setCreateTime(new Timestamp(System.currentTimeMillis()));
			comment.setUser((User)session.getAttribute("loginInfo"));
			//4.调用commentService保存评论
			commentService.save(comment);
			result.setStatus(true);
			result.setMsg("保存评论成功！");
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(false);
			result.setMsg("保存评论失败,原因:"+e.getMessage());
		}
		return result;
	}
	
	
	@RequestMapping("/getCommentList")
	@ResponseBody
	public Json getCommentList(Integer shareId){
		
		Json result=new Json();
		
		try {
			//获取评论列表，有两层，第一层是直接评论分享，第二层是回复评论，
			 JSONArray data = commentService.findByShareId(shareId);
			 result.setData(data);
			 result.setStatus(true);
			 result.setMsg("获取评论列表成功！");
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(false);
			result.setMsg("获取评论列表失败！");
		}
		 
		 return result;
	}
}
