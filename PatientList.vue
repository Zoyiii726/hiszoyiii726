<template>
    <div class="page-card">
        <div class="page-title">{{ pageTitle }}</div>

        <div class="search-bar">
            <el-input
                v-model="keyword"
                :placeholder="searchPlaceholder"
                style="width: 280px"
                clearable
                @keyup.enter.native="loadList">
            </el-input>
            <el-button type="primary" @click="loadList">查询</el-button>
            <el-button @click="resetSearch">重置</el-button>
        </div>

        <el-table v-if="user.roleCode === 'CHARGE'" :data="filteredList" border style="width: 100%" v-loading="loading">
            <el-table-column prop="patientNo" label="病历号" width="140"></el-table-column>
            <el-table-column prop="patientName" label="姓名" width="110"></el-table-column>
            <el-table-column label="性别" width="80" align="center">
                <template slot-scope="scope">{{ normalizeGender(scope.row.gender) }}</template>
            </el-table-column>
            <el-table-column prop="age" label="年龄" width="80" align="center"></el-table-column>
            <el-table-column prop="phone" label="联系电话" width="140"></el-table-column>
            <el-table-column prop="address" label="家庭住址"></el-table-column>
            <el-table-column prop="createTime" label="建档时间" width="180"></el-table-column>
        </el-table>

        <el-table v-else-if="user.roleCode === 'DOCTOR'" :data="filteredList" border style="width: 100%" v-loading="loading">
            <el-table-column prop="regNo" label="挂号单号" width="180"></el-table-column>
            <el-table-column prop="queueNumber" label="排队号" width="80" align="center"></el-table-column>
            <el-table-column prop="patientName" label="患者姓名" width="110"></el-table-column>
            <el-table-column prop="patientNo" label="病历号" width="120"></el-table-column>
            <el-table-column prop="gender" label="性别" width="80" align="center"></el-table-column>
            <el-table-column prop="age" label="年龄" width="80" align="center"></el-table-column>
            <el-table-column prop="deptName" label="科室" width="100"></el-table-column>
            <el-table-column prop="doctorName" label="医生" width="100"></el-table-column>
            <el-table-column prop="status" label="状态" width="100" align="center">
                <template slot-scope="scope">
                    <el-tag :type="regStatusType(scope.row.status)">{{ regStatusText(scope.row.status) }}</el-tag>
                </template>
            </el-table-column>
            <el-table-column prop="regDate" label="看诊日期" width="180"></el-table-column>
        </el-table>

        <el-table v-else-if="user.roleCode === 'EXAM'" :data="filteredList" border style="width: 100%" v-loading="loading">
            <el-table-column prop="applyNo" label="申请单号" width="180"></el-table-column>
            <el-table-column prop="patientName" label="患者姓名" width="110"></el-table-column>
            <el-table-column prop="patientNo" label="病历号" width="120"></el-table-column>
            <el-table-column prop="itemName" label="检查项目"></el-table-column>
            <el-table-column prop="doctorName" label="开单医生" width="100"></el-table-column>
            <el-table-column prop="status" label="状态" width="100" align="center">
                <template slot-scope="scope">
                    <el-tag :type="applyStatusType(scope.row.status)">{{ examStatusText(scope.row.status) }}</el-tag>
                </template>
            </el-table-column>
            <el-table-column prop="createTime" label="申请时间" width="180"></el-table-column>
        </el-table>

        <el-table v-else-if="user.roleCode === 'LAB'" :data="filteredList" border style="width: 100%" v-loading="loading">
            <el-table-column prop="applyNo" label="申请单号" width="180"></el-table-column>
            <el-table-column prop="patientName" label="患者姓名" width="110"></el-table-column>
            <el-table-column prop="patientNo" label="病历号" width="120"></el-table-column>
            <el-table-column prop="itemName" label="检验项目"></el-table-column>
            <el-table-column prop="doctorName" label="开单医生" width="100"></el-table-column>
            <el-table-column prop="status" label="状态" width="100" align="center">
                <template slot-scope="scope">
                    <el-tag :type="applyStatusType(scope.row.status)">{{ labStatusText(scope.row.status) }}</el-tag>
                </template>
            </el-table-column>
            <el-table-column prop="createTime" label="申请时间" width="180"></el-table-column>
        </el-table>

        <el-table v-else-if="user.roleCode === 'PHARMACY'" :data="filteredList" border style="width: 100%" v-loading="loading">
            <el-table-column prop="presNo" label="处方单号" width="180"></el-table-column>
            <el-table-column prop="patientName" label="患者姓名" width="110"></el-table-column>
            <el-table-column prop="patientNo" label="病历号" width="120"></el-table-column>
            <el-table-column prop="doctorName" label="开方医生" width="100"></el-table-column>
            <el-table-column prop="status" label="状态" width="100" align="center">
                <template slot-scope="scope">
                    <el-tag :type="presStatusType(scope.row.status)">{{ presStatusText(scope.row.status) }}</el-tag>
                </template>
            </el-table-column>
            <el-table-column prop="createTime" label="开方时间" width="180"></el-table-column>
        </el-table>

        <el-table v-else-if="user.roleCode === 'TREATMENT'" :data="filteredList" border style="width: 100%" v-loading="loading">
            <el-table-column prop="applyNo" label="申请单号" width="180"></el-table-column>
            <el-table-column prop="patientName" label="患者姓名" width="110"></el-table-column>
            <el-table-column prop="patientNo" label="病历号" width="120"></el-table-column>
            <el-table-column prop="itemName" label="处置项目"></el-table-column>
            <el-table-column prop="doctorName" label="开单医生" width="100"></el-table-column>
            <el-table-column prop="status" label="状态" width="100" align="center">
                <template slot-scope="scope">
                    <el-tag :type="applyStatusType(scope.row.status)">{{ treatmentStatusText(scope.row.status) }}</el-tag>
                </template>
            </el-table-column>
            <el-table-column prop="createTime" label="申请时间" width="180"></el-table-column>
        </el-table>
    </div>
