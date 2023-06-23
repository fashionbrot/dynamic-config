package com.github.fashionbrot.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;

import com.github.fashionbrot.model.LoginModel;
import com.github.fashionbrot.service.UserLoginService;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class FieldMetaObjectHandler implements MetaObjectHandler, BeanFactoryAware {

    private final static String CREATE_ID = "createId";
    private final static String CREATE_DATE = "createDate";
    private final static String UPDATE_ID = "updateId";
    private final static String UPDATE_DATE = "updateDate";
    private final static String DEL_FLAG = "delFlag"; //删除标志位 1删除 0未删除
    private final static String OPERATOR="operator";


    /**
     * insert 表时添加必填字段
     * @param metaObject
     */
    @Override
    public void insertFill(MetaObject metaObject) {

        this.strictInsertFill(metaObject,CREATE_ID,Long.class,getUserId());
        this.strictInsertFill(metaObject,CREATE_DATE,Date.class,new Date());
        this.strictInsertFill(metaObject,DEL_FLAG, Integer.class,0);

        this.strictInsertFill(metaObject,OPERATOR, String.class,getOperator());
    }

    /**
     * update 表时更新必填字段
     * @param metaObject
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        Date date = new Date();
        this.strictUpdateFill(metaObject,UPDATE_ID,Long.class,getUserId());
        this.strictUpdateFill(metaObject,UPDATE_DATE,Date.class,date);

        this.strictUpdateFill(metaObject,OPERATOR, String.class,getOperator());
        this.strictUpdateFill(metaObject,CREATE_DATE,Date.class,date);
    }

    private Long getUserId(){
        LoginModel safeLogin = sysUserService.getSafeLogin();
        if (safeLogin!=null){
            return safeLogin.getUserId();
        }
        return 0L;
    }

    private String getOperator(){
        LoginModel safeLogin = sysUserService.getSafeLogin();
        if (safeLogin!=null){
            return safeLogin.getUserName();
        }
        return "";
    }


    private UserLoginService sysUserService;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        sysUserService = beanFactory.getBean(UserLoginService.class);
    }

}
