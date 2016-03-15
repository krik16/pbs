package com.shouyingbao.pbs.entity;

import java.io.Serializable;

public class RoleAuthority implements Serializable {
    private static final long serialVersionUID = -8694878974669838161L;
    private Integer id;

    private Integer roleId;

    private Integer authorityId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getAuthorityId() {
        return authorityId;
    }

    public void setAuthorityId(Integer authorityId) {
        this.authorityId = authorityId;
    }
}