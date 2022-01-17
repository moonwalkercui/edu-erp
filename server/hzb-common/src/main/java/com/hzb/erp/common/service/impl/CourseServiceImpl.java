package com.hzb.erp.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hzb.erp.common.entity.Course;
import com.hzb.erp.common.entity.CourseLink;
import com.hzb.erp.common.enums.SwitchEnum;
import com.hzb.erp.common.mapper.CourseLinkMapper;
import com.hzb.erp.common.mapper.CourseMapper;
import com.hzb.erp.common.pojo.dto.CourseParamDTO;
import com.hzb.erp.common.pojo.dto.CourseSaveDTO;
import com.hzb.erp.common.pojo.vo.CourseVO;
import com.hzb.erp.common.service.CourseService;
import com.hzb.erp.common.exception.BizException;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author 541720500@qq.com
 */
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {

    @Resource
    private CourseLinkMapper courseLinkMapper;

    @Override
    public CourseVO getInfo(Long id) {
        return this.baseMapper.getInfo(id, null);
    }

    @Override
    public CourseVO getInfoByName(String name) {
        return this.baseMapper.getInfo(null, name);
    }

    @Override
    public IPage<CourseVO> getList(CourseParamDTO param) {
        return this.baseMapper.getList(new Page<>(param.getPage(), param.getPageSize()), param);
    }

    @Override
    public List<CourseVO> getAll(CourseParamDTO param) {
        return this.baseMapper.getList(param);
    }

    @Override
    public Course getByName(String name) {
        QueryWrapper<Course> qw = new QueryWrapper<>();
        qw.eq("name", name);
        return this.getOne(qw);
    }

    @Override
    public Boolean saveOrUpdateByDTO(CourseSaveDTO courseSaveDTO) {
        Course item = new Course();

        if (courseSaveDTO.getName() != null) {
            Course find = this.getByName(courseSaveDTO.getName());
            if (find != null && !find.getId().equals(courseSaveDTO.getId())) {
                throw new BizException("该课程名已存在");
            }
        }

        BeanUtils.copyProperties(courseSaveDTO, item);

        if ("期".equals(courseSaveDTO.getUnitName())) {
            BigDecimal res = courseSaveDTO.getPrice().divide(BigDecimal.valueOf(courseSaveDTO.getLessonCount()));
            if (res.compareTo(new BigDecimal(1)) < 0) {
                throw new BizException("单价不能小于1");
            }
            item.setUnitPrice(res);
        }

        return this.saveOrUpdate(item);
    }

    @Override
    public Boolean delete(List<Long> ids) {
        return this.removeByIds(ids);
    }

    @Override
    public Boolean open(List<Long> ids) {

        List<Course> list = this.listByIds(ids);
        for (Course item : list) {
            item.setState(SwitchEnum.YES);
        }
        return this.updateBatchById(list);
    }

    @Override
    public Boolean close(List<Long> ids) {
        List<Course> list = this.listByIds(ids);
        for (Course item : list) {
            item.setState(SwitchEnum.NO);
        }
        return this.updateBatchById(list);
    }

    @Override
    public Integer handleLink(Long courseId, Long[] linkedIds) {
        handleUnlink(courseId, linkedIds);
        int count = 0;
        for (int i = 0; i < linkedIds.length; i++) {
            CourseLink item = new CourseLink();
            item.setCourseId(courseId);
            item.setLinkedId(linkedIds[i]);
            courseLinkMapper.insert(item);
            count = i;
        }
        return count + 1;
    }

    @Override
    public Integer handleUnlink(Long courseId, Long[] linkedIds) {
        int count = 0;
        for (int i = 0; i < linkedIds.length; i++) {
            Map<String, Object> map = new HashMap<>(2);
            map.put("course_id", courseId);
            map.put("linked_id", linkedIds[i]);
            courseLinkMapper.deleteByMap(map);
            count = i;
        }
        return count + 1;
    }
}
