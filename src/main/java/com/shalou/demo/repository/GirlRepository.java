package com.shalou.demo.repository;

import com.shalou.demo.domain.Girl;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

//数据接口
public interface GirlRepository extends JpaRepository<Girl,Integer> {

    //通过年龄来查询
    public List<Girl> findGirlByAge(Integer age);
}
