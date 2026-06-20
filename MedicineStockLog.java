package com.his.entity;

import lombok.Data;
import java.util.Date;

@Data
public class MedicineStockLog {
    private Long logId;
    private Long medicineId;
    /** 操作类型：IN-入库, OUT-出库, DISPENSE-发药, RETURN-退药 */
    private String optType;
    /** 数量（正数） */
    private Integer quantity;
    /** 关联业务ID（如处方ID） */
    private Long relateId;
    private Long operatorId;
    private String remark;
    private Date createTime;

    // 关联展示字段
    private String medicineName;
    private String medicineCode;
    private String operatorName;
    private String patientName;
    private String patientNo;
}
