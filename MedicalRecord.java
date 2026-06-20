package com.his.entity;

import lombok.Data;
import java.util.Date;

@Data
public class MedicalRecord {
    private Long recordId;
    private Long regId;
    private Long patientId;
    private Long doctorId;
    private String chiefComplaint;
    private String presentIllness;
    private String pastIllness;
    private String physicalExam;
    private String diagnosis;
    private String advice;
    private String diseaseCode;
    private String diseaseName;
    private Date createTime;
    private Date updateTime;
    
    private String patientName;
    private String patientNo;
    private String doctorName;
}
