package com.hzb.erp.service;

import com.hzb.erp.common.enums.*;
import com.hzb.erp.dataScope.DataScopeEntityEnum;
import com.hzb.erp.utils.EnumTools;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author Ryan
 * description 通用服务
 **/
@Service
public class CommonService {

    /**
     * 获取码值列表
     * 需要加入到系统码值的枚举都要加入到方法中的enumList中
     */
    public static Map<String, Map<Object, String>> enumList() throws Exception {

        // 把要返回前端的枚举都放到这个list里。
        List<? extends Class<? extends BaseEnum>> enumList = Arrays.asList(
                LessonStateEnum.class,
                GenderEnum.class,
                SignStateEnum.class,
                SignTypeEnum.class,
                StudentStageEnum.class,
                SwitchEnum.class,
                TeachTypeEnum.class,
                ContactStageEnum.class,
                ContactTypeEnum.class,
                LessonTypeEnum.class,
                DegreeEnum.class,
                StaffStateEnum.class,
                FinanceTypeEnum.class,
                FinanceStateEnum.class,
                AdvertisementTypeEnum.class,
                OrgLevelEnum.class,
                FamilyRelationshipEnum.class,
                OprationTypeEnum.class,
                DataScopeTypeEnum.class,
                DateRangeNameEnum.class,
                VerifyStateEnum.class
        );
        Map<String, Map<Object, String>> list = new HashMap<>();
        for (Class<? extends BaseEnum> item : enumList) {
            list.put(item.getSimpleName(), EnumTools.fetchForMap(item));
        }

        /*
         * 数据权限
         * */
        Map<Object, String> dataScopeEntityMap = new HashMap<>();
        for(DataScopeEntityEnum dse: DataScopeEntityEnum.values()) {
            dataScopeEntityMap.put(dse.getCode(), dse.getDist());
        }
        list.put("DataScopeEntities", dataScopeEntityMap);

        return list;
    }
}
