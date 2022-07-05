package com.hzb.erp.common.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hzb.erp.common.entity.Staff;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hzb.erp.common.pojo.ChangePasswordDTO;
import com.hzb.erp.common.pojo.StaffChangeStateParamDTO;
import com.hzb.erp.common.pojo.StaffParamDTO;
import com.hzb.erp.common.pojo.StaffSaveDTO;
import com.hzb.erp.common.pojo.StaffVO;

import java.util.List;

/**
 * <p>
 * 教师员工表 服务类
 * </p>
 *
 * @author Ryan
 */
public interface StaffService extends IService<Staff> {
    IPage<StaffVO> getList(StaffParamDTO param);

    Boolean saveOrUpdateByDTO(StaffSaveDTO dto);

    StaffVO getInfo(Long id);

    Staff getByMobile(String mob);

    Boolean deleteTeacher(List<Long> ids);

    Boolean changeState(StaffChangeStateParamDTO dto);

    boolean changePassword(ChangePasswordDTO dto);

    Staff getByWxAccessId(Long id);

    String getWxOpenid(Long id);

    boolean changeHeadImg(Long staffId, String img);
}
