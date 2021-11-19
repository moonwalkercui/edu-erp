package com.hzb.erp.common.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hzb.erp.common.entity.StudentLessonCountLog;
import com.hzb.erp.common.pojo.dto.StudentLessonLogParamDTO;
import com.hzb.erp.common.pojo.vo.StudentLessonCountLogVO;

/**
 * 课次记录
 */
public interface StudentLessonCountLogMapper extends BaseMapper<StudentLessonCountLog> {
    IPage<StudentLessonCountLogVO> getList(Page<?> page, StudentLessonLogParamDTO param);
}
