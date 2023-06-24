package com.github.fashionbrot.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.fashionbrot.entity.SystemConfigEntity;
import com.github.fashionbrot.entity.SystemConfigHistoryEntity;
import com.github.fashionbrot.entity.SystemReleaseEntity;
import com.github.fashionbrot.enums.RespEnum;
import com.github.fashionbrot.enums.SystemRoleEnum;
import com.github.fashionbrot.exception.DynamicConfigException;
import com.github.fashionbrot.mapper.SystemConfigHistoryMapper;
import com.github.fashionbrot.mapper.SystemConfigMapper;
import com.github.fashionbrot.mapper.SystemConfigRoleRelationMapper;
import com.github.fashionbrot.mapper.SystemReleaseMapper;
import com.github.fashionbrot.model.LoginModel;
import com.github.fashionbrot.request.DataConfigRequest;
import com.github.fashionbrot.request.SystemConfigApiRequest;
import com.github.fashionbrot.request.SystemConfigRequest;
import com.github.fashionbrot.response.ForDataListResponse;
import com.github.fashionbrot.response.ForDataResponse;
import com.github.fashionbrot.response.PageResponse;
import com.github.fashionbrot.service.SystemConfigCacheService;
import com.github.fashionbrot.service.SystemConfigService;
import com.github.fashionbrot.service.UserLoginService;
import com.github.fashionbrot.util.ObjectUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * 应用系统配置表
 *
 * @author fashionbrot
 * @email fashionbrot@163.com
 * @date 2021-07-12
 */
@SuppressWarnings("ALL")
@Service
@RequiredArgsConstructor
public class SystemConfigServiceImpl extends ServiceImpl<SystemConfigMapper, SystemConfigEntity> implements SystemConfigService {


    final UserLoginService userLoginService;
    final SystemConfigHistoryMapper systemConfigHistoryMapper;
    final SystemConfigRoleRelationMapper systemConfigRoleRelationMapper;
    final SystemReleaseMapper systemReleaseMapper;
    final SystemConfigCacheService systemConfigCacheService;

    private static final String SYSTEM_CONFIG_DEL = "[del]";
    private static final String SEQUENCE_NAME="system";

    @Autowired
    private Environment environment;


    /**
     * 判断是否有权限
     *
     * @param systemConfigId
     * @param roleEnum
     */
    public void checkRole(Long systemConfigId, SystemRoleEnum roleEnum) {
        /*LoginModel login = userLoginService.getLogin();
        if (!login.isSuperAdmin()) {
            QueryWrapper q = new QueryWrapper();
            q.eq("role_id", login.getRoleId());
            q.eq("system_config_id", systemConfigId);
            SystemConfigRoleRelationEntity systemConfigRoleRelationEntity = systemConfigRoleRelationMapper.selectOne(q);
            if (systemConfigRoleRelationEntity == null) {
                throw new MarsException(RespEnum.NOT_PERMISSION_ERROR);
            }
            if ("0".equals(systemConfigRoleRelationEntity.getPermission().toString().charAt(roleEnum.getCode()))) {
                throw new MarsException(RespEnum.NOT_PERMISSION_ERROR);
            }
        }*/
    }

    /**
     * 更新发布表
     */
    public void updateRelease(String envCode, String appCode, String fileName) {
        QueryWrapper releaseQuery = new QueryWrapper();
        releaseQuery.eq("env_code", envCode);
        releaseQuery.eq("app_code", appCode);
        releaseQuery.eq("release_flag",0);


        SystemReleaseEntity systemReleaseEntity = systemReleaseMapper.selectOne(releaseQuery);
        if (systemReleaseEntity == null) {
            SystemReleaseEntity releaseEntity = SystemReleaseEntity.builder()
                    .envCode(envCode)
                    .appCode(appCode)
                    .releaseFlag(0)
                    .files(fileName)
                    .build();
            systemReleaseMapper.insert(releaseEntity);
        }else{

            String oldKeys = systemReleaseEntity.getFiles();
            if (StringUtils.isNotEmpty(oldKeys)){
                oldKeys=oldKeys+","+fileName;
                List<String> keys = Arrays.stream(oldKeys.split(",")).distinct().collect(Collectors.toList());
                if (CollectionUtils.isNotEmpty(keys)){
                    oldKeys = String.join(",",keys);
                }
            }else{
                oldKeys = fileName;
            }
            systemReleaseEntity.setFiles(oldKeys);
            systemReleaseMapper.updateById(systemReleaseEntity);
        }
    }

