<template>
    <div class="page-card">
        <div class="page-title">窗口退号</div>

        <div class="search-bar">
            <el-input v-model="keyword" placeholder="输入挂号单号/患者姓名/病历号" style="width: 280px" clearable @keyup.enter.native="loadList"></el-input>
            <el-button type="primary" @click="loadList">查询</el-button>
        </div>

        <el-table :data="regList" border style="width: 100%">
            <el-table-column prop="regNo" label="挂号单号" width="180"></el-table-column>
            <el-table-column prop="patientName" label="患者姓名" width="100"></el-table-column>
            <el-table-column prop="patientNo" label="病历号" width="110"></el-table-column>
            <el-table-column prop="deptName" label="科室" width="90"></el-table-column>
            <el-table-column prop="doctorName" label="医生" width="90"></el-table-column>
            <el-table-column prop="regLevel" label="号别" width="80"></el-table-column>
            <el-table-column prop="regFee" label="挂号费" width="90" align="right">
                <template slot-scope="scope">¥{{ scope.row.regFee }}</template>
            </el-table-column>
            <el-table-column prop="status" label="状态" width="100" align="center">
                <template slot-scope="scope">
                    <el-tag :type="statusTagType(scope.row.status)">{{ statusText(scope.row.status) }}</el-tag>
                </template>
            </el-table-column>
            <el-table-column label="操作" width="100" align="center">
                <template slot-scope="scope">
                    <el-button type="danger" size="mini" :disabled="scope.row.status !== 'WAITING'" @click="doRefund(scope.row)">退号</el-button>
                </template>
            </el-table-column>
        </el-table>
    </div>
</template>

<script>
import { getRegistrationList, updateRegStatus } from '../../api'

export default {
    name: 'ChargeRefundReg',
    data() {
        return {
            keyword: '',
            regList: []
        }
    },
    created() {
        this.loadList()
    },
    methods: {
        loadList() {
            getRegistrationList({}).then(res => {
                const keyword = (this.keyword || '').trim()
                const list = res.data || []
                this.regList = !keyword ? list : list.filter(item => {
                    return [item.regNo, item.patientName, item.patientNo].some(value => (value || '').includes(keyword))
                })
            })
        },
        statusText(s) {
            return { WAITING: '待就诊', CONSULTING: '就诊中', COMPLETED: '已完成', CANCELED: '已退号' }[s] || s
        },
        statusTagType(s) {
            return { WAITING: 'success', CONSULTING: 'warning', COMPLETED: 'info', CANCELED: 'danger' }[s] || ''
        },
        doRefund(row) {
            this.$confirm(`确定对【${row.patientName}】的挂号（${row.regNo}）执行退号吗？`, '退号确认', {
                confirmButtonText: '确定', cancelButtonText: '取消', type: 'warning'
            }).then(() => {
                updateRegStatus(row.regId, 'CANCELED').then(() => {
                    this.$message.success('退号成功')
                    this.loadList()
                })
            }).catch(() => {})
        }
    }
}
</script>
