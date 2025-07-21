// src/main.js
import { createApp } from 'vue';
import App from './App.vue';
import VueCookies from 'vue-cookies';
import router from './router';
import '@/assets/css/main.css';
import '@/assets/css/product.css';
import '@/assets/css/base.css';
import '@/assets/css/account.css';
import '@/assets/css/login.css';
import '@/assets/css/cart.css';
import '@/assets/css/pay.css';
import '@/assets/css/productdetail.css';
import '@/assets/css/reponsive1.css';

const app = createApp(App);
app.use(router);
app.use(VueCookies);
app.mount('#app');