<template>
  <div class="container">
    <div class="login__form">
      <div class="row">
        <div class="col-sm-12 col-lg-6">
          <form method="POST" class="form" id="form-2" @submit.prevent="login">
            <h3 class="heading">ĐĂNG NHẬP</h3>
            <a href="/forgot-password" class="form__forgot-password">Bạn quên mật khẩu?</a>
            <div class="form-group">
              <label for="username" class="form-label">Tên đăng nhập</label>
              <input id="username" v-model="username" type="text" placeholder="Nhập tên đăng nhập" class="form-control">
              <span class="form-message"></span>
            </div>

            <div class="form-group matkhau">
              <label for="password" class="form-label">Mật khẩu</label>
              <div class="password-wrapper">
                <input
                    id="password"
                    v-model="password"
                    :type="showPassword ? 'text' : 'password'"
                    placeholder="Nhập mật khẩu"
                    class="form-control">
                <i
                    :class="showPassword ? 'fas fa-eye' : 'fas fa-eye-slash'"
                    class="show-hide"
                    @click="togglePasswordVisibility"></i>
              </div>
              <span class="form-message"></span>
            </div>
            <button class="form-submit btn-blocker" type="submit" style="border-radius: unset;">
              ĐĂNG NHẬP
              <i class="fas fa-arrow-right" style="font-size: 16px;margin-left: 10px;"></i>
            </button>

          </form>
        </div>
        <div class="col-sm-12 col-lg-6">
          <h3 class="heading">TẠO MỘT TÀI KHOẢN</h3>
          <p class="text-login">Thật dễ dàng tạo một tài khoản. Hãy nhập địa chỉ email của bạn và điền vào mẫu
            trên trang tiếp theo và tận hưởng những lợi ích của việc sở hữu một tài khoản :</p>
          <ul>
            <li class="text-login-item"><i class="fas fa-check"></i> Tạo tài khoản nhanh chóng</li>
            <li class="text-login-item"><i class="fas fa-check"></i> Thanh toán nhanh hơn</li>
            <li class="text-login-item"><i class="fas fa-check"></i> Ưu đãi và khuyến mãi độc quyền</li>
            <li class="text-login-item"><i class="fas fa-check"></i> Các sản phẩm mới nhất</li>
            <li class="text-login-item"><i class="fas fa-check"></i> Các bộ sưu tập giới hạn và bộ sưu tập theo mùa mới
            </li>
            <li class="text-login-item"><i class="fas fa-check"></i> Các sự kiện sắp tới</li>
          </ul>
          <a href="/createinfo">
            <button class="form-submit btn-blocker custom-btn" style="border-radius: unset;margin:unset">ĐĂNG KÍ
              <i class="fas fa-arrow-right" style="font-size: 16px;margin-left: 10px;"></i></button>
          </a>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import {ref} from 'vue';
import Cookies from 'js-cookie';

const username = ref('');
const password = ref('');
const showPassword = ref(false);
const apiUrl = 'http://localhost:8080/MiniatureCrafts/signin';

const togglePasswordVisibility = () => {
  showPassword.value = !showPassword.value;
};

const validatePassword = (password) => {
  // Check if password is longer than 7 characters and contains no spaces
  const isValid = password.length > 7 && !/\s/.test(password);
  return isValid;
};

const login = async () => {
  if (!username.value || !password.value) {
    alert('Vui lòng nhập đầy đủ tên đăng nhập và mật khẩu!');
    return;
  }

  if (!validatePassword(password.value)) {
    alert('Mật khẩu phải dài hơn 7 ký tự và không chứa khoảng trắng!');
    return;
  }

  const credentials = {
    username: username.value,
    password: password.value,
  };

  console.log('username:', username.value);
  console.log('password:', password.value);
  console.log('Gửi yêu cầu POST đến: ', apiUrl);

  try {
    const response = await fetch(apiUrl, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(credentials),
    });

    const data = await response.json();

    if (response.ok) {
      console.log('Đăng nhập thành công', data);
      alert('Đăng nhập thành công!');

      Cookies.set('authToken', data.token);
      Cookies.set('customers', JSON.stringify(data));
      console.log("Line 99: " + Cookies.set('authToken', data.token));
      console.log("Line 99: " + data.token);
      window.location.href = '/';
    } else {
      throw new Error(data.message || 'Đăng nhập thất bại');
    }
  } catch (error) {
    console.error('Đăng nhập thất bại', error);
    alert('Tên đăng nhập hoặc mật khẩu không đúng. Vui lòng thử lại!');
  }
};
</script>

<style scoped>
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

.show-hide {
  position: absolute;
  top: 56px;
  right: 20px;
  transform: translateY(-50%);
  cursor: pointer;
}
.container {
   width: 100%;
}
</style>