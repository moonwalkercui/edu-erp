package com.hzb.erp.common.mapper;

import com.hzb.erp.common.entity.DictItem;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 系统设置表 Mapper 接口
 * </p>
 *
 * @author Ryan
 */
@Mapper
public interface DictItemMapper extends BaseMapper<DictItem> {

    List<DictItem> listItemByCode(String code);
}
