<template>
  <div class="app sidebar-mini rtl">
    <!-- Navbar -->
    <header class="app-header">
      <ul class="app-nav">
        <li>
          <a class="app-nav__item" href="/order">
            <i class='bx bx-log-out bx-rotate-180'></i>
          </a>
        </li>
      </ul>
    </header>

    <main class="app-content">
      <div class="app-title">
        <ul class="app-breadcrumb breadcrumb">
          <li class="breadcrumb-item"><a href="#"><b>Bảng điều khiển</b></a></li>
        </ul>
        <div id="clock">{{ currentTime }}</div>
      </div>
      <div class="row">
        <div class="col-md-12">
          <div class="tile">
            <h3 class="tile-title">Danh sách nhân viên</h3>
            <div class="tile-body">
              <div class="row">
                <div class="col-md-6">
                  <div class="widget-small primary coloured-icon">
                    <i class='icon bx bxs-user-account fa-3x'></i>
                    <div class="info">
                      <h4>Tổng nhân viên</h4>
                      <p><b>{{ totalEmployees }} nhân viên</b></p>
                      <p class="info-tong">Tổng số nhân viên được quản lý.</p>
                    </div>
                  </div>
                </div>
                <div class="col-md-6">
                  <div class="widget-small info coloured-icon">
                    <i class='icon bx bxs-data fa-3x'></i>
                    <div class="info">
                      <h4>Tình trạng</h4>
                      <p><b>{{ activeEmployees }} nhân viên đang hoạt động</b></p>
                      <p class="info-tong">Số nhân viên đang hoạt động.</p>
                    </div>
                  </div>
                </div>
              </div>
              <table class="table table-hover table-bordered">
                <thead>
                <tr>
                  <th>ID</th>
                  <th>Tên nhân viên</th>
                  <th>Số điện thoại</th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="employee in employees" :key="employee.id">
                  <td>{{ employee.id }}</td>
                  <td>{{ employee.name }}</td>
                  <td><span class="tag tag-success">{{ employee.phone }}</span></td>
                </tr>
                </tbody>
              </table>
            </div>
          </div>
        </div>
      </div>
      <div class="text-center" style="font-size: 13px">
        <p><b>Copyright © 2023. All rights reserved.</b></p>
      </div>
    </main>
  </div>
</template>

<script>
import axios from 'axios';
import Cookies from 'js-cookie';
export default {
  data() {
    return {
      employees: [],
      totalEmployees: 0,
      activeEmployees: 0,
      currentTime: new Date().toLocaleString()
    };
  },
  mounted() {
    this.fetchEmployees(); // Gọi hàm để lấy danh sách nhân viên khi component được mount
    this.updateClock(); // Cập nhật đồng hồ
  },
  methods: {
    async fetchEmployees() {
      try {
        const response = await axios.get('http://localhost:8080/admin/employee/findall'); // Đường dẫn API để lấy danh sách nhân viên
        this.employees = response.data; // Lưu danh sách nhân viên
        this.totalEmployees = this.employees.length; // Cập nhật tổng số nhân viên
        this.activeEmployees = this.employees.filter(emp => emp.status).length; // Cập nhật số nhân viên đang hoạt động
      } catch (error) {
        console.error("Có lỗi xảy ra khi lấy danh sách nhân viên:", error);
      }
    },
    updateClock() {
      setInterval(() => {
        this.currentTime = new Date().toLocaleString(); // Cập nhật thời gian hiện tại
      }, 1000);
    }
  }
};
</script>

<style scoped>
</style>