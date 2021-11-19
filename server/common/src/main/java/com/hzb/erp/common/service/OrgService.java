package com.hzb.erp.common.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hzb.erp.common.entity.Org;
import com.hzb.erp.common.pojo.dto.OrgParamDTO;
import com.hzb.erp.common.pojo.dto.OrgSaveDTO;
import com.hzb.erp.common.pojo.vo.OrgVO;

import java.util.List;

/**
 * 机构
 */
public interface OrgService extends IService<Org> {

    IPage<OrgVO> getList(OrgParamDTO param);

    List<OrgVO> getAll(OrgParamDTO param);

    boolean saveOrUpdateByDTO(OrgSaveDTO dto);

    boolean handleDel(List<Long> ids);

    /**
     * 父级结果数据构成List
     */
    List<Org> getParents(Long pid);

    List<Long> getParentsIds(Long pid);

    OrgVO getInfo(Long id);

    /**
     * 通过机构id获取所属集团id，机构id，部门id
     * 返回长度为3
     */
    Org[] getParentOrgById(Long orgId);

}