    @Override
    public Object pageReq(SystemConfigRequest req) {
        Page<?> page = PageHelper.startPage(req.getPageNum(), req.getPageSize());
        QueryWrapper q=new QueryWrapper();
        q.select("        id,app_code,env_code,modifier,file_name,file_desc,file_type,status,create_date,update_date ");
        if (ObjectUtil.isNotEmpty(req.getEnvCode())){
            q.eq("env_code",req.getEnvCode());
        }
        if (ObjectUtil.isNotEmpty(req.getAppCode())){
            q.eq("app_code",req.getAppCode());
        }
        List<SystemConfigEntity> listByMap = baseMapper.selectList(q);

        return PageResponse.<SystemConfigEntity>builder()
                .rows(listByMap)
                .total(page.getTotal())
                .build();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(SystemConfigEntity entity) {
        QueryWrapper q = new QueryWrapper();
        q.eq("env_code", entity.getEnvCode());
        q.eq("app_code", entity.getAppCode());
        q.eq("file_name", entity.getFileName());
        if (baseMapper.selectCount(q) > 0) {
            throw new DynamicConfigException(entity.getFileName() + "已存在，请重新填写");
        }

        LoginModel login = userLoginService.getLogin();
        entity.setStatus(1);
        entity.setModifier(login.getUserName());
        baseMapper.insert(entity);

        updateRelease(entity.getEnvCode(),entity.getAppCode(),entity.getFileName());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void edit(SystemConfigEntity entity) {
        SystemConfigEntity old = baseMapper.selectById(entity.getId());

        checkRole(entity.getId(), SystemRoleEnum.EDIT);

        QueryWrapper q = new QueryWrapper();
        q.eq("env_code", entity.getEnvCode());
        q.eq("app_code", entity.getAppCode());
        q.eq("file_name", entity.getFileName());
        SystemConfigEntity systemConfigEntity = baseMapper.selectOne(q);
        if (systemConfigEntity != null && systemConfigEntity.getId().longValue() != systemConfigEntity.getId().longValue()) {
            throw new DynamicConfigException(entity.getFileName() + "已存在，请重新填写");
        }
        LoginModel login = userLoginService.getLogin();
        entity.setStatus(2);
        entity.setModifier(login.getUserName());
        baseMapper.updateById(entity);

        if (ObjectUtil.isNotEmpty(old.getJson()) && !old.getJson().equals(entity.getTempJson())) {

            SystemConfigHistoryEntity historyEntity = SystemConfigHistoryEntity.builder().build();
            BeanUtils.copyProperties(old, historyEntity);
            historyEntity.setTempJson(entity.getTempJson());
            systemConfigHistoryMapper.insert(historyEntity);

            updateRelease(old.getEnvCode(), old.getAppCode(), old.getFileName());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(Long id) {
        SystemConfigEntity entity = baseMapper.selectById(id);
        if (entity.getStatus() == 1) {
            baseMapper.deleteById(id);
            return;
        }

        checkRole(entity.getId(), SystemRoleEnum.DEL);

        updateRelease(entity.getEnvCode(), entity.getAppCode(), entity.getFileName() + SYSTEM_CONFIG_DEL);

        LoginModel login = userLoginService.getLogin();
        entity.setStatus(3);
        entity.setModifier(login.getUserName());
        baseMapper.updateById(entity);

        SystemConfigHistoryEntity historyEntity = SystemConfigHistoryEntity.builder().build();
        BeanUtils.copyProperties(entity, historyEntity);
        systemConfigHistoryMapper.insert(historyEntity);
    }

    @Override
    public Object selectById(Long id) {

        checkRole(id, SystemRoleEnum.VIEW);

        SystemConfigEntity entity = baseMapper.selectById(id);
        if (entity != null) {
            if (entity.getStatus() == 1 || entity.getStatus() == 2 || entity.getStatus()==5) {
                entity.setJson(entity.getTempJson());
            }
        }
        return entity;
    }

    @Override
    public void undel(Long id) {
        SystemConfigEntity entity = baseMapper.selectById(id);
        entity.setStatus(4);
        baseMapper.updateById(entity);
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public void releaseConfig(SystemConfigEntity req) {


        QueryWrapper q = new QueryWrapper<SystemReleaseEntity>()
                .eq("env_code", req.getEnvCode())
                .eq("app_code", req.getAppCode())
                .eq("release_flag", 0);
        SystemReleaseEntity  release = systemReleaseMapper.selectOne(q);
        if (release==null) {
            throw new DynamicConfigException("没有要发布的配置");
        }

        long nowNextValue = release.getId();

        //更新 SystemConfigEntity表 新增 编辑 状态为发布
        baseMapper.updateRelease(SystemConfigRequest.builder()
                .envCode(req.getEnvCode())
                .appCode(req.getAppCode())
                .updateReleaseStatus(4)
                .whereReleaseStatus(Arrays.asList(1, 2))
                .build());
        //更新 SystemConfigEntity表 删除 状态为 删除
        deleteByReleaseStatus(req.getEnvCode(), req.getAppCode(), 3);


        //更新 release
        SystemReleaseEntity updateRelease = SystemReleaseEntity.builder()
                .releaseFlag(1)
                .build();
        if (systemReleaseMapper.update(updateRelease, q) <=0) {
            throw new DynamicConfigException(RespEnum.FAIL);
        }

        String key = getKey(req.getEnvCode(), req.getAppCode());
        systemConfigCacheService.setCache(key,nowNextValue );


    }

    private void deleteByReleaseStatus(String envCode, String appName, Integer whereReleaseStatus) {
        QueryWrapper qq = new QueryWrapper();
        qq.eq("env_code", envCode);
        qq.eq("app_code", appName);
        qq.eq("status", whereReleaseStatus);
        baseMapper.delete(qq);
    }

    private String getKey(String env, String app) {
        return env + app;
    }



    @Override
    public long checkForUpdate(DataConfigRequest req) {
        String key = systemConfigCacheService.getKey(req.getEnvCode(), req.getAppCode());
        if (systemConfigCacheService.containsKey(key)) {
            return systemConfigCacheService.getCache(key);
        }

        Long version = systemReleaseMapper.getTopReleaseId(req.getEnvCode(), req.getAppCode(), 1);
        if (version == null) {
            version = 0L;
        } else {
            systemConfigCacheService.setCache(key, version);
        }

        return version;
    }

    @Override
    public ForDataListResponse forDataVo(DataConfigRequest req) {
        String key = systemConfigCacheService.getKey(req.getEnvCode(), req.getAppCode());
        QueryWrapper q = new QueryWrapper();
        q.select("file_name,file_type,json");
        q.eq("env_code", req.getEnvCode());
        q.eq("app_code", req.getAppCode());
        SystemReleaseEntity release = null;
        List<String> delKeyList = null;
        List<String> keyList = null;
        //如果是客户端第一次调用,并且 本地缓存没有最新的version，则进行数据库查询
        if (req.getFirst() != null && req.getFirst()) {
            if (!systemConfigCacheService.containsKey(key)) {
                Long version = systemReleaseMapper.getTopReleaseId(req.getEnvCode(), req.getAppCode(), 1);
                if (version != null) {
                    systemConfigCacheService.setCache(key, version);
                }
            }

            List<SystemConfigEntity> list = baseMapper.selectList(q);
            List<ForDataResponse> forDataVoList = null;
            if (ObjectUtil.isNotEmpty(list)) {
                forDataVoList = list.stream()
                        .filter(m -> ObjectUtil.isNotEmpty(m.getJson()))
                        .map(m -> changeForData(m))
                        .collect(Collectors.toList());
            }
            Long lastVersion = systemConfigCacheService.getCache(key);
            return ForDataListResponse.builder()
                    .version(lastVersion)
                    .list(forDataVoList)
                    .build();
        } else {

            release = systemReleaseMapper.selectById(req.getVersion());
            if (release!=null){
                List<String> stringStream =  Arrays.stream(release.getFiles().split(",")).collect(Collectors.toList());
                keyList = stringStream.stream().filter(k-> !k.endsWith(SYSTEM_CONFIG_DEL)).collect(Collectors.toList());
                delKeyList = stringStream.stream().filter(k-> k.endsWith(SYSTEM_CONFIG_DEL)).map(k-> k.replace(SYSTEM_CONFIG_DEL,"")).collect(Collectors.toList());
                if (ObjectUtil.isNotEmpty(keyList)){
                    q.in("file_name",keyList);
                }
            }
            List<SystemConfigEntity> list = null;
            if (ObjectUtil.isNotEmpty(keyList)) {
                list = baseMapper.selectList(q);
            }

            List<ForDataResponse>  forDataVoList = new ArrayList<>();
            if (ObjectUtil.isNotEmpty(list)){
                forDataVoList = list.stream()
                        .filter(m-> ObjectUtil.isNotEmpty(m.getJson()))
                        .map(m-> changeForData(m))
                        .collect(Collectors.toList());
            }
            addDelKey(delKeyList, forDataVoList);


            Long lastVersion = systemConfigCacheService.getCache(key);
            return ForDataListResponse.builder()
                    .version(lastVersion)
                    .list(forDataVoList)
                    .build();
        }
    }

    private ForDataResponse changeForData(SystemConfigEntity info) {
        return ForDataResponse.builder()
                .fileName(info.getFileName())
                .fileType(info.getFileType())
                .content(info.getJson())
                .build();
    }

    private void addDelKey(List<String> delKeyList, List<ForDataResponse> forDataVoList) {
        if (ObjectUtil.isNotEmpty(delKeyList)) {
            for (int i = 0; i < delKeyList.size(); i++) {
                forDataVoList.add(ForDataResponse.builder()
                        .fileName(delKeyList.get(i))
                        .fileType("PROPERTIES")
                        .content("")
                        .delFlag(true)
                        .build());
            }
        }
    }

    @Override
    public Long clusterSync(SystemConfigApiRequest req) {
        String key = getKey(req.getEnvCode(),req.getAppCode());
        long version =req.getVersion().longValue();
        long v = 0;
        if (systemConfigCacheService.containsKey(key)){
            v = systemConfigCacheService.getCache(key).longValue();
        }
        if(v<version){
            systemConfigCacheService.setCache(key,req.getVersion());
        }
        return systemConfigCacheService.getCache(key);
    }
}