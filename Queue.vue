<template>
    <div class="page-card">
        <div class="page-title">处置叫号</div>
        <div class="search-bar">
            <el-button type="primary" @click="loadList">刷新队列</el-button>
        </div>
        <el-table :data="list" border style="width: 100%">
            <el-table-column type="index" label="序号" width="60"></el-table-column>
            <el-table-column prop="patientName" label="患者姓名" width="120"></el-table-column>
            <el-table-column prop="patientNo" label="病历号" width="120"></el-table-column>
            <el-table-column prop="gender" label="性别" width="70" align="center"></el-table-column>
            <el-table-column prop="age" label="年龄" width="70" align="center"></el-table-column>
            <el-table-column prop="itemName" label="处置项目"></el-table-column>
            <el-table-column prop="status" label="状态" width="100" align="center">
                <template slot-scope="scope">
                    <el-tag :type="scope.row.status === 'PAID' ? 'warning' : 'success'">{{ statusText(scope.row.status) }}</el-tag>
                </template>
            </el-table-column>
            <el-table-column label="操作" width="120" align="center">
                <template slot-scope="scope">
                    <el-button type="primary" size="mini" @click="callPatient(scope.row)">叫号</el-button>
                </template>
            </el-table-column>
        </el-table>
        <div class="patient-info-bar" v-if="current" style="margin-top:15px">
            <span><strong>当前叫号：</strong>{{ current.patientName }}（{{ current.itemName }}）</span>
        </div>
    </div>
</template>

<script>
import { getTreatmentApplyList, updateTreatmentApply } from '../../api'

export default {
    name: 'TreatmentQueue',
    data() { return { list: [], current: null } },
    created() { this.loadList() },
    methods: {
        loadList() { getTreatmentApplyList({ status: 'PAID' }).then(res => { this.list = res.data || [] }) },
        callPatient(row) {
            this.current = row
            updateTreatmentApply({ applyId: row.applyId, status: 'EXECUTING' }).then(() => {
                this.$message.success('已叫号：' + row.patientName)
            })
        },
        statusText(s) { return { PAID: '待处置', EXECUTING: '处置中', COMPLETED: '已完成' }[s] || s }
    }
}
</script>
