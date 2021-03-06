package com.winston.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName GroupUserRoleDTO
 * @Author: Winston
 * @Description: TODO
 * @Date:Create：in 2020/1/6 15:00
 * @Version：
 */
@Data
public class GroupUserRoleDTO implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column group_user_role.user_id
     *
     * @mbg.generated
     */
    private Integer userId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column group_user_role.role_id
     *
     * @mbg.generated
     */
    private Integer roleId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column group_user_role.group_id
     *
     * @mbg.generated
     */
    private Integer[] groupId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table group_user_role
     *
     * @mbg.generated
     */
    private static final long serialVersionUID = 1L;
}
