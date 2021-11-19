package com.hzb.erp.common.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hzb.erp.common.entity.StaffOrginfo;
import org.springframework.stereotype.Repository;

/**
 *  com.xiangrui.marine.common.entity.StaffOrginfo
 */
@Repository
public interface StaffOrginfoMapper extends BaseMapper<StaffOrginfo> {
    StaffOrginfo getByStaffId(Long staffId);
}




