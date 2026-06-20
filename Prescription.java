package com.his.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class Prescription {
    private Long presId;
    private String presNo;
    private Long regId;
    private Long patientId;
    private Long doctorId;
    private String status;
    private Date createTime;
    private Date updateTime;
    
    private String patientName;
    private String patientNo;
    private String doctorName;
    private BigDecimal totalAmount;
    private List<PrescriptionDetail> details;
}
