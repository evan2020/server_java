package com.shalou.demo.controller;

import com.shalou.demo.domain.CountDown;
import com.shalou.demo.repository.CountDownRespository;
import com.shalou.demo.utils.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

//设置API注解
@Controller
public class CountDownController {
    //引入日志
    private final static Logger logger = LoggerFactory.getLogger(CountDownController.class);

    @Autowired
    private CountDownRespository countDownRespository;

    //添加时间卡片
    @GetMapping(value = "/timer/addCard")
    @ResponseBody
    public Object addCard(CountDown countDown) {
        //设置事件载体
        CountDown countDownNew = new CountDown();
        //获取事件名称
        countDownNew.setItemName(countDown.getItemName());
        //获取目标时间
        countDownNew.setTargetDate(countDown.getTargetDate());
        //获取用户的openId
        countDownNew.setOpenId(countDown.getOpenId());
        //默认分类类型为综合
        countDownNew.setClassIfy("综合");
        //默认为不置顶
        countDownNew.setChecked("0");
        //默认为不重复
        countDownNew.setIsRepeat("0");
        //默认为不提醒
        countDownNew.setIsRemind("0");
        //保存到数据库
        countDownRespository.save(countDownNew);

        //返回添加成功
        return ResultUtil.success("添加成功");
    }

    //删除当前卡片
    @GetMapping(value = "/timer/delCard")
    @ResponseBody
    public Object delCard(@RequestParam("cardId") Integer cardId) {
        //根据卡片id来删除当前卡片
        countDownRespository.deleteById(cardId);
        return ResultUtil.success("删除成功");
    }

    //查询所有卡片
    @GetMapping(value = "/timer/findAllCard")
    @ResponseBody
    public Object findAllCard(CountDown countDown) {
        //查询所有
        List<CountDown> countDowns = countDownRespository.findAllByOpenId(countDown.getOpenId());
        return ResultUtil.success(countDowns);
    }

    //编辑卡片
    @GetMapping(value = "/timer/editCard")
    @ResponseBody
    public Object editCard(CountDown countDown) {
        //根据id获取对应的信息
        CountDown countDownOne = countDownRespository.findCountDownById(countDown.getId());

        //新建一个实体数据
        CountDown countDownNew = new CountDown();
        //设置id(根据id为单个保存-覆盖)
        countDownNew.setId(countDown.getId());
        //修改事件名称
        countDownNew.setItemName(countDown.getItemName());
        //修改目标日期
        countDownNew.setTargetDate(countDown.getTargetDate());
        //保留初始的创建时间
        countDownNew.setCreateTime(countDownOne.getCreateTime());
        //保留初始的openID
        countDownNew.setOpenId(countDownOne.getOpenId());
        //默认分类类型为综合
        countDownNew.setClassIfy("综合");
        //默认为不置顶
        countDownNew.setChecked("0");
        //默认为不重复
        countDownNew.setIsRepeat("0");
        //默认为不提醒
        countDownNew.setIsRemind("0");
        //保存到数据库
        countDownRespository.save(countDownNew);

        return ResultUtil.success("编辑成功");
    }
}
