package com.hzb.erp.common.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hzb.erp.common.entity.CreditMall;
import com.hzb.erp.common.entity.Dict;
import com.hzb.erp.common.entity.DictItem;
import com.hzb.erp.common.mapper.CreditMallMapper;
import com.hzb.erp.common.pojo.dto.CreditMallParamDTO;
import com.hzb.erp.common.pojo.vo.CreditMallVO;
import com.hzb.erp.common.service.CreditMallService;
import com.hzb.erp.common.service.DictItemService;
import com.hzb.erp.common.service.DictService;
import com.hzb.erp.service.enums.DictCodeEnum;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public IPage<CreditMallVO> getList(CreditMallParamDTO param) {
        return this.baseMapper.getList(new Page<>(param.getPage(), param.getPageSize()), param);
    }

    @Override
    public List<Map<String, Object>> getAll() {
        List<DictItem> categories = dictService.listItemByCode(DictCodeEnum.CREDIT_MALL_CATEGORY.getCode());

        List<Map<String, Object>> res = new ArrayList<>();
        for (DictItem cate : categories) {
            Map<String, Object> map = new HashMap<>();
            map.put("category", cate.getName());
            CreditMallParamDTO param = new CreditMallParamDTO();
            param.setCategoryId(cate.getId());
            map.put("list", this.baseMapper.getList(param));
            res.add(map);
        }
        return res;
    }

    @Override
    public Boolean saveOrUpdateByDTO(CreditMall dto) {
        CreditMall item = new CreditMall();
        BeanUtils.copyProperties(dto, item);
        return this.saveOrUpdate(item);
    }

    @Override
    public CreditMallVO getInfo(Long id) {
        return baseMapper.getInfo(id);
    }
}




