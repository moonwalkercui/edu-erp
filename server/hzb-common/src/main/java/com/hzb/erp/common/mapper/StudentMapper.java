package com.hzb.erp.common.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hzb.erp.annotation.DataScoped;
import com.hzb.erp.common.entity.Student;
import com.hzb.erp.common.pojo.dto.StudentParamDTO;
import com.hzb.erp.common.pojo.vo.ClassStudentVO;
import com.hzb.erp.common.pojo.vo.StudentVO;
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
