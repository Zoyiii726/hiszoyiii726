<template>
    <div class="page-card">
        <div class="page-title">处置管理</div>
        <div class="search-bar">
            <el-select v-model="status" placeholder="状态" clearable style="width: 140px" @change="loadList">
                <el-option label="未缴费" value="UNPAID"></el-option>
                <el-option label="已缴费" value="PAID"></el-option>
                <el-option label="处置中" value="EXECUTING"></el-option>
                <el-option label="已完成" value="COMPLETED"></el-option>
            </el-select>
            <el-button type="primary" @click="loadList">查询</el-button>
        </div>
        <el-table :data="list" border style="width: 100%">
            <el-table-column prop="applyNo" label="申请单号" width="180"></el-table-column>
            <el-table-column prop="patientName" label="患者" width="100"></el-table-column>
            <el-table-column prop="itemName" label="处置项目"></el-table-column>
            <el-table-column prop="doctorName" label="开单医生" width="100"></el-table-column>
            <el-table-column prop="treatDoctorName" label="处置医生" width="100"></el-table-column>
            <el-table-column prop="status" label="状态" width="100" align="center">
                <template slot-scope="scope">
                    <el-tag :type="statusType(scope.row.status)">{{ statusText(scope.row.status) }}</el-tag>
                </template>
            </el-table-column>
            <el-table-column prop="treatTime" label="处置时间" width="180"></el-table-column>
        </el-table>
    </div>
</template>

<script>
import { getTreatmentApplyList } from '../../api'

export default {
    name: 'TreatmentManage',
    data() { return { status: '', list: [] } },
    created() { this.loadList() },
    methods: {
        loadList() {
            const params = {}
            if (this.status) params.status = this.status
            getTreatmentApplyList(params).then(res => { this.list = res.data || [] })
        },
        statusText(s) { return { UNPAID: '未缴费', PAID: '已缴费', EXECUTING: '处置中', COMPLETED: '已完成' }[s] || s },
        statusType(s) { return { UNPAID: 'info', PAID: 'warning', EXECUTING: '', COMPLETED: 'success' }[s] || '' }
    }
}
</script>
