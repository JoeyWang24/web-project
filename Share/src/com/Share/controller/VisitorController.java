package com.Share.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.Share.Constant;
import com.Share.entity.Share;
import com.Share.entity.ShareCondition;
import com.Share.entity.User;
import com.Share.service.IShareService;
import com.Share.service.IUserService;
import com.Share.util.Json;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

@Controller
@Scope("prototype")
@RequestMapping("/visitor")
public class VisitorController {
	
	@Autowired
	IShareService<Share> shareService;
	
	@Autowired
	IUserService<User> userService;
	
	@RequestMapping("/indexUI")
	public ModelAndView indexUI(){
		return new ModelAndView("visitor/index");
	}
	
	@RequestMapping("/rankingPage")
	public ModelAndView rankingPage(){
		return new ModelAndView("visitor/ranking");
	}
	
	@RequestMapping("/searchPage")
	public ModelAndView searchPage(){
		return new ModelAndView("visitor/search");
	}

	
	/**
     * 显示分页分享列表
     * @param pageNo 当前页
     * @param pageSize 页大小
     * @param userId 用户Id
     * @param condition 搜索条件
     * @param shareType 显示分享的类型（哪个用户点赞/评论/收藏的分享）
     * @param request 请求
     * @return
     */
    @ResponseBody
    @RequestMapping("/listShare")
    public JSONObject listShare(Integer pageNo, Integer pageSize, Integer userId, String condition, Integer lastNDays, Integer shareType, HttpServletRequest request) {
        //参数验证
        if (pageNo == null) {
            //默认显示第一页
            pageNo = 1;
        }
        if (pageSize == null) {
            //默认页大小
            pageSize = Constant.SHARE_PAGE_SIZE;
        }
        //封装要传递的参数
        ShareCondition shareCondition = new ShareCondition();
        shareCondition.setPageNo(pageNo);
        shareCondition.setPageSize(pageSize);
        shareCondition.setUserId(userId);
        shareCondition.setCondition(condition);
        shareCondition.setCurrentUserId(null);
        shareCondition.setShareType(shareType);
        shareCondition.setLastNDays(lastNDays);

        JSONObject jsonObject = null;
        if (shareType == null || shareType.equals(Constant.SHARE_TYPE_DEFAULT)) {
            //默认的分享
            jsonObject = shareService.findByPage(shareCondition);
        }else if (shareType.equals(Constant.SHARE_TYPE_LIKE_RANKING)) {
            //点赞榜
            jsonObject = shareService.findByLikeRankingAndPage(shareCondition);
        }
        return jsonObject;
    }
    
    /**
	 * 根据用户名和内容搜索相关用户
	 * @param condition
	 * @return
	 */
	@RequestMapping("/getSearchUsers")
	@ResponseBody
	public Json getSearchUsers(String condition){
		Json result=new Json();
		try {
			JSONArray searchUsers = userService.getSearchUsers(condition);
			result.setData(searchUsers);
			result.setStatus(true);
			result.setMsg("搜素用户成功！");
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(false);
			result.setMsg("搜索用户失败！"+e.getMessage());
		}
		
		return result;
	}
}
