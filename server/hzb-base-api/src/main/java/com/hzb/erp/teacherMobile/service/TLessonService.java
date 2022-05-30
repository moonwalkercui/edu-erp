package com.hzb.erp.teacherMobile.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hzb.erp.common.entity.Lesson;
import com.hzb.erp.common.pojo.dto.LessonParamDTO;

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
