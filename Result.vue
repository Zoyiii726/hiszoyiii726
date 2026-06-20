<template>
    <div class="page-card">
        <div class="page-title">录入处置结果</div>
        <div class="search-bar">
            <el-button type="primary" @click="searchList">刷新</el-button>
            <span style="color:#909399;margin-left:10px">仅显示已缴费可处置的申请</span>
        </div>
        <el-table :data="list" border style="width: 100%" @row-click="selectRow">
            <el-table-column prop="patientName" label="患者" width="100"></el-table-column>
            <el-table-column prop="patientNo" label="病历号" width="110"></el-table-column>
            <el-table-column prop="itemName" label="处置项目"></el-table-column>
            <el-table-column prop="itemPrice" label="费用" width="90" align="right"></el-table-column>
            <el-table-column prop="status" label="状态" width="100" align="center">
                <template slot-scope="scope">
                    <el-tag :type="scope.row.status === 'COMPLETED' ? 'success' : 'warning'">
                        {{ scope.row.status === 'COMPLETED' ? '已完成' : '待处置' }}
                    </el-tag>
                </template>
            </el-table-column>
        </el-table>
        <el-divider></el-divider>
        <div v-if="current" class="patient-info-bar">
            <span><strong>已选择：</strong>{{ current.patientName }} - {{ current.itemName }}</span>
        </div>
        <el-form :model="resultForm" label-width="100px" v-if="current" style="margin-top:15px">
            <el-form-item label="处置医生">
                <el-input :value="userName" disabled></el-input>
            </el-form-item>
            <el-form-item label="处置结果">
                <el-input type="textarea" v-model="resultForm.treatResult" :rows="5" placeholder="请输入处置结果"></el-input>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="saveResult">提交处置结果</el-button>
            </el-form-item>
        </el-form>
    </div>
</template>

<script>
import { getTreatmentApplyList, updateTreatmentApply } from '../../api'

export default {
    name: 'TreatmentResult',
    data() { return { list: [], current: null, resultForm: { treatResult: '' } } },
    computed: {
        userName() { return JSON.parse(localStorage.getItem('user') || '{}').realName || '' }
    },
    created() { this.searchList() },
    methods: {
        searchList() {
            getTreatmentApplyList({ status: 'PAID' }).then(res => {
                const paid = res.data || []
                getTreatmentApplyList({ status: 'EXECUTING' }).then(r => { this.list = paid.concat(r.data || []) })
            })
        },
        selectRow(row) {
            this.current = row
            this.resultForm = { treatResult: '' }
        },
        saveResult() {
            if (!this.current) { this.$message.warning('请先选择处置申请'); return }
            const user = JSON.parse(localStorage.getItem('user'))
            updateTreatmentApply({
                applyId: this.current.applyId,
                treatResult: this.resultForm.treatResult, treatDoctorId: user.userId
            }).then(() => {
                this.$message.success('处置结果提交成功')
                this.current = null
                this.searchList()
            })
        }
    }
}
</script>
