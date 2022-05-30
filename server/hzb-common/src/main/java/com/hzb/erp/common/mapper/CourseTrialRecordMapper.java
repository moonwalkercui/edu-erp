package com.hzb.erp.common.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hzb.erp.common.entity.CourseTrialRecord;
import com.hzb.erp.common.pojo.dto.CourseTrialRecordParamDTO;
import com.hzb.erp.common.pojo.vo.CourseTrialRecordVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * 体验卡
 */
public interface CourseTrialRecordMapper extends BaseMapper<CourseTrialRecord> {

    IPage<CourseTrialRecordVO> getList(Page<?> objectPage, CourseTrialRecordParamDTO param);
    List<CourseTrialRecordVO> getList(@Param("param") CourseTrialRecordParamDTO param);

}
