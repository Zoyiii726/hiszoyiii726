<template>
    <div class="page-card">
        <div class="page-title">窗口挂号</div>
        
        <el-tabs v-model="activeTab">
            <el-tab-pane label="挂号" name="register">
                <el-form :model="regForm" label-width="100px" size="small">
                    <el-divider content-position="left">基本信息</el-divider>
                    <el-row :gutter="20">
                        <el-col :span="8">
                            <el-form-item label="病历号">
                                <el-input v-model="regForm.patientNo" placeholder="输入病历号查询患者">
                                    <el-button slot="append" icon="el-icon-search" @click="searchPatient">查询</el-button>
                                </el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :span="8">
                            <el-form-item label="姓名">
                                <el-input v-model="regForm.patientName"></el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :span="8">
                            <el-form-item label="性别">
                                <el-select v-model="regForm.gender" placeholder="请选择">
                                    <el-option label="男" value="男"></el-option>
                                    <el-option label="女" value="女"></el-option>
                                </el-select>
                            </el-form-item>
                        </el-col>
                    </el-row>
                    <el-row :gutter="20">
                        <el-col :span="8">
                            <el-form-item label="年龄">
                                <el-input-number v-model="regForm.age" :min="0" :max="150"></el-input-number>
                            </el-form-item>
                        </el-col>
                        <el-col :span="8">
                            <el-form-item label="身份证号">
                                <el-input v-model="regForm.idCard"></el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :span="8">
                            <el-form-item label="联系电话">
                                <el-input v-model="regForm.phone"></el-input>
                            </el-form-item>
                        </el-col>
                    </el-row>
                    <el-row :gutter="20">
                        <el-col :span="24">
                            <el-form-item label="家庭住址">
                                <el-input v-model="regForm.address"></el-input>
                            </el-form-item>
                        </el-col>
                    </el-row>

                    <el-divider content-position="left">挂号信息</el-divider>
                    <el-row :gutter="20">
                        <el-col :span="8">
                            <el-form-item label="挂号科室">
                                <el-select v-model="regForm.deptId" placeholder="请选择科室" @change="onDeptChange">
                                    <el-option v-for="dept in departments" :key="dept.deptId" :label="dept.deptName" :value="dept.deptId"></el-option>
                                </el-select>
                            </el-form-item>
                        </el-col>
                        <el-col :span="8">
                            <el-form-item label="看诊日期">
                                <el-date-picker v-model="regForm.regDate" type="date" placeholder="选择日期" style="width: 100%"></el-date-picker>
                            </el-form-item>
                        </el-col>
                        <el-col :span="8">
                            <el-form-item label="午别">
                                <el-select v-model="regForm.timeSlot" placeholder="请选择">
                                    <el-option label="上午" value="上午"></el-option>
                                    <el-option label="下午" value="下午"></el-option>
                                </el-select>
                            </el-form-item>
                        </el-col>
                    </el-row>
                    <el-row :gutter="20">
                        <el-col :span="8">
                            <el-form-item label="挂号医生">
                                <el-select v-model="regForm.doctorId" placeholder="请选择医生">
                                    <el-option v-for="doc in doctors" :key="doc.userId" :label="doc.realName" :value="doc.userId"></el-option>
                                </el-select>
                            </el-form-item>
                        </el-col>
                        <el-col :span="8">
                            <el-form-item label="挂号级别">
                                <el-select v-model="regForm.regLevel" placeholder="请选择">
                                    <el-option label="普通号" value="普通号"></el-option>
                                    <el-option label="专家号" value="专家号"></el-option>
                                </el-select>
                            </el-form-item>
                        </el-col>
                        <el-col :span="8">
                            <el-form-item label="挂号费">
                                <el-input v-model="regForm.regFee"></el-input>
                            </el-form-item>
                        </el-col>
                    </el-row>
                    <el-row :gutter="20">
                        <el-col :span="8">
                            <el-form-item label="初诊">
                                <el-switch v-model="isFirstVisit"></el-switch>
                            </el-form-item>
                        </el-col>
                        <el-col :span="8">
                            <el-form-item label="收费方式">
                                <el-select v-model="regForm.payType" placeholder="请选择">
                                    <el-option label="现金" value="现金"></el-option>
                                    <el-option label="医保" value="医保"></el-option>
                                    <el-option label="微信" value="微信"></el-option>
                                    <el-option label="支付宝" value="支付宝"></el-option>
                                </el-select>
                            </el-form-item>
                        </el-col>
                    </el-row>

                    <el-form-item>
                        <el-button type="primary" @click="submitRegister" :loading="loading">挂号</el-button>
                        <el-button @click="resetForm">重置</el-button>
                        <el-button type="success" @click="showNewPatient = true">新建患者</el-button>
                    </el-form-item>
                </el-form>
            </el-tab-pane>
            <el-tab-pane label="退号" name="refund">
                <div class="search-bar">
                    <el-input v-model="searchRegNo" placeholder="输入挂号单号" style="width: 200px"></el-input>
                    <el-button type="primary" @click="searchRegistration">查询</el-button>
                </div>
                <el-table :data="regList" border style="width: 100%">
                    <el-table-column prop="regNo" label="挂号单号" width="180"></el-table-column>
                    <el-table-column prop="patientName" label="患者姓名"></el-table-column>
                    <el-table-column prop="deptName" label="科室"></el-table-column>
                    <el-table-column prop="doctorName" label="医生"></el-table-column>
                    <el-table-column prop="regDate" label="挂号日期" width="120"></el-table-column>
                    <el-table-column prop="regFee" label="挂号费" width="100"></el-table-column>
                    <el-table-column prop="status" label="状态" width="100">
                        <template slot-scope="scope">
                            <el-tag :type="scope.row.status === 'WAITING' ? 'success' : 'info'">
                                {{ scope.row.status === 'WAITING' ? '待就诊' : scope.row.status }}
                            </el-tag>
                        </template>
                    </el-table-column>
                    <el-table-column label="操作" width="100">
                        <template slot-scope="scope">
                            <el-button type="danger" size="mini" @click="doRefund(scope.row)" :disabled="scope.row.status !== 'WAITING'">退号</el-button>
                        </template>
                    </el-table-column>
                </el-table>
            </el-tab-pane>
        </el-tabs>

        <!-- 新建患者弹窗 -->
        <el-dialog title="新建患者" :visible.sync="showNewPatient" width="600px">
            <el-form :model="newPatient" label-width="100px" size="small">
                <el-form-item label="姓名">
                    <el-input v-model="newPatient.patientName"></el-input>
                </el-form-item>
                <el-form-item label="性别">
                    <el-select v-model="newPatient.gender">
                        <el-option label="男" value="男"></el-option>
                        <el-option label="女" value="女"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="年龄">
                    <el-input-number v-model="newPatient.age" :min="0" :max="150"></el-input-number>
                </el-form-item>
                <el-form-item label="身份证号">
                    <el-input v-model="newPatient.idCard"></el-input>
                </el-form-item>
                <el-form-item label="联系电话">
                    <el-input v-model="newPatient.phone"></el-input>
                </el-form-item>
                <el-form-item label="家庭住址">
                    <el-input v-model="newPatient.address"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer">
                <el-button @click="showNewPatient = false">取消</el-button>
                <el-button type="primary" @click="createPatient">确定</el-button>
            </div>
        </el-dialog>
    </div>
