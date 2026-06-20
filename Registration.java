package com.his.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class Registration {
    private Long regId;
    private String regNo;
    private Long patientId;
    private Long deptId;
    private Long doctorId;
    private String regLevel;
    private Date regDate;
    private String timeSlot;
    private BigDecimal regFee;
    private String status;
    private Integer queueNumber;
    private Long chargeUserId;
    private Date createTime;
    private Date updateTime;
    
    private String patientName;
    private String patientNo;
    private String gender;
    private Integer age;
    private String deptName;
    private String doctorName;
    private String idCard;
    private String phone;
}
