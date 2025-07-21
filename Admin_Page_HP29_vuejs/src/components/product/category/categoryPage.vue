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
            <a href="#"><b>Danh sách danh mục</b></a>
          </li>
        </ul>
        <div></div>
      </div>

      <div class="row">
        <div class="col-md-12">
          <div class="tile">
            <div class="tile-body">
              <!-- Form Thêm/Sửa Danh Mục -->
              <form @submit.prevent="isEditing ? updateCategory() : addCategory()" class="row">
                <div class="form-group col-md-3">
                  <label for="categoryName">Tên danh mục:</label>
                  <input
                      type="text"
                      id="categoryName"
                      v-model="categoryForm.name"
                      class="form-control"
                  />
                  <span class="text-danger" v-if="errors.name">{{ errors.name }}</span>
                </div>

                <div class="form-group col-md-3">
                  <label for="categoryStatus">Tình trạng:</label>
                  <select id="categoryStatus" v-model="categoryForm.status" class="form-control">
                    <option :value="true">Đang hoạt động</option>
                    <option :value="false">Không hoạt động</option>
                  </select>
                </div>

                <button type="submit" class="btn btn-primary">
                  {{ isEditing ? "Cập nhật danh mục" : "Thêm danh mục" }}
                </button>
                <button type="button" class="btn btn-secondary ml-2" @click="resetForm">Hủy</button>
              </form>

              <!-- Danh Sách Danh Mục -->
              <table class="table table-hover table-bordered mt-3">
                <thead>
                <tr>
                  <th>ID</th>
                  <th>Tên danh mục</th>
                  <th>Tình trạng</th>
                  <th>Chức năng</th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="item in categoryList" :key="item.id">
                  <td>{{ item.id }}</td>
                  <td>{{ item.name }}</td>
                  <td>
                      <span :class="{'badge bg-success': item.status, 'badge bg-danger': !item.status}">
                        {{ item.status ? "Đang hoạt động" : "Không hoạt động" }}
                      </span>
                  </td>
                  <td>
                    <button class="btn btn-warning btn-sm" @click="editCategory(item)">Sửa</button>
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
      currentTime: "",
      currentPage: 0,
      pageSize: 5,
      totalPages: 0,
      categoryList: [],
      categoryForm: {
        id: "",
        name: "",
        status: true,
      },
      isEditing: false,
      errors: {
        name: "",
      },
    };
  },

  mounted() {
    this.fetchCategories(this.currentPage, this.pageSize);
  },

  methods: {
    // Lấy danh sách danh mục
    async fetchCategories(page = 0, size = 5) {
      const token = Cookies.get("authToken");
      if (!token) {
        alert("Bạn cần đăng nhập.");
        this.$router.push("/login");
        return;
      }

      try {
        const response = await axios.get(`http://localhost:8080/admin/category/get?page=${page}&size=${size}`, {
          headers: {Authorization: `Bearer ${token}`},
        });
        this.categoryList = response.data.content;
        this.totalPages = response.data.page.totalPages;
      } catch (error) {
        console.error("Lỗi khi tải danh mục:", error);
        alert("Không thể tải danh mục.");
      }
    },

    validateForm() {
      this.errors.name = "";

      if (!this.categoryForm.name.trim()) {
        this.errors.name = "Tên danh mục không được để trống hoặc chỉ chứa khoảng trắng.";
        return false;
      }
      return true;
    },

    // Thêm danh mục
    async addCategory() {
      if (!this.validateForm()) return; // Kiểm tra dữ liệu trước khi gửi
      const token = Cookies.get("authToken");
      try {
        await axios.post("http://localhost:8080/admin/category/save",
            this.categoryForm, // Dữ liệu gửi lên
            {
              headers: {
                Authorization: `Bearer ${token}`,
                "Content-Type": "application/json",
              },
            }
        );
        alert("Thêm danh mục thành công!");
        this.fetchCategories();
        this.resetForm();

      } catch (error) {
        console.error("Lỗi khi thêm danh mục:", error);
      }
    },

// Cập nhật danh mục
    async updateCategory() {
      if (!this.validateForm()) return; // Kiểm tra dữ liệu trước khi gửi
      const token = Cookies.get("authToken");
      try {
        await axios.post("http://localhost:8080/admin/category/update",
            this.categoryForm, // Dữ liệu gửi lên
            {
              headers: {
                Authorization: `Bearer ${token}`,
                "Content-Type": "application/json",
              },
            }
        );
        alert("Cập nhật danh mục thành công!");
        this.fetchCategories();
        this.resetForm();
      } catch (error) {
        console.error("Lỗi khi cập nhật danh mục:", error);
      }
    },


    // Sửa danh mục
    editCategory(category) {
      this.categoryForm = {...category};
      this.isEditing = true;
    },

    // Xóa danh mục
    confirmDelete(category) {
      if (confirm(`Bạn có chắc chắn muốn xóa danh mục "${category.name}"?`)) {
        this.deleteCategory(category.id);
      }
    },

    async deleteCategory(id) {
      const token = Cookies.get("authToken");
      try {
        await axios.get(`http://localhost:8080/admin/category/delete/${id}`, {
          headers: {Authorization: `Bearer ${token}`},
        });
        alert("Xóa danh mục thành công!");
        this.fetchCategories(this.currentPage, this.pageSize);
      } catch (error) {
        console.error("Lỗi khi xóa danh mục:", error);
      }
    },

    // Phân trang
    changePage(page) {
      this.currentPage = page;
      this.fetchCategories(page, this.pageSize);
    },

    // Reset form
    resetForm() {
      this.categoryForm = {id: "", name: "", status: true};
      this.isEditing = false;
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

.pagination button {
  margin: 0 5px;
  padding: 8px 16px;
  border: none;
  background-color: #007bff;
  color: white;
  border-radius: 4px;
  cursor: pointer;
}

.pagination button:disabled {
  background-color: #ccc;
  cursor: not-allowed;
}
</style>
