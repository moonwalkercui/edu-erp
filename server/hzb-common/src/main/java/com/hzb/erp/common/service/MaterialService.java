package com.hzb.erp.common.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hzb.erp.common.entity.CreditMall;
import com.hzb.erp.common.entity.Material;
import com.hzb.erp.common.enums.SwitchEnum;
import com.hzb.erp.common.pojo.dto.MaterialParamDTO;
import com.hzb.erp.common.pojo.dto.MaterialStorageDTO;
import com.hzb.erp.common.pojo.vo.MaterialVO;

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
