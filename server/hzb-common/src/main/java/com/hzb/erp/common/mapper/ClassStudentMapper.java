package com.hzb.erp.common.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hzb.erp.common.entity.ClassStudent;
import com.hzb.erp.common.pojo.dto.ClassStudentParamDTO;
import com.hzb.erp.common.pojo.vo.ClassStudentSignVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * <p>
 * 班级学员表 Mapper 接口
 * </p>
 *
 * @author 541720500@qq.com
 */
@Repository
public interface ClassStudentMapper extends BaseMapper<ClassStudent> {

    /**
     * 查询班级学员信息，支持按课次签到情况
     */

//    List<ClassStudentVO> getList(@Param("param") ClassStudentParamDTO param);
//
//    IPage<ClassStudentVO> getList(Page<?> page, ClassStudentParamDTO param);

    /**
     * 通过lessonid找到班级里所有的同学中间表数据
     *
     * @param lessonId 课次id
     * @param all      是否所有 true表示所有 false表示只包括没有签到记录的
     */
    List<ClassStudent> selectClassStudentsByLessonId(Long lessonId, Boolean all);

    /**
     * 查询班级学员签到信息、消课名称等
     */
    List<ClassStudentSignVO> signRecord(@Param("param") ClassStudentParamDTO param);

    IPage<ClassStudentSignVO> signRecord(Page<?> page, ClassStudentParamDTO param);
}
