package com.shalou.demo.controller;

import com.shalou.demo.domain.TotalModule;
import com.shalou.demo.repository.TotalModuleRespository;
import com.shalou.demo.service.TotalModuleService;
import com.shalou.demo.utils.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

//设置API注解
@RestController
public class TotalModuleController {

    //引入日志
    private final static Logger logger = LoggerFactory.getLogger(TotalModuleController.class);

    //自动装配接口
    @Autowired
    private TotalModuleRespository totalModuleRespository;

    //引入service
    @Autowired
    private TotalModuleService totalModuleService;

    //测试接口
    @GetMapping(value = "/totalModule/test")
    public String test() {
        return "测试totalModule接口";
    }

    //添加总的分类
    @PostMapping(value = "/totalModule/addModule")
    public Object addModule(TotalModule totalModule) throws Exception {
        return totalModuleService.addModule(totalModule);
    }

    //编辑总的分类
    @PostMapping(value = "/totalModule/editModule")
    public Object editModule(TotalModule totalModule) throws Exception {
        return totalModuleService.editModule(totalModule);
    }

    //删除当前分类
    @PostMapping(value = "/totalModule/deleteModule")
    public Object deleteModule(TotalModule totalModule) throws Exception {
        return totalModuleService.deleteModule(totalModule);
    }

    //查询当前分类
    @PostMapping(value = "/totalModule/queryModule")
    public Object queryModule(TotalModule totalModule,
                              @RequestParam("pageIndex") Integer pageIndex,
                              @RequestParam("pageSize") Integer pageSize) throws Exception {
        //设置分页(记得页码减一)
        PageRequest pageRequest = PageRequest.of(pageIndex - 1, pageSize);

        //获取条件查询和分页的结果
        Page<TotalModule> page = totalModuleRespository.findAll(totalModuleService.queryModule(totalModule, pageIndex, pageSize), pageRequest);

        //返回res
        return ResultUtil.success(page);
    }


}
