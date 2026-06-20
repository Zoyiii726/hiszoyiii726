<template>
    <div class="page-card">
        <div class="page-title">门诊收费</div>

        <div class="search-bar">
            <el-input v-model="patientNo" placeholder="输入病历号" style="width: 200px"></el-input>
            <el-button type="primary" @click="searchPatient">查询患者</el-button>
        </div>

        <div v-if="patient" class="patient-info-bar" ref="patientSummary">
            <span><strong>患者：</strong>{{ patient.patientName }}</span>
            <span><strong>病历号：</strong>{{ patient.patientNo }}</span>
            <span><strong>性别：</strong>{{ normalizeGender(patient.gender) }}</span>
            <span><strong>年龄：</strong>{{ patient.age }}岁</span>
        </div>

        <template v-if="patient">
            <el-divider content-position="left">未缴费项目</el-divider>
            <el-table :data="unpaidList" border style="width: 100%" v-loading="loading">
                <el-table-column type="index" label="序号" width="60"></el-table-column>
                <el-table-column prop="typeName" label="费用类型" width="100"></el-table-column>
                <el-table-column prop="itemName" label="项目名称"></el-table-column>
                <el-table-column prop="amount" label="金额" width="120" align="right">
                    <template slot-scope="scope">¥{{ scope.row.amount }}</template>
                </el-table-column>
                <el-table-column label="操作" width="140" align="center">
                    <template slot-scope="scope">
                        <el-button type="primary" size="mini" @click="chargeItem(scope.row)">收费</el-button>
                    </template>
                </el-table-column>
            </el-table>

            <el-divider content-position="left">本次已收费</el-divider>
            <el-table :data="paidList" border style="width: 100%">
                <el-table-column type="index" label="序号" width="60"></el-table-column>
                <el-table-column prop="chargeTypeName" label="费用类型" width="100"></el-table-column>
                <el-table-column prop="totalAmount" label="金额" width="120" align="right">
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
                <el-table-column prop="createTime" label="收费时间" width="180"></el-table-column>
            </el-table>
        </template>

        <el-dialog title="收费" :visible.sync="showCharge" width="420px">
            <el-form :model="chargeForm" label-width="100px" size="small">
                <el-form-item label="费用类型">{{ chargeForm.typeName }}</el-form-item>
                <el-form-item label="项目">{{ chargeForm.itemName }}</el-form-item>
                <el-form-item label="金额">¥{{ chargeForm.amount }}</el-form-item>
                <el-form-item label="支付方式">
                    <el-select v-model="chargeForm.payType" placeholder="请选择">
                        <el-option label="现金" value="现金"></el-option>
                        <el-option label="医保" value="医保"></el-option>
                        <el-option label="微信" value="微信"></el-option>
                        <el-option label="支付宝" value="支付宝"></el-option>
                    </el-select>
                </el-form-item>
            </el-form>
            <div slot="footer">
                <el-button @click="showCharge = false">取消</el-button>
                <el-button type="primary" @click="confirmCharge">确认收费</el-button>
            </div>
        </el-dialog>
    </div>
</template>

<script>
import { getPatientByNo, getExamApplyList, getLabApplyList, getTreatmentApplyList, getPrescriptionList, addCharge, getChargeList } from '../../api'

const TYPE_MAP = { EXAM: '检查费', LAB: '检验费', TREAT: '处置费', PRES: '药费' }

export default {
    name: 'ChargeCharge',
    data() {
        return {
            patientNo: '',
            patient: null,
            loading: false,
            unpaidList: [],
            paidList: [],
            showCharge: false,
            chargeForm: { chargeType: '', relateId: null, itemName: '', amount: 0, payType: '现金', typeName: '' }
        }
    },
    methods: {
        searchPatient() {
            if (!this.patientNo) { this.$message.warning('请输入病历号'); return }
            getPatientByNo(this.patientNo).then(res => {
                if (!res.data) { this.$message.warning('未找到该患者'); return }
                this.selectPatient(res.data)
            })
        },
        selectPatient(patient) {
            this.patient = patient
            this.patientNo = patient.patientNo
            this.$message.success(`已选择患者：${patient.patientName}`)
            this.loadUnpaid()
            this.loadPaid()
            this.$nextTick(() => {
                const el = this.$refs.patientSummary
                if (el && el.scrollIntoView) {
                    el.scrollIntoView({ behavior: 'smooth', block: 'start' })
                }
            })
        },
        normalizeGender(gender) {
            if (gender === 'ç”·') return '男'
            if (gender === 'å¥³') return '女'
            return gender || ''
        },
        loadUnpaid() {
            this.loading = true
            const pid = this.patient.patientId
            Promise.all([
                getExamApplyList({ patientId: pid, status: 'UNPAID' }),
                getLabApplyList({ patientId: pid, status: 'UNPAID' }),
                getTreatmentApplyList({ patientId: pid, status: 'UNPAID' }),
                getPrescriptionList({ patientId: pid, status: 'UNPAID' })
            ]).then(([exam, lab, treat, pres]) => {
                const list = []
                ;(exam.data || []).forEach(i => list.push({ chargeType: 'EXAM', relateId: i.applyId, itemName: i.itemName, amount: i.itemPrice, regId: i.regId, typeName: TYPE_MAP['EXAM'] }))
                ;(lab.data || []).forEach(i => list.push({ chargeType: 'LAB', relateId: i.applyId, itemName: i.itemName, amount: i.itemPrice, regId: i.regId, typeName: TYPE_MAP['LAB'] }))
                ;(treat.data || []).forEach(i => list.push({ chargeType: 'TREAT', relateId: i.applyId, itemName: i.itemName, amount: i.itemPrice, regId: i.regId, typeName: TYPE_MAP['TREAT'] }))
                ;(pres.data || []).forEach(p => {
                    const amt = p.totalAmount || 0
                    const detailNames = (p.details || []).map(item => item.medicineName).filter(Boolean)
                    list.push({
                        chargeType: 'PRES',
                        relateId: p.presId,
                        itemName: detailNames.length ? detailNames.join('、') : '处方药品',
                        amount: amt,
                        regId: p.regId,
                        typeName: TYPE_MAP['PRES']
                    })
                })
                this.unpaidList = list
            }).finally(() => { this.loading = false })
        },
        loadPaid() {
            getChargeList({ patientId: this.patient.patientId }).then(res => {
                this.paidList = (res.data || []).map(c => ({ ...c, chargeTypeName: TYPE_MAP[c.chargeType] || c.chargeType }))
            })
        },
        chargeItem(row) {
            this.chargeForm = { ...row, payType: '现金' }
            this.showCharge = true
        },
        confirmCharge() {
            const user = JSON.parse(localStorage.getItem('user'))
            const data = {
                patientId: this.patient.patientId,
                regId: this.chargeForm.regId,
                chargeType: this.chargeForm.chargeType,
                relateId: this.chargeForm.relateId,
                totalAmount: this.chargeForm.amount,
                payType: this.chargeForm.payType,
                chargeUserId: user.userId
            }
            addCharge(data).then(() => {
                this.$message.success('收费成功')
                this.showCharge = false
                this.loadUnpaid()
                this.loadPaid()
            })
        }
    }
}
</script>
