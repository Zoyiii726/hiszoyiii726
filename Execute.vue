<template>
    <div class="page-card">
        <div class="page-title">执行处置</div>
        <div class="search-bar">
            <el-button type="primary" @click="loadList">刷新</el-button>
            <span style="color:#909399;margin-left:10px">仅显示已缴费待处置的申请</span>
        </div>
        <el-table :data="list" border style="width: 100%">
            <el-table-column prop="patientName" label="患者" width="100"></el-table-column>
            <el-table-column prop="patientNo" label="病历号" width="110"></el-table-column>
            <el-table-column prop="itemName" label="处置项目"></el-table-column>
            <el-table-column prop="doctorName" label="开单医生" width="100"></el-table-column>
            <el-table-column prop="clinicalInfo" label="临床信息"></el-table-column>
            <el-table-column prop="status" label="状态" width="100" align="center">
                <template slot-scope="scope">
                    <el-tag :type="scope.row.status === 'EXECUTING' ? 'warning' : 'success'">{{ statusText(scope.row.status) }}</el-tag>
                </template>
            </el-table-column>
            <el-table-column label="操作" width="120" align="center">
                <template slot-scope="scope">
                    <el-button type="primary" size="mini" @click="goResult">录入结果</el-button>
                </template>
            </el-table-column>
        </el-table>
    </div>
</template>

<script>
import { getTreatmentApplyList, updateTreatmentApply } from '../../api'

export default {
    name: 'TreatmentExecute',
    data() { return { list: [] } },
    created() { this.loadList() },
    methods: {
        loadList() {
            getTreatmentApplyList({ status: 'PAID' }).then(res => {
                const paid = res.data || []
                getTreatmentApplyList({ status: 'EXECUTING' }).then(r => { this.list = paid.concat(r.data || []) })
            })
        },
        goResult(row) {
            const targetRow = row || {}
            const next = () => this.$router.push('/treatment/result')
            if (targetRow.applyId && targetRow.status === 'PAID') {
                updateTreatmentApply({ applyId: targetRow.applyId, status: 'EXECUTING' }).then(() => {
                    this.$message.success('处置申请已进入执行中')
                    this.loadList()
                    next()
                })
                return
            }
            next()
        },
        statusText(s) { return { PAID: '待处置', EXECUTING: '处置中', COMPLETED: '已完成' }[s] || s }
    }
}
</script>
