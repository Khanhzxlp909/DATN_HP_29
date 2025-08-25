<template>
  <header class="app-header">
    <!-- Sidebar toggle button -->
    <a
        class="app-sidebar__toggle"
        href="/"
        @click.prevent="toggleSidebar"
        aria-label="Hide Sidebar"
    ></a>
    <!-- Navbar Right Menu -->
    <ul class="app-nav">
      <!-- User Menu -->
      <li>
        <a class="app-nav__item" href="/posorder">
          <i class="bx bx-log-out bx-rotate-180"></i>
        </a>
      </li>
    </ul>
  </header>
  <!-- Sidebar menu -->
  <div class="app-sidebar__overlay" @click="toggleSidebar"></div>
  <aside class="app-sidebar" v-if="!isWarehouseDetailPage" v-show="isSidebarVisible">
    <div class="app-sidebar__user">
      <img
          class="app-sidebar__user-avatar"
          src="../../../public/favicon.ico"
          width="50px"
          alt="User Image"
      />
      <div>
        <p class="app-sidebar__user-name">
          <b>{{ adminName }}</b>
        </p>
        <p class="app-sidebar__user-designation">Chào mừng bạn trở lại</p>
      </div>
    </div>
    <hr/>
    <ul class="app-menu">
      <li>
        <a class="app-menu__item haha" href="/order" @click.prevent="navigateTo('order')">
          <i class="app-menu__icon bx bx-cart"></i>
          <span class="app-menu__label">POS Bán Hàng</span>
        </a>
      </li>
      <li v-if="showDashboard">
        <a class="app-menu__item" href="/">
          <i class='app-menu__icon bx bx-tachometer'></i>
          <span class="app-menu__label">Bảng điều khiển</span>
        </a>
      </li>
      <li v-if="showCustomer">
        <a class="app-menu__item" href="/customer">
          <i class="app-menu__icon bx bx-group"></i>
          <span class="app-menu__label">Quản lý khách hàng</span>
        </a>
      </li>
      <li>
        <a class="app-menu__item" href="/product">
          <i class="app-menu__icon bx bx-package"></i>
          <span class="app-menu__label">Quản lý sản phẩm</span>
        </a>
      </li>
      <li>
        <a class="app-menu__item" href="/variation">
          <i class="app-menu__icon bx bx-package"></i>
          <span class="app-menu__label">Quản lý biến thể</span>
        </a>
      </li>
      <li>
        <a class="app-menu__item" href="/posorder">
          <i class="app-menu__icon bx bx-receipt"></i>
          <span class="app-menu__label">Quản lý đơn hàng</span>
        </a>
      </li>
      <li v-if="showEmployee">
        <a class="app-menu__item" href="/employee">
          <i class="app-menu__icon bx bx-id-card"></i>
          <span class="app-menu__label">Nhân viên</span>
        </a>
      </li>
      <li>
        <a class="app-menu__item" href="/contact">
          <i class="app-menu__icon bx bx-bell"></i>
          <span class="app-menu__label">Thông báo</span>
        </a>
      </li>
      <li>
        <a class="app-menu__item" href="/login">
          <i class="app-menu__icon bx bx-log-in"></i>
          <span class="app-menu__label">Đăng nhập</span>
        </a>
      </li>
      <li>
        <a class="app-menu__item" @click.prevent="logout" href="#">
          <i class="app-menu__icon bx bx-log-out"></i>
          <span class="app-menu__label">Đăng xuất</span>
        </a>
      </li>
    </ul>

  </aside>
</template>

<script>
import Cookies from "js-cookie";

export default {
  data() {
    return {
      isSidebarVisible: true,
      admin: JSON.parse(Cookies.get("admin") || "{}"),
      roles : Cookies.get("roles"|| "[]"),
    };
  },
  computed: {
    adminName() {
      return this.admin ? this.admin.name : 'Người dùng'; // Lấy tên người dùng
    },
    isWarehouseDetailPage() {
      return this.$route.path === "/warehouse/warehouseDetails";
    },
    isOrderPage() {
      return this.$route.path === "/warehouse/warehouseDetails";
    },
    showDashboard() {
      // Hiển thị bảng điều khiển nếu là admin, ẩn nếu là USER
      return this.roles.includes('ADMIN');
    },
    showEmployee() {
      // Hiển thị nhân viên nếu là admin, ẩn nếu là USER
      return this.roles.includes('ADMIN');
    }
  },
  mounted() {
    if (!this.admin || Object.keys(this.admin).length === 0) {
      this.admin = 'Chưa đăng nhập'
      this.$router.push("/login"); // Chuyển hướng đến trang đăng nhập nếu admin null
    }
  },
  methods: {
    toggleSidebar() {
      this.isSidebarVisible = !this.isSidebarVisible;
    },
    logout() {
      // Xóa token khỏi cookies
      Cookies.remove("roles");
      Cookies.remove("token");
      Cookies.remove("authToken");
      Cookies.remove("admin");  
      location.reload();
      // Chuyển hướng đến trang đăng nhập
      this.$router.push("/login"); // Hoặc trang bạn muốn chuyển hướng đến
    },
    navigateTo(route) {
      this.isSidebarVisible = false; // Ẩn sidebar
      this.$router.push(`/${route}`); // Chuyển hướng đến trang mới
    },
  },
};
</script>

<style scoped>
/* Thêm các style tùy chỉnh của bạn ở đây */
</style>
