package com.shalou.demo.service;

import com.shalou.demo.domain.User;
import com.shalou.demo.repository.UserRepository;
import com.shalou.demo.utils.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;


//设置service
@Service
public class UserService {

    //引入日志
    private final static Logger logger = LoggerFactory.getLogger(UserService.class);

    //自动装配
    @Autowired
    private UserRepository userRepository;

    //设置API res (添加用户)
    @ResponseBody
    public Object addUser(User user) throws Exception {

        /*
        1.判断是否已有同名用户
        2.判断是否已注册相同手机号
        3.判断是否已注册相同邮箱
        4.id自增(不用提交ID)
        */

        //判断某个值是否为空(比如用户名),设置是否非必填字段

        //通过用户名来查询
        List userNameOne = userRepository.findUserByUserName(user.getUserName());
        //如果用户名查询的结果不为空
        if (!userNameOne.isEmpty() && userNameOne != null) {
            return ResultUtil.error(-1, "用户名已存在");
        }
        //通过手机号来查询
        List userPhoneNumOne = userRepository.findUserByPhoneNum(user.getPhoneNum());
        //如果手机号查询的结果不为空
        if (!userPhoneNumOne.isEmpty() && userPhoneNumOne != null) {
            return ResultUtil.error(-1, "该手机号已注册");
        }
        //通过邮箱来查询
        List userEmailOne = userRepository.findUserByUserEmail(user.getUserEmail());
        //如果邮箱查询的结果不为空
        if (!userEmailOne.isEmpty() && userEmailOne != null) {
            return ResultUtil.error(-1, "该邮箱已注册");
        }

        //校验过后存储到数据库
        user.setUserName(user.getUserName());
        user.setPhoneNum(user.getPhoneNum());
        user.setUserEmail(user.getUserEmail());
        user.setSex(user.getSex());
        user.setCity(user.getCity());
        user.setStatus(user.getStatus());
        userRepository.save(user);

        //返回 res
        return ResultUtil.success();

    }

    //设置API res (查询用户)(多条件单表分页查询)
    @ResponseBody
    public Specification<User> queryUser(User user, Integer pageIndex, Integer pageSize) throws Exception {
        //设置条件查询
        Specification<User> specification = new Specification<User>() {
            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

                //设置res数组
                List<Predicate> predicates = new ArrayList<>();

                //设置查询参数
                Path userName = root.get("userName");//设置用户名
                Path phoneNum = root.get("phoneNum");//设置手机号码
                Path userEmail = root.get("userEmail");//设置邮箱
                Path city = root.get("city");//设置城市
                Path sex = root.get("sex");//设置性别
                Path status = root.get("status");//设置状态

                //设置查询条件和获取对应结果

                //如果用户名不为空
                if (user.getUserName() != null && user.getUserName().equals("") && !user.getUserName().isEmpty()) {
                    Predicate name = criteriaBuilder.equal(userName, user.getUserName());
                    predicates.add(name);
                }

                //如果手机号码不为空
                if (user.getPhoneNum() != null && user.getPhoneNum().equals("") && !user.getPhoneNum().isEmpty()) {
                    Predicate phone = criteriaBuilder.equal(phoneNum, user.getPhoneNum());
                    predicates.add(phone);
                }

                //如果邮箱不为空
                if (user.getUserEmail() != null && user.getUserEmail().equals("") && !user.getUserEmail().isEmpty()) {
                    Predicate email = criteriaBuilder.equal(userEmail, user.getUserEmail());
                    predicates.add(email);
                }

                //如果城市不为空
                if (user.getCity() != null && user.getCity().equals("") && !user.getCity().isEmpty()) {
                    Predicate citys = criteriaBuilder.equal(city, user.getCity());
                    predicates.add(citys);
                }

                //如果性别不为空
                if (user.getSex() != null && user.getSex() != 0) {
                    Predicate userSex = criteriaBuilder.equal(sex, user.getSex());
                    predicates.add(userSex);
                }

                //如果状态不为空
                if (user.getStatus() != null && user.getStatus() != 0) {
                    Predicate userStatus = criteriaBuilder.equal(status, user.getStatus());
                    predicates.add(userStatus);
                }

                //将所有条件获取的数据格式化并返回给Controller进行分页
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));

            }
        };

        //返回给controller
        return specification;
    }

    //编辑用户
    @ResponseBody
    public Object editUser(User user, Integer id) throws Exception {

        // 通过Id获取当前被编辑的用户信息
        User userIdOne = userRepository.findUserById(id);
        logger.info(userIdOne.toString());

        //通过用户名来查询
        List userNameOne = userRepository.findUserByUserName(user.getUserName());
        //如果用户名查询的结果不为空
        if (!userNameOne.isEmpty() && userNameOne != null) {
            //如果当前传入的用户名和修改前的用户名一致则不做处理
            if (userIdOne.getUserName().equals(user.getUserName())) {
                logger.info("用户名未修改");
            }
            //如果用户名在数据库已有且与修改前不一致则提示已存在同样的名称
            else {
                return ResultUtil.error(-1, "用户名已存在");
            }
        }
        //通过手机号来查询
        List userPhoneNumOne = userRepository.findUserByPhoneNum(user.getPhoneNum());
        //如果手机号查询的结果不为空
        if (!userPhoneNumOne.isEmpty() && userPhoneNumOne != null) {
            //如果当前传入的手机号和修改前的手机号一致则不做处理
            if (userIdOne.getPhoneNum().equals(user.getPhoneNum())) {
                logger.info("手机号未修改");
            }
            //如果用户名在数据库已有且与修改前不一致则提示已存在同样的名称
            else {
                return ResultUtil.error(-1, "该手机号已注册");
            }
        }
        //通过邮箱来查询
        List userEmailOne = userRepository.findUserByUserEmail(user.getUserEmail());
        //如果邮箱查询的结果不为空
        if (!userEmailOne.isEmpty() && userEmailOne != null) {
            //如果当前传入的手机号和修改前的手机号一致则不做处理
            if (userIdOne.getUserEmail().equals(user.getUserEmail())) {
                logger.info("邮箱未修改");
            }
            //如果用户名在数据库已有且与修改前不一致则提示已存在同样的名称
            else {
                return ResultUtil.error(-1, "该邮箱已注册");
            }
        }

        //创建一个新的用户(用户编辑保存)
        User users = new User();
        users.setId(user.getId());
        users.setUserName(user.getUserName());
        users.setUserEmail(user.getUserEmail());
        users.setPhoneNum(user.getPhoneNum());
        users.setCity(user.getCity());
        users.setSex(user.getSex());
        users.setStatus(user.getStatus());
        userRepository.save(users);

        //返回res信息
        return ResultUtil.success();
    }

    //删除单个用户
    @ResponseBody
    public Object deleteUser(Integer id) throws Exception {
        userRepository.deleteById(id);
        return ResultUtil.success();
    }

}
