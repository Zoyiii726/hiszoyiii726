<template>
    <div class="page-card">
        <div class="page-title">开立检验申请</div>

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
            <el-table-column prop="deptName" label="科室" width="100"></el-table-column>
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
            <el-divider content-position="left">已开检验申请</el-divider>
            <el-table :data="applyList" border style="width: 100%">
                <el-table-column prop="itemName" label="检验项目"></el-table-column>
                <el-table-column prop="itemPrice" label="费用" width="100" align="right"></el-table-column>
                <el-table-column prop="status" label="状态" width="100" align="center">
                    <template slot-scope="scope">
                        <el-tag :type="statusType(scope.row.status)">{{ statusText(scope.row.status) }}</el-tag>
                    </template>
                </el-table-column>
                <el-table-column prop="createTime" label="开单时间" width="180"></el-table-column>
            </el-table>

            <el-divider content-position="left">新建检验申请</el-divider>
            <el-form :model="form" label-width="100px" size="small" inline>
                <el-form-item label="检验项目">
                    <el-select v-model="form.itemId" placeholder="请选择" style="width: 240px">
                        <el-option v-for="i in itemList" :key="i.itemId" :label="i.itemName + '（¥' + i.itemPrice + '）'" :value="i.itemId"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="临床信息">
                    <el-input v-model="form.clinicalInfo" placeholder="简要说明" style="width: 300px"></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="submit">开立检验申请</el-button>
                </el-form-item>
            </el-form>
        </template>
    </div>
</template>

<script>
import { getRegistrationByRegNo, getLabItemList, addLabApply, getLabApplyList, getRegistrationByDoctor } from '../../api'

export default {
    name: 'DoctorLab',
    data() {
        return {
            regNo: '', patient: null, itemList: [], applyList: [], patientList: [], queueLoading: false,
            form: { itemId: null, clinicalInfo: '' }
        }
    },
    created() {
        this.loadItems()
        this.loadPatientList()
    },
    methods: {
        loadItems() { getLabItemList().then(res => { this.itemList = res.data || [] }) },
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
            this.loadApply()
        },
        loadApply() {
            getLabApplyList({ patientId: this.patient.patientId }).then(res => { this.applyList = res.data || [] })
        },
        submit() {
            if (!this.form.itemId) { this.$message.warning('请选择检验项目'); return }
            const user = JSON.parse(localStorage.getItem('user'))
            addLabApply({
                regId: this.patient.regId, patientId: this.patient.patientId, doctorId: user.userId,
                itemId: this.form.itemId, clinicalInfo: this.form.clinicalInfo
            }).then(() => {
                this.$message.success('检验申请开立成功')
                this.form = { itemId: null, clinicalInfo: '' }
                this.loadApply()
            })
        },
        statusText(s) { return { UNPAID: '未缴费', PAID: '已缴费', EXECUTING: '检验中', COMPLETED: '已完成' }[s] || s },
        statusType(s) { return { UNPAID: 'info', PAID: 'warning', EXECUTING: '', COMPLETED: 'success' }[s] || '' }
    }
}
</script>
