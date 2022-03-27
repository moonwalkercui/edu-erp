package com.hzb.erp.quartz.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hzb.erp.quartz.entity.QuartzJob;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

/**
 * quarz定时任务(quartz_job)数据Mapper
 *
 * @author kancy
 * @since 2022-01-11 09:26:04
*/
@Mapper
public interface QuartzJobMapper extends BaseMapper<QuartzJob> {

    @Cacheable(value = "QuartzJobList")
    List<QuartzJob> getList();

}
