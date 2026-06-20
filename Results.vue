<template>
    <div class="page-card">
        <div class="page-title">查看检查/检验结果</div>

        <div class="search-bar">
            <el-input v-model="patientNo" placeholder="输入病历号" style="width: 200px" @keyup.enter.native="loadData"></el-input>
            <el-button type="primary" @click="loadData">查询结果</el-button>
        </div>

        <el-tabs v-model="activeTab">
            <el-tab-pane label="检查结果" name="exam">
                <el-table :data="examList" border style="width: 100%">
                    <el-table-column prop="patientName" label="患者" width="100"></el-table-column>
                    <el-table-column prop="itemName" label="检查项目"></el-table-column>
                    <el-table-column prop="status" label="状态" width="100" align="center">
                        <template slot-scope="scope">
                            <el-tag :type="scope.row.status === 'COMPLETED' ? 'success' : 'info'">
                                {{ scope.row.status === 'COMPLETED' ? '已完成' : '未完成' }}
                            </el-tag>
                        </template>
                    </el-table-column>
                    <el-table-column prop="examResult" label="检查结果"></el-table-column>
                    <el-table-column prop="examTime" label="检查时间" width="180"></el-table-column>
                </el-table>
            </el-tab-pane>
            <el-tab-pane label="检验结果" name="lab">
                <el-table :data="labList" border style="width: 100%">
                    <el-table-column prop="patientName" label="患者" width="100"></el-table-column>
                    <el-table-column prop="itemName" label="检验项目"></el-table-column>
                    <el-table-column prop="status" label="状态" width="100" align="center">
                        <template slot-scope="scope">
                            <el-tag :type="scope.row.status === 'COMPLETED' ? 'success' : 'info'">
                                {{ scope.row.status === 'COMPLETED' ? '已完成' : '未完成' }}
                            </el-tag>
                        </template>
                    </el-table-column>
                    <el-table-column prop="labValue" label="检验值" width="120"></el-table-column>
                    <el-table-column prop="labResult" label="检验结果"></el-table-column>
                    <el-table-column prop="labTime" label="检验时间" width="180"></el-table-column>
                </el-table>
            </el-tab-pane>
        </el-tabs>
    </div>
</template>

<script>
import { getExamApplyList, getLabApplyList, getPatientByNo } from '../../api'

export default {
    name: 'DoctorResults',
    data() {
        return {
            patientNo: '', activeTab: 'exam', patientId: null,
            examList: [], labList: []
        }
    },
    created() { this.loadData() },
    methods: {
        loadData() {
            const params = { status: 'COMPLETED' }
            if (this.patientNo) {
                getPatientByNo(this.patientNo).then(res => {
                    if (res.data) {
                        params.patientId = res.data.patientId
                        this.query(params)
                    } else {
                        this.$message.warning('未找到患者')
                    }
                })
            } else {
                this.query(params)
            }
        },
        query(params) {
            getExamApplyList(params).then(res => { this.examList = res.data || [] })
            getLabApplyList(params).then(res => { this.labList = res.data || [] })
        }
    }
}
</script>