</template>

<script>
import { getDepartmentList, getDoctorsByDept, addPatient, addRegistration, getRegistrationList, updateRegStatus, getPatientByNo } from '../../api'

export default {
    name: 'ChargeRegistration',
    data() {
        return {
            activeTab: 'register',
            loading: false,
            isFirstVisit: true,
            showNewPatient: false,
            searchRegNo: '',
            departments: [],
            doctors: [],
            regList: [],
            regForm: {
                patientId: null,
                patientNo: '',
                patientName: '',
                gender: '',
                age: null,
                idCard: '',
                phone: '',
                address: '',
                deptId: null,
                doctorId: null,
                regDate: new Date(),
                timeSlot: '上午',
                regLevel: '普通号',
                regFee: 10,
                payType: '现金'
            },
            newPatient: {
                patientName: '',
                gender: '男',
                age: 30,
                idCard: '',
                phone: '',
                address: ''
            }
        }
    },
    created() {
        this.loadDepartments()
    },
    methods: {
        loadDepartments() {
            getDepartmentList().then(res => {
                this.departments = res.data
            })
        },
        onDeptChange(deptId) {
            getDoctorsByDept(deptId).then(res => {
                this.doctors = res.data
            })
        },
        searchPatient() {
            if (!this.regForm.patientNo) {
                this.$message.warning('请输入病历号')
                return
            }
            getPatientByNo(this.regForm.patientNo).then(res => {
                const patient = res.data
                if (!patient) {
                    this.$message.warning('未找到该患者')
                    return
                }
                this.selectPatient(patient)
                this.$message.success('查询成功，已填充患者信息')
            })
        },
        selectPatient(patient) {
            this.regForm.patientId = patient.patientId
            this.regForm.patientNo = patient.patientNo
            this.regForm.patientName = patient.patientName
            this.regForm.gender = patient.gender
            this.regForm.age = patient.age
            this.regForm.idCard = patient.idCard
            this.regForm.phone = patient.phone
            this.regForm.address = patient.address
        },
        createPatient() {
            addPatient(this.newPatient).then(res => {
                this.$message.success('创建患者成功')
                this.showNewPatient = false
                
                // 新建患者成功后，回填患者信息到挂号表单
                const patient = res.data
                this.selectPatient(patient)
                this.$message.success('创建患者成功，已回填病历号和患者信息')
            })
        },
        submitRegister() {
            if (!this.regForm.patientId) {
                this.$message.warning('请先查询病历号或先新建患者，再进行挂号')
                return
            }
            if (!this.regForm.deptId) {
                this.$message.warning('请选择挂号科室')
                return
            }
            if (!this.regForm.doctorId) {
                this.$message.warning('请选择挂号医生')
                return
            }
            const user = JSON.parse(localStorage.getItem('user'))
            const data = {
                ...this.regForm,
                chargeUserId: user.userId
            }
            this.loading = true
            addRegistration(data).then(res => {
                this.$message.success('挂号成功！挂号单号：' + res.data.regNo)
                this.resetForm()
                this.doctors = []
            }).catch(err => {
                const message = err?.response?.data?.message || '挂号失败，请检查患者和挂号信息'
                this.$message.error(message)
            }).finally(() => {
                this.loading = false
            })
        },
        resetForm() {
            this.regForm = {
                patientId: null,
                patientNo: '',
                patientName: '',
                gender: '',
                age: null,
                idCard: '',
                phone: '',
                address: '',
                deptId: null,
                doctorId: null,
                regDate: new Date(),
                timeSlot: '上午',
                regLevel: '普通号',
                regFee: 10,
                payType: '现金'
            }
        },
        searchRegistration() {
            getRegistrationList({}).then(res => {
                const regNo = (this.searchRegNo || '').trim()
                const list = res.data || []
                this.regList = !regNo ? list : list.filter(item => (item.regNo || '').includes(regNo))
            })
        },
        doRefund(row) {
            this.$confirm('确定要退号吗？', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                updateRegStatus(row.regId, 'CANCELED').then(() => {
                    this.$message.success('退号成功')
                    this.searchRegistration()
                })
            })
        }
    }
}
</script>
