import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

const routes = [
    {
        path: '/login',
        name: 'Login',
        component: () => import('../views/Login.vue')
    },
    {
        path: '/',
        name: 'Home',
        component: () => import('../views/Home.vue'),
        redirect: '/dashboard',
        children: [
            {
                path: 'dashboard',
                name: 'Dashboard',
                component: () => import('../views/Dashboard.vue')
            },
            {
                path: 'patients',
                name: 'PatientList',
                component: () => import('../views/PatientList.vue')
            },
            // 挂号收费员
            {
                path: 'charge/registration',
                name: 'ChargeRegistration',
                component: () => import('../views/charge/Registration.vue')
            },
            {
                path: 'charge/refund-reg',
                name: 'ChargeRefundReg',
                component: () => import('../views/charge/RefundReg.vue')
            },
            {
                path: 'charge/charge',
                name: 'ChargeCharge',
                component: () => import('../views/charge/Charge.vue')
            },
            {
                path: 'charge/refund',
                name: 'ChargeRefund',
                component: () => import('../views/charge/Refund.vue')
            },
            {
                path: 'charge/records',
                name: 'ChargeRecords',
                component: () => import('../views/charge/Records.vue')
            },
            // 门诊医生
            {
                path: 'doctor/queue',
                name: 'DoctorQueue',
                component: () => import('../views/doctor/Queue.vue')
            },
            {
                path: 'doctor/medical',
                name: 'DoctorMedical',
                component: () => import('../views/doctor/Medical.vue')
            },
            {
                path: 'doctor/exam',
                name: 'DoctorExam',
                component: () => import('../views/doctor/Exam.vue')
            },
            {
                path: 'doctor/lab',
                name: 'DoctorLab',
                component: () => import('../views/doctor/Lab.vue')
            },
            {
                path: 'doctor/prescription',
                name: 'DoctorPrescription',
                component: () => import('../views/doctor/Prescription.vue')
            },
            {
                path: 'doctor/treatment',
                name: 'DoctorTreatment',
                component: () => import('../views/doctor/Treatment.vue')
            },
            {
                path: 'doctor/results',
                name: 'DoctorResults',
                component: () => import('../views/doctor/Results.vue')
            },
            // 检查医生
            {
                path: 'exam/queue',
                name: 'ExamQueue',
                component: () => import('../views/exam/Queue.vue')
            },
            {
                path: 'exam/execute',
                name: 'ExamExecute',
                component: () => import('../views/exam/Execute.vue')
            },
            {
                path: 'exam/result',
                name: 'ExamResult',
                component: () => import('../views/exam/Result.vue')
            },
            {
                path: 'exam/manage',
                name: 'ExamManage',
                component: () => import('../views/exam/Manage.vue')
            },
            // 检验医生
            {
                path: 'lab/queue',
                name: 'LabQueue',
                component: () => import('../views/lab/Queue.vue')
            },
            {
                path: 'lab/execute',
                name: 'LabExecute',
                component: () => import('../views/lab/Execute.vue')
            },
            {
                path: 'lab/result',
                name: 'LabResult',
                component: () => import('../views/lab/Result.vue')
            },
            {
                path: 'lab/manage',
                name: 'LabManage',
                component: () => import('../views/lab/Manage.vue')
            },
            // 药库管理员
            {
                path: 'pharmacy/dispense',
                name: 'PharmacyDispense',
                component: () => import('../views/pharmacy/Dispense.vue')
            },
            {
                path: 'pharmacy/refund',
                name: 'PharmacyRefund',
                component: () => import('../views/pharmacy/Refund.vue')
            },
            {
                path: 'pharmacy/manage',
                name: 'PharmacyManage',
                component: () => import('../views/pharmacy/Manage.vue')
            },
            {
                path: 'pharmacy/records',
                name: 'PharmacyRecords',
                component: () => import('../views/pharmacy/Records.vue')
            },
            // 处置医生
            {
                path: 'treatment/queue',
                name: 'TreatmentQueue',
                component: () => import('../views/treatment/Queue.vue')
            },
            {
                path: 'treatment/execute',
                name: 'TreatmentExecute',
                component: () => import('../views/treatment/Execute.vue')
            },
            {
                path: 'treatment/result',
                name: 'TreatmentResult',
                component: () => import('../views/treatment/Result.vue')
            },
            {
                path: 'treatment/manage',
                name: 'TreatmentManage',
                component: () => import('../views/treatment/Manage.vue')
            }
        ]
    }
]

const router = new VueRouter({
    mode: 'hash',
    routes
})

router.beforeEach((to, from, next) => {
    const token = localStorage.getItem('token')
    if (to.path === '/login') {
        next()
    } else {
        if (!token) {
            next('/login')
        } else {
            next()
        }
    }
})

export default router
