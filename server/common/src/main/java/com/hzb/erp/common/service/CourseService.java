package com.hzb.erp.common.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hzb.erp.common.entity.Course;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hzb.erp.common.pojo.dto.CourseParamDTO;
import com.hzb.erp.common.pojo.dto.CourseSaveDTO;
import com.hzb.erp.common.pojo.vo.CourseVO;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author 541720500@qq.com
 */
public interface CourseService extends IService<Course> {

    CourseVO getInfo(Long id);

    CourseVO getInfoByName(String name);

    IPage<CourseVO> getList(CourseParamDTO param);

    List<CourseVO> getAll(CourseParamDTO param);

    Course getByName(String name);

    /**
     * 新增和更新课程
     */
    Boolean saveOrUpdateByDTO(CourseSaveDTO courseSaveDTO);

    Boolean delete(List<Long> ids);

    Boolean open(List<Long> ids);

    Boolean close(List<Long> ids);

    Integer handleLink(Long courseId, Long[] linkedId);

    Integer handleUnlink(Long courseId, Long[] linkedId);
}
