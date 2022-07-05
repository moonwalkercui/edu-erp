package com.hzb.erp.api.pc.lesson.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hzb.erp.api.pc.lesson.entity.Homework;
import com.hzb.erp.api.pc.lesson.pojo.HomeworkParamDTO;
import com.hzb.erp.api.pc.lesson.pojo.HomeworkSaveDTO;
import com.hzb.erp.api.pc.lesson.pojo.HomeworkVO;

import java.util.List;

/**
 * <p>
 * 作业 服务类
 * </p>
 *
 * @author Ryan
 */
public interface HomeworkService extends IService<Homework> {

    IPage<HomeworkVO> getList(HomeworkParamDTO param);

    List<HomeworkVO> getAll(HomeworkParamDTO param);

    Boolean saveOrUpdateByDTO(HomeworkSaveDTO dto);

    HomeworkVO getInfo(Long id, Long studentId);
}
