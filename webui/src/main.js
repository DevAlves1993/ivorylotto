import Vue from 'vue'
import VueRouter from 'vue-router'
import App from './App.vue'
import Index from './pages/Index'
import Bulk from './pages/Bulk';
import Premium from './pages/Premium';
import TwoWay from './pages/TwoWay';
import 'bootstrap/dist/css/bootstrap.min.css'

Vue.use(VueRouter);

Vue.config.productionTip = false;

const router = new VueRouter({
    routes : [
        {path:'/',component: Index},
        {path:'/bulk',component: Bulk},
        {path:'/premium',component:Premium},
        {path:'/twoway',component: TwoWay}
    ]
});

new Vue({
    router,
    render: h => h(App),
}).$mount('#app');
