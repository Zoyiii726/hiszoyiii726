package com.his.entity;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class PrescriptionDetail {
    private Long detailId;
    private Long presId;
    private Long medicineId;
    private Integer quantity;
    private BigDecimal price;
    private BigDecimal amount;
    private String usageMethod;
    private String dosage;
    
    private String medicineName;
    private String medicineSpec;
    private String packageUnit;
}
