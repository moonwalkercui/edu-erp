package com.hzb.erp.api.pc.clazz.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hzb.erp.api.pc.clazz.entity.Clazz;
import com.hzb.erp.api.pc.course.entity.Course;
import com.hzb.erp.api.pc.student.entity.Student;
import com.hzb.erp.common.enums.LessonTypeEnum;
import com.hzb.erp.common.enums.StudentStageEnum;
import com.hzb.erp.api.pc.clazz.mapper.ClazzMapper;
import com.hzb.erp.api.pc.clazz.pojo.ClassParamDTO;
import com.hzb.erp.api.pc.clazz.pojo.ClassSaveDTO;
import com.hzb.erp.api.pc.clazz.pojo.ClassVO;
import com.hzb.erp.api.pc.clazz.service.ClassStudentService;
import com.hzb.erp.api.pc.clazz.service.ClazzService;
import com.hzb.erp.common.service.SettingService;
import com.hzb.erp.api.pc.student.service.StudentService;
import com.hzb.erp.service.enums.SettingNameEnum;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 班级 服务实现类
 * </p>
 *
 * @author 541720500@qq.com
 */
@Service
public class ClazzServiceImpl extends ServiceImpl<ClazzMapper, Clazz> implements ClazzService {

    @Autowired
    private StudentService studentService;
    @Autowired
    private ClassStudentService classStudentService;
    @Autowired
    private SettingService settingService;

    @Override
    public IPage<ClassVO> getList(ClassParamDTO param) {
        return this.baseMapper.getList(new Page<>(param.getPage(), param.getPageSize()), param);
    }

    @Override
    public List<ClassVO> getAll(ClassParamDTO param) {
        return this.baseMapper.getList(param);
    }

    @Override
    public ClassVO getInfo(Long id) {
        return this.baseMapper.getInfo(id);
    }

    @Override
    public Boolean saveOrUpdateByDTO(ClassSaveDTO classSaveDTO) {
        Clazz item = new Clazz();
        BeanUtils.copyProperties(classSaveDTO, item);
        return this.saveOrUpdate(item);
    }

    @Override
    public Boolean over(List<Long> ids, Long operatorId) {
        List<Clazz> list = this.listByIds(ids);
        for (Clazz item : list) {
            item.setBeOver(true);
            item.setOverOperator(operatorId);
            item.setOverTime(LocalDateTime.now());
            Boolean bl = settingService.boolValue(SettingNameEnum.CLASS_CLOSE_WITH_GRADUATE_STUDENT.getCode());
            if (bl != null && bl) {
                // 学生结业
                List<Student> studentList = classStudentService.getStudentsByClassId(item.getId());
                if (studentList != null && studentList.size() > 0) {
                    studentService.changeStage(studentList.stream().map(Student::getId).collect(Collectors.toList()), StudentStageEnum.GRADUATION, false);
                }
            }
        }
        return this.updateBatchById(list);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void autoCreateOne2One(Student student, Course course, Long teacherId) {

        // 判断是否1v1并自动生成班级
        if (LessonTypeEnum.VIP.equals(course.getLessonType()) && settingService.boolValue(SettingNameEnum.CLASS_AUTO_CREATE_ON_ONE2ONE.getCode())) {
            Clazz clazz = new Clazz();
            clazz.setName(student.getName() + "1V1");
            clazz.setCourseId(course.getId());
            clazz.setTeacherId(teacherId);
            clazz.setStartDate(LocalDate.now());
            clazz.setEndDate(LocalDate.now().plusMonths(course.getExpireMonths()));
            clazz.setPlannedLessonCount(course.getLessonCount());
            clazz.setPlannedStudentCount(1);
            this.save(clazz);

            classStudentService.addClassStudent(clazz.getId(), student.getId(), teacherId);
        }

    }
}
