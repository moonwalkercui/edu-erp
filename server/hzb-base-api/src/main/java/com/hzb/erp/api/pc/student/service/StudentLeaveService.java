package com.hzb.erp.api.pc.student.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hzb.erp.api.mobile.student.pojo.dto.StudentLeaveDTO;
import com.hzb.erp.api.pc.student.entity.StudentLeave;
import com.hzb.erp.api.pc.student.pojo.StudentLeaveParamDTO;
import com.hzb.erp.api.pc.student.pojo.StudentLeaveVO;

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

    /**
     * 取消请假
     * */
    //    Boolean handle(List<Long> ids, VerifyStateEnum pass);
    Boolean cancel(List<Long> ids, Long staffId);

    /**
    * 请假
    * */
    boolean handleLeave(StudentLeaveDTO dto);
}
