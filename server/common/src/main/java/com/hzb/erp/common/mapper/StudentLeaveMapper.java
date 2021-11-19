package com.hzb.erp.common.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hzb.erp.common.entity.StudentLeave;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hzb.erp.common.pojo.dto.AppointmentParamDTO;
import com.hzb.erp.common.pojo.dto.StudentLeaveParamDTO;
import com.hzb.erp.common.pojo.vo.AppointmentVO;
import com.hzb.erp.common.pojo.vo.StudentLeaveVO;

/**
 * <p>
 * 学员请假 Mapper 接口
 * </p>
 *
 * @author Ryan
 */
public interface StudentLeaveMapper extends BaseMapper<StudentLeave> {
    IPage<StudentLeaveVO> getList(Page<?> page, StudentLeaveParamDTO param);
}
