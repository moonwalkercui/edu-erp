package com.hzb.erp.teacherMobile.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hzb.erp.common.entity.Clazz;
import com.hzb.erp.common.pojo.dto.ClassParamDTO;
import com.hzb.erp.common.pojo.vo.ClassVO;

/**
 * <p>
 * 班级 服务类
 * </p>
 *
 * @author 541720500@qq.com
 */
public interface TClassService extends IService<Clazz> {

    IPage<ClassVO> getList(ClassParamDTO param);

}
