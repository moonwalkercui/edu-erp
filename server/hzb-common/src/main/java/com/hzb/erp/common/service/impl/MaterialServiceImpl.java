package com.hzb.erp.common.service.impl;

import cn.hutool.core.util.BooleanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hzb.erp.common.entity.CreditMall;
import com.hzb.erp.common.entity.Material;
import com.hzb.erp.common.entity.MaterialRecord;
import com.hzb.erp.common.enums.MaterialRecordTypeEnum;
import com.hzb.erp.common.enums.SwitchEnum;
import com.hzb.erp.common.exception.BizException;
import com.hzb.erp.common.mapper.MaterialMapper;
import com.hzb.erp.common.mapper.MaterialRecordMapper;
import com.hzb.erp.common.pojo.dto.MaterialParamDTO;
import com.hzb.erp.common.pojo.dto.MaterialStorageDTO;
import com.hzb.erp.common.pojo.vo.MaterialVO;
import com.hzb.erp.common.service.DictItemService;
import com.hzb.erp.common.service.MaterialService;
import com.hzb.erp.utils.EnumTools;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.List;

/**
 * 物料
 */
@Service
public class MaterialServiceImpl extends ServiceImpl<MaterialMapper, Material> implements MaterialService{

    @Autowired
    private DictItemService dictItemService;

    @Autowired
    private MaterialRecordMapper materialRecordMapper;

    @Override
    public IPage<MaterialVO> getList(MaterialParamDTO param) {
        param.setState(EnumTools.getCodeByDist(param.getStateText(), SwitchEnum.class));
        return this.baseMapper.getList(new Page<>(param.getPage(), param.getPageSize()), param);
    }

    @Override
    public List<MaterialVO> getAll(MaterialParamDTO param) {
        return this.baseMapper.getList(param);
    }

    @Override
    public Boolean saveOrUpdateByDTO(Material dto) {
        Material item = new Material();
        BeanUtils.copyProperties(dto, item);
        item.setCategoryName(dictItemService.getNameById(item.getCategoryId()));
        return this.saveOrUpdate(item);
    }

    @Override
    public MaterialVO getInfo(Long id) {
        return baseMapper.getInfo(id);
    }

    @Override
    @Nullable
    public Material getByCreditMall(CreditMall creditMall) {
        if(creditMall.getMaterialId() == null) {
            return null;
        }
        return baseMapper.selectById(creditMall.getMaterialId());
    }

    @Override
    public Boolean switchState(List<Long> ids, SwitchEnum switchEnum) {
        List<Material> list = this.listByIds(ids);
        for (Material item : list) {
            item.setState(switchEnum);
        }
        return this.updateBatchById(list);
    }

    @Override
    public boolean handleStorage(MaterialStorageDTO dto) {

        Material material = this.baseMapper.selectById(dto.getId());
        if(material == null) {
            throw new BizException("未知物料");
        }
        int changeNum;
        MaterialRecordTypeEnum changeType;
        if( BooleanUtils.isTrue(dto.getInStorage())) {
            changeNum = Math.abs(dto.getStorage());
            changeType = MaterialRecordTypeEnum.IN;
        } else {
            changeNum = - Math.abs(dto.getStorage());
            changeType = MaterialRecordTypeEnum.OUT;
        }

        if(changeNum == 0) {
            throw new BizException("数量不能为0");
        }

        // 减少库存
        material.setStorage(material.getStorage() + changeNum);
        this.baseMapper.updateById(material);

        // 记录物资日志
        MaterialRecord materialRecord = new MaterialRecord();
        materialRecord.setMaterialId(material.getId());
        materialRecord.setAmount(changeNum);
        materialRecord.setReason(dto.getReason());
        materialRecord.setChangeType(changeType);
        materialRecordMapper.insert(materialRecord);

        return true;
    }
}




