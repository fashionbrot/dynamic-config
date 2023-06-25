package com.github.fashionbrot.controller;

import com.github.fashionbrot.annotation.MarsPermission;
import com.github.fashionbrot.entity.SystemConfigEntity;
import com.github.fashionbrot.request.SystemConfigRequest;
import com.github.fashionbrot.response.Response;
import com.github.fashionbrot.service.SystemConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


/**
 * 应用系统配置表
 *
 * @author fashionbrot
 * @email fashionbrot@163.com
 * @date 2021-07-12
 */

@MarsPermission(value="m:system:config")
@Controller
@RequestMapping("m/system/config")
@Api(tags="应用系统配置表")
@RequiredArgsConstructor
public class SystemConfigController {



    @Autowired
    public SystemConfigService service;

    @MarsPermission(":index")
    @GetMapping("/index")
    public String index(){
        return "m/systemConfig/index";
    }

    @GetMapping("/add")
    public String add(){
        return "m/systemConfig/add";
    }

    @GetMapping("/edit")
    public String edit(){
        return "m/systemConfig/edit";
    }

    @GetMapping("/clone")
    public String clone(){
        return "m/systemConfig/clone";
    }




    @ApiOperation("分页列表")
    @GetMapping("/page")
    @ResponseBody
    public Response pageReq(SystemConfigRequest req) {
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
        return Response.success(service.selectById(id));
    }

    @MarsPermission(":insert")
    @ApiOperation("新增")
    @PostMapping("/insert")
    @ResponseBody
    public Response add(@RequestBody SystemConfigEntity entity){
        service.add(entity);
        return Response.success();
    }


    @MarsPermission(":updateById")
    @ApiOperation("修改")
    @PostMapping("/updateById")
    @ResponseBody
    public Response updateById(@RequestBody SystemConfigEntity entity){
        service.edit(entity);
        return Response.success();
    }


    @MarsPermission(":deleteById")
    @ApiOperation("根据id删除")
    @PostMapping("/deleteById")
    @ResponseBody
    public Response deleteById(Long id){
        service.deleteById(id);
        return Response.success();
    }

    @MarsPermission(":unDel")
    @ApiOperation("撤销删除")
    @PostMapping("/unDel")
    @ResponseBody
    public Response undel(Long id){
        service.undel(id);
        return Response.success();
    }


    @MarsPermission(":releaseConfig")
    @RequestMapping(value = "releaseConfig")
    @ResponseBody
    public Response releaseConfig(SystemConfigEntity req) {
        service.releaseConfig(req);
        return Response.success();
    }



}