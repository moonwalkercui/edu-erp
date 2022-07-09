package com.hzb.erp.api.pc.student.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hzb.erp.common.enums.FamilyRelationshipEnum;
import com.hzb.erp.common.enums.GenderEnum;
import com.hzb.erp.common.enums.StudentStageEnum;
import com.hzb.erp.utils.DateTool;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * <p>
 * 学生表vo
 * </p>
 *
 * @author 541720500@qq.com
 */
@Data
public class StudentVO {
    @ApiModelProperty(value = "主键")
    private Long id;
    @ApiModelProperty(value = "姓名")
    private String name;
    @ApiModelProperty(value = "亲属名")
    private String parentName;
    @ApiModelProperty(value = "学校名")
    private String schoolName;
    @ApiModelProperty(value = "家庭关系")
    private FamilyRelationshipEnum familyRel;
    @ApiModelProperty(value = "手机号")
    private String mobile;
    @ApiModelProperty(value = "阶段")
    private StudentStageEnum stage;
    @ApiModelProperty(value = "年龄")
    private Integer age;
    @ApiModelProperty(value = "性别")
    private GenderEnum gender;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;
    private String headImg;
    @ApiModelProperty(value = "身份证号")
    private String idcard;
    @ApiModelProperty(value = "客户来源")
    private Long joinWay;
    @ApiModelProperty(value = "客户来源名称")
    private String joinWayName;
    @ApiModelProperty(value = "备注")
    private String remark;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate graduationDate;
    @ApiModelProperty(value = "毕业原因")
    private String graduationReason;
    @ApiModelProperty(value = "顾问姓名")
    private String counselorName;
    @ApiModelProperty(value = "添加时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime addTime;
    @ApiModelProperty(value = "最新跟进记录时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime contactTime;
    @ApiModelProperty(value = "下次联系时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime contactNextTime;
    @ApiModelProperty(value = "联系信息")
    private String contactInfo;
    // 剩余课次 当参数里有班级id的时候有只返回班级关联的课程数 todo
    @ApiModelProperty(value = "剩余课次数")
    private Integer countLessonRemaining;

    public Integer getAge() {
        return DateTool.getAgeByBirthday(this.getBirthday());
    }

    @ApiModelProperty(value = "课次少提醒标记")
    private Boolean warning;

    @ApiModelProperty(value = "年级")
    private Integer grade;

    @ApiModelProperty(value = "入学时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate joinDate;

    @ApiModelProperty(value = "当前积分")
    private Integer credit;

    @ApiModelProperty(value = "体验卡数量")
    private Integer countCourseTrial;

    public Boolean getWarning() {
        return countLessonRemaining == null ? null : countLessonRemaining <= 5;
    }
}
