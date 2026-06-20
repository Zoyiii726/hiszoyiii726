<template>
    <div class="page-card">
        <div class="page-title">欢迎使用云医院HIS系统</div>
        <el-row :gutter="20">
            <el-col :span="6">
                <el-card shadow="hover" style="text-align: center; background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); color: #fff;">
                    <i class="el-icon-user" style="font-size: 40px; margin-bottom: 10px;"></i>
                    <div style="font-size: 24px; font-weight: bold;">{{ user.realName }}</div>
                    <div style="opacity: 0.8;">{{ roleName }}</div>
                </el-card>
            </el-col>
            <el-col :span="6">
                <el-card shadow="hover" style="text-align: center;">
                    <i class="el-icon-date" style="font-size: 40px; color: #409EFF; margin-bottom: 10px;"></i>
                    <div style="font-size: 24px; font-weight: bold;">{{ currentDate }}</div>
                    <div style="color: #909399;">当前日期</div>
                </el-card>
            </el-col>
            <el-col :span="6">
                <el-card shadow="hover" style="text-align: center;">
                    <i class="el-icon-time" style="font-size: 40px; color: #67C23A; margin-bottom: 10px;"></i>
                    <div style="font-size: 24px; font-weight: bold;">{{ currentTime }}</div>
                    <div style="color: #909399;">当前时间</div>
                </el-card>
            </el-col>
            <el-col :span="6">
                <el-card shadow="hover" style="text-align: center;">
                    <i class="el-icon-s-custom" style="font-size: 40px; color: #E6A23C; margin-bottom: 10px;"></i>
                    <div style="font-size: 24px; font-weight: bold;">HIS</div>
                    <div style="color: #909399;">医院信息系统</div>
                </el-card>
            </el-col>
        </el-row>
        <el-divider></el-divider>
        <el-alert
            title="系统功能说明"
            type="info"
            :closable="false"
            show-icon>
            <div slot="default">
                <p>本系统支持6种角色登录，不同角色拥有不同的功能权限：</p>
                <ul style="margin-top: 10px; padding-left: 20px;">
                    <li><strong>挂号收费员</strong>：窗口挂号、退号、收费、退费、费用记录查询</li>
                    <li><strong>门诊医生</strong>：患者叫号、初步诊断、开立检查/检验/处方/处置申请、查看结果</li>
                    <li><strong>检查医生</strong>：患者叫号、执行检查、录入检查结果、检查管理</li>
                    <li><strong>检验医生</strong>：患者叫号、执行检验、录入检验结果、检验管理</li>
                    <li><strong>药库管理员</strong>：药房发药、退药、药库管理、交易记录</li>
                    <li><strong>处置医生</strong>：患者叫号、执行处置、录入处置结果、处置管理</li>
                </ul>
            </div>
        </el-alert>
    </div>
</template>

<script>
export default {
    name: 'Dashboard',
    data() {
        return {
            user: {},
            timer: null
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
        },
        currentDate() {
            const now = new Date()
            return `${now.getFullYear()}-${String(now.getMonth() + 1).padStart(2, '0')}-${String(now.getDate()).padStart(2, '0')}`
        },
        currentTime() {
            const now = new Date()
            return `${String(now.getHours()).padStart(2, '0')}:${String(now.getMinutes()).padStart(2, '0')}:${String(now.getSeconds()).padStart(2, '0')}`
        }
    },
    created() {
        this.user = JSON.parse(localStorage.getItem('user') || '{}')
        this.timer = setInterval(() => {
            this.$forceUpdate()
        }, 1000)
    },
    beforeDestroy() {
        if (this.timer) {
            clearInterval(this.timer)
        }
    }
}
</script>
