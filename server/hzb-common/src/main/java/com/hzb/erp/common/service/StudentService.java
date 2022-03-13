package com.hzb.erp.common.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hzb.erp.common.entity.Student;
import com.hzb.erp.common.entity.User;
import com.hzb.erp.common.enums.StudentStageEnum;
import com.hzb.erp.common.pojo.dto.*;
import com.hzb.erp.common.pojo.vo.ClassStudentVO;
import com.hzb.erp.common.pojo.vo.StudentVO;

import java.util.List;

/**
 * <p>
 * 学生表 服务类
 * </p>
 *
 * @author 541720500@qq.com
 */
public interface StudentService extends IService<Student> {
    StudentVO getBaseInfo(Long id);

    User getUser(Student student);

//    Student getByMobile(String mobile);

//    Student getByName(String name);

    List<Student> listByName(String name);

    /**
     * 为了杜绝重名，通过数量获取一个code
     */
    Long codeByName(String name);

    IPage<StudentVO> getList(StudentParamDTO param);

    List<StudentVO> getAll(StudentParamDTO param);

    Boolean saveOrUpdateByDTO(StudentBaseInfoDTO studentBaseInfoDTO);

    /**
     * 改变学员状态
     *
     * @param force 是否抛出异常
     */
    Boolean changeStage(List<Long> ids, StudentStageEnum stage, Boolean force);

    Boolean delete(List<Long> ids);

    IPage<ClassStudentVO> getListByClassId(StudentParamDTO param);

    boolean saveParentInfo(ParentInfoSaveDTO dto);

    Student getDefaultStudent(Long userId);

    boolean saveOrUpdateByUser(StudentRegisterDTO dto, Long currentUserId);

    List<Student> listByUid(Long userId);

    boolean handleDelByUser(Long studentId, Long userId);

    boolean switchStudent(Long studentId, Long userId);

    boolean changeHeadImg(Long studentId, String img);

    boolean changeCounselor(Long studentId, Long staffId);
}
