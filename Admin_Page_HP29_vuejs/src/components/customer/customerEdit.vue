<template>
  <div id="app" class="app sidebar-mini rtl">
    <header class="app-header">
      <a
          class="app-sidebar__toggle"
          href="#"
          data-toggle="sidebar"
          aria-label="Hide Sidebar"
      ></a>
      <ul class="app-nav">
        <li>
          <a class="app-nav__item" href="/">
            <i class="bx bx-log-out bx-rotate-180"></i>
          </a>
        </li>
      </ul>
    </header>

    <main class="app-content">
      <div class="app-title">
        <ul class="app-breadcrumb breadcrumb side">
          <li class="breadcrumb-item active">
            <a href="#"><b>Chỉnh sửa thông tin khách hàng</b></a>
          </li>
        </ul>
      </div>
      <div class="row">
        <div class="col-md-12">
          <div class="tile">
            <div class="tile-body">
              <form @submit.prevent="saveCustomer">
                <div class="form-group">
                  <label for="customerId">ID khách hàng:</label>
                  <input
                      class="form-control"
                      type="text"
                      v-model="customer.id"
                      disabled
                  />
                </div>
                <div class="form-group">
                  <label for="customerName">Họ và tên:</label>
                  <input
                      class="form-control"
                      type="text"
                      v-model="customer.name"
                      required
                  />
                </div>
                <div class="form-group">
                  <label for="customerAddress">Địa chỉ:</label>
                  <input
                      class="form-control"
                      type="text"
                      v-model="customer.address"
                      required
                  />
                </div>
                <div class="form-group">
                  <label for="customerAddress">Ghi chú</label>
                  <input
                      class="form-control"
                      type="text"
                      v-model="customer.note"
                      required
                  />
                </div>
                <div class="form-group">
                  <label for="creationDate">Ngay tao:</label>
                  <input
                      class="form-control"
                      type="date"
                      v-model="customer.creation_date"
                      required
                  />
                </div>
                <div class="form-group">
                  <label for="phone">Số điện thoại:</label>
                  <input
                      class="form-control"
                      type="text"
                      v-model="customer.phone"
                      required
                  />
                </div>

                <div class="form-group">
                  <button class="btn btn-primary" type="submit">Lưu</button>
                  <router-link to="/" class="btn btn-secondary"
                  >Quay lại
                  </router-link
                  >
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
import {useRoute} from "vue-router";
import Cookies from "js-cookie";

export default {
  props: ["id"], // Nhận ID từ URL
  data() {
    return {
      customer: {
        id: "",
        name: "",
        address: "",
        note: "",
        creation_date: "",
        phone: "",
      },
    };
  },
  created() {
    this.fetchCustomer();
  },
  methods: {
    async fetchCustomer() {
      const route = useRoute();
      const id = this.$route.query.id;

      try {
        const response = await axios.get(
            `http://localhost:8080/admin/customer/edit/` + id
        );
        this.customer = response.data;
      } catch (error) {
        console.error("Có lỗi xảy ra khi tải thông tin khách hàng:", error);
        alert("Không thể tải thông tin khách hàng.");
      }
    },
    async saveCustomer() {
      const token = Cookies.get("token");
      console.log("Token: " + token);
      console.log("Customer data: " + this.customer);
      const data = {
        name: this.customer.name,
        address: this.customer.address,
        id: this.customer.id,
        status: true,
        note: this.customer.note,
        creation_date: null,
        edit_Date: null,
        phone: this.customer.phone,
      };
      console.log("data: ", data);
      try {
        const url = `http://localhost:8080/admin/customer/update`;
        await axios.post(url, data, {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        });
        alert("Cập nhật thông tin khách hàng thành công!");
        this.$router.push("/customer"); // Điều hướng về trang customerList.vue
      } catch (error) {
        console.error("Có lỗi xảy ra khi cập nhật:", error);
        alert("Cập nhật thất bại!");
      }
    }

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
</style>
