package com.shalou.demo.service;

import com.shalou.demo.domain.TotalModule;
import com.shalou.demo.repository.TotalModuleRespository;
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
public class TotalModuleService {

    //引入日志
    private final static Logger logger = LoggerFactory.getLogger(TotalModuleService.class);

    //自动装配接口
    @Autowired
    private TotalModuleRespository totalModuleRespository;

    //设置API

    //添加总的分类模块
    @ResponseBody
    public Object addModule(TotalModule totalModule) throws Exception {
        TotalModule totalModule1Add = new TotalModule();
        totalModule1Add.setCategoryName(totalModule.getCategoryName());
        totalModule1Add.setCoverImgUrl(totalModule.getCoverImgUrl());
        totalModule1Add.setWebUrl(totalModule.getWebUrl());
        totalModule1Add.setDescription(totalModule.getDescription());
        totalModule1Add.setRemarks(totalModule.getRemarks());
        totalModule1Add.setTitle(totalModule.getTitle());
        totalModule1Add.setSort(totalModule.getSort());

        totalModule1Add.setLevel(totalModule.getLevel());
        totalModule1Add.setType(totalModule.getType());
        totalModule1Add.setClickNum(totalModule.getClickNum());
        totalModule1Add.setForwardNum(totalModule.getForwardNum());
        totalModule1Add.setCollectionNum(totalModule.getCollectionNum());

        totalModuleRespository.save(totalModule1Add);
        return ResultUtil.success();
    }

    //编辑总的分类
    @ResponseBody
    public Object editModule(TotalModule totalModule) throws Exception {
        TotalModule totalModule1Edit = new TotalModule();
        totalModule1Edit.setId(totalModule.getId());
        totalModule1Edit.setCategoryName(totalModule.getCategoryName());
        totalModule1Edit.setCoverImgUrl(totalModule.getCoverImgUrl());
        totalModule1Edit.setWebUrl(totalModule.getWebUrl());
        totalModule1Edit.setDescription(totalModule.getDescription());
        totalModule1Edit.setRemarks(totalModule.getRemarks());
        totalModule1Edit.setTitle(totalModule.getTitle());
        totalModule1Edit.setSort(totalModule.getSort());

        totalModule1Edit.setLevel(totalModule.getLevel());
        totalModule1Edit.setType(totalModule.getType());
        totalModule1Edit.setClickNum(totalModule.getClickNum());
        totalModule1Edit.setForwardNum(totalModule.getForwardNum());
        totalModule1Edit.setCollectionNum(totalModule.getCollectionNum());

        totalModuleRespository.save(totalModule1Edit);
        return ResultUtil.success();
    }

    //删除当前分类
    @ResponseBody
    public Object deleteModule(TotalModule totalModule) throws Exception {
        totalModuleRespository.deleteById(totalModule.getId());
        return ResultUtil.success();
    }


    //查询当前分类
    @ResponseBody
    public Specification<TotalModule> queryModule(TotalModule totalModule, Integer pageIndex, Integer pageSize) throws Exception {

        //设置多条件查询
        Specification<TotalModule> specification = new Specification<TotalModule>() {
            @Override
            public Predicate toPredicate(Root<TotalModule> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

                //设置res数组
                List<Predicate> predicates = new ArrayList<>();

                //设置查询参数
                Path id = root.get("id");
                Path categoryName = root.get("categoryName");
                Path coverImgUrl = root.get("coverImgUrl");
                Path webUrl = root.get("webUrl");
                Path title = root.get("title");
                Path description = root.get("description");
                Path remarks = root.get("remarks");
                Path sort = root.get("sort");

                Path level = root.get("level");
                Path type = root.get("type");

                //如果level不为空
                if (totalModule.getLevel() != null) {
                    Predicate levelRes = criteriaBuilder.equal(level, totalModule.getLevel());
                    predicates.add(levelRes);
                }

                //如果type不为空
                if (totalModule.getType() != null) {
                    Predicate typeRes = criteriaBuilder.equal(type, totalModule.getType());
                    predicates.add(typeRes);
                }

                //如果ID不为空
                if (totalModule.getId() != null) {
                    Predicate idRes = criteriaBuilder.equal(id, totalModule.getId());
                    predicates.add(idRes);
                }

                //如果排序不为空
                if (totalModule.getSort() != null) {
                    Predicate sortRes = criteriaBuilder.equal(sort, totalModule.getSort());
                    predicates.add(sortRes);
                }


                //如果分类名不为空
                if (totalModule.getCategoryName() != null && !totalModule.getCategoryName().isEmpty() && !totalModule.getCategoryName().equals("")) {
                    Predicate categoryNameRes = criteriaBuilder.equal(categoryName, totalModule.getCategoryName());
                    predicates.add(categoryNameRes);
                }

                //如果封面地址不为空
                if (totalModule.getCoverImgUrl() != null && !totalModule.getCoverImgUrl().isEmpty() && !totalModule.getCoverImgUrl().equals("")) {
                    Predicate coverImgUrlRes = criteriaBuilder.equal(coverImgUrl, totalModule.getCoverImgUrl());
                    predicates.add(coverImgUrlRes);
                }

                //如果H5地址不为空
                if (totalModule.getWebUrl() != null && !totalModule.getWebUrl().isEmpty() && !totalModule.getWebUrl().equals("")) {
                    Predicate webUrlRes = criteriaBuilder.equal(webUrl, totalModule.getWebUrl());
                    predicates.add(webUrlRes);
                }
                //如果标题不为空
                if (totalModule.getTitle() != null && !totalModule.getTitle().isEmpty() && !totalModule.getTitle().equals("")) {
                    Predicate titleRes = criteriaBuilder.equal(title, totalModule.getTitle());
                    predicates.add(titleRes);
                }

                //如果描述不为空
                if (totalModule.getDescription() != null && !totalModule.getDescription().isEmpty() && !totalModule.getDescription().equals("")) {
                    Predicate descriptionRes = criteriaBuilder.equal(description, totalModule.getDescription());
                    predicates.add(descriptionRes);
                }

                //如果备注不为空
                if (totalModule.getRemarks() != null && !totalModule.getRemarks().isEmpty() && !totalModule.getRemarks().equals("")) {
                    Predicate remarksRes = criteriaBuilder.equal(remarks, totalModule.getRemarks());
                    predicates.add(remarksRes);
                }

                //将所有条件获取的数据格式化并返回给Controller进行分页
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };

        //返回给controller
        return specification;

    }


}
