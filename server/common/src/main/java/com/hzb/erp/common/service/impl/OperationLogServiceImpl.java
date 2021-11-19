package com.hzb.erp.common.service.impl;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hzb.erp.common.entity.OperationLog;
import com.hzb.erp.common.mapper.OperationLogMapper;
import com.hzb.erp.common.service.OperationLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 操作日志 服务实现类
 * </p>
 *
 * @author Ryan
 */
@Service
@Slf4j
public class OperationLogServiceImpl extends ServiceImpl<OperationLogMapper, OperationLog> implements OperationLogService {

    @Async
    @Override
    public void addOne(OperationLog olog) {
        this.save(olog);
        log.info(JSONUtil.toJsonStr(olog));
    }

}
