package com.github.fashionbrot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.fashionbrot.entity.TemplateEntity;
import com.github.fashionbrot.request.TemplateRequest;

/**
 * 模板表
 *
 * @author fashionbrot
 * @email fashionbrot@163.com
 * @date 2021-04-18
 */
public interface TemplateService  extends IService<TemplateEntity> {

    Object pageReq(TemplateRequest req);

    void add(TemplateEntity entity);

    void edit(TemplateEntity entity);
}