<template>
  <div id="app" class="app sidebar-mini rtl">
    <main class="app-content">
      <div class="app-title">
        <ul class="app-breadcrumb breadcrumb side">
          <li class="breadcrumb-item active">
            <a href="#"><b>Giới thiệu</b></a>
          </li>
        </ul>
      </div>

      <div class="row">
        <div class="col-md-12">
          <div class="tile">
            <div class="tile-body">
              <!-- Form Thêm/Sửa Giới thiệu -->
              <form @submit.prevent="isEditing ? updateIntro() : addIntro()" class="row">
                <div class="form-group col-md-4">
                  <label for="newsTitle">Tiêu đề:</label>
                  <input
                      type="text"
                      id="newsTitle"
                      v-model="introForm.header"
                      class="form-control"
                      required
                      @input="validateForm"
                  />
                  <span class="text-danger" v-if="errors.header">{{ errors.header }}</span>
                </div>

                <div class="form-group col-md-4">
                  <label for="newsTitle">Tiêu đề nhỏ:</label>
                  <textarea
                      id="newsTitle"
                      v-model="introForm.title"
                      class="form-control"
                      rows="4"
                      required
                      @input="validateForm"
                  ></textarea>
                  <span class="text-danger" v-if="errors.title">{{ errors.title }}</span>
                </div>

                <div class="form-group col-md-4">
                  <label for="newsContent">Nội dung:</label>
                  <textarea
                      id="newsContent"
                      v-model="introForm.content"
                      class="form-control"
                      rows="4"
                      required
                      @input="validateForm"
                  ></textarea>
                  <span class="text-danger" v-if="errors.content">{{ errors.content }}</span>
                </div>

                <button type="submit" class="btn btn-primary" :disabled="!isValid">
                  {{ isEditing ? "Cập nhật giới thiệu" : "Thêm giới thiệu" }}
                </button>
                <button type="button" class="btn btn-secondary ml-2" @click="resetForm">Hủy</button>
              </form>

              <!-- Danh Sách Giới thiệu -->
              <table class="table table-hover table-bordered mt-3">
                <thead>
                <tr>
                  <th>ID</th>
                  <th>Tiêu đề</th>
                  <th>Tiêu đề nhỏ</th>
                  <th>Nội dung</th>
                  <th>Hành động</th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="item in introList" :key="item.id">
                  <td>{{ item.id }}</td>
                  <td>{{ item.header }}</td>
                  <td>{{ item.title }}</td>
                  <td>{{ item.content }}</td>
                  <td>
                    <button class="btn btn-warning btn-sm" @click="editIntro(item)">Sửa</button>
                    <button class="btn btn-danger btn-sm" @click="confirmDelete(item)">Xóa</button>
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
      token: Cookies.get("authToken"),
      introList: [],
      introForm: {
        id: "",
        header: "",
        title: "",
        content: "",
      },
      isEditing: false,
      errors: {
        header: null,
        title: null,
        content: null
      }
    };
  },
  computed: {
    isValid() {
      return !this.errors.header && !this.errors.title && !this.errors.content;
    }
  },
  mounted() {
    this.fetchIntro();
  },
  methods: {
    async fetchIntro() {
      try {
        const response = await axios.get("http://localhost:8080/admin/introduces/findall", {
          headers: {Authorization: `Bearer ${this.token}`},
        });
        this.introList = response.data;
      } catch (error) {
        console.error("Lỗi khi tải giới thiệu:", error);
      }
    },

    async addIntro() {
      if (!this.isValid) return;

      try {
        await axios.post("http://localhost:8080/admin/introduces/save", this.introForm, {
          headers: {Authorization: `Bearer ${this.token}`},
        });
        alert("Thêm giới thiệu thành công!");
        this.fetchIntro();
        this.resetForm();
      } catch (error) {
        console.error("Lỗi khi thêm giới thiệu:", error);
      }
    },

    editIntro(news) {
      this.introForm = {...news};
      this.isEditing = true;
      this.validateForm(); // Kiểm tra lỗi khi load dữ liệu cũ
    },

    async updateIntro() {
      if (!this.isValid) return;

      try {
        await axios.post("http://localhost:8080/admin/introduces/save", this.introForm, {
          headers: {Authorization: `Bearer ${this.token}`},
        });
        alert("Cập nhật giới thiệu thành công!");
        this.fetchIntro();
        this.resetForm();
      } catch (error) {
        console.error("Lỗi khi cập nhật giới thiệu:", error);
      }
    },

    confirmDelete(news) {
      if (confirm(`Bạn có chắc chắn muốn xóa giới thiệu "${news.title}"?`)) {
        this.deleteNews(news.id);
      }
    },

    async deleteNews(id) {
      try {
        await axios.delete(`http://localhost:8080/admin/introduces/delete/${id}`, {
          headers: {Authorization: `Bearer ${this.token}`},
        });
        alert("Xóa giới thiệu thành công!");
        this.fetchIntro();
      } catch (error) {
        console.error("Lỗi khi xóa giới thiệu:", error);
      }
    },

    resetForm() {
      this.introForm = {id: "", header: "", title: "", content: ""};
      this.isEditing = false;
      this.errors = {header: null, title: null, content: null};
    },

    validateForm() {
      const isEmptyOrSpaces = (str) => !str || str.trim().length === 0;

      this.errors.header = isEmptyOrSpaces(this.introForm.header) ? "Không được để trống tiêu đề." : null;
      this.errors.title = isEmptyOrSpaces(this.introForm.title) ? "Không được để trống tiêu đề nhỏ." : null;
      this.errors.content = isEmptyOrSpaces(this.introForm.content) ? "Không được để trống nội dung." : null;
    }
  }
};
</script>

<style>
.text-danger {
  color: red;
  font-size: 14px;
}
</style>
