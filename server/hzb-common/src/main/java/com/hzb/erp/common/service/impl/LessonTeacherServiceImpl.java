package com.hzb.erp.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hzb.erp.common.entity.LessonTeacher;
import com.hzb.erp.common.mapper.LessonTeacherMapper;
import com.hzb.erp.common.service.LessonTeacherService;
import com.hzb.erp.common.exception.BizException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 课时老师关联表 服务实现类
 * </p>
 *
 * @author Ryan
 */
@Service
public class LessonTeacherServiceImpl extends ServiceImpl<LessonTeacherMapper, LessonTeacher> implements LessonTeacherService {
    /**
     * 上课老师和助教重复检查
     */
    @Override
    public void validateTeacherIds(List<Long> teacherIds, List<Long> assistantIds) {
        if (teacherIds == null || teacherIds.size() == 0) {
            throw new BizException("未设置上课老师");
        }
        if (assistantIds != null && assistantIds.size() > 0) {
            for (Long aid : assistantIds) {
                if (teacherIds.contains(aid)) {
                    throw new BizException("上课老师和助教不可重复");
                }
            }
        }
    }

    @Override
    public Boolean deleteByLessonId(Long id) {
        QueryWrapper<LessonTeacher> qw = new QueryWrapper<>();
        qw.eq("lesson_id", id);
        return this.remove(qw);
    }

}
