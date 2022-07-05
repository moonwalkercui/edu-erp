package com.hzb.erp.api.pc.clazz.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hzb.erp.api.pc.clazz.entity.GradeRecord;
import com.hzb.erp.api.pc.clazz.pojo.GradeParamDTO;
import com.hzb.erp.api.pc.clazz.pojo.GradeRecordVO;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 成绩单学生分数 Mapper 接口
 * </p>
 *
 * @author Ryan
 */
@Mapper
public interface GradeRecordMapper extends BaseMapper<GradeRecord> {
    IPage<GradeRecordVO> getList(Page<?> page, GradeParamDTO param);
}
