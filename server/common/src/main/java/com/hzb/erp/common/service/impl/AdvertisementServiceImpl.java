package com.hzb.erp.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hzb.erp.common.entity.Advertisement;
import com.hzb.erp.common.mapper.AdvertisementMapper;
import com.hzb.erp.common.pojo.dto.AdvertisementParamDTO;
import com.hzb.erp.common.pojo.dto.AdvertisementSaveDTO;
import com.hzb.erp.common.pojo.vo.AdvertisementVO;
import com.hzb.erp.common.service.AdvertisementService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 通知管理 服务实现类
 * </p>
 *
 * @author Ryan
 */
@Service
public class AdvertisementServiceImpl extends ServiceImpl<AdvertisementMapper, Advertisement> implements AdvertisementService {

    @Override
    public IPage<AdvertisementVO> getList(AdvertisementParamDTO param) {
        return this.baseMapper.getList(new Page<>(param.getPage(), param.getPageSize()), param);
    }

    @Override
    public List<AdvertisementVO> getAll(AdvertisementParamDTO param) {
        return this.baseMapper.getList(param);
    }

    @Override
    public Boolean saveOrUpdateByDTO(AdvertisementSaveDTO dto) {
        Advertisement item = new Advertisement();
        BeanUtils.copyProperties(dto, item);

        if (dto.getCover() != null && dto.getCover().length > 0) {
            item.setCover(dto.getCover()[0]);
        }

        return this.saveOrUpdate(item);
    }

    @Override
    public boolean changeState(List<Long> ids, boolean b) {
        List<Advertisement> list = this.listByIds(ids);
        for (Advertisement item : list) {
            item.setState(b);
        }
        return this.updateBatchById(list);
    }

    @Override
    public Advertisement getByCode(String code) {
        QueryWrapper<Advertisement> qw  = new QueryWrapper<>();
        qw.eq("code", code).last("limit 1");
        return getOne(qw);
    }
}
