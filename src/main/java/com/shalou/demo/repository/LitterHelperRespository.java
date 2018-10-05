package com.shalou.demo.repository;

import com.shalou.demo.domain.LitterHelperUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface LitterHelperRespository extends JpaRepository<LitterHelperUser,Integer>, JpaSpecificationExecutor<LitterHelperUser> {
    public LitterHelperUser findLitterHelperUserByOpenid(String openid);
}
