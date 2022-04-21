package com.hzb.erp.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hzb.erp.common.entity.Org;
import com.hzb.erp.common.entity.StaffOrginfo;
import com.hzb.erp.common.enums.OrgLevelEnum;
import com.hzb.erp.common.exception.BizException;
import com.hzb.erp.common.mapper.OrgMapper;
import com.hzb.erp.common.mapper.StaffOrginfoMapper;
import com.hzb.erp.common.pojo.dto.OrgParamDTO;
import com.hzb.erp.common.pojo.dto.OrgSaveDTO;
import com.hzb.erp.common.pojo.vo.OrgVO;
import com.hzb.erp.common.service.OrgService;
import com.hzb.erp.common.service.StaffOrginfoService;
import com.hzb.erp.utils.ParentListBuilder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 *
 */
@Service
public class OrgServiceImpl extends ServiceImpl<OrgMapper, Org> implements OrgService {

    @Autowired
    private StaffOrginfoMapper staffOrginfoMapper;

    @Override
    public IPage<OrgVO> getList(OrgParamDTO param) {
        return baseMapper.getList(new Page<>(param.getPage(), param.getPageSize()), param);
    }

    @Override
    public List<OrgVO> getAll(OrgParamDTO param) {
        return baseMapper.getList(param);
    }

    @Override
    public boolean saveOrUpdateByDTO(OrgSaveDTO dto) {
        Org item = new Org();
        BeanUtils.copyProperties(dto, item);
        if (dto.getRegion() != null && dto.getRegion().size() == 3) {
            item.setProvince(dto.getRegion().get(0));
            item.setCity(dto.getRegion().get(1));
            item.setDistrict(dto.getRegion().get(2));
        }
        this.saveOrUpdate(item);

        List<Long> idList = getParentsIds(dto.getPid());
        if(idList == null) {
            item.setIdPath(item.getId().toString());
        } else {
            idList.add(item.getId());
            item.setIdPath(StringUtils.join(idList, ","));
        }

        if(item.getPid() == null || item.getPid() == 0) {
            item.setNamePath(item.getName());
        } else {
            Org parentOrg = getById(item.getPid());
            item.setNamePath(StringUtils.isBlank(parentOrg.getNamePath())
                    ? item.getName()
                    : parentOrg.getNamePath() + " " + item.getName());
        }

        this.updateById(item);
        return true;
    }

    @Override
    public boolean handleDel(List<Long> ids) {
        for (Long id : ids) {
            QueryWrapper<Org> qw = new QueryWrapper<>();
            qw.eq("pid", id);
            if (count(qw) > 0) {
                Org target = getById(id);
                throw new BizException(target.getName() + "下有子机构故无法删除");
            }
            QueryWrapper<StaffOrginfo> qw1 = new QueryWrapper<>();
            qw1.eq("group_id", id).or().eq("com_id", id).or().eq("dpt_id", id);
            if (staffOrginfoMapper.selectCount(qw1) > 0) {
                Org target = getById(id);
                throw new BizException(target.getName() + "下有人员故无法删除");
            }
        }
        return removeByIds(ids);
    }

    @Override
    public List<Org> getParents(Long pid) {
        if (pid == null) {
            return null;
        }
        List<Org> records = list();
        ParentListBuilder<Org> treeUtil = new ParentListBuilder<>(records);
        treeUtil.building(pid);
        return treeUtil.getRes();
    }

    @Override
    public List<Long> getParentsIds(Long pid) {
        List<Org> list = getParents(pid);
        if (list == null) {
            return null;
        }
        return list.stream().map(Org::getId).collect(Collectors.toList());
    }

    @Override
    public OrgVO getInfo(Long id) {
        return baseMapper.getInfo(id);
    }

    @Override
    public Org[] getParentOrgById(Long orgId) {

        Org[] res = new Org[3];

        Org self = getById(orgId);
        List<Org> parents = getParents(self.getPid());
        Org group = findGroupByParents(parents);
        Org com = findComByParents(parents);

        if (OrgLevelEnum.GROUP.equals(self.getLevel())) {
            res[0] = self;
        } else if (OrgLevelEnum.COM.equals(self.getLevel())) {
            res[0] = group;
            res[1] = self;
        } else if (OrgLevelEnum.DPT.equals(self.getLevel())) {
            res[0] = group;
            res[1] = com;
            res[2] = self;
        }

        return res;
    }


    private Org findGroupByParents(List<Org> parents) {
        if (parents == null) {
            return null;
        }
        for (Org p : parents) {
            if (OrgLevelEnum.GROUP.equals(p.getLevel())) {
                return p;
            }
        }
        return null;
    }

    private Org findComByParents(List<Org> parents) {
        if (parents == null) {
            return null;
        }
        for (Org p : parents) {
            if (OrgLevelEnum.COM.equals(p.getLevel())) {
                return p;
            }
        }
        return null;
    }

    private Org findDptByParents(List<Org> parents) {
        if (parents == null) {
            return null;
        }
        for (Org p : parents) {
            if (OrgLevelEnum.DPT.equals(p.getLevel())) {
                return p;
            }
        }
        return null;
    }

    /**
    * 更新全路径 todo
    * */
    private void updateNamePath(Org org) {

        List<Org> children = baseMapper.getChildrenList(org.getId());

    }

}




