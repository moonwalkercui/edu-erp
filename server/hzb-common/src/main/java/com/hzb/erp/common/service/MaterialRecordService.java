package com.hzb.erp.common.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hzb.erp.common.entity.MaterialRecord;
import com.hzb.erp.common.pojo.dto.MaterialRecordParamDTO;
import com.hzb.erp.common.pojo.vo.MaterialRecordVO;

/**
 * 物料出入库服务类
 */
public interface MaterialRecordService extends IService<MaterialRecord> {

    /**
    * 分页
    * */
    IPage<MaterialRecordVO> getList(MaterialRecordParamDTO param);

}
