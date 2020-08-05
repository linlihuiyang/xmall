package com.xll.xmall.admin.web.mbg.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 后台用户角色和权限关系表
 * </p>
 *
 * @author ${author}
 * @since 2020-08-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class UmsRolePermissionRelation implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long roleId;

    private Long permissionId;


}
