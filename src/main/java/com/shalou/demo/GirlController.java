package com.shalou.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class GirlController {

    @Autowired
    private GirlRepository girlRepository;

    //查询所有女生
    @GetMapping(value = "/girls")
    public List<Girl> girlList(){
        return girlRepository.findAll();
    }
    //添加一个女生
    @PostMapping(value = "/girls")
    public Girl girlAdd(@RequestParam("cupSize") String cupSize,
                          @RequestParam("age") Integer age){
        Girl girl=new Girl();
        girl.setCupSize(cupSize);
        girl.setAge(age);

        return girlRepository.save(girl);
    }

    //查询一个女生
    @GetMapping(value = "/girls/{id}")
    public Girl girlFindOne(@PathVariable("id") Integer id){
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
}
