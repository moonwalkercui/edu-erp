package com.hzb.erp.common.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hzb.erp.common.entity.GradeRecord;
import com.hzb.erp.common.pojo.dto.GradeParamDTO;
import com.hzb.erp.common.pojo.vo.GradeRecordVO;

/**
 * <p>
 * 成绩单学生分数 Mapper 接口
 * </p>
 *
 * @author Ryan
 */
public interface GradeRecordMapper extends BaseMapper<GradeRecord> {
    IPage<GradeRecordVO> getList(Page<?> page, GradeParamDTO param);
}
