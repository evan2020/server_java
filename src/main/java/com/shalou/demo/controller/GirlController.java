package com.shalou.demo.controller;

import com.shalou.demo.result.Result;
import com.shalou.demo.repository.GirlRepository;
import com.shalou.demo.domain.Girl;
import com.shalou.demo.service.GirlService;
import com.shalou.demo.utils.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

//设置resultApi注解
@RestController
public class GirlController {

    //引入日志
    private final static Logger logger= LoggerFactory.getLogger(GirlController.class);

    //自动装配GirlRepository
    @Autowired
    private GirlRepository girlRepository;

    //自动装配GirlService
    @Autowired
    private GirlService girlService;

    //查询所有女生
    @GetMapping(value = "/girls")
    public List<Girl> girlList(){
        //使用findAll
        return girlRepository.findAll();
    }
    //添加一个女生(注意和下一个方法参数引用的区别)
   /* @PostMapping(value = "/girls")
    public Girl girlAdd(@RequestParam("cupSize") String cupSize,
                          @RequestParam("age") Integer age){
        Girl girl=new Girl();
        girl.setCupSize(cupSize);
        girl.setAge(age);
        return girlRepository.save(girl);
    }*/

    //添加一个女生
    @PostMapping(value = "/girls")
    public Result<Girl> girlAdd(@Valid Girl girl, BindingResult bindingResult){
        //表单校验
        if(bindingResult.hasErrors()){
            //打印错误信息
            System.out.println(bindingResult.getFieldError().getDefaultMessage());
            //返回错误信息
            return ResultUtil.error(1,bindingResult.getFieldError().getDefaultMessage());
        }else{
            girl.setCupSize(girl.getCupSize());
            girl.setAge(girl.getAge());
            //返回被添加的数据
            return ResultUtil.success(girlRepository.save(girl));
        }
    }

    //查询一个女生
    @GetMapping(value = "/girls/{id}")
    public Girl girlFindOne(@PathVariable("id") Integer id){
        //注意新版本使用findById以及get()方法,老版本的写法不一样
        return girlRepository.findById(id).get();
    }

    //更新一个女生
    @PutMapping(value = "/girlAdd")
    public void girlUpdate(@RequestParam("cupSize") String cupSize,
                           @RequestParam("age") Integer age,
                           @RequestParam("id") Integer id){
        Girl girl=new Girl();
        girl.setId(id);
        girl.setCupSize(cupSize);
        girl.setAge(age);
        girlRepository.save(girl);
    }

    //删除一个女生
    @PostMapping(value = "/girlsDelete")
    public String girlDelete(@RequestParam("id") Integer id){
        girlRepository.deleteById(id);
        return "delete one success";
    }

    //通过年龄查询
    @GetMapping(value = "/girlsQueryAge")
    public List<Girl> girlListByAge(@RequestParam("age") Integer age){
        return girlRepository.findGirlByAge(age);
    }

    //使用throws Exception对状态码进行统一管理
    @GetMapping(value = "/girlAge")
    public Object getGirlAge(@RequestParam("id") Integer id)throws Exception{
        ///使用girlService来处理
        // 在girlService层使用@ResponseBody返回获取的对象,然后在此方法获取再次返回
        return girlService.getAge(id);
    }
}
