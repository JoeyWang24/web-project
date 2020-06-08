package com.Share.controller;

import com.Share.Constant;
import com.Share.entity.Share;
import com.Share.entity.ShareCondition;
import com.Share.entity.ShareImg;
import com.Share.entity.User;
import com.Share.exception.FileUploadException;
import com.Share.exception.HideShareException;
import com.Share.exception.ShowShareException;
import com.Share.service.IShareImgService;
import com.Share.service.IShareService;
import com.Share.util.FileUtil;
import com.Share.util.Json;
import com.alibaba.fastjson.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;



@Controller
@Scope("prototype")
@RequestMapping("/share")
public class ShareController {

    @Autowired
    private IShareService<Share> shareService;
    @Autowired
    private IShareImgService<ShareImg> shareImgService;

    /**
     * 发布分享
     * @param share 分享，表单自动填充了content
     * @param request 文件上传需要
     * @param isPublic 是否公开
     * @return 
     * @throws Exception 文件上传抛出的异常
     */
    @RequestMapping("/releaseShare")
    @ResponseBody
    public Json releaseShare(Share share,String isPublic,Integer imgCount,HttpServletRequest request,HttpSession session){
    	
    	Json result=new Json();
        try {
			//取得当前登录的用户
			User user = (User) request.getSession().getAttribute("loginInfo");
			share.setUser(user);
			//默认状态为公开
			if(isPublic==null||isPublic.equals("0")){
				share.setStatus(Share.STATUS_PUBLIC);
			}else {
				share.setStatus(Share.STATUS_PRIVATE);
			}
			//设置发布时间
			share.setCreateTime(new Timestamp(new Date().getTime()));
			
			shareService.save(share);

			List<String> pathList = (List<String>)session.getAttribute("pathList");
			if (pathList!=null&&pathList.size()>0&&imgCount==0) {
				//图片上传后，没有发布，此时将上传的文件删除
				FileUtil.deletePathList(request);
				
			}else if(pathList!=null&&pathList.size()>0&&pathList.size()!=imgCount) {
				//说明上传的数量大于目前数量，则从后面往前取
				for(int i=pathList.size()-1;i>=pathList.size()-imgCount;i--){
					ShareImg shareImg = new ShareImg();
				    shareImg.setImgPath(pathList.get(i).split("#")[0]);
				    shareImg.setSmallImgPath(pathList.get(i).split("#")[1]);
				    shareImg.setShare(share);
				    shareImgService.save(shareImg);
				}
				
				
				request.setAttribute("pathList", null);
				
			}else if(pathList!=null){
				for (String s : pathList) {
				    ShareImg shareImg = new ShareImg();
				    shareImg.setImgPath(s.split("#")[0]);
				    shareImg.setSmallImgPath(s.split("#")[1]);
				    shareImg.setShare(share);
				    shareImgService.save(shareImg);
				}
				
				
				request.setAttribute("pathList", null);
			}
			
				
			result.setStatus(true);
			result.setMsg("发布分享成功！");
		} catch (Exception e) {
			
			//有异常也将文件删除
			FileUtil.deletePathList(request);
			e.printStackTrace();
			result.setStatus(false);
			result.setMsg("发布分享失败！");
		}

        return result;
       
    }
    
    /**
     * session中的pathList格式为 原图路径+'#'+缩略图路径
     * @param request
     * @param session
     * @return
     */
    @RequestMapping("/upload")
    @ResponseBody
    public String upload(HttpServletRequest request,HttpSession session){
    	try {
			List<String> pathList = FileUtil.upload(request, new String[]{"jpg","jpeg","png","gif"});
			pathList=FileUtil.createThumbnails(pathList, request);
			
			//将路径放入缓存
			List<String> buffer = (List<String>)session.getAttribute("pathList");
			if (buffer==null) {
				buffer=new ArrayList<String>();
			}
			if(pathList!=null){
				for (String path : pathList) {
					buffer.add(path);
				}
			}
			session.setAttribute("pathList", buffer);
			
			
			return "true";
		} catch (FileUploadException e) {
			e.printStackTrace();
			return "false";
		}
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
        //当前用户
        User currentUser = (User) request.getSession().getAttribute("loginInfo");
        //封装要传递的参数
        ShareCondition shareCondition = new ShareCondition();
        shareCondition.setPageNo(pageNo);
        shareCondition.setPageSize(pageSize);
        shareCondition.setUserId(userId);
        shareCondition.setCondition(condition);
        shareCondition.setCurrentUserId(currentUser.getId());
        shareCondition.setShareType(shareType);
        shareCondition.setLastNDays(lastNDays);

        JSONObject jsonObject = null;
        if (shareType == null || shareType.equals(Constant.SHARE_TYPE_DEFAULT)) {
            //默认的分享
            jsonObject = shareService.findByPage(shareCondition);
        } else if (shareType.equals(Constant.SHARE_TYPE_LIKE)) {
            //点赞的分享
            jsonObject = shareService.findByLikeAndPage(shareCondition);
        } else if (shareType.equals(Constant.SHARE_TYPE_COMMENT)) {
            //评论的分享
            jsonObject = shareService.findByCommentAndSqlPage(shareCondition);
        } else if (shareType.equals(Constant.SHARE_TYPE_COLLECT)) {
            //收藏的分享
            jsonObject = shareService.findByCollectAndPage(shareCondition);
        } else if (shareType.equals(Constant.SHARE_TYPE_LIKE_RANKING)) {
            //点赞榜
            jsonObject = shareService.findByLikeRankingAndPage(shareCondition);
        }
        return jsonObject;
    }

    /**
     * 屏蔽分享
     * @param id 被屏蔽的分享id
     * @return
     */
    @RequestMapping("/hideShare")
    @ResponseBody
    public Json hideShare(Integer id,HttpSession session){
    	Json result=new Json();
    	try {
			//1.获取当前用户id
			Integer currentUserId = ((User)session.getAttribute("loginInfo")).getId();
			//2.调用service隐藏share
			shareService.hideShare(currentUserId, id);
			result.setStatus(true);
			result.setMsg("屏蔽分享成功！");
		} catch (HideShareException e) {
			e.printStackTrace();
			result.setStatus(false);
			result.setMsg(e.getMessage());
		}
    	return result;
    }

    /**
     * 取消屏蔽分享
     * @param id 分享id
     * @return
     */
    @RequestMapping("/showShare")
    @ResponseBody
    public Json showShare(Integer id,HttpSession session){
    	Json result=new Json();
    	try {
			//1.获取当前用户id
			Integer currentUserId = ((User)session.getAttribute("loginInfo")).getId();
			//2.调用service隐藏share
			shareService.showShare(currentUserId, id);
			result.setStatus(true);
			result.setMsg("取消屏蔽分享成功！");
		} catch (ShowShareException e) {
			e.printStackTrace();
			result.setStatus(false);
			result.setMsg(e.getMessage());
		}
    	return result;
    }

	/**
	 * 删除分享
	 * @param share 分享(id)
	 * @return
	 */
	@RequestMapping("/deleteShare")
	@ResponseBody
	public Json deleteShare(Share share) {
		Json result = new Json();
		shareService.deleteShare(share);
		result.setStatus(true);
		return result;
	}
}
