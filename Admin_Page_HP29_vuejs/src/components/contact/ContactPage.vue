<template>
  <div id="app" class="app sidebar-mini rtl">
    <header class="app-header">
      <a class="app-sidebar__toggle" href="#" data-toggle="sidebar" aria-label="Hide Sidebar"></a>
      <ul class="app-nav">
        <li>
          <a class="app-nav__item" href="/order">
            <i class="bx bx-log-out bx-rotate-180"></i>
          </a>
        </li>
      </ul>
    </header>

    <main class="app-content">
      <div class="app-title">
        <ul class="app-breadcrumb breadcrumb side">
          <li class="breadcrumb-item active">
            <a href="#"><b>Thông báo về yêu cầu của khách hàng</b></a>
          </li>
        </ul>
        <div id="clock">{{ currentTime }}</div>
      </div>
      <div class="row">
        <div class="col-md-12">
          <div class="tile">
            <div class="tile-body">
              <table class="table table-hover table-bordered" id="sampleTable">
                <thead>
                <tr>
                  <th>ID</th>
                  <th>Tên khách hàng</th>
                  <th>Email</th>
                  <th>Số điện thoại</th>
                  <th>Nội dung</th>
                  <th>Hành động</th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="c in Contact" :key="c.id">
                  <td>{{ c.id }}</td>
                  <td>{{ c.fullName }}</td>
                  <td>{{ c.email }}</td>
                  <td>{{ c.phone }}</td>
                  <td>{{ c.content }}</td>
                  <td>
                    <button class="btn btn-warning btn-sm" @click="deleteContact(c.id)">Xóa</button>
                  </td>
                </tr>
                </tbody>
              </table>
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
      currentTime: "",
      currentPage: 0,
      pageSize: 10,
      totalPages: 0,
      Contact: [],
    };
  },
  created() {
    this.fetchContact();
  },
  methods: {
    deleteContact(id) {
      const token = Cookies.get("authToken");
      if (confirm("Bạn có chắc chắn muốn xóa không?")) {
        axios
            .get(`http://localhost:8080/MiniatureCrafts/contact/delete/${id}`, {
              headers: {
                Authorization: `Bearer ${token}`,
              },
            })
            .then((response) => {
              this.fetchContact();
            })
            .catch((error) => {
              console.error("Có lỗi xảy ra khi xóa sản phẩm:", error);
            });
      }
    },
    fetchContact() {
      const token = Cookies.get("authToken");
      axios
          .get(`http://localhost:8080/MiniatureCrafts/contact/findall`, {
            headers: {
              Authorization: `Bearer ${token}`,
            },
          })
          .then((response) => {
            this.Contact = response.data;
            console.log(this.Contact)
          })
          .catch((error) => {
            console.error("Có lỗi xảy ra khi lấy dữ liệu sản phẩm:", error);
          });
    },
  },
};
</script>