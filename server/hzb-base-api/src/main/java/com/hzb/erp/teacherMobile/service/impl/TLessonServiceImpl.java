package com.hzb.erp.teacherMobile.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hzb.erp.common.entity.Lesson;
import com.hzb.erp.common.mapper.LessonMapper;
import com.hzb.erp.common.pojo.dto.LessonParamDTO;
import com.hzb.erp.adminCenter.service.UserAuthService;
import com.hzb.erp.teacherMobile.service.TLessonService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课次表 服务实现类
 * </p>
 *
 * @author 541720500@qq.com
 */
@Service
public class TLessonServiceImpl extends ServiceImpl<LessonMapper, Lesson> implements TLessonService {

    @Resource
    private LessonMapper lessonMapper;

    @Override
    public List<Map<String, Object>> getLessonNumEveryDay(LessonParamDTO param) {
        param.setTeacherId(UserAuthService.getCurrentUserId());
        return lessonMapper.getLessonNumEveryDay(param);
    }
}
