package com.hzb.erp.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hzb.erp.common.entity.StaffOrginfo;
import com.hzb.erp.common.pojo.dto.OrgPutinStaffDTO;
import com.hzb.erp.common.pojo.dto.PositionSetDTO;


/**
 *
 */
public interface StaffOrginfoService extends IService<StaffOrginfo> {

    Boolean saveStaffOrgInfo(Long id, Long orgId, Boolean force, Long creator);

    /**
     * 把人员转入机构
     */
    Boolean putStaffIntoOrg(OrgPutinStaffDTO dto, Boolean force, Long creator);

    boolean setStaffPosition(PositionSetDTO dto);
}
