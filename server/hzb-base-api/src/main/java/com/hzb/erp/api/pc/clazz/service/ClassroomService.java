package com.hzb.erp.api.pc.clazz.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hzb.erp.api.pc.clazz.entity.Classroom;
import com.hzb.erp.api.pc.clazz.pojo.ClassroomParamDTO;
import com.hzb.erp.api.pc.clazz.pojo.ClassroomSaveDTO;
import com.hzb.erp.api.pc.clazz.pojo.ClassroomVO;

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
