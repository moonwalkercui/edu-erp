package com.hzb.erp.api.pc.course.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hzb.erp.api.pc.course.entity.CourseTrialRecord;
import com.hzb.erp.api.pc.course.pojo.CourseTrialRecordParamDTO;
import com.hzb.erp.api.pc.course.pojo.CourseTrialRecordVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * 体验卡
 */
@Mapper
public interface CourseTrialRecordMapper extends BaseMapper<CourseTrialRecord> {

    IPage<CourseTrialRecordVO> getList(Page<?> objectPage, CourseTrialRecordParamDTO param);
    List<CourseTrialRecordVO> getList(@Param("param") CourseTrialRecordParamDTO param);

}
