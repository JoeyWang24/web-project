package com.Share.dao;

import com.Share.entity.Collect;
import com.Share.entity.ShareCondition;
import com.Share.util.Pagination;

public interface ICollectDao<T> extends IBaseDao<T> {

    /**
     * 获取收藏的分享
     * @param shareCondition
     * @return
     */
    Pagination<Collect> findCollectByPage(ShareCondition shareCondition);
}
