package com.hzb.erp.api.pc.lesson.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hzb.erp.api.pc.lesson.entity.Subject;
import com.hzb.erp.common.pojo.CommonParamDTO;
import com.hzb.erp.common.pojo.SubjectVO;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 科目表 Mapper 接口
 * </p>
 *
 * @author Ryan
 */
@Mapper
public interface SubjectMapper extends BaseMapper<Subject> {

    IPage<SubjectVO> getList(Page<Object> objectPage, CommonParamDTO param);

}
