package com.github.fashionbrot.controller;


import com.github.fashionbrot.annotation.MarsLog;
import com.github.fashionbrot.annotation.MarsPermission;
import com.github.fashionbrot.entity.AppEntity;
import com.github.fashionbrot.request.AppRequest;
import com.github.fashionbrot.response.Response;
import com.github.fashionbrot.service.AppService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


/**
 * 应用表
 *
 * @author fashionbrot
 * @email fashionbrot@163.com
 * @date 2021-04-18
 */

@MarsPermission(value="m:app")
@Controller
@RequestMapping("m/app")
@Api(tags="应用表")
public class AppController {

    /**
     * 权限 注解 MarsPermission
     * 默认接口以下
     * 分页       m/app/page        权限：m:app:page
     * 数据列表    m/app/queryList   权限：m:app:queryList
     * 根据id查询  m/app/selectById  权限：m:app:selectById
     * 新增       m/app/insert      权限：m:app:insert
     * 修改       m/app/updateById  权限：m:app:updateById
     * 根据id删除  m/app/deleteById  权限：m:app:deleteById
     * 多个id删除  m/app/deleteByIds 权限：m:app:deleteByIds
     */


    @Autowired
    public AppService service;

    @MarsPermission(":index")
    @GetMapping("/index")
    public String index(){
        return "/m/app/index";
    }

    @MarsPermission(":add")
    @GetMapping("/add")
    public String add(){
        return "/m/app/add";
    }

    @MarsPermission(":edit")
    @GetMapping("/edit")
    public String edit(){
        return "/m/app/edit";
    }



    @ApiOperation("分页列表")
    @GetMapping("/page")
    @ResponseBody
    public Response pageReq(AppRequest req) {
        return Response.success(service.pageReq(req));
    }


    @ApiOperation("数据列表")
    @GetMapping("/queryList")
    @ResponseBody
    public Response queryList(@RequestParam Map<String, Object> params){
        return  Response.success(service.listByMap(params));
    }


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
    public Response add(@RequestBody AppEntity entity){
        service.save(entity);
        return Response.success();
    }


    @MarsLog
    @MarsPermission(":updateById")
    @ApiOperation("修改")
    @PostMapping("/updateById")
    @ResponseBody
    public Response updateById(@RequestBody AppEntity entity){
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



}