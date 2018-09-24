package com.shalou.demo.repository;

import com.shalou.demo.domain.TotalModule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

//总的分类模块数据接口
public interface TotalModuleRespository extends JpaRepository<TotalModule,Integer>, JpaSpecificationExecutor<TotalModule> {

}
