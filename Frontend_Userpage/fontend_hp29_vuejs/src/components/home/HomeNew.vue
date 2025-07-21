<template>
  <div class="container">
    <div class="row mb-20" id="news">
      <div v-for=" n in newsItems" :key="n.id" class="col-lg-4 col-md-6 col-sm-12 mb-20">
        <a href="#" class="product__new-item">
          <div class="card" style="width: 100%">
            <img class="card-img-top" :src="`http://localhost:8080/upload/images/${n.img}`" alt="Card image cap" />
            <div class="card-body">
              <h5 class="card-title custom__name-product title-news">{{ n.title }}</h5>
              <p class="card-text custom__name-product" style="font-weight: 400;">{{ n.summary }}</p>
            </div>
          </div>
        </a>
      </div>
    </div>
    <div class="loadmore" v-if="page < maxPage">
      <a class="loadmore-btn" @click="loadMore" style="cursor: pointer;">Tải thêm</a>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      page: 1,
      newsItems: [],
      totalPages: 0, // Biến để theo dõi tổng số trang
      isLoading: false, // Biến để theo dõi trạng thái tải
      errorMessage: '' // Biến để lưu thông báo lỗi
    };
  },
  methods: {
    async fetchNews() {
      this.isLoading = true; // Bắt đầu tải
      this.errorMessage = ''; // Reset thông báo lỗi
      try {
        const response = await axios.get(`http://localhost:8080/admin/news/findall`);
        this.newsItems = response.data; // Giả sử API trả về một đối tượng với thuộc tính 'content'
        this.totalPages = response.data.totalPages; // Giả sử API trả về tổng số trang
        console.log("Data: ", this.newsItems);
      } catch (error) {
        console.error('Error fetching news:', error);
        this.errorMessage = 'Không thể tải tin tức. Vui lòng thử lại sau.'; // Cập nhật thông báo lỗi
      } finally {
        this.isLoading = false; // Kết thúc tải
      }
    },
    loadMore() {
      if (this.page < this.totalPages) { // Kiểm tra nếu còn trang để tải
        this.page++;
        this.fetchNews();
      } else {
        alert("Không còn tin tức để tải thêm.");
      }
    }
  },
  mounted() {
    this.fetchNews();
  }
};
</script>

<style scoped>
/* Thêm CSS của bạn ở đây */
</style>