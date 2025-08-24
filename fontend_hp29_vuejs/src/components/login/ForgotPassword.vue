<template>
  <div class="container">
    <div class="login__form">
      <div class="row">
        <div class="col-sm-12 col-lg-6">
          <form method="POST" class="form" @submit.prevent="handleSubmit">

            <!-- Bước 1: Gửi OTP -->
            <div v-if="currentStep === 1">
              <h3 class="heading">QUÊN MẬT KHẨU</h3>
              <p class="text-login">Nhập tên đăng nhập và email của bạn để nhận mã OTP:</p>

              <div class="form-group">
                <label for="username" class="form-label">Tên đăng nhập</label>
                <input id="username" v-model="username" type="text" placeholder="Nhập tên đăng nhập" class="form-control"/>
              </div>

              <div class="form-group">
                <label for="email" class="form-label">Email</label>
                <input id="email" v-model="email" type="email" placeholder="Nhập email của bạn" class="form-control"/>
              </div>

              <button class="form-submit btn-blocker" type="submit">GỬI OTP</button>
            </div>

            <!-- Bước 2: Xác thực OTP -->
            <div v-if="currentStep === 2">
              <h3 class="heading">XÁC THỰC OTP</h3>
              <p class="text-login">Chúng tôi đã gửi OTP đến email của bạn. Vui lòng nhập mã OTP bên dưới:</p>

              <div class="form-group">
                <label for="otp" class="form-label">Mã OTP</label>
                <input id="otp" v-model="otp" type="text" placeholder="Nhập OTP" class="form-control"/>
              </div>

              <button class="form-submit btn-blocker" type="submit">XÁC NHẬN OTP</button>
            </div>

            <!-- Bước 3: Đặt lại mật khẩu -->
            <div v-if="currentStep === 3">
              <h3 class="heading">ĐẶT LẠI MẬT KHẨU</h3>
              <p class="text-login">Nhập mật khẩu mới cho tài khoản của bạn:</p>

              <!-- Mật khẩu mới -->
              <div class="form-group matkhau">
                <label for="newPassword" class="form-label">Mật khẩu mới</label>
                <div class="password-wrapper">
                  <input
                      id="newPassword"
                      v-model="newPassword"
                      :type="showPassword ? 'text' : 'password'"
                      placeholder="Nhập mật khẩu mới"
                      class="form-control"
                  />
                  <i
                      :class="showPassword ? 'fas fa-eye' : 'fas fa-eye-slash'"
                      class="show-hide"
                      @click="togglePasswordVisibility"
                  ></i>
                </div>
              </div>

              <!-- Nhập lại mật khẩu -->
              <div class="form-group matkhau">
                <label for="confirmPassword" class="form-label">Nhập lại mật khẩu</label>
                <div class="password-wrapper">
                  <input
                      id="confirmPassword"
                      v-model="confirmPassword"
                      :type="showPassword ? 'text' : 'password'"
                      placeholder="Nhập lại mật khẩu"
                      class="form-control"
                  />
                  <i
                      :class="showPassword ? 'fas fa-eye' : 'fas fa-eye-slash'"
                      class="show-hide"
                      @click="togglePasswordVisibility"
                  ></i>
                </div>
              </div>

              <button class="form-submit btn-blocker" type="submit">ĐẶT LẠI MẬT KHẨU</button>
            </div>

            <div class="back-to-login">
              <a href="/login">← Quay lại đăng nhập</a>
            </div>

          </form>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from "vue";

const currentStep = ref(1);

const username = ref("");
const email = ref("");
const otp = ref("");
const newPassword = ref("");
const confirmPassword = ref("");
const showPassword = ref(false);

const baseUrl = "http://localhost:8080/MiniatureCrafts/account/forgot-password";

const togglePasswordVisibility = () => {
  showPassword.value = !showPassword.value;
};

const handleSubmit = async () => {
  try {
    if (currentStep.value === 1) {
      const res = await fetch(
          `${baseUrl}/send-otp?email=${encodeURIComponent(email.value)}&username=${encodeURIComponent(username.value)}`,
          { method: "POST" }
      );
      if (res.ok) {
        alert("OTP đã được gửi đến email!");
        currentStep.value = 2;
      } else {
        const errText = await res.text();
        alert(errText || "Gửi OTP thất bại!");
      }
    } else if (currentStep.value === 2) {
      const res = await fetch(
          `${baseUrl}/verify-otp?username=${encodeURIComponent(username.value)}&otp=${encodeURIComponent(otp.value)}`,
          { method: "POST" }
      );
      if (res.ok) {
        alert("OTP chính xác! Vui lòng nhập mật khẩu mới.");
        currentStep.value = 3;
      } else {
        const errText = await res.text();
        alert(errText || "OTP không hợp lệ!");
      }
    } else if (currentStep.value === 3) {
      if (newPassword.value !== confirmPassword.value) {
        alert("Mật khẩu nhập lại không khớp!");
        return;
      }
      const res = await fetch(
          `${baseUrl}/reset?email=${encodeURIComponent(email.value)}&newPassword=${encodeURIComponent(newPassword.value)}`,
          { method: "POST" }
      );
      if (res.ok) {
        alert("Mật khẩu đã được đặt lại thành công! Vui lòng đăng nhập.");
        window.location.href = "/login";
      } else {
        const errText = await res.text();
        alert(errText || "Đặt lại mật khẩu thất bại!");
      }
    }
  } catch (error) {
    console.error("Lỗi:", error);
    alert("Có lỗi xảy ra, vui lòng thử lại!");
  }
};
</script>

<style scoped>
.heading {
  text-align: start;
  font-weight: 600;
  font-size: 34px;
  color: var(--black-color);
  margin: 15px 0;
}

.text-login {
  font-size: 16px;
  margin-bottom: 10px;
}

.back-to-login {
  margin-top: 20px;
}

.back-to-login a {
  color: var(--black-color);
  text-decoration: underline;
  font-size: 16px;
}

.back-to-login a:hover {
  color: #777575;
}

/* Icon hiện/ẩn mật khẩu */
.password-wrapper {
  position: relative;
}

.show-hide {
  position: absolute;
  top: 50%;
  right: 15px;
  transform: translateY(-50%);
  cursor: pointer;
}
</style>
