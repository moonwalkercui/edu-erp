package com.hzb.erp.common.service;

import com.hzb.erp.common.entity.Dict;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 系统设置表 服务类
 * </p>
 *
 * @author Ryan
 */
public interface DictService extends IService<Dict> {
    Dict getByCode(String code);
}
