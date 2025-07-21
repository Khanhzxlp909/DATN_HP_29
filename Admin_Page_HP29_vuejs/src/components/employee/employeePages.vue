<template>
  <div id="app" class="app sidebar-mini rtl">
    <main class="app-content">
      <div class="app-title">
        <ul class="app-breadcrumb breadcrumb side">
          <li class="breadcrumb-item active">
            <a href="#"><b>Tạo mới nhân viên</b></a>
          </li>
        </ul>
      </div>

      <div class="row">
        <div class="col-md-12">
          <div class="tile">
            <h3 class="tile-title">Thông tin nhân viên</h3>
            <div class="tile-body">
              <form @submit.prevent="submitForm">
                <div class="row">
                  <div class="form-group col-md-4">
                    <label for="employeeName">Họ và tên:</label>
                    <input v-model="employee.name" class="form-control" type="text" id="employeeName" @input="validateName" required />
                    <small v-if="errors.name" class="text-danger">{{ errors.name }}</small>
                  </div>

                  <div class="form-group col-md-4">
                    <label for="employeePhone">Số điện thoại:</label>
                    <input v-model="employee.phone" class="form-control" type="text" id="employeePhone" @input="validatePhone" required />
                    <small v-if="errors.phone" class="text-danger">{{ errors.phone }}</small>
                  </div>

                  <div class="form-group col-md-4">
                    <label for="employeeNote">Ghi chú:</label>
                    <input v-model="employee.note" class="form-control" type="text" id="employeeNote" @input="validateNote" />
                    <small v-if="errors.note" class="text-danger">{{ errors.note }}</small>
                  </div>

                  <div class="form-group col-md-4">
                    <label for="employeeStatus">Trạng thái:</label>
                    <select v-model="employee.status" class="form-control" id="employeeStatus" required>
                      <option value="">Chọn trạng thái</option>
                      <option :value="true">Hoạt động</option>
                      <option :value="false">Không hoạt động</option>
                    </select>
                  </div>
                </div>

                <div class="form-group">
                  <button class="btn btn-primary" type="submit" :disabled="hasErrors">Lưu lại</button>
                  <router-link to="/" class="btn btn-secondary">Quay lại</router-link>
                </div>
              </form>
            </div>
          </div>
        </div>

        <div class="col-md-12 mt-4">
          <div class="tile">
            <h3 class="tile-title">Danh sách nhân viên</h3>
            <div class="tile-body">
              <table class="table table-hover">
                <thead>
                <tr>
                  <th>ID</th>
                  <th>Họ và tên</th>
                  <th>Số điện thoại</th>
                  <th>Hành động</th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="emp in employees" :key="emp.id">
                  <td>{{ emp.id }}</td>
                  <td>{{ emp.name }}</td>
                  <td>{{ emp.phone }}</td>
                  <td>
                    <button @click="setAccount(emp.id)" class="btn btn-sm btn-success">Cấp tài khoản</button>
                  </td>
                </tr>
                </tbody>
              </table>
              <p v-if="employees.length === 0" class="text-center">Không có nhân viên nào.</p>
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
      employee: {
        id: null,
        name: "",
        phone: "",
        note: "",
        status: "",
      },
      employees: [],
      errors: {
        name: "",
        phone: "",
        note: "",
      },
    };
  },
  computed: {
    hasErrors() {
      return Object.values(this.errors).some((error) => error !== "");
    },
  },
  methods: {
    async submitForm() {
      if (this.hasErrors) {
        alert("Vui lòng sửa các lỗi trước khi lưu!");
        return;
      }

      try {
        const token = Cookies.get("token");
        if (!token) {
          alert("Chưa đăng nhập!");
          return;
        }

        const response = await axios.post(
            "http://localhost:8080/admin/employee/save",
            this.employee,
            { headers: { Authorization: `Bearer ${token}` } }
        );

        alert("Tạo nhân viên thành công!");
        this.fetchEmployees();
        this.resetForm();
      } catch (error) {
        console.error("Lỗi khi lưu nhân viên:", error);
        alert("Có lỗi xảy ra, vui lòng thử lại!");
      }
    },

    validateName() {
      const name = this.employee.name.trim();
      if (!name) {
        this.errors.name = "Họ và tên không được để trống.";
      } else if (name.split(" ").length < 2) {
        this.errors.name = "Họ và tên phải có ít nhất 2 từ.";
      } else {
        this.errors.name = "";
      }
    },

    validatePhone() {
      const phone = this.employee.phone.trim();
      if (!phone) {
        this.errors.phone = "Số điện thoại không được để trống.";
      } else if (!/^\d{10}$/.test(phone)) {
        this.errors.phone = "Số điện thoại phải có 10 chữ số.";
      } else {
        this.errors.phone = "";
      }
    },

    validateNote() {
      const note = this.employee.note.trim();
      if (note.length > 200) {
        this.errors.note = "Ghi chú không được quá 200 ký tự.";
      } else {
        this.errors.note = "";
      }
    },

    async fetchEmployees() {
      try {
        const token = Cookies.get("token");
        if (!token) return;

        const response = await axios.get("http://localhost:8080/admin/employee/findall", {
          headers: { Authorization: `Bearer ${token}` },
        });

        this.employees = response.data;
      } catch (error) {
        console.error("Lỗi khi tải danh sách nhân viên:", error);
      }
    },

    setAccount(id) {
      this.$router.push({ name: "EmployeeAccount", params: { id } });
    },

    resetForm() {
      this.employee = { id: null, name: "", phone: "", note: "", status: "" };
      this.errors = { name: "", phone: "", note: "" };
    },
  },
  mounted() {
    this.fetchEmployees();
  },
};
</script>

<style scoped>
.text-danger {
  font-size: 12px;
  color: red;
}

.btn-primary {
  background-color: #007bff;
  border: none;
  padding: 10px 15px;
  border-radius: 5px;
  color: #fff;
  cursor: pointer;
  font-size: 14px;
}

.btn-primary:disabled {
  background-color: #ccc;
  cursor: not-allowed;
}

.btn-success {
  background-color: #28a745;
  border: none;
  padding: 8px 12px;
  color: #fff;
  cursor: pointer;
  font-size: 14px;
}

.btn-success:hover {
  background-color: #218838;
}
</style>
