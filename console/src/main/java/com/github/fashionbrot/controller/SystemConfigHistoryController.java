package com.github.fashionbrot.controller;

import com.github.fashionbrot.annotation.MarsPermission;
import com.github.fashionbrot.entity.SystemConfigHistoryEntity;
import com.github.fashionbrot.request.SystemConfigHistoryRequest;
import com.github.fashionbrot.response.Response;
import com.github.fashionbrot.service.SystemConfigHistoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


/**
 * 应用系统配置历史表
 *
 * @author fashionbrot
 * @email fashionbrot@163.com
 * @date 2021-07-15
 */

@MarsPermission(value="m:system:config:history")
@Controller
@RequestMapping("m/system/config/history")
@Api(tags="应用系统配置历史表")
@RequiredArgsConstructor
public class SystemConfigHistoryController  {


    @Autowired
    public SystemConfigHistoryService service;

    @MarsPermission(":index")
    @GetMapping("/index")
    public String index(){
        return "m/systemConfigHistory/index";
    }

    @MarsPermission(":view")
    @GetMapping("/view")
    public String view(){
        return "m/systemConfigHistory/view";
    }

    @ApiOperation("分页列表")
    @GetMapping("/page")
    @ResponseBody
    public Response pageReq(SystemConfigHistoryRequest req) {
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

    @MarsPermission(":insert")
    @ApiOperation("新增")
    @PostMapping("/insert")
    @ResponseBody
    public Response add(@RequestBody SystemConfigHistoryEntity entity){
        service.save(entity);
        return Response.success();
    }


    @MarsPermission(":updateById")
    @ApiOperation("修改")
    @PostMapping("/updateById")
    @ResponseBody
    public Response updateById(@RequestBody SystemConfigHistoryEntity entity){
        service.updateById(entity);
        return Response.success();
    }


    @MarsPermission(":deleteById")
    @ApiOperation("根据id删除")
    @PostMapping("/deleteById")
    @ResponseBody
    public Response deleteById(Long id){
        service.removeById(id);
        return Response.success();
    }


    @MarsPermission(":rollback")
    @ApiOperation("回滚操作")
    @PostMapping("/rollback")
    @ResponseBody
    public Response rollback(Long id){
        service.rollback(id);
        return Response.success();
    }



}