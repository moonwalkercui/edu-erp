package com.hzb.erp.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hzb.erp.common.entity.SysLog;

/**
 * <p>
 * 操作日志 服务类
 * </p>
 *
 * @author Ryan
 */
public interface SysLogService extends IService<SysLog> {

    void addOne(SysLog olog);
}
