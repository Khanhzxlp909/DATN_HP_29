<template>
  <div id="app" class="app sidebar-mini rtl">
    <main class="app-content">
      <div class="app-title">
        <ul class="app-breadcrumb breadcrumb side">
          <li class="breadcrumb-item active">
            <a href="#"><b>Danh sách tin tức</b></a>
          </li>
        </ul>
      </div>

      <div class="row">
        <div class="col-md-12">
          <div class="tile">
            <div class="tile-body">
              <form @submit.prevent="isEditing ? updateNews() : addNews()" class="row">
                <div class="form-group col-md-4" v-for="(field, key) in fields" :key="key">
                  <label :for="key">{{ field.label }}</label>
                  <input v-if="field.type === 'text'" :id="key" v-model.trim="newsForm[key]" class="form-control" @blur="validateField(key)"/>
                  <textarea v-else :id="key" v-model.trim="newsForm[key]" class="form-control" rows="4" @blur="validateField(key)"></textarea>
                  <span class="text-danger" v-if="errors[key]">{{ errors[key] }}</span>
                </div>
                <div class="form-group col-md-4">
                  <label for="newsImage">Ảnh:</label>
                  <input type="file" id="newsImage" @change="handleImageUpload" class="form-control" accept="image/*"/>
                  <img v-if="newsForm.imgPreview" :src="newsForm.imgPreview" alt="Preview" style="max-width: 100px; margin-top: 5px;"/>
                </div>
                <div class="form-group col-md-12">
                  <button type="submit" class="btn btn-primary" :disabled="hasErrors">{{ isEditing ? "Cập nhật tin tức" : "Thêm tin tức" }}</button>
                  <button type="button" class="btn btn-secondary ml-2" @click="resetForm">Hủy</button>
                </div>
              </form>

              <table class="table table-hover table-bordered mt-3">
                <thead>
                <tr>
                  <th>ID</th>
                  <th>Tiêu đề</th>
                  <th>Tóm tắt</th>
                  <th>Nội dung</th>
                  <th>Ảnh</th>
                  <th>Hành động</th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="item in newsList" :key="item.id">
                  <td>{{ item.id }}</td>
                  <td>{{ item.title }}</td>
                  <td>{{ item.summary }}</td>
                  <td>{{ item.content }}</td>
                  <td><img :src="`http://localhost:8080/upload/images/${item.img}`" alt="Ảnh tin tức" style="max-width: 100px;"/></td>
                  <td>
                    <button class="btn btn-warning btn-sm" @click="editNews(item)">Sửa</button>
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
      newsList: [],
      newsForm: {
        id: "",
        title: "",
        summary: "",
        content: "",
        img: "",
        imgPreview: ""
      },
      errors: {},
      isEditing: false,
      fields: {
        title: { label: "Tiêu đề:", type: "text" },
        summary: { label: "Tóm tắt:", type: "textarea" },
        content: { label: "Nội dung:", type: "textarea" }
      }
    };
  },
  computed: {
    hasErrors() {
      return Object.values(this.errors).some(error => error);
    }
  },
  mounted() {
    this.fetchNews();
  },
  methods: {
    validateField(field) {
      if (!this.newsForm[field] || !this.newsForm[field].trim()) {
        this.errors[field] = "Trường này không được để trống.";
      } else {
        this.errors[field] = "";
      }
    },
    async fetchNews() {
      try {
        const response = await axios.get("http://localhost:8080/admin/news/findall", { headers: { Authorization: `Bearer ${this.token}` } });
        this.newsList = response.data;
      } catch (error) {
        console.error("Lỗi khi tải tin tức:", error);
      }
    },
    async handleImageUpload(event) {
      const file = event.target.files[0];
      if (!file) return;
      const formData = new FormData();
      formData.append("file", file);
      try {
        const response = await axios.post("http://localhost:8080/admin/variation/images/upload", formData, { headers: { "Content-Type": "multipart/form-data" } });
        this.newsForm.img = response.data.urls[0];
        this.newsForm.imgPreview = `http://localhost:8080/upload/images/${this.newsForm.img}`;
      } catch (error) {
        console.error("Lỗi khi upload ảnh:", error);
        alert("Lỗi khi tải ảnh lên!");
      }
    },
    async addNews() {
      if (this.hasErrors) return;
      try {
        await axios.post("http://localhost:8080/admin/news/save", this.newsForm, { headers: { Authorization: `Bearer ${this.token}` } });
        alert("Thêm tin tức thành công!");
        this.fetchNews();
        this.resetForm();
      } catch (error) {
        console.error("Lỗi khi thêm tin tức:", error);
      }
    },
    editNews(news) {
      this.newsForm = { ...news, imgPreview: news.img ? `http://localhost:8080/upload/images/${news.img}` : "" };
      this.isEditing = true;
    },
    async updateNews() {
      if (this.hasErrors) return;
      try {
        await axios.post("http://localhost:8080/admin/news/save", this.newsForm, { headers: { Authorization: `Bearer ${this.token}` } });
        alert("Cập nhật tin tức thành công!");
        this.fetchNews();
        this.resetForm();
      } catch (error) {
        console.error("Lỗi khi cập nhật tin tức:", error);
      }
    },
    confirmDelete(news) {
      if (confirm(`Bạn có chắc chắn muốn xóa tin tức "${news.title}"?`)) {
        this.deleteNews(news.id);
      }
    },
    async deleteNews(id) {
      try {
        await axios.delete(`http://localhost:8080/admin/news/delete/${id}`, { headers: { Authorization: `Bearer ${this.token}` } });
        alert("Xóa tin tức thành công!");
        this.fetchNews();
      } catch (error) {
        console.error("Lỗi khi xóa tin tức:", error);
      }
    },
    resetForm() {
      this.newsForm = {id: "", title: "", summary: "", content: "", img: "", imgPreview: ""};
      this.errors = {};
      this.isEditing = false;
    }
  }
};
</script>
