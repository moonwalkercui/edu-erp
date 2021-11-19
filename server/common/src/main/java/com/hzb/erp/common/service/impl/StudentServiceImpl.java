package com.hzb.erp.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hzb.erp.common.entity.StaffOrginfo;
import com.hzb.erp.common.entity.Student;
import com.hzb.erp.common.entity.User;
import com.hzb.erp.common.enums.StudentStageEnum;
import com.hzb.erp.common.exception.BizException;
import com.hzb.erp.common.mapper.StaffOrginfoMapper;
import com.hzb.erp.common.mapper.StudentMapper;
import com.hzb.erp.common.pojo.dto.ParentInfoSaveDTO;
import com.hzb.erp.common.pojo.dto.StudentBaseInfoDTO;
import com.hzb.erp.common.pojo.dto.StudentParamDTO;
import com.hzb.erp.common.pojo.dto.StudentRegisterDTO;
import com.hzb.erp.common.pojo.vo.ClassStudentVO;
import com.hzb.erp.common.pojo.vo.StudentVO;
import com.hzb.erp.common.service.StaffOrginfoService;
import com.hzb.erp.common.service.StudentCourseService;
import com.hzb.erp.common.service.StudentService;
import com.hzb.erp.common.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 学生表 服务实现类
 * </p>
 *
 * @author 541720500@qq.com
 */
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {

    @Autowired
    private StudentCourseService studentCourseService;

    @Autowired
    private UserService userService;

    @Autowired
    private StaffOrginfoMapper staffOrginfoMapper;

    @Override
    public StudentVO getBaseInfo(Long id) {
        return this.baseMapper.getBaseInfo(id);
    }

    @Override
    public User getUser(Student student) {
        return userService.getById(student.getUserId());
    }

//    @Override
//    public Student getByMobile(String mobile) {
//        QueryWrapper<Student> qw = new QueryWrapper<>();
//        qw.eq("mobile", mobile).last("limit 1");
//        return this.getOne(qw);
//    }

//    @Override
//    public Student getByName(String name) {
//        QueryWrapper<Student> qw = new QueryWrapper<>();
//        qw.eq("name", name).orderByDesc("id").last("limit 1");
//        return this.getOne(qw);
//    }

    @Override
    public List<Student> listByName(String name) {
        QueryWrapper<Student> qw = new QueryWrapper<>();
        qw.eq("name", name);
        return list(qw);
    }

    @Override
    public Integer codeByName(String name) {
        QueryWrapper<Student> qw = new QueryWrapper<>();
        qw.likeRight("name", name);
        return this.count(qw);
    }

    @Override
    public IPage<StudentVO> getList(StudentParamDTO param) {
        return this.baseMapper.getList(new Page<>(param.getPage(), param.getPageSize()), param);
    }

    @Override
    public List<StudentVO> getAll(StudentParamDTO param) {
        return this.baseMapper.getList(param);
    }

    @Override
    public Boolean saveOrUpdateByDTO(StudentBaseInfoDTO dto) {
        if (StringUtils.isBlank(dto.getName()) && StringUtils.isBlank(dto.getParentName())) {
            throw new BizException("家长姓名和学生姓名须提供一个");
        }

        User user = userService.existOrCreate(dto.getMobile(), dto.getParentName(), dto.getPasswordEncode());
        if (dto.getParentName() != null && !dto.getParentName().equals(user.getName())) {
            user.setName(dto.getParentName());
            userService.updateById(user);
        }

        Student student = new Student();
        BeanUtils.copyProperties(dto, student);

        student.setUserId(user.getId());
        student.setEditor(dto.getCounselor());
        student.setEditTime(LocalDateTime.now());

        if (student.getId() == null) {
            student.setCreator(dto.getCounselor());
            student.setAddTime(LocalDateTime.now());
            List<Student> exists = listByUid(user.getId());
            student.setAsDefault(exists == null || exists.size() == 0);
        }

        return this.saveOrUpdate(student);
    }

    @Override
    public boolean saveOrUpdateByUser(StudentRegisterDTO dto, Long userId) {
        List<Student> exists = listByUid(userId);
        if (exists != null && exists.size() > 0) {
            for (Student e : exists) {
                if (dto.getName().equals(e.getName()) && !e.getId().equals(dto.getId())) {
                    throw new BizException("您的学生不能重名");
                }
            }
        }

        Student student = new Student();
        BeanUtils.copyProperties(dto, student);
        student.setUserId(userId);
        // 默认为意向客户
        student.setStage(StudentStageEnum.INTENTION);
        if (student.getId() == null) {
            student.setAddTime(LocalDateTime.now());
        }
        student.setEditTime(LocalDateTime.now());
        student.setAsDefault(exists == null || exists.size() == 0);
        return saveOrUpdate(student);
    }

    @Override
    public List<Student> listByUid(Long userId) {
        QueryWrapper<Student> qw = new QueryWrapper<>();
        qw.eq("user_id", userId);
        return list(qw);
    }

    @Override
    public boolean handleDelByUser(Long studentId, Long userId) {
        QueryWrapper<Student> qw = new QueryWrapper<>();
        qw.likeRight("id", studentId).eq("user_id", userId);
        return remove(qw);
    }

    @Override
    public boolean switchStudent(Long studentId, Long userId) {
        return baseMapper.switchStudent(studentId, userId);
    }



    @Override
    public Boolean changeStage(List<Long> ids, StudentStageEnum stage, Boolean force) {
        List<Student> students = this.listByIds(ids);
        for (Student student : students) {
            BigDecimal remaining = studentCourseService.getTotalLessonRemainingCount(student.getId());
            if (StudentStageEnum.GRADUATION.equals(stage) && remaining != null && remaining.compareTo(BigDecimal.ZERO) > 0) {
                if (force) {
                    throw new BizException(student.getName() + "的课次有剩余，无法结业");
                } else {
                    continue;
                }
            }
            student.setStage(stage);
        }
        return this.updateBatchById(students);
    }

    @Override
    public Boolean delete(List<Long> ids) {
        return this.removeByIds(ids);
    }

    @Override
    public IPage<ClassStudentVO> getListByClassId(StudentParamDTO param) {
        return this.baseMapper.getListByClassId(new Page<>(param.getPage(), param.getPageSize()), param);
    }

    @Override
    public boolean saveParentInfo(ParentInfoSaveDTO dto) {
        User user = userService.getById(dto.getId());
        user.setName(dto.getName());
        user.setState(dto.getState());
        return userService.updateById(user);
    }

    @Override
    public Student getDefaultStudent(Long userId) {

        QueryWrapper<Student> qw = new QueryWrapper<>();
        qw.eq("user_id", userId)
                .eq("as_default", 1)
                .last("limit 1");
        Student find = getOne(qw);
        if (find == null) {
            List<Student> exists = listByUid(userId);
            if (exists == null || exists.size() == 0) {
                return null;
            } else {
                exists.get(0).setAsDefault(true);
                updateById(exists.get(0));
                return exists.get(0);
            }
        }
        return find;
    }


    @Override
    public boolean changeHeadImg(Long studentId, String img) {
        if(studentId == null) {
            throw new BizException("缺少参数");
        }
        if(StringUtils.isBlank(img)) {
            throw new BizException("缺少图片");
        }
        Student student = getById(studentId);
        student.setHeadImg(img);
        return updateById(student);
    }

    @Override
    public boolean changeCounselor(Long studentId, Long staffId) {
        Student student = getById(studentId);
        student.setCounselor(staffId);

        StaffOrginfo staffOrginfo = staffOrginfoMapper.getByStaffId(staffId);
        student.setSchoolId(staffOrginfo.getComId());

        return updateById(student);
    }
}
