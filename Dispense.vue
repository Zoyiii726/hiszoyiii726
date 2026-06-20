<template>
    <div class="page-card">
        <div class="page-title">药房发药</div>
        <div class="search-bar">
            <el-button type="primary" @click="loadList">刷新待发药列表</el-button>
            <span style="color:#909399;margin-left:10px">仅显示已缴费待发药处方</span>
        </div>
        <el-table :data="list" border style="width: 100%">
            <el-table-column prop="presNo" label="处方单号" width="180"></el-table-column>
            <el-table-column prop="patientName" label="患者" width="100"></el-table-column>
            <el-table-column prop="patientNo" label="病历号" width="110"></el-table-column>
            <el-table-column prop="doctorName" label="开方医生" width="100"></el-table-column>
            <el-table-column label="药品明细">
                <template slot-scope="scope">
                    <el-button type="text" @click="viewDetail(scope.row)">查看明细</el-button>
                </template>
            </el-table-column>
            <el-table-column prop="status" label="状态" width="100" align="center">
                <template slot-scope="scope">
                    <el-tag type="warning">待发药</el-tag>
                </template>
            </el-table-column>
            <el-table-column label="操作" width="120" align="center">
                <template slot-scope="scope">
                    <el-button type="primary" size="mini" @click="doDispense(scope.row)">发药</el-button>
                </template>
            </el-table-column>
        </el-table>

        <el-dialog title="处方明细" :visible.sync="showDetail" width="600px">
            <el-table :data="details" border>
                <el-table-column prop="medicineName" label="药品名称"></el-table-column>
                <el-table-column prop="medicineSpec" label="规格" width="120"></el-table-column>
                <el-table-column prop="quantity" label="数量" width="80" align="center"></el-table-column>
                <el-table-column prop="price" label="单价" width="90" align="right"></el-table-column>
                <el-table-column prop="amount" label="金额" width="90" align="right"></el-table-column>
                <el-table-column prop="dosage" label="用量"></el-table-column>
            </el-table>
        </el-dialog>
    </div>
</template>

<script>
import { getDispenseList, dispense } from '../../api'

export default {
    name: 'PharmacyDispense',
    data() { return { list: [], showDetail: false, details: [] } },
    created() { this.loadList() },
    methods: {
        loadList() { getDispenseList().then(res => { this.list = res.data || [] }) },
        viewDetail(row) {
            this.details = row.details || []
            this.showDetail = true
            // 若列表项未带明细，按 presId 单独请求也可（此处用 getById 需导入）
        },
        doDispense(row) {
            this.$confirm(`确定为【${row.patientName}】的处方（${row.presNo}）发药吗？发药后将扣减库存。`, '发药确认', {
                confirmButtonText: '确定', cancelButtonText: '取消', type: 'warning'
            }).then(() => {
                const user = JSON.parse(localStorage.getItem('user'))
                dispense(row.presId, user.userId).then(() => {
                    this.$message.success('发药成功')
                    this.loadList()
                })
            }).catch(() => {})
        }
    }
}
</script>
