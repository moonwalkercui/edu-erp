package com.hzb.erp.common.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hzb.erp.common.entity.StudentLeave;
import com.hzb.erp.common.pojo.dto.StudentLeaveParamDTO;
import com.hzb.erp.common.pojo.vo.StudentLeaveVO;

import java.util.List;

/**
 * <p>
 * 学员请假 服务类
 * </p>
 *
 * @author Ryan
 */
public interface StudentLeaveService extends IService<StudentLeave> {
    IPage<StudentLeaveVO> getList(StudentLeaveParamDTO param);

    //    Boolean handle(List<Long> ids, VerifyStateEnum pass);
    Boolean cancel(List<Long> ids, Long staffId);
}
