<template>
  <div class="container">
    <div class="registration__form">
      <div class="row">
        <div class="col-sm-12 col-lg-6">
          <form @submit.prevent="register" class="form" id="form-1">
            <h3 class="heading">ĐĂNG KÍ</h3>
            <div class="form-group">
              <label for="fullname" class="form-label">Tên đăng nhập</label>
              <input id="fullname" v-model="form.username" type="text" placeholder="VD: Nguyễn Văn A" class="form-control">
              <span class="form-message">{{ messages.username }}</span>
            </div>
            <div class="form-group">
              <label for="email" class="form-label">Email</label>
              <input id="email" v-model="form.email" type="email" placeholder="VD: email@gmail.com" class="form-control">
              <span class="form-message">{{ messages.email }}</span>
              <label for="email" class="form-label">Vui lòng ghi đúng email của bạn</label>
              <label for="email" class="form-label">Email để nhận thông báo về đơn hàng và sản phẩm</label>
            </div>
            <div class="form-group matkhau">
              <label for="password" class="form-label">Mật khẩu</label>
              <div class="password-wrapper">
                <input id="password" v-model="form.password" :type="passwordVisibility.password ? 'text' : 'password'" placeholder="Nhập mật khẩu" class="form-control">
                <span class="show-hide" @click="togglePasswordVisibility('password')">
                  <i :class="passwordVisibility.password ? 'fas fa-eye' : 'fas fa-eye-slash'"></i>
                </span>
              </div>
              <span class="form-message">{{ messages.password }}</span>
            </div>
            <div class="form-group matkhau">
              <label for="password_confirmation" class="form-label">Nhập lại mật khẩu</label>
              <div class="password-wrapper">
                <input id="password_confirmation" v-model="form.password_confirmation" :type="passwordVisibility.password_confirmation ? 'text' : 'password'" placeholder="Nhập lại mật khẩu" class="form-control">
                <span class="show-hide" @click="togglePasswordVisibility('password_confirmation')">
                  <i :class="passwordVisibility.password_confirmation ? 'fas fa-eye' : 'fas fa-eye-slash'"></i>
                </span>
              </div>
              <span class="form-message">{{ messages.password_confirmation }}</span>
            </div>
            <button class="form-submit btn-blocker" style="border-radius: unset;">Đăng ký <i class="fas fa-arrow-right"
                                                                                             style="font-size: 16px;margin-left: 10px;"></i>
            </button>
            <p style="font-size: 16px;margin: 10px 0;">Bạn đã có tài khoản?
              <a href="/login" style="color: black; font-weight: bold">Đăng nhập</a>
            </p>
          </form>
        </div>
        <div class="col-sm-12 col-lg-6">
          <h3 class="heading">TẠO MỘT TÀI KHOẢN</h3>
          <p class="text-login">Đăng nhập bằng tài khoản sẽ giúp bạn truy cập:</p>
          <ul>
            <li class="text-login-item"><i class="fas fa-check"></i>
              <p class="text-login">Một lần đăng nhập chung duy nhất để tương tác với các sản phẩm và dịch vụ của Miniature Craft</p>
            </li>
            <li class="text-login-item"><i class="fas fa-check"></i>
              <p class="text-login">Thanh toán nhanh hơn</p>
            </li>
            <li class="text-login-item"><i class="fas fa-check"></i>
              <p class="text-login">Xem lịch sử đặt hàng riêng của bạn</p>
            </li>
            <li class="text-login-item">
              <i class="fas fa-check"></i>
              <p class="text-login">Thêm hoặc thay đổi tùy chọn email</p>
            </li>
          </ul>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { ref } from 'vue';
import axios from 'axios';

export default {
  setup() {
    const form = ref({
      username: '',
      email: '',
      password: '',
      password_confirmation: '',
    });

    const messages = ref({
      username: '',
      email: '',
      password: '',
      password_confirmation: '',
    });

    const passwordVisibility = ref({
      password: false,
      password_confirmation: false
    });

    const togglePasswordVisibility = (field) => {
      passwordVisibility.value[field] = !passwordVisibility.value[field];
    };

    const validateForm = () => {
      let isValid = true;
      messages.value = {
        username: '',
        email: '',
        password: '',
        password_confirmation: '',
      };

      // Validate username
      if (!form.value.username) {
        messages.value.username = 'Tên đăng nhập không được để trống.';
        isValid = false;
      }

      // Validate email
      const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/; // Basic email regex
      if (!form.value.email) {
        messages.value.email = 'Email không được để trống.';
        isValid = false;
      } else if (!emailRegex.test(form.value.email)) {
        messages.value.email = 'Email không hợp lệ.';
        isValid = false;
      }

      // Validate password
      if (!form.value.password) {
        messages.value.password = 'Mật khẩu không được để trống.';
        isValid = false;
      } else if (form.value.password.length < 8) {
        messages.value.password = 'Mật khẩu phải có ít nhất 8 ký tự.';
        isValid = false;
      }

      // Validate password confirmation
      if (form.value.password !== form.value.password_confirmation) {
        messages.value.password_confirmation = 'Mật khẩu không khớp!';
        isValid = false;
      }

      return isValid;
    };

    const register = async () => {
      if (!validateForm()) {
        return; // Stop if validation fails
      }

      const usersid = sessionStorage.getItem("userid");
      console.log(form.value);
      try {
        await axios.post('http://localhost:8080/MiniatureCrafts/signup', {
          usersid: usersid,
          username: form.value.username,
          email: form.value.email,
          password: form.value.password
        });
        alert('Đăng ký thành công!');
        window.location.href = '/login';
      } catch (error) {
        console.error('Lỗi khi đăng ký:', error);
        alert('Tên người dùng hoặc email đã tồn tại.');
      }
    };

    return {
      form,
      messages,
      passwordVisibility,
      togglePasswordVisibility,
      register
    };
  }
};
</script>

