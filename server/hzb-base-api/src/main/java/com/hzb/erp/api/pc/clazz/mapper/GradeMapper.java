package com.hzb.erp.api.pc.clazz.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hzb.erp.annotation.DataScoped;
import com.hzb.erp.api.pc.clazz.entity.Grade;
import com.hzb.erp.api.pc.clazz.pojo.GradeParamDTO;
import com.hzb.erp.api.pc.clazz.pojo.GradeVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 成绩单 Mapper 接口
 * </p>
 *
 * @author Ryan
 */
@Mapper
public interface GradeMapper extends BaseMapper<Grade> {
    @DataScoped
    IPage<GradeVO> getList(Page<?> page, GradeParamDTO param);

    @DataScoped
    List<GradeVO> getList(@Param("param") GradeParamDTO param);
}
