<script setup>
import {ref} from 'vue';
import axios from 'axios';
import {useRouter} from 'vue-router';
import Cookies from 'js-cookie'; // Import js-cookie

const username = ref('');
const password = ref('');
const errorMessage = ref('');

const router = useRouter();

const login = async () => {
  errorMessage.value = '';
  try {
    const response = await axios.post('http://localhost:8080/MiniatureCrafts/signin', {
      username: username.value,
      password: password.value,
    });

    if (response.data.roles.includes('ADMIN') || response.data.roles.includes('USER')) {
      if (response.data && response.data.token) {
        Cookies.set('token', response.data.token);
        Cookies.set('authToken', response.data.token);
        Cookies.set('admin', JSON.stringify(response.data.userInfo), { expires: 1 });
        Cookies.set('roles', JSON.stringify(response.data.roles));
        console.log("ADMIN: " + JSON.stringify(Cookies.get('admin')));
        console.log("ADMIN: " + JSON.stringify(response.data.userInfo.name));
        // Chuyển hướng đến trang sản phẩm
        window.location.href = '/product'; // Dùng window.location.href để chuyển hướng đến URL bên ngoài
      }
    }else{
      errorMessage.value = error.response?.data?.message || 'Bạn không đủ quyền hạn, vui lòng đăng nhập lại.';
      window.location.href = '/login';
    }
  } catch (error) {
    console.log(error);

    errorMessage.value = error.response?.data?.message || 'Đăng nhập thất bại. Vui lòng thử lại.';
  }
};
</script>

<template>
  <div class="login-container">
    <h1>Đăng Nhập</h1>
    <form @submit.prevent="login" class="login-form">
      <div class="form-group">
        <label for="username">Tên đăng nhập:</label>
        <input
            id="username"
            type="text"
            v-model="username"
            placeholder="Nhập tên đăng nhập"
            required
        />
      </div>

      <div class="form-group">
        <label for="password">Mật khẩu:</label>
        <input
            id="password"
            type="password"
            v-model="password"
            placeholder="Nhập mật khẩu"
            required
        />
      </div>

      <p v-if="errorMessage" class="error-message">{{ errorMessage }}</p>

      <button type="submit" class="login-button">Đăng Nhập</button>
    </form>
  </div>
</template>

<style scoped>
/* Định dạng tổng thể của trang */
.login-container {
  max-width: 400px;
  margin: 50px auto;
  padding: 40px;
  border-radius: 12px;
  box-shadow: 0 6px 12px rgba(0, 0, 0, 0.1);
  background-color: #fff;
  border: 1px solid #e1e1e1;
}

h1 {
  text-align: center;
  color: #4CAF50;
  font-size: 28px;
  margin-bottom: 30px;
  font-family: 'Arial', sans-serif;
}

.login-form {
  display: flex;
  flex-direction: column;
}

.form-group {
  margin-bottom: 20px;
}

label {
  font-weight: 600;
  margin-bottom: 8px;
  color: #333;
  font-size: 16px;
}

input {
  width: 100%;
  padding: 12px;
  border: 1px solid #ccc;
  border-radius: 8px;
  font-size: 14px;
  margin-top: 5px;
  transition: border-color 0.3s ease;
}

input:focus {
  outline: none;
  border-color: #4CAF50;
}

.error-message {
  color: red;
  font-size: 14px;
  text-align: center;
  margin-top: 10px;
}

.login-button {
  background-color: #4CAF50;
  color: white;
  padding: 12px;
  border: none;
  border-radius: 8px;
  font-size: 16px;
  cursor: pointer;
  transition: background-color 0.3s ease, transform 0.3s ease;
}

.login-button:hover {
  background-color: #45a049;
  transform: translateY(-2px);
}

.login-button:active {
  background-color: #388e3c;
  transform: translateY(2px);
}

/* Responsive layout */
@media (max-width: 480px) {
  .login-container {
    padding: 30px;
  }

  h1 {
    font-size: 24px;
  }

  .login-button {
    font-size: 14px;
  }
}
</style>