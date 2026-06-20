package com.his.entity;

import lombok.Data;
import java.util.Date;

@Data
public class SysUser {
    private Long userId;
    private String username;
    private String password;
    private String realName;
    private String roleCode;
    private Long deptId;
    private String phone;
    private Integer status;
    private Date createTime;
    private Date updateTime;
    
    private String deptName;
    private String token;
}
