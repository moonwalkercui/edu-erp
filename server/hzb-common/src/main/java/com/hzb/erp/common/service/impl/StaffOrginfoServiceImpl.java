package com.hzb.erp.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hzb.erp.common.entity.Org;
import com.hzb.erp.common.entity.StaffOrginfo;
import com.hzb.erp.exception.BizException;
import com.hzb.erp.common.mapper.StaffOrginfoMapper;
import com.hzb.erp.common.pojo.OrgPutinStaffDTO;
import com.hzb.erp.common.pojo.PositionSetDTO;
import com.hzb.erp.common.service.OrgService;
import com.hzb.erp.common.service.StaffOrginfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * @author Administrator
 */
@Service
public class StaffOrginfoServiceImpl extends ServiceImpl<StaffOrginfoMapper, StaffOrginfo> implements StaffOrginfoService {

    @Autowired
    @Lazy
    private OrgService orgService;

    /**
     * 保存员工的机构信息
     *
     * @param staffId 员工id
     * @param orgId   机构id
     * @param force   强制模式下一个员工会强制转入一个机构，其他机构删除
     * @return Boolean
     */
    @Override
    @Transactional
    public Boolean saveStaffOrgInfo(Long staffId, Long orgId, Boolean force, Long creator) {
        QueryWrapper<StaffOrginfo> qw = new QueryWrapper<>();
        qw.eq("staff_id", staffId);
        if (!force) {
            qw.eq("org_id", orgId);
        }
        remove(qw);

        StaffOrginfo newItem = buildEntity(staffId, orgId, creator);
        return save(newItem);
    }

    @Override
    public Boolean putStaffIntoOrg(OrgPutinStaffDTO dto, Boolean force, Long creator) {
        Long orgId = dto.getOrgId();
        Long[] staffIds = dto.getStaffIds();

        if (orgId == null) {
            throw new BizException("缺少机构");
        }

        if (staffIds == null || staffIds.length == 0) {
            throw new BizException("缺少人员");
        }

        for (Long staffId : staffIds) {
            saveStaffOrgInfo(staffId, orgId, true, creator);
        }

        return true;
    }

    @Override
    public boolean setStaffPosition(PositionSetDTO dto) {

        QueryWrapper<StaffOrginfo> qw = new QueryWrapper<>();
        qw.eq("staff_id", dto.getStaffId())
                .eq("org_id", dto.getOrgId())
                .last("limit 1");
        StaffOrginfo find = getOne(qw);
        boolean res;
        if (find != null) {
            find.setPositionId(dto.getPositionId());
            res = updateById(find);
        } else {
            StaffOrginfo newItem = buildEntity(dto.getStaffId(), dto.getOrgId(), dto.getCreator());
            newItem.setPositionId(dto.getPositionId());
            res = save(newItem);
        }

        return res;
    }

    private StaffOrginfo buildEntity(Long staffId, Long orgId, Long creator) {
        StaffOrginfo item = new StaffOrginfo();
        item.setStaffId(staffId);
        item.setOrgId(orgId);
        item.setAddTime(LocalDateTime.now());
        item.setCreator(creator);

        Org[] parentOrgs = orgService.getParentOrgById(orgId);
        item.setGroupId(parentOrgs[0] == null ? null : parentOrgs[0].getId());
        item.setComId(parentOrgs[1] == null ? null : parentOrgs[1].getId());
        item.setDptId(parentOrgs[2] == null ? null : parentOrgs[2].getId());

        Org parentOrg = orgService.getById(orgId);
        item.setIdPath(parentOrg.getIdPath());

        return item;
    }
}




