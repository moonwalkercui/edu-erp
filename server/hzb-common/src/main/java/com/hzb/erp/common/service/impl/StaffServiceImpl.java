package com.hzb.erp.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hzb.erp.common.entity.Staff;
import com.hzb.erp.common.enums.StaffStateEnum;
import com.hzb.erp.common.exception.BizException;
import com.hzb.erp.common.mapper.StaffMapper;
import com.hzb.erp.common.pojo.dto.*;
import com.hzb.erp.common.pojo.vo.StaffVO;
import com.hzb.erp.common.service.StaffOrginfoService;
import com.hzb.erp.common.service.StaffService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 教师员工表 服务实现类
 * </p>
 *
 * @author Ryan
 */
@Service
public class StaffServiceImpl extends ServiceImpl<StaffMapper, Staff> implements StaffService {
    @Autowired
    private StaffOrginfoService staffOrginfoService;

    @Override
    public StaffVO getInfo(Long id) {
        return this.baseMapper.getInfo(id);
    }

    @Override
    public Staff getByMobile(String mob) {
        QueryWrapper<Staff> qw = new QueryWrapper<>();
        qw.eq("mobile", mob);
        return this.getOne(qw);
    }

    @Override
    public IPage<StaffVO> getList(StaffParamDTO param) {
        return baseMapper.getList(new Page<>(param.getPage(), param.getPageSize()), param);
    }

    @Override
    public Boolean saveOrUpdateByDTO(StaffSaveDTO dto) {

        if (dto.getMobile() != null) {
            Staff existStaff = getByMobile(dto.getMobile());
            if (existStaff != null && !existStaff.getId().equals(dto.getId())) {
                throw new BizException("该手机号码已存在");
            }
        }

        Staff item = new Staff();
        BeanUtils.copyProperties(dto, item);
        Boolean res = this.saveOrUpdate(item);

        staffOrginfoService.saveStaffOrgInfo(item.getId(), dto.getOrgId(), true, dto.getCreator());

        PositionSetDTO positionSetDTO = new PositionSetDTO();
        positionSetDTO.setOrgId(dto.getOrgId());
        positionSetDTO.setPositionId(dto.getPositionId());
        positionSetDTO.setStaffId(item.getId());
        positionSetDTO.setCreator(dto.getCreator());
        staffOrginfoService.setStaffPosition(positionSetDTO);

        return res;
    }

    @Override
    public Boolean deleteTeacher(List<Long> ids) {
        return this.removeByIds(ids);
    }

    @Override
    public Boolean changeState(StaffChangeStateParamDTO dto) {
        List<Staff> list = this.listByIds(dto.getId());
        for (Staff s : list) {
            s.setState(dto.getState());
            if (StaffStateEnum.ON_JOB.equals(dto.getState())) {
                s.setHireDate(dto.getDate());
            } else {
                s.setFireDate(dto.getDate());
            }
        }
        return this.updateBatchById(list);
    }

    @Override
    public boolean changePassword(ChangePasswordDTO dto) {
        if (dto.getId() == null) {
            throw new BizException("缺少参数");
        }

        if (StringUtils.isBlank(dto.getPassword())) {
            throw new BizException("缺少密码");
        }

        if (dto.getPassword().length() <= 3) {
            throw new BizException("密码过短");
        }
        if (StringUtils.isBlank(dto.getPasswordEncode())) {
            throw new BizException("密码未加密无法保存");
        }
        Staff staff = getById(dto.getId());
        staff.setPassword(dto.getPasswordEncode());
        return updateById(staff);
    }

    @Override
    public Staff getByWxAccessId(Long id) {
        QueryWrapper<Staff> qw = new QueryWrapper<>();
        qw.eq("wx_access_id", id).orderByDesc("id").last("limit 1");
        return getOne(qw);
    }

    @Override
    public String getWxOpenid(Long id) {
        return baseMapper.getWxOpenid(id);
    }

    @Override
    public boolean changeHeadImg(Long staffId, String img) {
        if (staffId == null) {
            throw new BizException("缺少参数");
        }
        if (StringUtils.isBlank(img)) {
            throw new BizException("缺少图片");
        }
        Staff staff = getById(staffId);
        staff.setHeadImg(img);
        return updateById(staff);
    }

}
