package com.hzb.erp.common.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hzb.erp.common.entity.StudentCourse;
import com.hzb.erp.common.pojo.dto.PayOverdueDTO;
import com.hzb.erp.common.pojo.dto.StudentCourseParamDTO;
import com.hzb.erp.common.pojo.dto.StudentCourseSaveDTO;
import com.hzb.erp.common.pojo.vo.StudentCourseVO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * <p>
 * 学员合约表 服务类
 * </p>
 *
 * @author 541720500@qq.com
 */
public interface StudentCourseService extends IService<StudentCourse> {
    /**
     * 列表
     */
    IPage<StudentCourseVO> getList(StudentCourseParamDTO param);

    List<StudentCourseVO> getAll(StudentCourseParamDTO param);

    /**
     * 删除
     */
    Boolean delete(List<Long> ids);

    /**
     * 添加
     */
    void addOne(StudentCourseSaveDTO postData, Long operator);

    /**
     * 编辑
     */
    void editOne(StudentCourseSaveDTO postData);

    /**
     * 信息
     */
    StudentCourseVO getInfo(Long id);

    /**
     * 直接修改已完成课时
     */
    void editLessonCountComplete(Long id, Integer countLessonComplete, String remark, Long staffId);

//    /**
//     * 获取学员的课次剩余数
//     * */
//    Integer getStudentSubjectNum(Long studentId, Long subjectId);

    /**
     * 剩余课次的计算公式
     */
    Integer getLessonRemainingCount(StudentCourse sc);

    /**
     * 获取所有剩余课次
     */
    Integer listLessonRemainingCountTotal(Long courseId, Long studentId);

    /***
     * 找课时足够的记录
     * @param courseId 课程id
     * @param studentId 学员id
     * @param decCount 扣减数量
     * @return StudentCourse
     * */
    StudentCourse getEnoughLessonCount(Long courseId, Long studentId, Integer decCount);

    /***
     * 查学员总剩余多少课时
     * */
    BigDecimal getTotalLessonRemainingCount(Long studentId);

    /***
     * 找到足够少的 准备预警
     * */
    List<StudentCourse> getWarningList();

    /***
     * 检查课时是否足够,并扣课时
     * @param courseId 课程id
     * @param studentId 学员id
     * @param decCount 扣减数量
     * @return java.lang.Boolean
     * */
    Boolean decLessonCount(Long courseId, Long studentId, Integer decCount);

    /**
     * 减少课时 后台用的 todo 需逻辑审查
     */
    void decLessonCount(Integer id, Integer count);

    /**
     * 数据导出
     */
    void exportExcel(StudentCourseParamDTO param);

    Boolean editLessonExpireDate(Long id, LocalDate expireDate, Long currentUserId);

    /**
     * 交欠费
     */
    Boolean payOverdue(PayOverdueDTO dto, Long currentUserId);

    /**
    * 修改消课优先级
    * */
    Boolean changePriority(Long id, Integer value);
}
