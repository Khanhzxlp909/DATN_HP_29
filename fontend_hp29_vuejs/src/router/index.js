import { createRouter, createWebHistory } from 'vue-router'; // Import cho Vue 3
import HomePage from '@/views/HomePage.vue'; // Import view component
import LoginPage from '@/components/login/loginPage.vue'; // Import view component
import RegisterPage from '@/components/login/registerPage.vue'; // Import view component
import ProductPage from '@/components/home/productPage.vue'; // Import view component
import ProductDetail from '@/components/home/ProductDetail.vue'; // Import view component
import AccountManagement from '@/components/profile/accountManagement.vue'; // Import view component
import SeachProduct from '@/components/home/seachProduct.vue'; // Import view component
import CartPage from '@/components/cart/cartPage.vue'; // Import view component
import PaymentPages from '@/components/pay/paymentPages.vue';
import RegisterInfoPage from "@/components/login/registerInfoPage.vue"; // Import view component
import HomeNew from "@/components/home/HomeNew.vue";
import ContactPage from "@/components/conttact/ConttactPage.vue";

const routes = [
    {
        path: '/',
        name: 'homepages',
        component: HomePage, // Component sẽ hiển thị khi truy cập đường dẫn "/"
    },
    {
        path: '/lien-he',
        name: 'ContactPage',
        component: ContactPage, // Component sẽ hiển thị khi truy cập đường dẫn "/"
    },
    {
        path: '/login',
        name: 'loginpages',
        component: LoginPage, // Component sẽ hiển thị khi truy cập đường dẫn "/"
    },
    {
        path: '/register',
        name: 'registerpages',
        component: RegisterPage, // Component sẽ hiển thị khi truy cập đường dẫn "/"
    },
    {
        path: '/createinfo',
        name: 'registerinfopages',
        component: RegisterInfoPage, // Component sẽ hiển thị khi truy cập đường dẫn "/"
    },
    {
        path: '/product',
        name: 'productpages',
        component: ProductPage, // Component sẽ hiển thị khi truy cập đường dẫn "/"
    },
    {
        path: '/result',
        name: 'resultpage',
        component: SeachProduct, // Component sẽ hiển thị khi truy cập đường dẫn "/"
    },
    {
        path: '/product/:id',
        name: 'productdetail',
        component: ProductDetail, // Component sẽ hiển thị khi truy cập đường dẫn "/"
    },
    {
        path: '/history',
        name: 'AccountManagement',
        component: AccountManagement, // Component sẽ hiển thị khi truy cập đường dẫn "/"
    },
    {
        path: '/cart',
        name: 'cartpages',
        component: CartPage, // Component sẽ hiển thị khi truy cập đường dẫn "/"
    },
    {
        path: '/pay',
        name: 'paypages',
        component: PaymentPages , // Component sẽ hiển thị khi truy cập đường dẫn "/"
    },
    {
        path: '/New',
        name: 'HomeNew',
        component: HomeNew, // Component sẽ hiển thị khi truy cập đường dẫn "/"
    },
    // Các route khác nếu có
];

// Tạo router mới với history mode
const router = createRouter({
    history: createWebHistory(), // Thay vì 'mode: history'
    routes,
});

export default router;
