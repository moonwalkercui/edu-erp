package com.hzb.erp.common.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hzb.erp.common.entity.Notice;
import com.hzb.erp.common.pojo.dto.CommonParamDTO;
import com.hzb.erp.common.pojo.dto.NoticeValidDTO;


/**
 *
 */
public interface NoticeService extends IService<Notice> {

    boolean saveOrUpdateByDTO(NoticeValidDTO dto);

    IPage<Notice> getList(CommonParamDTO param);
}
