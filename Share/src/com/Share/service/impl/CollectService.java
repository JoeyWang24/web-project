package com.Share.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.Share.entity.Collect;
import com.Share.service.ICollectService;

@Transactional
@Service
public class CollectService extends BaseService<Collect> implements
		ICollectService<Collect> {

}
