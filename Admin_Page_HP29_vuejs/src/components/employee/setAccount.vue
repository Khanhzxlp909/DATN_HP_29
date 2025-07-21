<template>
  <div id="app" class="app sidebar-mini rtl">
    <main class="app-content">
      <div class="app-title">
        <ul class="app-breadcrumb breadcrumb side">
          <li class="breadcrumb-item active">
            <a href="#"><b>Tạo mới khách hàng</b></a>
          </li>
        </ul>
      </div>
      <div class="row">
        <div class="col-md-12">
          <div class="tile">
            <h3 class="tile-title">Thông tin khách hàng</h3>
            <div class="tile-body">
              <form @submit.prevent="submitForm">
                <div class="row">
                  <div class="form-group col-md-4">
                    <label for="username">Tài khoản:</label>
                    <input v-model.trim="account.username" class="form-control" type="text" id="username"/>
                    <span class="error-message" v-if="errors.username">{{ errors.username }}</span>
                  </div>

                  <div class="form-group col-md-4">
                    <label for="email">Email:</label>
                    <input v-model.trim="account.email" class="form-control" type="email" id="email"/>
                    <span class="error-message" v-if="errors.email">{{ errors.email }}</span>
                  </div>

                  <div class="form-group col-md-4">
                    <label for="password">Mật khẩu:</label>
                    <input :type="showPassword ? 'text' : 'password'" v-model.trim="account.password" class="form-control" id="password"/>
                    <span class="error-message" v-if="errors.password">{{ errors.password }}</span>
                  </div>

                  <div class="form-group col-md-4">
                    <label for="confirmPassword">Nhập lại mật khẩu:</label>
                    <input :type="showPassword ? 'text' : 'password'" v-model.trim="confirmPassword" class="form-control" id="confirmPassword"/>
                    <span class="error-message" v-if="errors.confirmPassword">{{ errors.confirmPassword }}</span>
                    <input type="checkbox" v-model="showPassword"/> Hiển thị mật khẩu
                  </div>
                </div>
                <div class="form-group">
                  <button class="btn btn-primary" type="submit">Lưu lại</button>
                  <router-link to="/" class="btn btn-secondary">Quay lại</router-link>
                </div>
              </form>
            </div>
          </div>
        </div>
      </div>
    </main>
  </div>
</template>

<script>
import axios from "axios";
import Cookies from "js-cookie";

export default {
  data() {
    return {
      account: { id: null, username: "", email: "", password: "" },
      confirmPassword: "",
      showPassword: false,
      errors: {},
    };
  },
  mounted() {
    this.account.id = this.$route.params.id;
  },
  methods: {
    validateForm() {
      this.errors = {};
      let valid = true;

      if (!this.account.username.trim()) {
        this.errors.username = "Tài khoản không được để trống.";
        valid = false;
      }

      if (!this.account.email.trim()) {
        this.errors.email = "Email không được để trống.";
        valid = false;
      } else if (!/^[\w-.]+@([\w-]+\.)+[\w-]{2,4}$/.test(this.account.email)) {
        this.errors.email = "Email không đúng định dạng.";
        valid = false;
      }

      if (!this.account.password.trim()) {
        this.errors.password = "Mật khẩu không được để trống.";
        valid = false;
      } else if (this.account.password.length < 6 || !/[0-9]/.test(this.account.password) || !/[a-zA-Z]/.test(this.account.password)) {
        this.errors.password = "Mật khẩu phải có ít nhất 6 ký tự, bao gồm cả số và chữ.";
        valid = false;
      }

      if (this.account.password !== this.confirmPassword) {
        this.errors.confirmPassword = "Mật khẩu nhập lại không khớp.";
        valid = false;
      }

      return valid;
    },
    async submitForm() {
      if (!this.validateForm()) return;

      const data = {
        usersid: this.account.id,
        username: this.account.username,
        email: this.account.email,
        password: this.account.password,
        role: [2],
      };

      const token = Cookies.get("token");
      try {
        const url = `http://localhost:8080/admin/signup`;
        await axios.post(url, data, {
          headers: { Authorization: `Bearer ${token}` },
        });
        alert("Cập nhật thông tin tài khoản thành công!");
        this.$router.push("/customer");
      } catch (error) {
        console.error("Có lỗi xảy ra khi cập nhật:", error.response?.data || error);
        alert("Cập nhật thất bại!");
      }
    },
  },
};
</script>

<style scoped>
.form-group {
  margin-bottom: 20px;
}

.btn-primary {
  background-color: #007bff;
  border: none;
  padding: 10px 20px;
  border-radius: 5px;
  cursor: pointer;
}

.btn-primary:hover {
  background-color: #0056b3;
}

.btn-secondary {
  background-color: #6c757d;
  border: none;
  padding: 10px 20px;
  border-radius: 5px;
  cursor: pointer;
}

.btn-secondary:hover {
  background-color: #5a6268;
}

.error-message {
  color: red;
  font-size: 14px;
}
</style>