</template>

<script>
import {
    getPatientList,
    getRegistrationByDoctor,
    getExamApplyList,
    getLabApplyList,
    getPrescriptionList,
    getTreatmentApplyList
} from '../api'

export default {
    name: 'PatientListPage',
    data() {
        return {
            user: {},
            keyword: '',
            loading: false,
            list: []
        }
    },
    computed: {
        pageTitle() {
            const titleMap = {
                CHARGE: '患者列表',
                DOCTOR: '门诊患者列表',
                EXAM: '检查患者列表',
                LAB: '检验患者列表',
                PHARMACY: '药房患者列表',
                TREATMENT: '处置患者列表'
            }
            return titleMap[this.user.roleCode] || '患者列表'
        },
        searchPlaceholder() {
            const placeholderMap = {
                CHARGE: '输入病历号/姓名',
                DOCTOR: '输入挂号单号/病历号/姓名',
                EXAM: '输入申请单号/病历号/姓名',
                LAB: '输入申请单号/病历号/姓名',
                PHARMACY: '输入处方单号/病历号/姓名',
                TREATMENT: '输入申请单号/病历号/姓名'
            }
            return placeholderMap[this.user.roleCode] || '输入关键词查询'
        },
        filteredList() {
            const keyword = (this.keyword || '').trim()
            if (!keyword) return this.list
            return this.list.filter(item => {
                return [
                    item.patientName,
                    item.patientNo,
                    item.regNo,
                    item.applyNo,
                    item.presNo,
                    item.itemName
                ].some(value => (value || '').includes(keyword))
            })
        }
    },
    created() {
        this.user = JSON.parse(localStorage.getItem('user') || '{}')
        this.loadList()
    },
    methods: {
        loadList() {
            this.loading = true
            const today = new Date().toISOString().split('T')[0]
            let request
            switch (this.user.roleCode) {
                case 'CHARGE':
                    request = getPatientList('')
                    break
                case 'DOCTOR':
                    request = getRegistrationByDoctor(this.user.userId, today)
                    break
                case 'EXAM':
                    request = getExamApplyList({})
                    break
                case 'LAB':
                    request = getLabApplyList({})
                    break
                case 'PHARMACY':
                    request = getPrescriptionList({})
                    break
                case 'TREATMENT':
                    request = getTreatmentApplyList({})
                    break
                default:
                    request = Promise.resolve({ data: [] })
                    break
            }
            request.then(res => {
                const data = res.data || []
                if (this.user.roleCode === 'EXAM' || this.user.roleCode === 'LAB' || this.user.roleCode === 'TREATMENT') {
                    this.list = data.filter(item => ['PAID', 'EXECUTING', 'COMPLETED'].includes(item.status))
                    return
                }
                if (this.user.roleCode === 'PHARMACY') {
                    this.list = data.filter(item => ['PAID', 'DISPENSED', 'RETURNED'].includes(item.status))
                    return
                }
                this.list = data
            }).finally(() => {
                this.loading = false
            })
        },
        resetSearch() {
            this.keyword = ''
            this.loadList()
        },
        normalizeGender(gender) {
            if (gender === 'ç”·') return '男'
            if (gender === 'å¥³') return '女'
            return gender || ''
        },
        regStatusText(status) {
            return { WAITING: '待就诊', CONSULTING: '就诊中', COMPLETED: '已完成', CANCELED: '已退号' }[status] || status
        },
        regStatusType(status) {
            return { WAITING: 'success', CONSULTING: 'warning', COMPLETED: 'info', CANCELED: 'danger' }[status] || ''
        },
        applyStatusType(status) {
            return { UNPAID: 'info', PAID: 'warning', EXECUTING: '', COMPLETED: 'success' }[status] || ''
        },
        examStatusText(status) {
            return { UNPAID: '未缴费', PAID: '待检查', EXECUTING: '检查中', COMPLETED: '已完成' }[status] || status
        },
        labStatusText(status) {
            return { UNPAID: '未缴费', PAID: '待检验', EXECUTING: '检验中', COMPLETED: '已完成' }[status] || status
        },
        treatmentStatusText(status) {
            return { UNPAID: '未缴费', PAID: '待处置', EXECUTING: '处置中', COMPLETED: '已完成' }[status] || status
        },
        presStatusText(status) {
            return { UNPAID: '未缴费', PAID: '待发药', DISPENSED: '已发药', RETURNED: '已退药' }[status] || status
        },
        presStatusType(status) {
            return { UNPAID: 'info', PAID: 'warning', DISPENSED: 'success', RETURNED: 'danger' }[status] || ''
        }
    }
}
</script>
