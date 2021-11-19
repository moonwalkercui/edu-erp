package com.hzb.erp.common.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hzb.erp.common.entity.Clazz;
import com.hzb.erp.common.entity.Course;
import com.hzb.erp.common.entity.Student;
import com.hzb.erp.common.pojo.dto.ClassParamDTO;
import com.hzb.erp.common.pojo.dto.ClassSaveDTO;
import com.hzb.erp.common.pojo.vo.ClassVO;

import java.util.List;

/**
 * <p>
 * 班级 服务类
 * </p>
 *
 * @author 541720500@qq.com
 */
public interface ClazzService extends IService<Clazz> {
    IPage<ClassVO> getList(ClassParamDTO param);

    List<ClassVO> getAll(ClassParamDTO param);

    ClassVO getInfo(Long id);

    Boolean saveOrUpdateByDTO(ClassSaveDTO classSaveDTO);

    /**
    * 结业
    * */
    Boolean over(List<Long> ids, Long operatorId);

    /**
     * 1对1自动生成班级
     */
    void autoCreateOne2One(Student student, Course course, Long teacherId);
}
