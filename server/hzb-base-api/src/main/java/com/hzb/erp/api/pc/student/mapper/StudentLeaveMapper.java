package com.hzb.erp.api.pc.student.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hzb.erp.api.pc.student.entity.StudentLeave;
import com.hzb.erp.api.pc.student.pojo.StudentLeaveParamDTO;
import com.hzb.erp.api.pc.student.pojo.StudentLeaveVO;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 学员请假 Mapper 接口
 * </p>
 *
 * @author Ryan
 */
@Mapper
public interface StudentLeaveMapper extends BaseMapper<StudentLeave> {
    IPage<StudentLeaveVO> getList(Page<?> page, StudentLeaveParamDTO param);
}
