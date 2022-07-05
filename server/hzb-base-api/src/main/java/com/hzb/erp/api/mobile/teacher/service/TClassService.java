package com.hzb.erp.api.mobile.teacher.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hzb.erp.api.pc.clazz.entity.Clazz;
import com.hzb.erp.api.pc.clazz.pojo.ClassParamDTO;
import com.hzb.erp.api.pc.clazz.pojo.ClassVO;

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
