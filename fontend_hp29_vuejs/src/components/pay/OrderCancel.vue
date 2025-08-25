<script setup>
import {ref, onMounted} from "vue";
import axios from "axios";
import Cookies from "js-cookie";
const orderId = ref(null);
const token = Cookies.get("token");
const message = ref("Đơn hàng đã bị hủy");
const tips = ref("Nếu đây là sự nhầm lẫn, vui lòng đặt hàng lại hoặc liên hệ hỗ trợ.");

onMounted(async () => {
  // Lấy orderId từ URL: /payment-cancel/59
  const pathParts = window.location.pathname.split("/");
  orderId.value = pathParts[pathParts.length - 1];

  if (orderId.value) {
    try {
      await axios.get(`http://localhost:8080/MiniatureCrafts/cancelOrder/${orderId.value}`,{
        headers: {
          Authorization: `Bearer ${token}`
        }
      });
      console.log("Đã hủy đơn hàng:", orderId.value);
    } catch (error) {
      console.error("Lỗi khi hủy đơn hàng:", error);
    }
  }
});
</script>

<template>
  <div class="cancel-container">
    <div class="cancel-card">
      <img src="../../assets/img/logo/img.png" alt="cancel" class="icon" />
      <h1>{{ message }}</h1>
      <p>{{ tips }}</p>
      <a href="/cart" class="btn">Quay lại giỏ hàng</a>
      <a href="/" class="btn-outline">Tiếp tục mua sắm</a>
    </div>
  </div>
</template>

<style>
.cancel-container {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 90vh;
  background: #ffffff;
}

.cancel-card {
  background: #fff;
  padding: 40px;
  border-radius: 16px;
  text-align: center;
  max-width: 500px;
  box-shadow: 0 6px 20px rgba(0,0,0,0.1);
}

.icon {
  width: 100px;
  margin-bottom: 20px;
}

h1 {
  color: #dc3545;
  font-size: 28px;
  margin-bottom: 15px;
}

p {
  font-size: 16px;
  color: #555;
  margin-bottom: 25px;
}

.btn {
  display: inline-block;
  margin: 5px;
  padding: 12px 24px;
  background: #dc3545;
  color: white;
  border-radius: 8px;
  text-decoration: none;
  transition: 0.3s;
}

.btn:hover {
  background: #c82333;
}

.btn-outline {
  display: inline-block;
  margin: 5px;
  padding: 12px 24px;
  background: transparent;
  color: #dc3545;
  border: 2px solid #dc3545;
  border-radius: 8px;
  text-decoration: none;
  transition: 0.3s;
}

.btn-outline:hover {
  background: #dc3545;
  color: white;
}
</style>
