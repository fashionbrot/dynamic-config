package com.github.fashionbrot.controller;


import com.github.fashionbrot.annotation.MarsLog;
import com.github.fashionbrot.annotation.MarsPermission;
import com.github.fashionbrot.entity.SysMenuEntity;
import com.github.fashionbrot.request.SysMenuRequest;
import com.github.fashionbrot.response.Response;
import com.github.fashionbrot.service.SysMenuService;
import com.github.fashionbrot.service.UserLoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiSort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


/**
 * 菜单表
 *
 * @author fashionbrot
 */

@MarsPermission(value="sys:menu")
@Controller
@RequestMapping("sys/menu")
@Api(tags="菜单")
@ApiSort(23744266)
@RequiredArgsConstructor
public class SysMenuController  {



    final SysMenuService service;
    final UserLoginService sysUserService;

    @MarsPermission(":index")
    @GetMapping("/index")
    public String index() {
        return "system/menu/menu" ;
    }

    @GetMapping("/index/add")
    public String indexAdd() {
        return "system/menu/add" ;
    }

    @GetMapping("/index/edit")
    public String indexEdit() {
        return "system/menu/edit" ;
    }





    @MarsPermission(":page")
    @ApiOperation("分页列表")
    @GetMapping("/page")
    @ResponseBody
    public Response pageReq(SysMenuRequest req) {
        return Response.success(service.pageReq(req));
    }


    @MarsPermission(":queryList")
    @ApiOperation("数据列表")
    @GetMapping("/queryList")
    @ResponseBody
    public Response queryList(@RequestParam Map<String, Object> params){
        return  Response.success(service.listByMap(params));
    }

    @MarsPermission(":queryList")
    @ApiOperation("数据列表")
    @GetMapping("/queryList2")
    @ResponseBody
    public List  queryList2(@RequestParam Map<String, Object> params){
        return  service.listByMap(params);
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
    public Response add(@RequestBody SysMenuEntity entity){
        service.save(entity);

        return Response.success();
    }


    @MarsLog
    @MarsPermission(":updateById")
    @ApiOperation("修改")
    @PostMapping("/updateById")
    @ResponseBody
    public Response updateById(@RequestBody SysMenuEntity entity){
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





    @RequestMapping("loadAllMenu")
    @ResponseBody
    public List<SysMenuEntity> loadAllMenu(Long roleId, Integer isShowCode) {
        return service.loadMenuAll(roleId, isShowCode);
    }


}