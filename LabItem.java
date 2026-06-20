package com.his.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class LabItem {
    private Long itemId;
    private String itemCode;
    private String itemName;
    private BigDecimal itemPrice;
    private String feeType;
    private String unit;
    private String referenceValue;
    private Long deptId;
    private Integer status;
    private Date createTime;
}
