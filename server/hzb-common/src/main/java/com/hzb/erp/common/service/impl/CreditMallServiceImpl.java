package com.hzb.erp.common.service.impl;

import cn.hutool.core.util.BooleanUtil;
import cn.hutool.core.util.EnumUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hzb.erp.common.entity.*;
import com.hzb.erp.common.enums.MaterialRecordTypeEnum;
import com.hzb.erp.common.enums.SwitchEnum;
import com.hzb.erp.common.exception.BizException;
import com.hzb.erp.common.mapper.CreditExchangeMapper;
import com.hzb.erp.common.mapper.CreditMallMapper;
import com.hzb.erp.common.mapper.MaterialRecordMapper;
import com.hzb.erp.common.pojo.dto.CreditExchangeParamDTO;
import com.hzb.erp.common.pojo.dto.CreditMallParamDTO;
import com.hzb.erp.common.pojo.vo.CreditMallVO;
import com.hzb.erp.common.service.CreditMallService;
import com.hzb.erp.common.service.DictItemService;
import com.hzb.erp.common.service.DictService;
import com.hzb.erp.common.service.MaterialService;
import com.hzb.erp.service.enums.DictCodeEnum;
import com.hzb.erp.utils.EnumTools;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 积分商城礼品
 */
@Service
public class CreditMallServiceImpl extends ServiceImpl<CreditMallMapper, CreditMall> implements CreditMallService{

    @Autowired
    private DictService dictService;

    @Autowired
    private DictItemService dictItemService;

    @Autowired
    private MaterialService materialService;

    @Resource
    private CreditExchangeMapper creditExchangeMapper;

    @Resource
    private MaterialRecordMapper materialRecordMapper;


    @Override
    public IPage<CreditMallVO> getList(CreditMallParamDTO param) {
        param.setState(EnumTools.getCodeByDist(param.getStateText(), SwitchEnum.class));
        return this.baseMapper.getList(new Page<>(param.getPage(), param.getPageSize()), param);
    }

    @Override
    public List<Map<String, Object>> getAll(String keyword) {
        List<DictItem> categories = dictService.listItemByCode(DictCodeEnum.CREDIT_MALL_CATEGORY.getCode());

        List<Map<String, Object>> res = new ArrayList<>();
        for (DictItem cate : categories) {
            CreditMallParamDTO param = new CreditMallParamDTO();
            param.setCategoryId(cate.getId());
            param.setName(keyword);
            param.setCanExchange(true);
            List<CreditMallVO> giftList = this.baseMapper.getList(param);
            if(!CollectionUtils.isEmpty(giftList)) {
                Map<String, Object> map = new HashMap<>();
                map.put("category", cate.getName());
                map.put("list", giftList);
                res.add(map);
            }
        }
        return res;
    }

    @Override
    public Boolean saveOrUpdateByDTO(CreditMall dto) {
        CreditMall item = new CreditMall();
        BeanUtils.copyProperties(dto, item);
        Material material = materialService.getById(item.getMaterialId());
        item.setSchoolId(material.getSchoolId());
        item.setCategoryName(dictItemService.getNameById(item.getCategoryId()));
        return this.saveOrUpdate(item);
    }

    @Override
    public CreditMallVO getInfo(Long id) {
        return baseMapper.getInfo(id);
    }

    @Override
    public int addViewNum(Long id) {
        CreditMall item = this.baseMapper.selectById(id);
        item.setViewNum(item.getViewNum() + 1);
        return this.baseMapper.updateById(item);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean exchange(CreditExchangeParamDTO dto) {

        CreditMall creditMall = this.baseMapper.selectById(dto.getId());
        Material material = materialService.getByCreditMall(creditMall);
        if(material == null) {
            throw new BizException("物资无记录");
        } else if (!SwitchEnum.YES.equals(material.getState())) {
            throw new BizException("物资已下架");
        } else if (material.getStorage() == null || material.getStorage() < 1) {
            throw new BizException("缺少库存");
        } else if(material.getStorage() < dto.getNum()) {
            throw new BizException("库存不足");
        }

        // 兑换记录
        CreditExchange exchangeData = new CreditExchange();
        exchangeData.setStudentId(dto.getStudentId());
        exchangeData.setUserId(dto.getUserId());
        exchangeData.setAmount(dto.getNum());
        int res = creditExchangeMapper.insert(exchangeData);

        // 减少库存
        material.setStorage(material.getStorage() - dto.getNum());
        materialService.updateById(material);

        // 记录物资日志
        MaterialRecord materialRecord = new MaterialRecord();
        materialRecord.setMaterialId(material.getId());
        materialRecord.setAmount(-dto.getNum());
        materialRecord.setReason("积分兑换礼品");
        materialRecord.setChangeType(MaterialRecordTypeEnum.CREDIT_MALL);
        materialRecord.setChangeTargetId(exchangeData.getId());
        materialRecord.setStudentId(dto.getStudentId());
        materialRecordMapper.insert(materialRecord);

        return res > 0;
    }

    @Override
    public Boolean switchState(List<Long> ids, SwitchEnum switchEnum) {
        List<CreditMall> list = this.listByIds(ids);
        for (CreditMall item : list) {
            item.setState(switchEnum);
        }
        return this.updateBatchById(list);
    }
}




