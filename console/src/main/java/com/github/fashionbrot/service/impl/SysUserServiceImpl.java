package com.github.fashionbrot.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.fashionbrot.entity.SysRoleEntity;
import com.github.fashionbrot.entity.SysUserEntity;
import com.github.fashionbrot.exception.DynamicConfigException;
import com.github.fashionbrot.mapper.SysRoleMapper;
import com.github.fashionbrot.mapper.SysUserMapper;
import com.github.fashionbrot.model.LoginModel;
import com.github.fashionbrot.request.SysUserRequest;
import com.github.fashionbrot.response.PageResponse;
import com.github.fashionbrot.service.SysUserService;
import com.github.fashionbrot.service.UserLoginService;
import com.github.fashionbrot.util.EncryptUtil;
import com.github.fashionbrot.util.JwtTokenUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 系统用户表
 *
 * @author fashionbrot
 */
@SuppressWarnings("ALL")
@Service
@RequiredArgsConstructor
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUserEntity> implements SysUserService {

    @Autowired
    private HttpServletRequest request;
    @Autowired
    private HttpServletResponse response;

    final SysRoleMapper sysRoleMapper;
    final UserLoginService userLoginService;



    @Override
    public Object pageReq(SysUserRequest req) {
        Page<?> page = PageHelper.startPage(req.getPageNum(),req.getPageSize());
        List<SysUserEntity> listByMap = baseMapper.selectByMap(null);

        return PageResponse.<SysUserEntity>builder()
                .rows(listByMap)
                .total(page.getTotal())
                .build();
    }


    @Override
    public void updatePwd(String pwd, String newPwd) {
        if (pwd.equals(newPwd)) {
            throw new DynamicConfigException("新密码和原密码一致，请修改");
        }
        LoginModel login = userLoginService.getLogin();
        SysUserEntity user = baseMapper.selectById(login.getUserId());
        if (user != null) {
            String salt =user.getSalt();
            String encryptPassword = EncryptUtil.encryptPassword(pwd, salt);
            if (!user.getPassword().equals(encryptPassword)) {
                throw new DynamicConfigException("原密码输入错误，请重新输入");
            }
            salt =EncryptUtil.getSalt();
            String password =EncryptUtil.encryptPassword(newPwd,salt);
            user.setPassword(password);
            user.setSalt(salt);
            if (baseMapper.updateById(user) != 1) {
                DynamicConfigException.throwMsg("修改失败");
            }
        }else{
            throw new DynamicConfigException("请刷新重试");
        }
    }

    @Override
    public Object login(String account, String password) {

        SysUserEntity userInfo = baseMapper.selectOne(new QueryWrapper<SysUserEntity>().eq("account", account));
        if (userInfo == null) {
            DynamicConfigException.throwMsg("用户名或者密码错误,请重新输入");
        }
        if (userInfo.getStatus() == 0) {
            throw new DynamicConfigException("用户已关闭");
        }
        String salt = userInfo.getSalt();
        String encryptPassword = EncryptUtil.encryptPassword(password, salt);
        if (!userInfo.getPassword().equals(encryptPassword)) {
            DynamicConfigException.throwMsg("用户名或者密码错误,请重新输入");
        }

        SysRoleEntity roleInfo =userInfo.getRoleId()==null?null : sysRoleMapper.selectById(userInfo.getRoleId());
        String roleName ="";
        Long roleId = null;
        if (roleInfo!=null){
            roleName =roleInfo.getRoleName();
            roleId = roleInfo.getId();
        }
        String token = JwtTokenUtil.createToken(userInfo.getId(),userInfo.getUserName(),roleId,roleName,userInfo.getSuperAdmin()==1,userInfo.getCompanyId());
        userLoginService.setCookie(token);


        return null;
    }

    @Override
    public void add(SysUserEntity entity) {
       String salt = EncryptUtil.getSalt();
       entity.setSalt(salt);
       entity.setPassword(EncryptUtil.encryptPassword(entity.getPassword(),salt));
       baseMapper.insert(entity);
    }

    @Override
    public void edit(SysUserEntity entity) {
        String salt = EncryptUtil.getSalt();
        entity.setSalt(salt);
        entity.setPassword(EncryptUtil.encryptPassword(entity.getPassword(),salt));
        baseMapper.updateById(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void register(SysUserEntity entity) {



    }


}