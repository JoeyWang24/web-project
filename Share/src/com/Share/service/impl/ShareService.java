package com.Share.service.impl;

import com.Share.entity.*;
import com.Share.exception.HideShareException;
import com.Share.exception.ShowShareException;
import com.Share.service.IShareService;
import com.Share.util.Pagination;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.List;

@Transactional
@Service
public class ShareService extends BaseService<Share> implements IShareService<Share> {
    //格式化日期
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    
    private JSONObject createJsonObject(Share share, Integer currentUserId) {
        JSONObject tShare = new JSONObject();
        tShare.put("id", share.getId());
        tShare.put("content", share.getContent());
//        tShare.put("createTime", sdf.format(new Date(share.getCreateTime().getTime())));
        tShare.put("createTime", sdf.format(share.getCreateTime()));//sdf.format支持java.sql.Timestamp
        tShare.put("userId", share.getUser().getId());
        tShare.put("userName", share.getUser().getUsername());
        tShare.put("userImg", share.getUser().getImgPath());
        tShare.put("collectCount", share.getCollects().size());
        tShare.put("likeCount", share.getLikes().size());
        tShare.put("status", share.getStatus());
        tShare.put("commentCount", share.getComments().size());
        //当前用户是否点赞过
        for (Like like : share.getLikes()) {
            if (like.getId().getUser().getId().equals(currentUserId)) {
                tShare.put("isLike", true);
                break;
            }
        }
        //当前用户是否收藏过
        for (Collect collect : share.getCollects()) {
            if (collect.getId().getUser().getId().equals(currentUserId)) {
                tShare.put("isCollect", true);
                break;
            }
        }
        //分享图片集合
        JSONArray tShareImgs = new JSONArray();
        for (ShareImg shareImg : share.getShareImgs()) {
            JSONObject tShareImg = new JSONObject();
            //原图
            tShareImg.put("bigPath", shareImg.getImgPath());
            //缩略图
            tShareImg.put("smallPath", shareImg.getSmallImgPath());
            tShareImgs.add(tShareImg);
        }
        tShare.put("shareImgs", tShareImgs);
        return tShare;
    }

    //封装分页数据
    private void createPageInfo(Pagination page, JSONObject tPage) {
        //分页数据
        tPage.put("pageNo", page.getPage());
        tPage.put("nextPage", page.getNextPage());
        tPage.put("prePage", page.getPrePage());
        tPage.put("pageSize", page.getRows());
        tPage.put("totalCount", page.getCount());
        tPage.put("pageCount", page.getPageCount());
    }

    @Override
    public JSONObject findByPage(ShareCondition shareCondition) {
        //源分页
        Pagination<Share> page = shareDao.findByPage(shareCondition);
        //目标分页
        JSONObject tPage = new JSONObject();
        //目标List
        JSONArray tList = new JSONArray();
        //当前登录用户的id
        Integer currentUserId = shareCondition.getCurrentUserId();

        for (Share share : page.getData()) {
            JSONObject tShare = createJsonObject(share, currentUserId);
            tList.add(tShare);
        }
        tPage.put("items", tList);

        createPageInfo(page, tPage);
        return tPage;
    }

    @Override
    public JSONObject findByLikeAndPage(ShareCondition shareCondition) {
        //源分页
        Pagination<Like> page = likeDao.findLikeByPage(shareCondition);
        //目标分页
        JSONObject tPage = new JSONObject();
        //目标List
        JSONArray tList = new JSONArray();
        //当前登录用户的id
        Integer currentUserId = shareCondition.getCurrentUserId();

        for (Like like1 : page.getData()) {
            Share share = like1.getId().getShare();
            JSONObject tShare = createJsonObject(share, currentUserId);
            tList.add(tShare);
        }
        tPage.put("items", tList);

        createPageInfo(page, tPage);
        return tPage;
    }

