package com.Share.dao;

import com.Share.entity.Like;
import com.Share.entity.ShareCondition;
import com.Share.util.Pagination;

public interface ILikeDao<T> extends IBaseDao<T> {
    /**
     * 查找点赞分享
     * @param shareCondition
     * @return
     */
    Pagination<Like> findLikeByPage(ShareCondition shareCondition);
}
