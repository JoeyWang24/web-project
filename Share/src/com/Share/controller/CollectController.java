package com.Share.controller;

import java.sql.Timestamp;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.Share.entity.Collect;
import com.Share.entity.CollectId;
import com.Share.entity.Share;
import com.Share.entity.User;
import com.Share.service.ICollectService;
import com.Share.util.Json;

@Controller
@Scope("prototype")
@RequestMapping("/collect")
public class CollectController {
	
	@Autowired
	ICollectService<Collect> collectService;
	
	/**
	 * 添加收藏
	 * @param share 要收藏的share(只需要传id)
	 * @param session
	 * @return
	 */
	@RequestMapping("/addCollect")
	@ResponseBody
	public Json addCollect(Share share,HttpSession session){
		Json result=new Json();
		try {
			Collect collect=new Collect();
			collect.setCreateTime(new Timestamp(System.currentTimeMillis()));
			User user = (User)session.getAttribute("loginInfo");
			collect.setId(new CollectId(share,user));
			collectService.save(collect);
			result.setStatus(true);
			result.setMsg("收藏分享成功!");
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(true);
			result.setMsg("收藏分享失败！");
		}
		return result;
	}
	
	/**
	 * 取消收藏
	 * @param share	要取消收藏的share(只需要传id)
	 * @param session
	 * @return
	 */
	@RequestMapping("/deleteCollect")
	@ResponseBody
	public Json deleteCollect(Share share,HttpSession session){
		Json result=new Json();
		try {
			Collect collect=new Collect();
			CollectId collectId=new CollectId();
			collectId.setShare(share);
			collectId.setUser((User)session.getAttribute("loginInfo"));
			collect.setId(collectId);
			collectService.delete(collect);
			result.setStatus(true);
			result.setMsg("取消分享成功!");
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(true);
			result.setMsg("取消分享失败！");
		}
		return result;
	}
	
}
