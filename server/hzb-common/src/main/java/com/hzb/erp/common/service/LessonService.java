package com.hzb.erp.common.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hzb.erp.common.entity.Lesson;
import com.hzb.erp.common.entity.Staff;
import com.hzb.erp.common.entity.Student;
import com.hzb.erp.common.enums.SignStateEnum;
import com.hzb.erp.common.pojo.dto.*;
import com.hzb.erp.common.pojo.vo.LessonTeacherStatsVO;
import com.hzb.erp.common.pojo.vo.LessonVO;

import java.util.List;

/**
 * <p>
 * 课次表 服务类
 * </p>
 *
 * @author 541720500@qq.com
 */
public interface LessonService extends IService<Lesson> {

    /**
     * 带分页的列表
     *
     * @param param 参数
     */
    IPage<LessonVO> getList(LessonParamDTO param);

    /**
     * 获取老师列表
     */
    List<Staff> getTeachers(Lesson lesson);

    /**
     * 获取学生列表
     */
    List<Student> getStudents(Lesson lesson);

    /**
     * 不带分页的全部
     *
     * @param param 参数
     */
    List<LessonVO> getAll(LessonParamDTO param);

    /**
     * 通过编排计划删除课表
     */
    Boolean deleteBySchedule(List<Long> ids);

    /**
     * 课时费统计
     */
    IPage<LessonTeacherStatsVO> statsByTeachers(LessonParamDTO param);

    /**
     * 删除
     */
    Boolean deleteLesson(List<Long> ids);

    /**
     * 停课
     */
    Boolean stopLesson(List<Long> ids);

    LessonVO getInfo(Long id);

    /**
     * 添加或更新
     */
    Boolean saveOrUpdateByDTO(LessonSaveDTO dto);

    /**
     * 快速创建课次，选择学生即可开课，不用选择班级
     */
    Boolean createQuickly(LessonSaveQuicklyDTO dto, Long creatorId);

    Boolean reopenLesson(List<Long> ids);

    /**
     * 学生签到
     */
    Boolean studentSign(Long lessonId, Long studentId, SignStateEnum state);

    /**
     * 点名
     */
//    Boolean rollCall(Long teacherId, Long lessonId, Long studentId, SignStateEnum state);

    /**
     * 批量点名
     */
    Boolean rollCallBatch(Long teacherId, List<LessonSignSaveDTO> signData);

    /**
     * 获取现在签到的状态
     */
    SignStateEnum getSignStateByNow(Long lessonId);

    /**
     * 生成学生的课次记录
     *
     * @param minutes 查询的分钟范围
     */
//    void generateLessonStudent(Integer minutes);

    /**
     * 结课逻辑
     */
    void closeLesson();

    /**
     * 导出统计
     */
    void exportStatisData(LessonParamDTO param);

    /**
     * 导出课表
     */
    void exportLessonData(LessonParamDTO param);

    /**
     * 上课提醒
     */
    void lessonNotice();

    /**
     * 课次数预警提醒
     */
    void lessonLessWarning();

    /**
     * 学生请假
     */
    Boolean studentLeaveApply(Long studentId, Long lessonId);

    /**
     * 修改课程签到消课课程
     */
    boolean changeCourseAtSign(Long lessonId, Long studentId, Long courseId);

    /**
     * 老师点评
     */
    boolean teachEvaluate(TeachEvaluateDTO dto, Long studentId);

    boolean addStudents(LessonStudentAddDTO dto, Long currentUserId);

    /**
    * 开启与关闭预约状态
    * */
    boolean handleBooking(List<Long> ids, Boolean state);

    /**
     * 学生预约
     */
    Boolean studentAppoint(Long lessonId, Long studentId);
}
