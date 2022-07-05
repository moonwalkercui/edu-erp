package com.hzb.erp.api.pc.creditMall.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hzb.erp.api.pc.creditMall.entity.CreditMall;
import com.hzb.erp.common.enums.SwitchEnum;
import com.hzb.erp.api.pc.creditMall.pojo.CreditExchangeDTO;
import com.hzb.erp.api.pc.creditMall.pojo.CreditMallParamDTO;
import com.hzb.erp.api.pc.creditMall.pojo.CreditMallVO;

import java.util.List;
import java.util.Map;

/**
 * 积分礼品商城
 */
public interface CreditMallService extends IService<CreditMall> {
    /**
     * 分页
     * */
    IPage<CreditMallVO> getList(CreditMallParamDTO param);

    /**
     * 列表
     * */
    List<Map<String, Object>> getAll(String keyword);

    /**
     * 保存
     * */
    Boolean saveOrUpdateByDTO(CreditMall dto);

    /**
     * 获取信息
     * */
    CreditMallVO getInfo(Long id);

    /**
     * 增加浏览次数
     * */
    int addViewNum(Long id);

    /**
     * 兑换
     * */
    boolean exchange(CreditExchangeDTO dto);

    /**
    * 启用 停用
    * */
    Boolean switchState(List<Long> ids, SwitchEnum switchEnum);

}
