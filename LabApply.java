package com.his.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class LabApply {
    private Long applyId;
    private String applyNo;
    private Long regId;
    private Long patientId;
    private Long doctorId;
    private Long itemId;
    private String clinicalInfo;
    private String status;
    private Long labDoctorId;
    private String labResult;
    private String labValue;
    private Date labTime;
    private Date createTime;
    private Date updateTime;
    
    private String patientName;
    private String patientNo;
    private String gender;
    private Integer age;
    private String doctorName;
    private String itemName;
    private String itemCode;
    private BigDecimal itemPrice;
    private String labDoctorName;
}
