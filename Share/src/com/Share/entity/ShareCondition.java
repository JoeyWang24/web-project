package com.Share.entity;


public class ShareCondition {
    //页号
    private Integer pageNo;
    //页大小
    private Integer pageSize;
    //该用户（的分享）
    private Integer userId;
    //当前登录用户（判断当前用户是否已经点赞等用到）
    private Integer currentUserId;
    //查询条件
    private String condition;
    //分享类型（点赞/评论/收藏等）
    private Integer shareType;
    //时间筛选（过去N天:N=1今天，N=7最近一周，null全部）
    private Integer lastNDays;

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getCurrentUserId() {
        return currentUserId;
    }

    public void setCurrentUserId(Integer currentUserId) {
        this.currentUserId = currentUserId;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public Integer getShareType() {
        return shareType;
    }

    public void setShareType(Integer shareType) {
        this.shareType = shareType;
    }

    public Integer getLastNDays() {
        return lastNDays;
    }

    public void setLastNDays(Integer lastNDays) {
        this.lastNDays = lastNDays;
    }
}
