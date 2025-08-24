<template>
  <footer class="footer">
    <div class="container">
      <div class="row">
        <!-- Logo & Liên hệ -->
        <div class="col-sm-12 col-md-6 col-lg-3 footer__brand">
          <img
              src="../assets/img/logo/img.png"
              alt="MinatureCraft Logo"
              class="footer__logo"
          />
          <p class="footer__desc">Mang đến thế giới mô hình tinh xảo cho bạn.</p>
          <ul class="footer__contact">
            <li><i class="fas fa-map-marker-alt"></i> Hai Phong, Viet Nam</li>
            <li><i class="fas fa-phone"></i> <a href="tel:0123456789">0338086647</a></li>
            <li><i class="fas fa-envelope"></i> <a href="mailto:abc@gmail.com">khanhzxlp909@gmail.com</a></li>
          </ul>
          <div class="footer__social">
            <a href="#"><i class="fab fa-facebook-f"></i></a>
            <a href="#"><i class="fab fa-instagram"></i></a>
            <a href="#"><i class="fab fa-youtube"></i></a>
            <a href="#"><i class="fab fa-twitter"></i></a>
          </div>
        </div>

        <!-- Thông tin -->
        <div class="col-sm-12 col-md-6 col-lg-3">
          <h3 class="footer__heading">Thông tin</h3>
          <ul class="footer__list">
            <li><a href="#">Cơ sở 1: 271 Lê Thánh Tông, Ngô Quyền, Hải Phòng</a></li>
            <li><a href="#">Lĩnh vực kinh doanh</a></li>
          </ul>
        </div>

        <!-- Chính sách -->
        <div class="col-sm-12 col-md-6 col-lg-3">
          <h3 class="footer__heading">Chính sách</h3>
          <ul class="footer__list">
            <li><a href="#">Thanh toán</a></li>
            <li><a href="#">Giao hàng</a></li>
            <li><a href="#">Bảo mật</a></li>
          </ul>
        </div>

        <!-- Hỗ trợ -->
        <div class="col-sm-12 col-md-6 col-lg-3">
          <h3 class="footer__heading">Hỗ trợ</h3>
          <ul class="footer__list">
            <li><router-link to="/">Trang chủ</router-link></li>
            <li><router-link to="/product">Sản phẩm</router-link></li>
            <li><router-link to="/contact">Liên hệ</router-link></li>
            <li><router-link to="/contact">Liên hệ</router-link></li>
          </ul>
        </div>

      </div>
    </div>
  </footer>
</template>

<script>
export default {
  name: "MainFooter",
};
</script>

<style scoped>
.footer {
  background: #ffffff;
  color: #000000;
  padding: 50px 0 20px;
  font-size: 15px;
  line-height: 1.8;
  margin-top: auto;           /* quan trọng: đẩy footer xuống cuối */
  border-top: 1px solid #e0e0e0;  /* vạch mờ phân chia */
}


.footer__logo {
  width: 90px;
  height: 90px;
  border-radius: 50%;
  border: 2px solid #fff;
  margin-bottom: 15px;
}

.footer__desc {
  margin-bottom: 15px;
  color: #000000;
}

.footer__contact li {
  margin-bottom: 8px;
}

.footer__contact i {
  margin-right: 8px;
  color: var(--hover-color);
}

.footer__social a {
  display: inline-block;
  margin-right: 12px;
  font-size: 18px;
  color: #000000;
  transition: 0.3s;
}

.footer__social a:hover {
  color: var(--hover-color);
}

.footer__heading {
  color: #fff;
  font-size: 16px;
  margin-bottom: 15px;
  font-weight: bold;
}

.footer__list {
  padding: 0;
  list-style: none;
}

.footer__list li {
  margin-bottom: 8px;
}

.footer__list a {
  color: #000000;
  text-decoration: none;
  transition: 0.3s;
}

.footer__list a:hover {
  color: var(--hover-color);
  padding-left: 5px;
}

.footer__bottom {
  border-top: 1px solid #333;
  margin-top: 30px;
  padding-top: 15px;
  text-align: center;
  font-size: 14px;
  color: #888;
}

.footer__bottom span {
  color: var(--hover-color);
  font-weight: 600;
}
</style>
