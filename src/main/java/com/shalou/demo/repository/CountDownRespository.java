package com.shalou.demo.repository;

import com.shalou.demo.domain.CountDown;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

//用户表数据接口
public interface CountDownRespository extends JpaRepository<CountDown, Integer>, JpaSpecificationExecutor<CountDown> {
    //通过id查询用户
    public CountDown findCountDownById(Integer id);
}
