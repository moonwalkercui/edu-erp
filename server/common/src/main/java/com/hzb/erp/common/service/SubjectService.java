package com.hzb.erp.common.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hzb.erp.common.entity.Subject;
import com.hzb.erp.common.pojo.dto.CommonParamDTO;
import com.hzb.erp.common.pojo.dto.SubjectSaveDTO;
import com.hzb.erp.common.pojo.vo.SubjectVO;

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
