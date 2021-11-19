package com.hzb.erp.common.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hzb.erp.common.entity.Classroom;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hzb.erp.common.pojo.dto.ClassroomParamDTO;
import com.hzb.erp.common.pojo.dto.ClassroomSaveDTO;
import com.hzb.erp.common.pojo.vo.ClassroomVO;

/**
 * <p>
 * 教室 服务类
 * </p>
 *
 * @author Ryan
 */
public interface ClassroomService extends IService<Classroom> {

    IPage<ClassroomVO> getList(ClassroomParamDTO param);

    Boolean saveOrUpdateByDTO(ClassroomSaveDTO dto);
}
