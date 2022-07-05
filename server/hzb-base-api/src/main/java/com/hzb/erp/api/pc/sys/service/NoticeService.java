package com.hzb.erp.api.pc.sys.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hzb.erp.api.pc.sys.entity.Notice;
import com.hzb.erp.common.pojo.CommonParamDTO;
import com.hzb.erp.api.pc.sys.pojo.NoticeValidDTO;


/**
 *
 */
public interface NoticeService extends IService<Notice> {

    boolean saveOrUpdateByDTO(NoticeValidDTO dto);

    IPage<Notice> getList(CommonParamDTO param);
}
