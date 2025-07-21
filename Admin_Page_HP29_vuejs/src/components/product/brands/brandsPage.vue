<template>
  <div id="app" class="app sidebar-mini rtl">
    <header class="app-header">
      <a class="app-sidebar__toggle" href="#" data-toggle="sidebar" aria-label="Hide Sidebar"></a>
      <ul class="app-nav">
        <li>
          <a class="app-nav__item" href="/brands">
            <i class="bx bx-log-out bx-rotate-180"></i>
          </a>
        </li>
      </ul>
    </header>

    <main class="app-content">
      <div class="app-title">
        <ul class="app-breadcrumb breadcrumb side">
          <li class="breadcrumb-item active">
            <a href="#"><b>Danh sách thương hiệu</b></a>
          </li>
        </ul>
        <div id="clock">{{ currentTime }}</div>
      </div>

      <div class="row">
        <div class="col-md-12">
          <div class="tile">
            <div class="tile-body">
              <!-- Form Thêm/Sửa Thương Hiệu -->
              <form @submit.prevent="isEditing ? updateBrand() : addBrand()" class="row">
                <div class="form-group col-md-4">
                  <label for="brandName">Tên thương hiệu:</label>
                  <input type="text" id="brandName" v-model="brand.name" class="form-control"   />
                  <span class="text-danger" v-if="errors.name">{{ errors.name }}</span>
                </div>

                <div class="form-group col-md-4">
                  <label for="brandNote">Ghi chú:</label>
                  <input type="text" id="brandNote" v-model="brand.note" class="form-control"   />
                  <span class="text-danger" v-if="errors.note">{{ errors.note }}</span>
                </div>

                <div class="form-group col-md-3">
                  <label for="categoryStatus">Tình trạng:</label>
                  <select id="categoryStatus" v-model="brand.status" class="form-control">
                    <option :value="true">Đang hoạt động</option>
                    <option :value="false">Không hoạt động</option>
                  </select>
                </div>
                <div class="form-group col-md-3">
                  <button type="submit" class="btn btn-primary">
                    {{ isEditing ? "Cập nhật thương hiệu" : "Thêm thương hiệu" }}
                  </button>
                  <button type="button" class="btn btn-secondary ml-2" @click="resetForm">Hủy</button>
                </div>

              </form>

              <!-- Danh Sách Thương Hiệu -->
              <table class="table table-hover table-bordered mt-3">
                <thead>
                <tr>
                  <th>ID</th>
                  <th>Tên thương hiệu</th>
                  <th>Ghi chú</th>
                  <th>Trạng thái</th>
                  <th>Hành động</th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="item in brands" :key="item.id">
                  <td>{{ item.id }}</td>
                  <td>{{ item.name }}</td>
                  <td>{{ item.note }}</td>
                  <td>
                      <span :class="{'badge bg-success': item.status, 'badge bg-danger': !item.status}">
                        {{ item.status ? "Đang hoạt động" : "Không hoạt động" }}
                      </span>
                  </td>
                  <td>
                    <button class="btn btn-warning btn-sm" @click="editBrand(item)">Sửa</button>
                    <button class="btn btn-danger btn-sm" @click="confirmDelete(item)">Xóa</button>
                  </td>
                </tr>
                </tbody>
              </table>

              <!-- Phân Trang -->
              <div class="pagination">
                <button :disabled="currentPage === 0" @click="changePage(currentPage - 1)">Previous</button>
                <span>Trang {{ currentPage + 1 }} / {{ totalPages }}</span>
                <button :disabled="currentPage >= totalPages - 1" @click="changePage(currentPage + 1)">Next</button>
              </div>
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
      currentPage: 0,
      pageSize: 5,
      totalPages: 0,
      brands: [],
      brand: { id: "", name: "", note: "" },
      isEditing: false,
      errors: { name: "", note: "" }, // Lưu lỗi input
    };
  },

  mounted() {
    this.fetchBrands(this.currentPage, this.pageSize);
  },

  methods: {
    // Lấy danh sách thương hiệu
    async fetchBrands(page = 0, size = 5) {
      const token = Cookies.get("token");
      if (!token) {
        alert("Bạn cần đăng nhập.");
        this.$router.push("/login");
        return;
      }

      try {
        const response = await axios.get(`http://localhost:8080/admin/brands/get?page=${page}&size=${size}`, {
          headers: { Authorization: `Bearer ${token}` },
        });
        this.brands = response.data.content;
        this.totalPages = response.data.page.totalPages;
        console.log("Danh sách thương hiệu:", this.brands);
      } catch (error) {
        console.error("Lỗi khi tải danh sách thương hiệu:", error);
        alert("Không thể tải danh sách thương hiệu.");
      }
    },

    // Kiểm tra dữ liệu đầu vào
    validateForm() {
      this.errors.name = "";
      this.errors.note = "";

      if (!this.brand.name.trim()) {
        this.errors.name = "Tên thương hiệu không được để trống hoặc chỉ có khoảng trắng.";
        return false;
      }
      if (!this.brand.note.trim()) {
        this.errors.note = "Ghi chú không được để trống hoặc chỉ có khoảng trắng.";
        return false;
      }
      return true;
    },

    // Thêm thương hiệu
    async addBrand() {
      if (!this.validateForm()) return;

      const token = Cookies.get("token");
      if (!token) {
        alert("Bạn cần đăng nhập.");
        return;
      }

      try {
        await axios.post("http://localhost:8080/admin/brands/save", this.brand, {
          headers: { Authorization: `Bearer ${token}` },
        });
        alert("Thêm thương hiệu thành công!");
        this.fetchBrands(this.currentPage, this.pageSize);
        this.resetForm();
      } catch (error) {
        console.error("Lỗi khi thêm thương hiệu:", error);
      }
    },

    // Cập nhật thương hiệu
    async updateBrand() {
      if (!this.validateForm()) return;

      const token = Cookies.get("token");
      if (!token) {
        alert("Bạn cần đăng nhập.");
        return;
      }

      try {
        await axios.post(`http://localhost:8080/admin/brands/update`, this.brand, {
          headers: { Authorization: `Bearer ${token}` },
        });
        alert("Cập nhật thương hiệu thành công!");
        this.fetchBrands(this.currentPage, this.pageSize);
        this.resetForm();
      } catch (error) {
        console.error("Lỗi khi cập nhật thương hiệu:", error);
      }
    },

    // Xóa thương hiệu
    confirmDelete(brand) {
      if (confirm(`Bạn có chắc chắn muốn xóa thương hiệu "${brand.name}"?`)) {
        this.deleteBrand(brand.id);
      }
    },

    async deleteBrand(id) {
      const token = Cookies.get("token");
      try {
        await axios.get(`http://localhost:8080/admin/brands/delete/${id}`, {
          headers: { Authorization: `Bearer ${token}` },
        });
        alert("Xóa thương hiệu thành công!");
        this.fetchBrands(this.currentPage, this.pageSize);
      } catch (error) {
        console.error("Lỗi khi xóa thương hiệu:", error);
      }
    },

    // Chỉnh sửa thương hiệu
    editBrand(brand) {
      this.brand = { ...brand };
      this.isEditing = true;
    },

    // Phân trang
    changePage(page) {
      this.currentPage = page;
      this.fetchBrands(page, this.pageSize);
    },

    // Reset form
    resetForm() {
      this.brand = { id: "", name: "", note: "" };
      this.isEditing = false;
      this.errors = { name: "", note: "" };
    },
  },
};
</script>

<style>
.pagination {
  display: flex;
  justify-content: center;
  margin-top: 20px;
}
</style>
