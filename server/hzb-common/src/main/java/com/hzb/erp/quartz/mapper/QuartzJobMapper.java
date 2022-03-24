package com.hzb.erp.quartz.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hzb.erp.quartz.entity.QuartzJob;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * quarz定时任务(quartz_job)数据Mapper
 *
 * @author kancy
 * @since 2022-01-11 09:26:04
 * @description 由 Mybatisplus Code Generator 创建
*/
@Mapper
public interface QuartzJobMapper extends BaseMapper<QuartzJob> {
    Long getCount();
    List<QuartzJob> getList();
}
