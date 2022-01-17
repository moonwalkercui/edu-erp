package com.hzb.erp.common.service;

import com.hzb.erp.common.entity.DictItem;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 系统设置表 服务类
 * </p>
 *
 * @author Ryan
 */
public interface DictItemService extends IService<DictItem> {
    /**
     * 不存在则插入
     */
    void inexistThenInsert(String code, String name);
}
