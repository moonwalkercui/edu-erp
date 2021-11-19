package com.hzb.erp.common.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hzb.erp.annotation.DataScoped;
import com.hzb.erp.common.entity.Course;
import com.hzb.erp.common.pojo.dto.CourseParamDTO;
import com.hzb.erp.common.pojo.vo.CourseVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author 541720500@qq.com
 */
public interface CourseMapper extends BaseMapper<Course> {

    CourseVO getInfo(Long id, String name);
    @DataScoped
    List<CourseVO> getList(@Param("param") CourseParamDTO param);
    @DataScoped
    IPage<CourseVO> getList(Page<Object> objectPage, CourseParamDTO param);

}