<style>
/* login */
.login__form,
.registration__form {
  margin-top: 20px;
}

.heading {
  text-align: start;
  font-weight: 600;
  font-size: 34px;
  color: var(--black-color);
  margin: 15px 0;
}

.form__forgot-password {
  font-size: 16px;
  text-decoration: underline;
  color: var(--black-color);
}

.form__forgot-password:hover {
  color: #777575;
}

.form-group {
  margin: 20px 0;
}

.form-label {
  text-align: left;
  font-size: 16px;
  font-weight: 700;
  line-height: 1.8rem;
  padding-bottom: 6px;
}

.form-control {
  height: 50px;
  padding: 8px 12px;
  border: 1px solid #b3b3b3;
  border-radius: 4px;
  outline: none !important;
  font-size: 1.4rem;
}

.form-control:focus {
  border-color: unset !important;
  border: 1px solid #1dbfaf !important;
}

.form-control:hover {
  border-color: #1dbfaf;
}

.form-group.invalid .form-control {
  border-color: #f33a58;
}

.form-group.invalid .form-message {
  color: #f33a58;
}

.form-check-inline {
  font-size: 16px;
}

.form-submit {
  padding: 20px 50px;
  background: none;
  color: var(--white-color);
  font-weight: 500;
  font-size: 18px;
  position: relative;
  overflow: hidden;
  border: 1px solid var(--black-color);
  transition: 0.8s;
  border-radius: 4px;
}

.form-submit::before {
  content: "";
  position: absolute;
  left: 0;
  width: 100%;
  height: 0%;
  background-color: var(--black-color);
  z-index: -1;
  transition: 0.8s;
  border-radius: 0 0 50% 50%;
  top: 0;
  height: 180%;
  color: var(--white-color);
}

.form-submit:hover {
  color: var(--black-color);
}

.form-submit:hover::before {
  height: 0%;
}

.fi-rs-arrow-right {
  font-size: 14px;
  color: var(--white-color);
  margin-left: 20px;
}

.form-submit:hover > .fi-rs-arrow-right {
  color: var(--black-color);
}

h4 {
  margin: 20px 0px 30px 0px;
}

.form-social {

}

.form-submit-social {
  background: none;
  color: var(--black-color);
  padding: 20px 40px 20px 20px;
  margin: 20px 0;
  border: 2px solid var(--black-color);
  position: relative;
  overflow: hidden;
  transition: color 0.4s linear;
}

.form-submit-social::before {
  background-color: var(--black-color);
  width: 100%;
  content: "";
  position: absolute;
  height: 100%;
  top: 0;
  left: 0;
  transition: transform 0.5s;
  z-index: -1;
  transform-origin: 0 0;
  transition-timing-function: cubic-bezier(0.5, 1.6, 0.4, 0.7);
  transform: scaleX(0);
}

.form-submit-social:hover {
  text-decoration: none;
  color: var(--white-color);
}

.form-submit-social:hover::before {
  transform: scaleX(1);
}

/* .form-submit-social:hover {
    color: var(--black-color);
    text-decoration: none;
} */
.form-submit-social span {
  text-transform: uppercase;
  font-size: 16px;
  font-weight: 500;
  vertical-align: middle;
}

.form-submit-social:first-child {
  margin-right: 40px;
}

.form-submit-social--img {
  width: 20px;
  height: 20px;
  vertical-align: middle;
  margin-left: 65px;
}

.text-login {
  font-size: 16px;
  color: #101920;
  margin: unset;
}

ul {
  list-style: none;
}

.text-login-item {
  display: flex;
  margin: 19px;
  align-items: center;
}

.fa-check {
  font-size: 16px;
  margin-right: 10px;
}

.fi-rs-eye-crossed {
  position: absolute;
  font-size: 20px;
  top: 264px;
  right: 20px;
  cursor: pointer;
}

#password_confirmation {
  position: relative;
}

.show-hide {
  position: absolute;
  top: 56px;
  right: 4px;
  transform: translateY(-50%);
}

.show-hide-two {
  top: 56px;
  position: absolute;
  right: 4px;
  transform: translateY(-50%);
}

.show-hide-three {
  position: absolute;
  right: 20px;
  transform: translateY(-50%);
}

.show-hide i {
  font-size: 20px;
  cursor: pointer;
}

.show-hide-two i {
  font-size: 20px;
  cursor: pointer;
}

.show-hide-three i {
  font-size: 20px;
  cursor: pointer;
}

.fa-eye.hide:before {
  content: '\f070';
}

.fa-eye-2.hide:before {
  content: '\f070';
}

.fa-eye-3.hide::before {
  content: '\f070';
}

.matkhau {
  position: relative;
}

.nhaplaimatkhau {
  position: relative;
}

.custom-btn {
  margin-left: 10px;
  margin-top: 18px;
}
</style>