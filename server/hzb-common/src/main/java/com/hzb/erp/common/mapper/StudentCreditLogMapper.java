package com.hzb.erp.common.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hzb.erp.common.entity.StudentCreditLog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hzb.erp.common.pojo.dto.StudentCreditLogParamDTO;
import com.hzb.erp.common.pojo.vo.StudentCreditLogVO;

/**
 * 积分变动记录
 * @Entity com.hzb.erp.common.entity.StudentCreditLog
 */
public interface StudentCreditLogMapper extends BaseMapper<StudentCreditLog> {

    IPage<StudentCreditLogVO> getList(Page<Object> objectPage, StudentCreditLogParamDTO param);
}




