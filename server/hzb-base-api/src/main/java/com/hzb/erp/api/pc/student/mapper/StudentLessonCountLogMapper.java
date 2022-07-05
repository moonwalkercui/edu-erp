package com.hzb.erp.api.pc.student.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hzb.erp.api.pc.student.entity.StudentLessonCountLog;
import com.hzb.erp.api.pc.student.pojo.StudentLessonLogParamDTO;
import com.hzb.erp.api.pc.student.pojo.StudentLessonCountLogVO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 课次记录
 */
@Mapper
public interface StudentLessonCountLogMapper extends BaseMapper<StudentLessonCountLog> {
    IPage<StudentLessonCountLogVO> getList(Page<?> page, StudentLessonLogParamDTO param);
}
