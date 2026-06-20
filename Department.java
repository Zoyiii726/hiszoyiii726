package com.his.entity;

import lombok.Data;
import java.util.Date;

@Data
public class Department {
    private Long deptId;
    private String deptCode;
    private String deptName;
    private String deptType;
    private Integer sortOrder;
    private Integer status;
    private Date createTime;
}
