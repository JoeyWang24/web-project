package com.Share.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.Share.entity.Like;
import com.Share.entity.LikeId;
import com.Share.entity.Share;
import com.Share.entity.User;
import com.Share.service.ILikeService;
import com.Share.util.Json;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.Date;


@Controller
@Scope("prototype")
@RequestMapping("/like")
public class LikeController {
    @Autowired
    private ILikeService<Like> likeService;

    /**
     * 点赞
     */
    @ResponseBody
    @RequestMapping("/addLike")
    public Json addLike(Share share, HttpServletRequest request) {
        Like like = new Like();
        //当前登录的用户
        User currentUser = (User) request.getSession().getAttribute("loginInfo");
        like.setId(new LikeId(currentUser,share));
        like.setCreateTime(new Timestamp(new Date().getTime()));
        likeService.save(like);
        //返回json结果
        Json json = new Json();
        json.setStatus(true);
        return json;
    }

    /**
     * 取消点赞
     */
    @ResponseBody
    @RequestMapping("/deleteLike")
    public Json deleteLike(Share share, HttpServletRequest request) {
        Like like = new Like();
        //当前登录的用户
        User currentUser = (User) request.getSession().getAttribute("loginInfo");
        like.setId(new LikeId(currentUser,share));
        likeService.delete(like);
        //返回json结果
        Json json = new Json();
        json.setStatus(true);
        return json;
    }
}
