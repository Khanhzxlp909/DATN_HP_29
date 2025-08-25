<script setup>
import {ref} from "vue";

const message = ref("Đặt hàng thành công!");
const tips = ref("Cảm ơn bạn đã mua sắm tại MiniatureCrafts. Đơn hàng của bạn sẽ được xử lý và giao trong vòng 48h.");
</script>

<template>
  <div class="success-container">
    <div class="success-card">
      <img src="../../assets/img/logo/img.png" alt="success" class="icon" />
      <h1>{{ message }}</h1>
      <p>{{ tips }}</p>
      <a href="/history" class="btn">Xem đơn hàng</a>
      <a href="/" class="btn-outline">Tiếp tục mua sắm</a>
    </div>
  </div>
</template>

<style scoped>
.success-container {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 90vh;
  background: #ffffff;
}

.success-card {
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
  color: #28a745;
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
  background: #28a745;
  color: white;
  border-radius: 8px;
  text-decoration: none;
  transition: 0.3s;
}

.btn:hover {
  background: #218838;
}

.btn-outline {
  display: inline-block;
  margin: 5px;
  padding: 12px 24px;
  background: transparent;
  color: #28a745;
  border: 2px solid #28a745;
  border-radius: 8px;
  text-decoration: none;
  transition: 0.3s;
}

.btn-outline:hover {
  background: #28a745;
  color: white;
}
</style>
