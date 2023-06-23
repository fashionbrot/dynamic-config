package com.github.fashionbrot.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.fashionbrot.annotation.MarsPermission;
import com.github.fashionbrot.entity.SysMenuEntity;
import com.github.fashionbrot.mapper.SysMenuMapper;
import com.github.fashionbrot.model.LoginModel;
import com.github.fashionbrot.request.SysMenuRequest;
import com.github.fashionbrot.response.PageResponse;
import com.github.fashionbrot.service.SysMenuService;
import com.github.fashionbrot.service.SysUserService;
import com.github.fashionbrot.service.UserLoginService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.method.HandlerMethod;

import java.lang.reflect.Method;
import java.util.*;

/**
 * 菜单表
 *
 * @author fashionbrot
 */
@Service
@RequiredArgsConstructor
public class SysMenuServiceImpl  extends ServiceImpl<SysMenuMapper, SysMenuEntity> implements SysMenuService {

    final SysUserService sysUserService;
    final UserLoginService userLoginService;


    @Override
    public Object pageReq(SysMenuRequest req) {
        Page<?> page = PageHelper.startPage(req.getPageNum(),req.getPageSize());
        List<SysMenuEntity> listByMap = baseMapper.selectByMap(null);

        return PageResponse.<SysMenuEntity>builder()
                .rows(listByMap)
                .total(page.getTotal())
                .build();
    }

    @Override
    public boolean checkPermission(Object handler, LoginModel model) {
        if (handler instanceof HandlerMethod) {
            //如果是超级管理员
            if (model!=null && model.isSuperAdmin()){
                return true;
            }

            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();

            String permission = null;
            MarsPermission classAnnotation = method.getDeclaringClass().getAnnotation(MarsPermission.class);
            if (classAnnotation!=null){
                MarsPermission methodAnnotation = method.getAnnotation(MarsPermission.class);
                if (methodAnnotation==null){ //方法没有权限直接返回
                    return true;
                }
                permission = classAnnotation.value() + methodAnnotation.value();
            }else{
                MarsPermission methodAnnotation = method.getAnnotation(MarsPermission.class);
                if (methodAnnotation ==null){ //方法没有权限直接返回
                    return true;
                }
                permission =  methodAnnotation.value();
            }

            if (permission!=null) {
                String databasePermission = getPermission(model);
                if (databasePermission.contains(permission)){
                    return true;
                }
                return false;
            }

            return true;
        }

        return false;
    }

    public String getPermission(LoginModel model) {
        Long userId = model.getUserId();
        String permission = baseMapper.selectMenuPermission(model.getRoleId());
        return permission;
    }

    @Override
    public List<SysMenuEntity> loadAllMenu() {

        LoginModel loginModel = userLoginService.getLogin();

        List<SysMenuEntity> checkedList = null;
        if (loginModel.isSuperAdmin() ){
            QueryWrapper q = new QueryWrapper();
            q.in("menu_level", Arrays.asList(1,2));
            checkedList = baseMapper.selectList(q);
        }else{
            Map<String,Object> map=new HashMap<>();
            if (!loginModel.isSuperAdmin()){
                map.put("roleId",loginModel.getRoleId());
//                map.put("orgId",loginModel.getOrgId());
            }
            checkedList = baseMapper.selectMenuRole(map);
        }

        Map<String, Boolean> checkedMap = new HashMap<>();
        if (CollectionUtils.isNotEmpty(checkedList)) {
            for (SysMenuEntity mm : checkedList) {
                checkedMap.put(mm.getId().toString(), true);
            }
        }

        if (CollectionUtils.isNotEmpty(checkedList)) {
            checkedList = loadChildMenu(checkedList, checkedMap);
        }
        return checkedList;
    }

    private List<SysMenuEntity> loadChildMenu(List<SysMenuEntity> menuBarList, Map<String, Boolean> checkedMap) {
        if (CollectionUtils.isNotEmpty(menuBarList)) {
            List<SysMenuEntity> menuList = new ArrayList<>(15);
            for (SysMenuEntity m : menuBarList) {
                if (m.getMenuLevel() != 1 ) {
                    continue;
                }
                if (checkedMap.containsKey(m.getId().toString())) {
                    m.setActive(1);
                }
                m.setChildMenu(loadChildMenu(menuBarList, m, checkedMap));
                menuList.add(m);
            }
            return menuList;
        }
        return null;
    }

    private List<SysMenuEntity> loadChildMenu(List<SysMenuEntity> menuBarList, SysMenuEntity parentMenu, Map<String, Boolean> checkedMap) {
        if (CollectionUtils.isNotEmpty(menuBarList)) {
            List<SysMenuEntity> menuList = new ArrayList<>(10);
            for (SysMenuEntity m : menuBarList) {
                if (Objects.equals(m.getParentMenuId(), parentMenu.getId())) {
                    if (checkedMap.containsKey(m.getId().toString())) {
                        m.setActive(1);
                    }
                    menuList.add(m);
                }
            }
            return menuList;
        }
        return null;
    }

    @Override
    public List<SysMenuEntity> loadMenuAll(Long roleId, Integer isShowCode) {
        List<SysMenuEntity> checkedList =null;
        if (roleId==null){
            checkedList = baseMapper.selectList(null);
        }else{
            Map<String,Object> map=new HashMap<>();
            map.put("roleId",roleId);
            checkedList = baseMapper.selectMenuRole(map);
        }


        Map<String, Boolean> checkedMap = new HashMap<>();
        if (CollectionUtils.isNotEmpty(checkedList)) {
            for (SysMenuEntity mm : checkedList) {
                checkedMap.put(mm.getId().toString(), true);
            }
        }

        List<SysMenuEntity> list = baseMapper.selectList(new QueryWrapper<SysMenuEntity>().eq("visible",0).orderByAsc("priority"));
        if (CollectionUtils.isNotEmpty(list)) {
            return loadChildMenuNotStructure(list, checkedMap);
        }
        return null;
    }

    private List<SysMenuEntity> loadChildMenuNotStructure(List<SysMenuEntity> menuBarList, Map<String, Boolean> checkedMap) {
        if (CollectionUtils.isNotEmpty(menuBarList)) {
            List<SysMenuEntity> menuList = new ArrayList<>(15);
            for (SysMenuEntity m : menuBarList) {

                m.setOpen(false);
                if (checkedMap.containsKey(m.getId().toString())) {
                    m.setActive(1);
                    m.setChecked(true);
                }else{
                    m.setChecked(false);
                }
                m.setName(m.getMenuName()+"&nbsp;"+m.getPermission());
                menuList.add(m);
            }
            return menuList;
        }
        return null;
    }
}