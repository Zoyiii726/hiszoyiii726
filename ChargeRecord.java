package com.his.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class ChargeRecord {
    private Long chargeId;
    private String chargeNo;
    private Long patientId;
    private Long regId;
    private String chargeType;
    private Long relateId;
    private BigDecimal totalAmount;
    private String payType;
    private String status;
    private Long chargeUserId;
    private Long refundUserId;
    private Date refundTime;
    private Date createTime;
    
    private String patientName;
    private String chargeUserName;
    private String refundUserName;
    private String chargeTypeName;
}