    @Override
    public JSONObject findByCommentAndSqlPage(ShareCondition shareCondition) {
        Pagination<Object[]> page = commentDao.findCommentBySqlPage(shareCondition);
        //目标分页
        JSONObject tPage = new JSONObject();
        //目标List
        JSONArray tList = new JSONArray();

        for (Object[] share : page.getData()) {
            JSONObject tShare = new JSONObject();
            tShare.put("id", share[0]);
            tShare.put("content", share[1]);
            tShare.put("createTime", sdf.format(share[2]));
            tShare.put("status", share[3]);
            tShare.put("userId", share[4]);
            tShare.put("userName", share[5]);
            tShare.put("userImg", share[6]);
            tShare.put("collectCount", commentDao.countBySql("select count(*) from collect where share_id=?", new Object[]{share[0]}));
            tShare.put("likeCount", commentDao.countBySql("select count(*) from `like` where share_id=?", new Object[]{share[0]}));
            tShare.put("commentCount", commentDao.countBySql("select count(*) from comment where share_id=?", new Object[]{share[0]}));

            //当前用户是否点赞过
            List<Object[]> likes = commentDao.findBySql("select * from `like` where share_id=?", new Object[]{share[0]});
            for (Object[] like : likes) {
                System.out.println(like[1]);
                if (shareCondition.getCurrentUserId().equals(like[1])) {
                    tShare.put("isLike", true);
                    break;
                }
            }

            //当前用户是否收藏过
            List<Object[]> collects = commentDao.findBySql("select * from collect where share_id=?", new Object[]{share[0]});
            for (Object[] collect : collects) {
                System.out.println(collect[1]);
                if (shareCondition.getCurrentUserId().equals(collect[1])) {
                    tShare.put("isCollect", true);
                    break;
                }
            }

            //分享图片集合
            JSONArray tShareImgs = new JSONArray();
            List<Object[]> shareImgs = commentDao.findBySql("select * from share_img where share_id=?", new Object[]{share[0]});
            for (Object[] shareImg : shareImgs) {
                JSONObject tShareImg = new JSONObject();
                //原图
                tShareImg.put("bigPath", shareImg[1]);
                //缩略图
                tShareImg.put("smallPath", shareImg[3]);
                tShareImgs.add(tShareImg);
            }
            tShare.put("shareImgs", tShareImgs);

            tList.add(tShare);
        }
        tPage.put("items", tList);
        createPageInfo(page, tPage);

        return tPage;
    }

    @Override
    public JSONObject findByCollectAndPage(ShareCondition shareCondition) {
        //源分页
        Pagination<Collect> page = collectDao.findCollectByPage(shareCondition);
        //目标分页
        JSONObject tPage = new JSONObject();
        //目标List
        JSONArray tList = new JSONArray();
        //当前登录用户的id
        Integer currentUserId = shareCondition.getCurrentUserId();

        for (Collect collect : page.getData()) {
            Share share = collect.getId().getShare();
            JSONObject tShare = createJsonObject(share, currentUserId);
            tList.add(tShare);
        }
        tPage.put("items", tList);

        createPageInfo(page, tPage);
        return tPage;
    }

    @Override
    public JSONObject findByLikeRankingAndPage(ShareCondition shareCondition) {
        //源分页
        Pagination<Share> page = shareDao.findByLikeRankingAndPage(shareCondition);
        //目标分页
        JSONObject tPage = new JSONObject();
        //目标List
        JSONArray tList = new JSONArray();
        //当前登录用户的id
        Integer currentUserId = shareCondition.getCurrentUserId();

        for (Share share : page.getData()) {
            JSONObject tShare = createJsonObject(share, currentUserId);
            tList.add(tShare);
        }
        tPage.put("items", tList);

        createPageInfo(page, tPage);
        return tPage;

    }

    @Override
    public void deleteShare(Share share) {
        Share tShare = shareDao.get(share.getId());
        //需要先把关联依赖的数据删掉
        // likes
        for (Like like : tShare.getLikes()) {
            likeDao.delete(like);
        }
        // shareImgs
        for (ShareImg shareImg : tShare.getShareImgs()) {
            shareImgDao.delete(shareImg);
        }
        // comments
        for (Comment comment : tShare.getComments()) {
            commentDao.delete(comment);
        }
        // collects
        for (Collect collect : tShare.getCollects()) {
            collectDao.delete(collect);
        }

        shareDao.delete(tShare);
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public void hideShare(Integer currentUserId, Integer shareId) throws HideShareException {
        User currentUser = userDao.get(currentUserId);
        Share share = shareDao.get(shareId);
        if (!currentUser.getId().equals(share.getUser().getId())) {
            throw new HideShareException("你没有权限屏蔽这条分享！");
        }
        share.setStatus(Share.STATUS_PRIVATE);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void showShare(Integer currentUserId, Integer shareId)
            throws ShowShareException {
        User currentUser = userDao.get(currentUserId);
        Share share = shareDao.get(shareId);
        if (!currentUser.getId().equals(share.getUser().getId())) {
            throw new ShowShareException("你没有权限取消这条分享的屏蔽状态！");
        }
        share.setStatus(Share.STATUS_PUBLIC);
    }


}
