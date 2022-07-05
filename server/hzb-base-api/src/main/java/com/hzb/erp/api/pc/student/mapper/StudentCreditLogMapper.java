package com.hzb.erp.api.pc.student.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hzb.erp.api.pc.student.entity.StudentCreditLog;
import com.hzb.erp.api.pc.student.pojo.StudentCreditLogParamDTO;
import com.hzb.erp.api.pc.student.pojo.StudentCreditLogVO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 积分变动记录
 * @Entity com.hzb.erp.api.adminCenter.student.entity.StudentCreditLog
 */
@Mapper
public interface StudentCreditLogMapper extends BaseMapper<StudentCreditLog> {

    IPage<StudentCreditLogVO> getList(Page<Object> objectPage, StudentCreditLogParamDTO param);
}




