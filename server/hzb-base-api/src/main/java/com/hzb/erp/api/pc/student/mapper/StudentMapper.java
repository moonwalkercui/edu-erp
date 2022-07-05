package com.hzb.erp.api.pc.student.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hzb.erp.annotation.DataScoped;
import com.hzb.erp.api.pc.student.entity.Student;
import com.hzb.erp.api.pc.student.pojo.StudentParamDTO;
import com.hzb.erp.api.pc.clazz.pojo.ClassStudentVO;
import com.hzb.erp.api.pc.student.pojo.StudentVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 学生表 Mapper 接口
 * </p>
 *
 * @author 541720500@qq.com
 */
@Mapper
public interface StudentMapper extends BaseMapper<Student> {

    StudentVO getBaseInfo(Long id);

    StudentVO getBaseInfoByUid(Long id, Long uid);

    @DataScoped
    IPage<StudentVO> getList(Page<StudentVO> ipage, StudentParamDTO param);

    @DataScoped
    List<StudentVO> getList(@Param("param") StudentParamDTO param);

    @DataScoped
    IPage<ClassStudentVO> getListByClassId(Page<ClassStudentVO> ipage, StudentParamDTO param);

    Map<String, Object> getRedpointCounts(Long studentId);

    @DataScoped
    List<Student> listByUserId(Long uid);

    boolean switchStudent(@Param("studentId") Long studentId, @Param("userId") Long userId);
}
