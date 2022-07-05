package com.hzb.erp.api.pc.material.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hzb.erp.api.pc.material.entity.MaterialRecord;
import com.hzb.erp.api.pc.material.pojo.MaterialRecordParamDTO;
import com.hzb.erp.api.pc.material.pojo.MaterialRecordVO;

/**
 * 物料出入库服务类
 */
public interface MaterialRecordService extends IService<MaterialRecord> {

    /**
    * 分页
    * */
    IPage<MaterialRecordVO> getList(MaterialRecordParamDTO param);

}
