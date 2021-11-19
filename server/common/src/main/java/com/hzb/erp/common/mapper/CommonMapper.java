package com.hzb.erp.common.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hzb.erp.common.pojo.vo.AutocompleteBuilderVO;
import com.hzb.erp.common.pojo.vo.SelectBuilderVO;
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
public interface CommonMapper extends BaseMapper {
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
