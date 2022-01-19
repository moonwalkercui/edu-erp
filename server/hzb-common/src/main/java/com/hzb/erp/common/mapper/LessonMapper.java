package com.hzb.erp.common.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hzb.erp.annotation.DataScoped;
import com.hzb.erp.common.entity.Lesson;
import com.hzb.erp.common.pojo.dto.LessonParamDTO;
import com.hzb.erp.common.pojo.vo.LessonTeacherStatsVO;
import com.hzb.erp.common.pojo.vo.LessonVO;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课次表 Mapper 接口
 * </p>
 *
 * @author 541720500@qq.com
 */
public interface LessonMapper extends BaseMapper<Lesson> {
    @DataScoped
    List<LessonVO> getList(@Param("param") LessonParamDTO param);

    @DataScoped
    IPage<LessonVO> getList(Page<?> page, LessonParamDTO param);

    LessonVO getInfo(Long id);

    @DataScoped
    IPage<LessonTeacherStatsVO> statsByTeachers(Page<?> page, LessonParamDTO param);

    @DataScoped
    List<LessonTeacherStatsVO> statsByTeachers(@Param("param") LessonParamDTO param);

    @MapKey("date")
    List<Map<String, Object>> getLessonNumEveryDay(@Param("param") LessonParamDTO param);

    /**
     * 定时任务里找到符合条件的待处理的课次。
     *
     * @param minutes 要检查的分钟间隔
     */
    List<Lesson> cronLessonToGenerateStudentRel(Integer minutes);

    List<Lesson> cronLessonToClose();

    void updateStateBatch(List<Long> ids, Integer state);
}
