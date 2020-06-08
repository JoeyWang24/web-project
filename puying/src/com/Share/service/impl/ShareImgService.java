package com.Share.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.Share.entity.ShareImg;
import com.Share.service.IShareImgService;

@Transactional
@Service
public class ShareImgService extends BaseService<ShareImg> implements
		IShareImgService<ShareImg> {

}
