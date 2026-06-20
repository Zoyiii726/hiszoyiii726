<template>
    <div class="login-container">
        <div class="login-box">
            <div class="login-title">云医院HIS系统</div>
            <el-form :model="loginForm" :rules="rules" ref="loginForm" label-width="0">
                <el-form-item prop="username">
                    <el-input v-model="loginForm.username" prefix-icon="el-icon-user" placeholder="请输入用户名"></el-input>
                </el-form-item>
                <el-form-item prop="password">
                    <el-input v-model="loginForm.password" prefix-icon="el-icon-lock" type="password" placeholder="请输入密码" @keyup.enter.native="handleLogin"></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" style="width: 100%" @click="handleLogin" :loading="loading">登录</el-button>
                </el-form-item>
            </el-form>
            <div style="text-align: center; color: #909399; font-size: 12px; margin-top: 20px;">
                <p>测试账号：</p>
                <p>挂号收费员：charge01 / 123456</p>
                <p>门诊医生：doctor01 / 123456</p>
                <p>检查医生：exam01 / 123456</p>
                <p>检验医生：lab01 / 123456</p>
                <p>药库管理员：pharmacy01 / 123456</p>
                <p>处置医生：treatment01 / 123456</p>
            </div>
        </div>
    </div>
</template>

<script>
import { login } from '../api'

export default {
    name: 'Login',
    data() {
        return {
            loginForm: {
                username: '',
                password: ''
            },
            rules: {
                username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
                password: [{ required: true, message: '请输入密码', trigger: 'blur' }]
            },
            loading: false
        }
    },
    methods: {
        handleLogin() {
            this.$refs.loginForm.validate(valid => {
                if (valid) {
                    this.loading = true
                    login(this.loginForm).then(res => {
                        localStorage.setItem('token', res.data.token)
                        localStorage.setItem('user', JSON.stringify(res.data.user))
                        this.$message.success('登录成功')
                        this.$router.push('/')
                    }).finally(() => {
                        this.loading = false
                    })
                }
            })
        }
    }
}
</script>
