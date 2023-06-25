package com.github.fashionbrot.entity;



import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 菜单-角色关系表
 *
 * @author fashionbrot
 */
@ApiModel(value = "菜单-角色关系表")
@Data
@EqualsAndHashCode(callSuper=false)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("sys_menu_role_relation")
public class SysMenuRoleRelationEntity implements Serializable {


	private static final long serialVersionUID = 8030707155597014997L;
	@ApiModelProperty(value = "自增id")
	@TableId(type = IdType.AUTO)
	@JsonSerialize(using = ToStringSerializer.class)
	private Long id;

	@ApiModelProperty(value = "用户ID")
	@TableField("menu_id")
	private Long menuId;

	@ApiModelProperty(value = "角色ID")
	@TableField("role_id")
	private Long roleId;

	@ApiModelProperty(value = "创建时间")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	@TableField(value="create_date",fill = FieldFill.INSERT)
	private Date createDate;


}