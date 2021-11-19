package com.hzb.erp.common.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hzb.erp.annotation.DataScoped;
import com.hzb.erp.common.entity.Staff;
import com.hzb.erp.common.pojo.dto.StaffParamDTO;
import com.hzb.erp.common.pojo.vo.StaffVO;

import java.util.List;

/**
 * <p>
 * 教师员工表 Mapper 接口
 * </p>
 *
 * @author Ryan
 */
public interface StaffMapper extends BaseMapper<Staff> {
    @DataScoped
    IPage<StaffVO> getList(Page<Object> page, StaffParamDTO param);

    StaffVO getInfo(Long id);

    List<StaffVO> listByComId(Long comId);

    String getWxOpenid(Long id);
}
