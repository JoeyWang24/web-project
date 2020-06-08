package com.Share.controller;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;


@Controller
@Scope("prototype")
@RequestMapping("/index")
public class IndexController {
    /**
     * 跳转到默认主页-显示全部的分享
     */
    @RequestMapping("/index")
    public ModelAndView index(HttpServletRequest request){
        return new ModelAndView("index/index");
    }

    /**
     * 跳转到-显示个人的分享
     */
    @RequestMapping("/personalPage")
    public ModelAndView personalPage(HttpServletRequest request){
        return new ModelAndView("index/personalPage");
    }

    /**
     * 跳转到-显示点赞的分享
     */
    @RequestMapping("/likePage")
    public ModelAndView likePage(HttpServletRequest request){
        return new ModelAndView("index/likePage");
    }

    /**
     * 跳转到-显示评论的分享
     */
    @RequestMapping("/commentPage")
    public ModelAndView commentPage(HttpServletRequest request){
        return new ModelAndView("index/commentPage");
    }

    /**
     * 跳转到-显示收藏的分享
     */
    @RequestMapping("/collectPage")
    public ModelAndView collectPage(HttpServletRequest request){
        return new ModelAndView("index/collectPage");
    }

    /**
     * 跳转到-搜索的分享
     */
    @RequestMapping("/searchPage")
    public ModelAndView searchPage(HttpServletRequest request){
        return new ModelAndView("index/searchPage");
    }

    /**
     * 跳转到-搜索的分享
     */
    @RequestMapping("/likeRankingPage")
    public ModelAndView likeRankingPage(HttpServletRequest request){
        return new ModelAndView("index/likeRankingPage");
    }
    
    /**
     * 粉丝列表页面
     * @return
     */
    @RequestMapping("/fansPage")
    public ModelAndView fansPage(){
    	return new ModelAndView("index/fansPage");
    }
    
    /**
     * 关注列表页面
     * @return
     */
    @RequestMapping("/followingPage")
    public ModelAndView followingPage(){
    	return new ModelAndView("index/followingPage");
    }

}
