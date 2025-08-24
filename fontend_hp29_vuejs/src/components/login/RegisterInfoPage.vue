<template>
  <div class="container">
    <div class="registration__form">
      <div class="row">
        <div class="col-sm-12 col-lg-6">
          <form @submit.prevent="registerInfo" class="form" id="form-1">
            <h3 class="heading">Đăng ký thông tin</h3>
            <div class="form-group">
              <label for="fullname" class="form-label">Họ tên</label>
              <input id="fullname" v-model="form.name" type="text" placeholder="VD: Nguyễn Văn A"
                     class="form-control"   >
              <span class="form-message">{{ messages.name }}</span>
            </div>
            <div class="form-group">
              <label for="address" class="form-label">Địa chỉ</label>
              <input id="address" v-model="form.address" type="text" placeholder="VD: Hải phòng computer"
                     class="form-control"   >
              <span class="form-message">{{ messages.address }}</span>
            </div>
            <div class="form-group">
              <label for="phone" class="form-label">Số điện thoại</label>
              <input id="phone" v-model="form.phone" type="text" placeholder="Nhập số điện thoại"
                     class="form-control"   >
              <span class="form-label" style="color: red">{{ messages.phone }}</span>
            </div>
            <div class="form-group">
              <label for="note" class="form-label">Ghi chú</label>
              <input id="note" v-model="form.note" type="text"
                     placeholder="Ghi chú" class="form-control">
              <span class="form-label" style="color: red">{{ messages.note }}</span>
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
      name: '',
      address: '',
      note: '',
      phone: '',
    });

    const messages = ref({
      name: '',
      address: '',
      note: '',
      phone: '',
    });

    const validateForm = () => {
      let isValid = true;
      messages.value = {
        name: '',
        address: '',
        note: '',
        phone: '',
      };

      // Validate name
      if (!form.value.name) {
        messages.value.name = 'Họ tên không được để trống.';
        isValid = false;
      }

      // Validate address
      if (!form.value.address) {
        messages.value.address = 'Địa chỉ không được để trống.';
        isValid = false;
      }

      // Validate phone
      if (!form.value.phone) {
        messages.value.phone = 'Số điện thoại không được để trống.';
        isValid = false;
      } else if (form.value.phone.length != 10) {
        console.log(form.value.phone.length);
        messages.value.phone = 'Số điện thoại không hợp lệ. Vui lòng nhập lại.';
        isValid = false;
      }

      // Validate note (optional, but can have a max length)
      if (form.value.note.length > 200) {
        messages.value.note = 'Ghi chú không được vượt quá 200 ký tự.';
        isValid = false;
      }

      return isValid;
    };

    const registerInfo = async () => {
      if (!validateForm()) {
        return; // Stop if validation fails
      }

      console.log(form.value);
      try {
        const response = await axios.post('http://localhost:8080/MiniatureCrafts/registerinfo', {
          name: form.value.name,
          address: form.value.address,
          phone: form.value.phone,
          status: 1,
          note: form.value.note
        });
        alert('Đăng ký thành công!');
        sessionStorage.setItem("userid", response.data.id);
        window.location.href = '/register';
        console.log(response.data);
      } catch (error) {
        console.error('Lỗi khi đăng ký:', error);
        alert('Số điện thoại đã tồn tại.');
      }
    };

    return {
      form,
      messages,
      registerInfo
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