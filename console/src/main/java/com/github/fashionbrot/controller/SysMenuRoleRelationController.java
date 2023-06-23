package com.github.fashionbrot.controller;


import com.github.fashionbrot.annotation.MarsLog;
import com.github.fashionbrot.annotation.MarsPermission;
import com.github.fashionbrot.entity.SysMenuRoleRelationEntity;
import com.github.fashionbrot.request.SysMenuRoleRelationRequest;
import com.github.fashionbrot.response.Response;
import com.github.fashionbrot.service.SysMenuRoleRelationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiSort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;


/**
 * 菜单-角色关系表
 *
 * @author fashionbrot
 */

@MarsPermission(value="sys:menu:role:relation")
@Controller
@RequestMapping("sys/menu/role/relation")
@Api(tags="菜单-角色关系表")
@ApiSort(23744272)
@RequiredArgsConstructor
public class SysMenuRoleRelationController  {

    /**
     * 权限 注解 MarsPermission
     * 默认接口以下
     * 分页       sys/menu/role/relation/page        权限：sys:menu:role:relation:page
     * 数据列表    sys/menu/role/relation/queryList   权限：sys:menu:role:relation:queryList
     * 根据id查询  sys/menu/role/relation/selectById  权限：sys:menu:role:relation:selectById
     * 新增       sys/menu/role/relation/insert      权限：sys:menu:role:relation:insert
     * 修改       sys/menu/role/relation/updateById  权限：sys:menu:role:relation:updateById
     * 根据id删除  sys/menu/role/relation/deleteById  权限：sys:menu:role:relation:deleteById
     * 多个id删除  sys/menu/role/relation/deleteByIds 权限：sys:menu:role:relation:deleteByIds
     */


    final SysMenuRoleRelationService service;



    @MarsPermission(":page")
    @ApiOperation("分页列表")
    @GetMapping("/page")
    @ResponseBody
    public Response pageReq(SysMenuRoleRelationRequest req) {
        return Response.success(service.pageReq(req));
    }


    @MarsPermission(":queryList")
    @ApiOperation("数据列表")
    @GetMapping("/queryList")
    @ResponseBody
    public Response queryList(@RequestParam Map<String, Object> params){
        return  Response.success(service.listByMap(params));
    }


    @MarsPermission(":selectById")
    @ApiOperation("根据id查询")
    @PostMapping("/selectById")
    @ResponseBody
    public Response selectById(Long id){
        return Response.success(service.getById(id));
    }

    @MarsLog
    @MarsPermission(":insert")
    @ApiOperation("新增")
    @PostMapping("/insert")
    @ResponseBody
    public Response add(@RequestBody SysMenuRoleRelationEntity entity){
        service.save(entity);
        return Response.success();
    }


    @MarsLog
    @MarsPermission(":updateById")
    @ApiOperation("修改")
    @PostMapping("/updateById")
    @ResponseBody
    public Response updateById(@RequestBody SysMenuRoleRelationEntity entity){
        service.updateById(entity);
        return Response.success();
    }


    @MarsLog
    @MarsPermission(":deleteById")
    @ApiOperation("根据id删除")
    @PostMapping("/deleteById")
    @ResponseBody
    public Response deleteById(Long id){
        service.removeById(id);
        return Response.success();
    }


    @MarsLog
    @MarsPermission(":deleteByIds")
    @ApiOperation("批量删除")
    @PostMapping("/deleteByIds")
    @ResponseBody
    public Response delete(@RequestBody Long[] ids){
        service.removeByIds(Arrays.asList(ids));
        return Response.success();
    }



}