package com.hzb.erp.common.service;

import com.hzb.erp.common.entity.Dict;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hzb.erp.common.entity.DictItem;

import java.util.List;

/**
 * <p>
 * 系统设置表 服务类
 * </p>
 *
 * @author Ryan
 */
public interface DictService extends IService<Dict> {

    /**
     * 通过code获取单个字典
     * */
    Dict getByCode(String code);
    /**
    * 通过code获取字典列表
    * */
    List<DictItem> listItemByCode(String code);
}
