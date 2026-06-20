<template>
    <div class="page-card">
        <div class="page-title">初步诊断</div>

        <div class="search-bar">
            <el-input v-model="searchRegNo" placeholder="输入挂号单号查询患者" style="width: 250px"></el-input>
            <el-button type="primary" @click="searchPatient">查询</el-button>
        </div>

        <el-divider content-position="left">今日待诊患者</el-divider>
        <el-table :data="patientList" border style="width: 100%; margin-bottom: 16px" v-loading="queueLoading">
            <el-table-column prop="regNo" label="挂号单号" width="180"></el-table-column>
            <el-table-column prop="queueNumber" label="排队号" width="80" align="center"></el-table-column>
            <el-table-column prop="patientName" label="患者姓名" width="100"></el-table-column>
            <el-table-column prop="patientNo" label="病历号" width="120"></el-table-column>
            <el-table-column prop="gender" label="性别" width="70" align="center"></el-table-column>
            <el-table-column prop="age" label="年龄" width="70" align="center"></el-table-column>
            <el-table-column prop="status" label="状态" width="100" align="center">
                <template slot-scope="scope">
                    <el-tag :type="scope.row.status === 'CONSULTING' ? 'warning' : 'success'">
                        {{ scope.row.status === 'CONSULTING' ? '就诊中' : '待就诊' }}
                    </el-tag>
                </template>
            </el-table-column>
            <el-table-column label="操作" width="220" align="center">
                <template slot-scope="scope">
                    <el-button type="primary" size="mini" @click="pickPatient(scope.row)">接诊</el-button>
                    <el-button type="danger" size="mini" @click="finishVisit(scope.row)">结束就诊</el-button>
                </template>
            </el-table-column>
        </el-table>

        <div class="patient-info-bar" v-if="currentPatient">
            <span><strong>患者：</strong>{{ currentPatient.patientName }}</span>
            <span><strong>病历号：</strong>{{ currentPatient.patientNo }}</span>
            <span><strong>性别：</strong>{{ currentPatient.gender }}</span>
            <span><strong>年龄：</strong>{{ currentPatient.age }}岁</span>
            <el-button type="danger" size="mini" @click="finishVisit(currentPatient)">结束就诊</el-button>
        </div>

        <el-form :model="medicalForm" label-width="100px" v-if="currentPatient">
            <el-form-item label="主诉">
                <el-input type="textarea" v-model="medicalForm.chiefComplaint" :rows="2" placeholder="请输入主诉"></el-input>
            </el-form-item>
            <el-form-item label="现病史">
                <el-input type="textarea" v-model="medicalForm.presentIllness" :rows="2" placeholder="请输入现病史"></el-input>
            </el-form-item>
            <el-form-item label="既往史">
                <el-input type="textarea" v-model="medicalForm.pastIllness" :rows="2" placeholder="请输入既往史"></el-input>
            </el-form-item>
            <el-form-item label="体格检查">
                <el-input type="textarea" v-model="medicalForm.physicalExam" :rows="2" placeholder="请输入体格检查结果"></el-input>
            </el-form-item>
            <el-form-item label="诊断">
                <el-input type="textarea" v-model="medicalForm.diagnosis" :rows="2" placeholder="请输入诊断"></el-input>
            </el-form-item>
            <el-form-item label="医嘱">
                <el-input type="textarea" v-model="medicalForm.advice" :rows="2" placeholder="请输入医嘱"></el-input>
            </el-form-item>
            <el-row :gutter="20">
                <el-col :span="12">
                    <el-form-item label="疾病编码">
                        <el-input v-model="medicalForm.diseaseCode"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="疾病名称">
                        <el-input v-model="medicalForm.diseaseName"></el-input>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-form-item>
                <el-button type="primary" @click="saveMedical">保存病历</el-button>
                <el-button type="danger" @click="finishVisit(currentPatient)">结束就诊</el-button>
                <el-button @click="resetForm">重置</el-button>
            </el-form-item>
        </el-form>
    </div>
</template>

<script>
import { getRegistrationByRegNo, getMedicalByReg, saveMedical, getRegistrationByDoctor, updateRegStatus } from '../../api'

export default {
    name: 'DoctorMedical',
    data() {
        return {
            searchRegNo: '',
            currentPatient: null,
            patientList: [],
            queueLoading: false,
            medicalForm: {
                regId: null,
                patientId: null,
                doctorId: null,
                chiefComplaint: '',
                presentIllness: '',
                pastIllness: '',
                physicalExam: '',
                diagnosis: '',
                advice: '',
                diseaseCode: '',
                diseaseName: ''
            }
        }
    },
    created() {
        this.loadPatientList()
    },
    methods: {
        loadPatientList() {
            const user = JSON.parse(localStorage.getItem('user') || '{}')
            if (!user.userId) return
            const today = new Date().toISOString().split('T')[0]
            this.queueLoading = true
            Promise.all([
                getRegistrationByDoctor(user.userId, today, 'WAITING'),
                getRegistrationByDoctor(user.userId, today, 'CONSULTING')
            ]).then(([waitingRes, consultingRes]) => {
                this.patientList = [...(consultingRes.data || []), ...(waitingRes.data || [])]
            }).finally(() => {
                this.queueLoading = false
            })
        },
        searchPatient() {
            if (!this.searchRegNo) {
                this.$message.warning('请输入挂号单号')
                return
            }
            getRegistrationByRegNo(this.searchRegNo).then(res => {
                const reg = res.data
                if (!reg) {
                    this.$message.warning('未找到该挂号单号')
                    this.currentPatient = null
                    return
                }
                this.pickPatient(reg)
            })
        },
        pickPatient(reg) {
            this.searchRegNo = reg.regNo
            this.currentPatient = reg
            this.resetForm()
            this.medicalForm.regId = reg.regId
            this.medicalForm.patientId = reg.patientId
            const user = JSON.parse(localStorage.getItem('user') || '{}')
            this.medicalForm.doctorId = user.userId
            getMedicalByReg(reg.regId).then(r => {
                if (r.data) {
                    Object.assign(this.medicalForm, {
                        chiefComplaint: r.data.chiefComplaint,
                        presentIllness: r.data.presentIllness,
                        pastIllness: r.data.pastIllness,
                        physicalExam: r.data.physicalExam,
                        diagnosis: r.data.diagnosis,
                        advice: r.data.advice,
                        diseaseCode: r.data.diseaseCode,
                        diseaseName: r.data.diseaseName
                    })
                }
            })
        },
        saveMedical() {
            saveMedical(this.medicalForm).then(() => {
                this.$message.success('病历保存成功')
            })
        },
        finishVisit(row) {
            if (!row || !row.regId) {
                this.$message.warning('请先选择患者')
                return
            }
            this.$confirm(`确定结束【${row.patientName}】本次就诊吗？结束后该患者将从待诊列表移除。`, '结束就诊', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                updateRegStatus(row.regId, 'COMPLETED').then(() => {
                    this.$message.success('已结束就诊')
                    if (this.currentPatient && this.currentPatient.regId === row.regId) {
                        this.currentPatient = null
                        this.searchRegNo = ''
                        this.resetForm()
                    }
                    this.loadPatientList()
                })
            }).catch(() => {})
        },
        resetForm() {
            this.medicalForm = {
                regId: null,
                patientId: null,
                doctorId: null,
                chiefComplaint: '',
                presentIllness: '',
                pastIllness: '',
                physicalExam: '',
                diagnosis: '',
                advice: '',
                diseaseCode: '',
                diseaseName: ''
            }
        }
    }
}
</script>
