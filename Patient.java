package com.his.entity;

import lombok.Data;
import java.util.Date;

@Data
public class Patient {
    private Long patientId;
    private String patientNo;
    private String patientName;
    private String gender;
    private Integer age;
    private String idCard;
    private String phone;
    private String address;
    private Date birthday;
    private Date createTime;
    private Date updateTime;
}
