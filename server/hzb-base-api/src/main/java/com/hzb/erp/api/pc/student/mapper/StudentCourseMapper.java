package com.hzb.erp.api.pc.student.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hzb.erp.annotation.DataScoped;
import com.hzb.erp.api.pc.student.entity.StudentCourse;
import com.hzb.erp.api.pc.student.pojo.StudentCourseParamDTO;
import com.hzb.erp.api.pc.student.pojo.StudentCourseVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 学员合约表 Mapper 接口
 * </p>
 *
 * @author 541720500@qq.com
 */
@Mapper
@Repository
public interface StudentCourseMapper extends BaseMapper<StudentCourse> {
    @DataScoped
    IPage<StudentCourseVO> getList(Page<StudentCourseVO> page, StudentCourseParamDTO param);

    @DataScoped
    List<StudentCourseVO> getList(@Param("param") StudentCourseParamDTO param);

    /**
     * 获取有效课程列表
     */
    IPage<StudentCourseVO> getValidList(Page<StudentCourseVO> page, StudentCourseParamDTO param);

    List<StudentCourseVO> getValidList(@Param("param") StudentCourseParamDTO param);

    StudentCourseVO getInfo(Long id);

    /**
     * 获取当前有效课次数
     */
    Integer listLessonRemainingCountTotal(@Param("courseId") Long courseId, @Param("studentId") Long studentId);


//    Integer getStudentSubjectNum(Long studentId, Long subjectId);
}
