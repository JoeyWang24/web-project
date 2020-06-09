package com.Share.service.impl;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.Share.dao.IAdminDao;
import com.Share.dao.IBadwordDao;
import com.Share.dao.IBaseDao;
import com.Share.dao.ICollectDao;
import com.Share.dao.ICommentDao;
import com.Share.dao.IFollowDao;
import com.Share.dao.ILikeDao;
import com.Share.dao.IShareDao;
import com.Share.dao.IShareImgDao;
import com.Share.dao.IUserDao;
import com.Share.entity.Admin;
import com.Share.entity.Badword;
import com.Share.entity.Collect;
import com.Share.entity.Comment;
import com.Share.entity.Follow;
import com.Share.entity.Like;
import com.Share.entity.Share;
import com.Share.entity.ShareImg;
import com.Share.entity.User;
import com.Share.service.IBaseService;

/**
 * BaseService实现，必须配置@Transactional,否则事务配置出错
 *
 * @param <T>
 */
@Transactional
public class BaseService<T> implements IBaseService<T> {

    @Autowired
    protected IBaseDao<T> baseDao;

    @Autowired
    protected IUserDao<User> userDao;

    @Autowired
    protected IShareDao<Share> shareDao;

    @Autowired
    protected IShareImgDao<ShareImg> shareImgDao;

    @Autowired
    protected ICommentDao<Comment> commentDao;

    @Autowired
    protected ICollectDao<Collect> collectDao;

    @Autowired
    protected IFollowDao<Follow> followDao;

    @Autowired
    protected ILikeDao<Like> likeDao;

    @Autowired
    protected IAdminDao<Admin> adminDao;

    @Autowired
    protected IBadwordDao<Badword> badwordDao;

    private String daoName;

    //反射泛型获取泛型参数的实际类型，拼接得到dao名称
    public BaseService() {
        ParameterizedType parentType = (ParameterizedType) this.getClass().getGenericSuperclass();
        Class<?> clazz = (Class<?>) parentType.getActualTypeArguments()[0];
        daoName = clazz.getSimpleName().substring(0, 1).toLowerCase()
                + clazz.getSimpleName().substring(1)
                + "Dao";
    }

    //初始化，反射设置baseDao为具体的类型
    @PostConstruct
    public void init() {
        try {
            Class<?> clazz = this.getClass().getSuperclass();
            //1.反射获取baseDao域
            Field baseField = clazz.getDeclaredField("baseDao");
            baseField.setAccessible(true);
            //2.反射获取daoName域
            Field actualField = clazz.getDeclaredField(daoName);
            actualField.setAccessible(true);
            //3.反射设置属性值
            baseField.set(this, actualField.get(this));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public Serializable save(T t) {
        return baseDao.save(t);
    }

    @Override
    public void delete(T t) {
        baseDao.delete(t);
    }

    @Override
    public void update(T t) {
        baseDao.delete(t);
    }

    @Override
    public void saveOrUpdate(T t) {
        baseDao.saveOrUpdate(t);
    }

    @Override
    public T get(Serializable id) {
        return baseDao.get(id);
    }

}
