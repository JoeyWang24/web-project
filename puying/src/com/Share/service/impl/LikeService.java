package com.Share.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.Share.entity.Like;
import com.Share.service.ILikeService;

@Transactional
@Service
public class LikeService extends BaseService<Like> implements ILikeService<Like> {

}
