package com.hzb.erp.adminCenter.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hzb.erp.common.mapper.OperationRecordMapper;
import com.hzb.erp.common.pojo.dto.OperationParamDTO;
import com.hzb.erp.common.pojo.vo.PaginationVO;
import com.hzb.erp.utils.JsonResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 审核日志
 * </p>
 *
 * @author 541720500@qq.com
 */
@RestController
@RequestMapping("/common/oprationRecord")
@Api(value = "审核日志", tags = "审核日志")
public class OprationRecordController {
    @Autowired
    private OperationRecordMapper auditRecordMapper;

    @ApiOperation("审核日志")
    @GetMapping("/list")
    public PaginationVO list(@RequestParam(value = "page", defaultValue = "1") Integer page,
                             @RequestParam(value = "pageSize", defaultValue = "30") Integer pageSize,
                             @RequestParam(value = "type", defaultValue = "") String type,
                             @RequestParam(value = "itemId") Long itemId) {
        OperationParamDTO dto = new OperationParamDTO();
        dto.setType(type);
        dto.setItemId(itemId);
        return JsonResponseUtil.paginate(auditRecordMapper.getList(new Page<>(page, pageSize), dto));
    }

}
