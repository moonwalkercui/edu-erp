package com.hzb.erp.common.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hzb.erp.common.entity.Advertisement;
import com.hzb.erp.common.pojo.dto.AdvertisementParamDTO;
import com.hzb.erp.common.pojo.vo.AdvertisementVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 通知管理 Mapper 接口
 * </p>
 *
 * @author Ryan
 */
public interface AdvertisementMapper extends BaseMapper<Advertisement> {
    IPage<AdvertisementVO> getList(Page<?> page, AdvertisementParamDTO param);

    List<AdvertisementVO> getList(@Param("param") AdvertisementParamDTO param);
}
