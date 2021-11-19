package com.hzb.erp.service.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import java.io.Serializable;


/**
 * 邮件发送VO
 */
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
@Data
public class EmailVO implements Serializable {

	private static final long serialVersionUID = 5817183105391564L;

	/**邮件记录id*/
	@ApiModelProperty(hidden=true)
	private Integer id;
	/**邮件标题*/

	@ApiModelProperty(name="title",value="邮件标题",required=false)
	private String title;
	/**邮件类型*/

	@ApiModelProperty(name="type",value="邮件类型",required=false)
	private String type;
	/**是否成功*/

	@Min(message="必须为数字", value = 0)
	@ApiModelProperty(name="success",value="是否成功",required=false)
	private Integer success;
	/**邮件接收者*/

	@Email(message="格式不正确")
	@ApiModelProperty(name="email",value="邮件接收者",required=false)
	private String email;
	/**邮件内容*/

	@ApiModelProperty(name="context",value="邮件内容",required=false)
	private String content;
	/**错误次数*/
	@Min(message="必须为数字", value = 0)
	@ApiModelProperty(name="error_num",value="错误次数",required=false)
	private Integer errorNum;
	/**最后发送时间*/
	@ApiModelProperty(name="last_send",value="最后发送时间",required=false)
	private Long lastSend;
}