package com.hzb.erp.api.pc.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hzb.erp.api.pc.sys.entity.Advertisement;
import com.hzb.erp.api.pc.sys.pojo.AdvertisementParamDTO;
import com.hzb.erp.api.pc.sys.pojo.AdvertisementVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 通知管理 Mapper 接口
 * </p>
 *
 * @author Ryan
 */
@Mapper
public interface AdvertisementMapper extends BaseMapper<Advertisement> {
    IPage<AdvertisementVO> getList(Page<?> page, AdvertisementParamDTO param);

    List<AdvertisementVO> getList(@Param("param") AdvertisementParamDTO param);
}
