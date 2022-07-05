package com.hzb.erp.api.pc.course.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hzb.erp.annotation.DataScoped;
import com.hzb.erp.api.pc.course.entity.Course;
import com.hzb.erp.api.pc.course.pojo.CourseParamDTO;
import com.hzb.erp.api.pc.course.pojo.CourseVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author 541720500@qq.com
 */
@Mapper
public interface CourseMapper extends BaseMapper<Course> {

    CourseVO getInfo(Long id, String name);

    @DataScoped
    List<CourseVO> getList(@Param("param") CourseParamDTO param);

    @DataScoped
    IPage<CourseVO> getList(Page<Object> objectPage, CourseParamDTO param);

}
