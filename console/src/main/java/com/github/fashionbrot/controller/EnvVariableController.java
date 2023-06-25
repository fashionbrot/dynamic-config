package com.github.fashionbrot.controller;


import com.github.fashionbrot.annotation.MarsLog;
import com.github.fashionbrot.annotation.MarsPermission;
import com.github.fashionbrot.entity.EnvVariableEntity;
import com.github.fashionbrot.request.EnvVariableRequest;
import com.github.fashionbrot.response.Response;
import com.github.fashionbrot.service.EnvVariableService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


/**
 * 常量表
 *
 * @author fashionbrot
 * @email fashionbrot@163.com
 * @date 2021-04-18
 */

@MarsPermission(value="m:env:variable")
@Controller
@RequestMapping("m/env/variable")
@Api(tags="常量表")
@RequiredArgsConstructor
public class EnvVariableController  {




    final EnvVariableService service;

    @MarsPermission(":index")
    @GetMapping("/index")
    public String index(){
        return "m/variable/index";
    }

    @MarsPermission(":add")
    @GetMapping("/add")
    public String add(){
        return "m/variable/add";
    }

    @MarsPermission(":edit")
    @GetMapping("/edit")
    public String edit(){
        return "m/variable/edit";
    }


    @MarsPermission(":index")
    @ApiOperation("分页列表")
    @GetMapping("/page")
    @ResponseBody
    public Response pageReq(EnvVariableRequest req) {
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
    public Response add(@RequestBody EnvVariableEntity entity){
        service.add(entity);
        return Response.success();
    }


    @MarsLog
    @MarsPermission(":updateById")
    @ApiOperation("修改")
    @PostMapping("/updateById")
    @ResponseBody
    public Response updateById(@RequestBody EnvVariableEntity entity){
        service.edit(entity);
        return Response.success();
    }


    @MarsLog
    @MarsPermission(":deleteById")
    @ApiOperation("根据id删除")
    @PostMapping("/deleteById")
    @ResponseBody
    public Response deleteById(Long id){
        service.deleteById(id);
        return Response.success();
    }





}