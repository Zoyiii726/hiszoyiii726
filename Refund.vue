<template>
    <div class="page-card">
        <div class="page-title">药房退药</div>
        <div class="search-bar">
            <el-button type="primary" @click="loadList">刷新已发药列表</el-button>
            <span style="color:#909399;margin-left:10px">仅显示已发药可退的处方</span>
        </div>
        <el-table :data="list" border style="width: 100%">
            <el-table-column prop="presNo" label="处方单号" width="180"></el-table-column>
            <el-table-column prop="patientName" label="患者" width="100"></el-table-column>
            <el-table-column prop="patientNo" label="病历号" width="110"></el-table-column>
            <el-table-column prop="doctorName" label="开方医生" width="100"></el-table-column>
            <el-table-column prop="status" label="状态" width="100" align="center">
                <template slot-scope="scope">
                    <el-tag :type="scope.row.status === 'DISPENSED' ? 'success' : 'info'">
                        {{ scope.row.status === 'DISPENSED' ? '已发药' : (scope.row.status === 'RETURNED' ? '已退药' : scope.row.status) }}
                    </el-tag>
                </template>
            </el-table-column>
            <el-table-column label="操作" width="120" align="center">
                <template slot-scope="scope">
                    <el-button type="danger" size="mini" :disabled="scope.row.status !== 'DISPENSED'" @click="doRefund(scope.row)">退药</el-button>
                </template>
            </el-table-column>
        </el-table>
    </div>
</template>

<script>
import { getPrescriptionList, refundMedicine } from '../../api'

export default {
    name: 'PharmacyRefund',
    data() { return { list: [] } },
    created() { this.loadList() },
    methods: {
        loadList() {
            // 查询已发药 + 已退药处方
            getPrescriptionList({ status: 'DISPENSED' }).then(res => {
                const dispensed = res.data || []
                getPrescriptionList({ status: 'RETURNED' }).then(r => {
                    this.list = dispensed.concat(r.data || [])
                })
            })
        },
        doRefund(row) {
            this.$confirm(`确定对【${row.patientName}】的处方（${row.presNo}）执行退药吗？库存将加回。`, '退药确认', {
                confirmButtonText: '确定', cancelButtonText: '取消', type: 'warning'
            }).then(() => {
                const user = JSON.parse(localStorage.getItem('user'))
                refundMedicine(row.presId, user.userId).then(() => {
                    this.$message.success('退药成功')
                    this.loadList()
                })
            }).catch(() => {})
        }
    }
}
</script>
