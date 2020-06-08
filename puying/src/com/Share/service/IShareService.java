package com.Share.service;

import com.Share.entity.Share;
import com.Share.entity.ShareCondition;
import com.Share.exception.HideShareException;
import com.Share.exception.ShowShareException;
import com.alibaba.fastjson.JSONObject;

public interface IShareService<T> extends IBaseService<T> {

    /**
     * 屏蔽分享
     * @param currentUserId	当前登陆用户的id
     * @param shareId	要屏蔽的分享id
     * @exception HideShareException 屏蔽分享异常（不可以屏蔽其他用户的分享）
     */
    public void hideShare(Integer currentUserId,Integer shareId) throws HideShareException;

    /**
     * 取消屏蔽分享
     * @param currentUserId	当前登陆用户的id
     * @param shareId	要取消屏蔽的分享id
     * @exception ShowShareException 取消屏蔽分享异常（不可以取消屏蔽其他用户的分享）
     */
    public void showShare(Integer currentUserId,Integer shareId) throws ShowShareException;

    /**
     * 获取分享
     * @param shareCondition 获取分享的条件
     * @return
     */
    JSONObject findByPage(ShareCondition shareCondition);

    /**
     * 获取点赞的分享
     * @param shareCondition
     * @return
     */
    JSONObject findByLikeAndPage(ShareCondition shareCondition);

    /**
     * 获取评论的分享
     * @param shareCondition
     * @return
     */
    JSONObject findByCommentAndSqlPage(ShareCondition shareCondition);

    /**
     * 获取收藏的分享
     * @param shareCondition
     * @return
     */
    JSONObject findByCollectAndPage(ShareCondition shareCondition);

    /**
     * 获取点赞榜
     * @param shareCondition
     * @return
     */
    JSONObject findByLikeRankingAndPage(ShareCondition shareCondition);

    /**
     * 删除分享
     * @param share
     */
    void deleteShare(T share);
}
