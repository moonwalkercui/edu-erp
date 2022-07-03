package com.hzb.erp.common.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hzb.erp.common.entity.StudentCreditLog;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hzb.erp.common.pojo.dto.CreditMallParamDTO;
import com.hzb.erp.common.pojo.dto.StudentCreditLogParamDTO;
import com.hzb.erp.common.pojo.vo.CreditMallVO;
import com.hzb.erp.common.pojo.vo.StudentCreditLogVO;

/**
 * 积分变动记录服务类
 */
public interface StudentCreditLogService extends IService<StudentCreditLog> {
    /**
     * 分页
     * */
    IPage<StudentCreditLogVO> getList(StudentCreditLogParamDTO param);

}
