package com.shalou.demo.repository;

import com.shalou.demo.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

//用户表数据接口
public interface UserRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {

    //通过用户名来查询用户
    public List<User> findUserByUserName(String name);

    //通过手机号查询用户
    public List<User> findUserByPhoneNum(Long phoneName);

    //通过邮箱来查询用户
    public List<User> findUserByUserEmail(String userEmail);

}
