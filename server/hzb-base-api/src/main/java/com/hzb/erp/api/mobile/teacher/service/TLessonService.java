package com.hzb.erp.api.mobile.teacher.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hzb.erp.api.pc.lesson.entity.Lesson;
import com.hzb.erp.api.pc.lesson.pojo.LessonParamDTO;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课次表 服务类
 * </p>
 *
 * @author 541720500@qq.com
 */
public interface TLessonService extends IService<Lesson> {


    List<Map<String, Object>> getLessonNumEveryDay(LessonParamDTO param);
}
