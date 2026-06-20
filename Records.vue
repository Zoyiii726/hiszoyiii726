<template>
    <div class="page-card">
        <div class="page-title">药品交易记录</div>
        <div class="search-bar">
            <el-select v-model="optType" placeholder="操作类型" clearable style="width: 140px" @change="loadList">
                <el-option label="发药" value="DISPENSE"></el-option>
                <el-option label="退药" value="RETURN"></el-option>
            </el-select>
            <el-button type="primary" @click="loadList">查询</el-button>
        </div>
        <el-table :data="list" border style="width: 100%">
            <el-table-column prop="patientName" label="患者姓名" width="120"></el-table-column>
            <el-table-column prop="patientNo" label="病历号" width="140"></el-table-column>
            <el-table-column prop="medicineCode" label="药品编码" width="120"></el-table-column>
            <el-table-column prop="medicineName" label="药品名称"></el-table-column>
            <el-table-column prop="optType" label="类型" width="90" align="center">
                <template slot-scope="scope">
                    <el-tag :type="scope.row.optType === 'DISPENSE' ? 'warning' : 'success'">
                        {{ scope.row.optType === 'DISPENSE' ? '发药' : (scope.row.optType === 'RETURN' ? '退药' : scope.row.optType) }}
                    </el-tag>
                </template>
            </el-table-column>
            <el-table-column prop="quantity" label="数量" width="80" align="center"></el-table-column>
            <el-table-column prop="operatorName" label="操作人" width="100"></el-table-column>
            <el-table-column prop="remark" label="备注"></el-table-column>
            <el-table-column prop="createTime" label="时间" width="180"></el-table-column>
        </el-table>
    </div>
</template>

<script>
import { getPharmacyRecords } from '../../api'

export default {
    name: 'PharmacyRecords',
    data() { return { optType: '', list: [] } },
    created() { this.loadList() },
    methods: {
        loadList() {
            const params = {}
            if (this.optType) params.optType = this.optType
            getPharmacyRecords(this.optType || undefined).then(res => { this.list = res.data || [] })
        }
    }
}
</script>
