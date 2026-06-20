<template>
    <div class="page-card">
        <div class="page-title">开立处方</div>

        <div class="search-bar">
            <el-input v-model="regNo" placeholder="输入挂号单号查询患者" style="width: 250px" @keyup.enter.native="searchPatient"></el-input>
            <el-button type="primary" @click="searchPatient">查询</el-button>
        </div>

        <el-divider content-position="left">今日患者列表</el-divider>
        <el-table :data="patientList" border style="width: 100%; margin-bottom: 16px" v-loading="queueLoading">
            <el-table-column prop="regNo" label="挂号单号" width="180"></el-table-column>
            <el-table-column prop="queueNumber" label="排队号" width="80" align="center"></el-table-column>
            <el-table-column prop="patientName" label="患者姓名" width="100"></el-table-column>
            <el-table-column prop="patientNo" label="病历号" width="120"></el-table-column>
            <el-table-column prop="status" label="状态" width="100" align="center">
                <template slot-scope="scope">
                    <el-tag :type="scope.row.status === 'CONSULTING' ? 'warning' : 'success'">
                        {{ scope.row.status === 'CONSULTING' ? '就诊中' : '待就诊' }}
                    </el-tag>
                </template>
            </el-table-column>
            <el-table-column label="操作" width="120" align="center">
                <template slot-scope="scope">
                    <el-button type="primary" size="mini" @click="pickPatient(scope.row)">选择患者</el-button>
                </template>
            </el-table-column>
        </el-table>

        <div class="patient-info-bar" v-if="patient">
            <span><strong>患者：</strong>{{ patient.patientName }}</span>
            <span><strong>病历号：</strong>{{ patient.patientNo }}</span>
            <span><strong>性别：</strong>{{ patient.gender }}</span>
            <span><strong>年龄：</strong>{{ patient.age }}岁</span>
        </div>

        <template v-if="patient">
            <el-divider content-position="left">处方明细</el-divider>
            <el-form :model="form" label-width="80px" size="small" inline>
                <el-form-item label="药品">
                    <el-select v-model="form.medicineId" filterable placeholder="搜索药品" style="width: 260px">
                        <el-option v-for="m in medicineList" :key="m.medicineId"
                                   :label="m.medicineName + '（¥' + m.price + '/' + m.packageUnit + '）'" :value="m.medicineId"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="数量">
                    <el-input-number v-model="form.quantity" :min="1" style="width: 110px"></el-input-number>
                </el-form-item>
                <el-form-item label="用法">
                    <el-input v-model="form.usageMethod" placeholder="如口服" style="width: 120px"></el-input>
                </el-form-item>
                <el-form-item label="用量">
                    <el-input v-model="form.dosage" placeholder="如每日3次" style="width: 140px"></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="addDetail">添加药品</el-button>
                </el-form-item>
            </el-form>

            <el-table :data="form.details" border style="width: 100%; margin-bottom: 15px">
                <el-table-column type="index" label="序号" width="60"></el-table-column>
                <el-table-column prop="medicineName" label="药品名称"></el-table-column>
                <el-table-column prop="medicineSpec" label="规格" width="120"></el-table-column>
                <el-table-column prop="price" label="单价" width="90" align="right"></el-table-column>
                <el-table-column prop="quantity" label="数量" width="80" align="center"></el-table-column>
                <el-table-column prop="amount" label="金额" width="100" align="right">
                    <template slot-scope="scope">¥{{ scope.row.amount }}</template>
                </el-table-column>
                <el-table-column prop="dosage" label="用量"></el-table-column>
                <el-table-column label="操作" width="80" align="center">
                    <template slot-scope="scope">
                        <el-button type="danger" size="mini" @click="removeDetail(scope.$index)">删除</el-button>
                    </template>
                </el-table-column>
            </el-table>

            <div style="margin-bottom: 15px;">
                <strong>处方合计：¥{{ totalAmount }}</strong>
            </div>
            <el-button type="primary" @click="submit" :loading="loading" :disabled="form.details.length === 0">保存处方</el-button>
        </template>
    </div>
</template>

<script>
import { getRegistrationByRegNo, getMedicineList, addPrescription, getRegistrationByDoctor } from '../../api'

export default {
    name: 'DoctorPrescription',
    data() {
        return {
            regNo: '', patient: null, medicineList: [], loading: false, patientList: [], queueLoading: false,
            form: { medicineId: null, quantity: 1, usageMethod: '口服', dosage: '', details: [] }
        }
    },
    computed: {
        totalAmount() {
            return this.form.details.reduce((s, d) => s + Number(d.amount || 0), 0).toFixed(2)
        }
    },
    created() {
        this.loadMedicine()
        this.loadPatientList()
    },
    methods: {
        loadMedicine() { getMedicineList('').then(res => { this.medicineList = res.data || [] }) },
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
            getRegistrationByRegNo(this.regNo).then(res => {
                if (!res.data) { this.$message.warning('未找到该挂号单号'); return }
                this.pickPatient(res.data)
            })
        },
        pickPatient(row) {
            this.regNo = row.regNo
            this.patient = row
        },
        addDetail() {
            if (!this.form.medicineId) { this.$message.warning('请选择药品'); return }
            const m = this.medicineList.find(x => x.medicineId === this.form.medicineId)
            if (!m) return
            this.form.details.push({
                medicineId: m.medicineId, medicineName: m.medicineName, medicineSpec: m.medicineSpec,
                price: m.price, quantity: this.form.quantity,
                amount: (m.price * this.form.quantity).toFixed(2),
                usageMethod: this.form.usageMethod, dosage: this.form.dosage
            })
            this.form.medicineId = null
            this.form.quantity = 1
            this.form.dosage = ''
        },
        removeDetail(idx) { this.form.details.splice(idx, 1) },
        submit() {
            const user = JSON.parse(localStorage.getItem('user'))
            this.loading = true
            addPrescription({
                regId: this.patient.regId, patientId: this.patient.patientId, doctorId: user.userId,
                details: this.form.details
            }).then(() => {
                this.$message.success('处方开立成功')
                this.form = { medicineId: null, quantity: 1, usageMethod: '口服', dosage: '', details: [] }
            }).finally(() => { this.loading = false })
        }
    }
}
</script>
