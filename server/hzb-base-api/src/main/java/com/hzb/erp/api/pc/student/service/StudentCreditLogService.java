package com.hzb.erp.api.pc.student.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hzb.erp.api.pc.student.entity.StudentCreditLog;
import com.hzb.erp.api.pc.student.pojo.StudentCreditLogParamDTO;
import com.hzb.erp.api.pc.student.pojo.StudentCreditLogVO;

/**
 * 积分变动记录服务类
 */
public interface StudentCreditLogService extends IService<StudentCreditLog> {
    /**
     * 分页
     * */
    IPage<StudentCreditLogVO> getList(StudentCreditLogParamDTO param);

}
