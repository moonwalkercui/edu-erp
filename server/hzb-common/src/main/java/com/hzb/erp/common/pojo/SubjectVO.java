package com.hzb.erp.common.pojo;

import lombok.Data;

@Data
public class SubjectVO {
    private Long id;
    private String name;
    private String info;
    private Integer sortNum;
    // 统计数据
//    // 总人数
//    private Integer studentCount;
//    // 总课次数
//    private Integer lessonCount;
//    // 签到人数
//    private Integer signCount;
}
