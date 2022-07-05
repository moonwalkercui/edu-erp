package com.hzb.erp.api.pc.lesson.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hzb.erp.api.pc.lesson.entity.Subject;
import com.hzb.erp.common.pojo.CommonParamDTO;
import com.hzb.erp.api.pc.lesson.pojo.SubjectSaveDTO;
import com.hzb.erp.common.pojo.SubjectVO;

/**
 * <p>
 * 科目表 服务类
 * </p>
 *
 * @author Ryan
 */
public interface SubjectService extends IService<Subject> {
    IPage<SubjectVO> getList(CommonParamDTO param);

    Boolean saveOrUpdateByDTO(SubjectSaveDTO subjectSaveDTO);
}
