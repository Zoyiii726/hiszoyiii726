package com.his.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class Medicine {
    private Long medicineId;
    private String medicineCode;
    private String medicineName;
    private String medicineSpec;
    private String packageUnit;
    private String manufacturer;
    private BigDecimal price;
    private Integer stock;
    private String dosageForm;
    private String usageMethod;
    private String dosage;
    private Integer status;
    private Date createTime;
    private Date updateTime;
}
