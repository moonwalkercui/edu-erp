package com.hzb.erp.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.hzb.erp.common.enums.FamilyRelationshipEnum;
import com.hzb.erp.common.enums.GenderEnum;
import com.hzb.erp.common.enums.StudentStageEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * <p>
 * 学生表
 * </p>
 *
 * @author 541720500@qq.com
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "Student对象", description = "学生表")
public class Student extends AutoFillEntity {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "家长id")
    private Long userId;

    @ApiModelProperty(value = "家庭关系")
    private FamilyRelationshipEnum familyRel;

    @ApiModelProperty(value = "默认标记")
    private Boolean asDefault;

    @ApiModelProperty(value = "姓名")
    private String name;

//    @ApiModelProperty(value = "手机")
//    private String mobile;

    @ApiModelProperty(value = "阶段状态")
    private StudentStageEnum stage;

    @ApiModelProperty(value = "性别")
    private GenderEnum gender;

    @ApiModelProperty(value = "生日")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;

    @ApiModelProperty(value = "头像")
    private String headImg;

    @ApiModelProperty(value = "加入方式")
    private Long joinWay;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "身份证号")
    private String idcard;

    @ApiModelProperty(value = "结业原因")
    private String graduationReason;

    @ApiModelProperty(value = "结业日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate graduationDate;

    @ApiModelProperty(value = "顾问（未用）")
    private Long counselor;

    @ApiModelProperty(value = "红点标记成绩最后查看时间")
    private LocalDateTime redpointGrade;

    @ApiModelProperty(value = "红点标记点评最后查看时间")
    private LocalDateTime redpointEvaluate;

//    @ApiModelProperty(value = "微信登录记录表id")
//    private Long wxAccessId;

    private Long schoolId;

    @ApiModelProperty(value = "添加时间")
    private LocalDateTime addTime;

    @ApiModelProperty(value = "编辑时间")
    private LocalDateTime editTime;

    @ApiModelProperty(value = "创建者")
    private Long creator;

    @ApiModelProperty(value = "编辑者")
    private Long editor;

    @TableLogic
    @ApiModelProperty(value = "逻辑删除")
    private Boolean deleted;

    @ApiModelProperty(value = "年级")
    private Integer grade;

    @ApiModelProperty(value = "入学日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate joinDate;

    @ApiModelProperty(value = "当前积分")
    private Integer credit;
}
