package com.hzb.erp.api.pc.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hzb.erp.common.pojo.AutocompleteBuilderVO;
import com.hzb.erp.common.pojo.SelectBuilderVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 科目表 Mapper 接口
 * </p>
 *
 * @author Ryan
 */
@Mapper
public interface CommonMapper extends BaseMapper<Object> {
    List<SelectBuilderVO> selectBuilder(String code);

    String loadSelectorLabel(String code, String[] ids);

    Long checkUnique(String tableName, String field, Object value);

    Map<String, Long> teacherCenterStatsCount(Long teacherId);

    @Transactional
    void revertData(String tableName);

    @Transactional
    void revertStaff();

    List<SelectBuilderVO> dictBuilder(String code);

    List<AutocompleteBuilderVO> aotucomplateBuilder(String code, String keyword);
}
