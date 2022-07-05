package com.hzb.erp.api.pc.material.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hzb.erp.api.pc.creditMall.entity.CreditMall;
import com.hzb.erp.api.pc.material.entity.Material;
import com.hzb.erp.common.enums.SwitchEnum;
import com.hzb.erp.api.pc.material.pojo.MaterialParamDTO;
import com.hzb.erp.api.pc.material.pojo.MaterialStorageDTO;
import com.hzb.erp.api.pc.material.pojo.MaterialVO;

import java.util.List;

/**
 *
 */
public interface MaterialService extends IService<Material> {

    IPage<MaterialVO> getList(MaterialParamDTO param);

    List<MaterialVO> getAll(MaterialParamDTO param);

    Boolean saveOrUpdateByDTO(Material dto);

    MaterialVO getInfo(Long id);

    /**
    * 通过积分礼品获取物料信息
    * */
    Material getByCreditMall(CreditMall creditMall);

    /**
     * 启用 停用
     * */
    Boolean switchState(List<Long> ids, SwitchEnum switchEnum);

    /**
    * 出入库
    * */
    boolean handleStorage(MaterialStorageDTO dto);
}
