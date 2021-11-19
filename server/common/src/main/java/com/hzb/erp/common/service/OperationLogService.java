package com.hzb.erp.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hzb.erp.common.entity.OperationLog;

/**
 * <p>
 * 操作日志 服务类
 * </p>
 *
 * @author Ryan
 */
public interface OperationLogService extends IService<OperationLog> {

    void addOne(OperationLog olog);
}
