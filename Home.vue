<template>
    <div class="home-container">
        <div class="home-header">
            <div class="header-title">云医院HIS系统</div>
            <div class="header-user">
                <span>{{ user.realName }}（{{ roleName }}）</span>
                <el-button type="text" style="color: #fff" @click="logout">退出登录</el-button>
            </div>
        </div>
        <div class="home-main">
            <div class="home-aside">
                <el-menu
                    :default-active="$route.path"
                    class="el-menu-vertical"
                    router
                    background-color="#304156"
                    text-color="#bfcbd9"
                    active-text-color="#409EFF">
                    <el-menu-item index="/patients">
                        <i class="el-icon-user"></i>
                        <span>患者列表</span>
                    </el-menu-item>
                    <template v-if="user.roleCode === 'CHARGE'">
                        <el-menu-item index="/charge/registration">
                            <i class="el-icon-document"></i>
                            <span>窗口挂号</span>
                        </el-menu-item>
                        <el-menu-item index="/charge/refund-reg">
                            <i class="el-icon-document-delete"></i>
                            <span>窗口退号</span>
                        </el-menu-item>
                        <el-menu-item index="/charge/charge">
                            <i class="el-icon-s-finance"></i>
                            <span>收费</span>
                        </el-menu-item>
                        <el-menu-item index="/charge/refund">
                            <i class="el-icon-refresh-left"></i>
                            <span>退费</span>
                        </el-menu-item>
                        <el-menu-item index="/charge/records">
                            <i class="el-icon-s-order"></i>
                            <span>费用记录查询</span>
                        </el-menu-item>
                    </template>
                    <template v-if="user.roleCode === 'DOCTOR'">
                        <el-menu-item index="/doctor/queue">
                            <i class="el-icon-user-solid"></i>
                            <span>患者叫号</span>
                        </el-menu-item>
                        <el-menu-item index="/doctor/medical">
                            <i class="el-icon-notebook-2"></i>
                            <span>初步诊断</span>
                        </el-menu-item>
                        <el-menu-item index="/doctor/exam">
                            <i class="el-icon-s-data"></i>
                            <span>开立检查申请</span>
                        </el-menu-item>
                        <el-menu-item index="/doctor/lab">
                            <i class="el-icon-reading"></i>
                            <span>开立检验申请</span>
                        </el-menu-item>
                        <el-menu-item index="/doctor/prescription">
                            <i class="el-icon-medicine-box"></i>
                            <span>开立处方</span>
                        </el-menu-item>
                        <el-menu-item index="/doctor/treatment">
                            <i class="el-icon-first-aid-kit"></i>
                            <span>开立处置申请</span>
                        </el-menu-item>
                        <el-menu-item index="/doctor/results">
                            <i class="el-icon-document-checked"></i>
                            <span>查看检查/检验结果</span>
                        </el-menu-item>
                    </template>
                    <template v-if="user.roleCode === 'EXAM'">
                        <el-menu-item index="/exam/queue">
                            <i class="el-icon-user-solid"></i>
                            <span>患者叫号</span>
                        </el-menu-item>
                        <el-menu-item index="/exam/execute">
                            <i class="el-icon-video-camera"></i>
                            <span>执行检查</span>
                        </el-menu-item>
                        <el-menu-item index="/exam/result">
                            <i class="el-icon-edit-outline"></i>
                            <span>录入检查结果</span>
                        </el-menu-item>
                        <el-menu-item index="/exam/manage">
                            <i class="el-icon-setting"></i>
                            <span>检查管理</span>
                        </el-menu-item>
                    </template>
                    <template v-if="user.roleCode === 'LAB'">
                        <el-menu-item index="/lab/queue">
                            <i class="el-icon-user-solid"></i>
                            <span>患者叫号</span>
                        </el-menu-item>
                        <el-menu-item index="/lab/execute">
                            <i class="el-icon-zoom-in"></i>
                            <span>执行检验</span>
                        </el-menu-item>
                        <el-menu-item index="/lab/result">
                            <i class="el-icon-edit-outline"></i>
                            <span>录入检验结果</span>
                        </el-menu-item>
                        <el-menu-item index="/lab/manage">
                            <i class="el-icon-setting"></i>
                            <span>检验管理</span>
                        </el-menu-item>
                    </template>
                    <template v-if="user.roleCode === 'PHARMACY'">
                        <el-menu-item index="/pharmacy/dispense">
                            <i class="el-icon-box"></i>
                            <span>药房发药</span>
                        </el-menu-item>
                        <el-menu-item index="/pharmacy/refund">
                            <i class="el-icon-refresh"></i>
                            <span>药房退药</span>
                        </el-menu-item>
                        <el-menu-item index="/pharmacy/manage">
                            <i class="el-icon-s-grid"></i>
                            <span>药库管理</span>
                        </el-menu-item>
                        <el-menu-item index="/pharmacy/records">
                            <i class="el-icon-s-order"></i>
                            <span>交易记录</span>
                        </el-menu-item>
                    </template>
                    <template v-if="user.roleCode === 'TREATMENT'">
                        <el-menu-item index="/treatment/queue">
                            <i class="el-icon-user-solid"></i>
                            <span>患者叫号</span>
                        </el-menu-item>
                        <el-menu-item index="/treatment/execute">
                            <i class="el-icon-s-custom"></i>
                            <span>执行处置</span>
                        </el-menu-item>
                        <el-menu-item index="/treatment/result">
                            <i class="el-icon-edit-outline"></i>
                            <span>录入处置结果</span>
                        </el-menu-item>
                        <el-menu-item index="/treatment/manage">
                            <i class="el-icon-setting"></i>
                            <span>处置管理</span>
                        </el-menu-item>
                    </template>
                </el-menu>
            </div>
            <div class="home-content">
                <router-view />
            </div>
        </div>
    </div>
</template>

<script>
export default {
    name: 'Home',
    data() {
        return {
            user: {}
        }
    },
    computed: {
        roleName() {
            const roleMap = {
                'CHARGE': '挂号收费员',
                'DOCTOR': '门诊医生',
                'EXAM': '检查医生',
                'LAB': '检验医生',
                'PHARMACY': '药库管理员',
                'TREATMENT': '处置医生'
            }
            return roleMap[this.user.roleCode] || ''
        }
    },
    created() {
        this.user = JSON.parse(localStorage.getItem('user') || '{}')
    },
    methods: {
        logout() {
            this.$confirm('确定要退出登录吗？', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                localStorage.removeItem('token')
                localStorage.removeItem('user')
                this.$router.push('/login')
            })
        }
    }
}
</script>
