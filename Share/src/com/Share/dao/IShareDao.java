package com.Share.dao;

import com.Share.entity.Like;
import com.Share.entity.Share;
import com.Share.entity.ShareCondition;
import com.Share.util.Pagination;

public interface IShareDao<T> extends IBaseDao<T> {

    /**
     * 查找分享
     * @param shareCondition 查找分享的条件
     * @return
     */
    Pagination<Share> findByPage(ShareCondition shareCondition);

    /**
     * 点赞榜
     * @param shareCondition
     * @return
     */
    Pagination<Share> findByLikeRankingAndPage(ShareCondition shareCondition);
}
