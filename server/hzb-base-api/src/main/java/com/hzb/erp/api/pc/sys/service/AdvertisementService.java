package com.hzb.erp.api.pc.sys.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hzb.erp.api.pc.sys.entity.Advertisement;
import com.hzb.erp.api.pc.sys.pojo.AdvertisementParamDTO;
import com.hzb.erp.api.pc.sys.pojo.AdvertisementSaveDTO;
import com.hzb.erp.api.pc.sys.pojo.AdvertisementVO;

import java.util.List;

/**
 * <p>
 * 通知管理 服务类
 * </p>
 *
 * @author Ryan
 */
public interface AdvertisementService extends IService<Advertisement> {
    IPage<AdvertisementVO> getList(AdvertisementParamDTO param);

    List<AdvertisementVO> getAll(AdvertisementParamDTO param);

    Boolean saveOrUpdateByDTO(AdvertisementSaveDTO dto);

    boolean changeState(List<Long> ids, boolean b);

    Advertisement getByCode(String code);
}
