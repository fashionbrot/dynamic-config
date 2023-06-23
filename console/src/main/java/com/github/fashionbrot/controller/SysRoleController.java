package com.github.fashionbrot.controller;


import com.github.fashionbrot.annotation.MarsLog;
import com.github.fashionbrot.annotation.MarsPermission;
import com.github.fashionbrot.entity.SysRoleEntity;
import com.github.fashionbrot.request.SysRoleRequest;
import com.github.fashionbrot.response.Response;
import com.github.fashionbrot.service.SysRoleService;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


/**
 * 角色表
 *
 * @author fashionbrot
 */

@MarsPermission(value="sys:role")
@Controller
@RequestMapping("sys/role")
@Api(tags="角色")
@ApiSort(23744266)
@RequiredArgsConstructor
public class SysRoleController  {


    final SysRoleService service;


    @MarsPermission(":index")
    @GetMapping("/index")
    public String index(){
        return "system/role/role";
    }

    @GetMapping("/index/add")
    public String indexAdd(){
        return "system/role/add";
    }

    @GetMapping("/index/edit")
    public String indexEdit(){
        return "system/role/edit";
    }



    @MarsPermission(":page")
    @ApiOperation("分页列表")
    @GetMapping("/page")
    @ResponseBody
    public Response pageReq(SysRoleRequest req) {
        return Response.success(service.pageReq(req));
    }



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
    public Response add(@RequestBody SysRoleEntity entity){
        service.add(entity);
        return Response.success();
    }


    @MarsLog
    @MarsPermission(":updateById")
    @ApiOperation("修改")
    @PostMapping("/updateById")
    @ResponseBody
    public Response updateById(@RequestBody SysRoleEntity entity){
        service.edit(entity);
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





}