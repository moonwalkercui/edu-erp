package com.hzb.erp.common.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hzb.erp.common.entity.Subject;
import com.hzb.erp.common.pojo.dto.CommonParamDTO;
import com.hzb.erp.common.pojo.vo.SubjectVO;

/**
 * <p>
 * 科目表 Mapper 接口
 * </p>
 *
 * @author Ryan
 */
public interface SubjectMapper extends BaseMapper<Subject> {

    IPage<SubjectVO> getList(Page<Object> objectPage, CommonParamDTO param);

}
