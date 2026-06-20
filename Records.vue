<template>
    <div class="page-card">
        <div class="page-title">费用记录查询</div>

        <div class="search-bar">
            <el-select v-model="query.chargeType" placeholder="费用类型" clearable style="width: 140px">
                <el-option label="挂号费" value="REG"></el-option>
                <el-option label="检查费" value="EXAM"></el-option>
                <el-option label="检验费" value="LAB"></el-option>
                <el-option label="药费" value="PRES"></el-option>
                <el-option label="处置费" value="TREAT"></el-option>
            </el-select>
            <el-select v-model="query.status" placeholder="状态" clearable style="width: 120px">
                <el-option label="已收费" value="PAID"></el-option>
                <el-option label="已退费" value="REFUNDED"></el-option>
            </el-select>
            <el-button type="primary" @click="loadList">查询</el-button>
            <el-button @click="reset">重置</el-button>
        </div>

        <el-table :data="list" border style="width: 100%" show-summary :summary-method="getSummaries">
            <el-table-column prop="chargeNo" label="收费单号" width="180"></el-table-column>
            <el-table-column prop="patientName" label="患者姓名" width="100"></el-table-column>
            <el-table-column prop="chargeTypeName" label="费用类型" width="100"></el-table-column>
            <el-table-column prop="totalAmount" label="金额" width="100" align="right">
                <template slot-scope="scope">¥{{ scope.row.totalAmount }}</template>
            </el-table-column>
            <el-table-column prop="payType" label="支付方式" width="100"></el-table-column>
            <el-table-column prop="status" label="状态" width="100" align="center">
                <template slot-scope="scope">
                    <el-tag :type="scope.row.status === 'PAID' ? 'success' : 'danger'">
                        {{ scope.row.status === 'PAID' ? '已收费' : '已退费' }}
                    </el-tag>
                </template>
            </el-table-column>
            <el-table-column prop="chargeUserName" label="收费员" width="100"></el-table-column>
            <el-table-column prop="createTime" label="收费时间" width="180"></el-table-column>
        </el-table>
    </div>
</template>

<script>
import { getChargeList } from '../../api'

const TYPE_MAP = { REG: '挂号费', EXAM: '检查费', LAB: '检验费', PRES: '药费', TREAT: '处置费' }

export default {
    name: 'ChargeRecords',
    data() {
        return {
            query: { chargeType: '', status: '' },
            list: []
        }
    },
    created() {
        this.loadList()
    },
    methods: {
        loadList() {
            const params = {}
            if (this.query.chargeType) params.chargeType = this.query.chargeType
            if (this.query.status) params.status = this.query.status
            getChargeList(params).then(res => {
                this.list = (res.data || []).map(c => ({ ...c, chargeTypeName: TYPE_MAP[c.chargeType] || c.chargeType }))
            })
        },
        reset() {
            this.query = { chargeType: '', status: '' }
            this.loadList()
        },
        getSummaries({ columns, data }) {
            const sums = []
            columns.forEach((col, i) => {
                if (i === 3) {
                    const total = data.filter(r => r.status === 'PAID').reduce((s, r) => s + Number(r.totalAmount || 0), 0)
                    sums[i] = '合计 ¥' + total.toFixed(2)
                } else {
                    sums[i] = ''
                }
            })
            return sums
        }
    }
}
</script>
