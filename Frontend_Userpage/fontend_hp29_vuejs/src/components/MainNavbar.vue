<template>
  <header class="header">
    <div class="container">
      <div class="top-link clearfix hidden-sm hidden-xs">
        <div class="row">
          <!-- Social Links -->
          <div class="col-6 social_link">
            <div class="social-title">Theo dõi:</div>
            <a href="https://www.facebook.com/share/ZAQmhY81qNSamVdd/">
              <i class="fab fa-facebook" style="font-size: 24px; margin-right: 10px"></i>
            </a>
            <a href="#">
              <i class="fab fa-instagram" style="font-size: 24px; margin-right: 10px; color: pink;"></i>
            </a>
            <a href="#">
              <i class="fab fa-youtube" style="font-size: 24px; margin-right: 10px; color: red;"></i>
            </a>
            <a href="#">
              <i class="fab fa-twitter" style="font-size: 24px; margin-right: 10px"></i>
            </a>
          </div>

          <!-- Login/Register Links -->
          <div class="nav nav__first right col-6 login_link">
            <ul class="header_link left ml-auto" v-if="!user">
              <li>
                <router-link to="/login"><i class="fas fa-sign-in-alt mr-3"></i>Đăng nhập</router-link>
              </li>
              <li>
                <router-link to="/createinfo">
                  <i class="fas fa-user-plus mr-3" style="margin-left: 10px;"></i>Đăng kí
                </router-link>
              </li>
            </ul>
            <ul class="nav nav__first right d-flex align-items-center" v-else>
              <li class="nav-item nav-item__first nav-item__first-user d-flex align-items-center">
                <img src="../assets/img/logo/avtusers.png" alt="" class="nav-item__first-img mr-2"/>
                <span class="nav-item__first-name">{{ user.userInfo.name }}</span>
                <ul class="nav-item__first-menu">
                  <li class="nav-item__first-item">
                    <a href="/history">Tài khoản của tôi</a>
                  </li>
                  <li class="nav-item__first-item">
                    <a href="#">Địa chỉ của tôi</a>
                  </li>
                  <li class="nav-item__first-item">
                    <a href="#">Đơn mua</a>
                  </li>
                  <li class="nav-item__first-item">
                    <a href="#" @click="logout">Đăng xuất</a>
                  </li>
                </ul>
              </li>
            </ul>

          </div>
        </div>
      </div>

      <div class="header-main clearfix">
        <div class="row">
          <div class="col-lg-3 col-100-h">
            <div id="trigger-mobile" class="visible-sm visible-xs"><i class="fas fa-bars"></i></div>
            <div class="logo">
              <router-link to="/">
                <img src="/img.png" alt="Logo"/>
              </router-link>
            </div>
          </div>
          <div class="col-lg-6 m-auto pdt15">
            <form class="example" @submit.prevent="submitSearch">
              <input type="text" class="input-search" placeholder="Tìm kiếm.." v-model="searchKeyword">
              <button type="submit" class="search-btn"><i class="fa fa-search"></i></button>
            </form>
          </div>
          <div class="col-3 m-auto hidden-sm hidden-xs">
            <div class="item-car clearfix">
              <router-link :to="user ? `/cart?userId=${user.userInfo.id}` : '/cart'" class="header__second__cart--icon">
                <i class="fas fa-shopping-cart"></i>
              </router-link>
            </div>
          </div>
        </div>
      </div>
    </div>

    <nav class="header_nav hidden-sm hidden-xs">
      <div class="container">
        <ul class="header_nav-list nav">
          <li class="header_nav-list-item">
            <router-link to="/" class="active">Trang chủ</router-link>
          </li>
          <li class="header_nav-list-item has-mega">
            <router-link to="/product">Sản phẩm</router-link>
          </li>
          <li class="header_nav-list-item">
            <router-link to="/New">Tin tức </router-link>
          </li>
          <li class="header_nav-list-item">
            <a href="/lien-he">Liên hệ</a>
          </li>
        </ul>
      </div>
    </nav>
  </header>
</template>

<script>
import axios from "axios";
import Cookies from "js-cookie"; // Ensure you have js-cookie installed
import {useRouter} from "vue-router"; // Import useRouter
import {ref} from 'vue'; // Import ref

export default {
  setup() {
    const user = ref(null);
    const searchKeyword = ref('');
    const router = useRouter();

    const getUserInfo = () => {
      const token = Cookies.get("authToken");
      if (!token) {
        console.error("No token found, please log in!");
        return;
      }
      axios
          .get("http://localhost:8080/MiniatureCrafts/user", {
            headers: {
              Authorization: `Bearer ${token}`,
            },
          })
          .then((response) => {
            user.value = response.data;
          })
          .catch((error) => {
            console.error("Error fetching user info:", error);
            user.value = null; // Reset user if error occurs
          });
    };

    const submitSearch = () => {
      if (searchKeyword.value) {
        router.push({name: 'resultpage', query: {search: searchKeyword.value}});
      }
    };

    const logout = () => {
      Cookies.remove("authToken");
      Cookies.remove("customers")
      user.value = null;
      router.push("/"); // Redirect to login page
    };

    getUserInfo();

    return {
      user,
      searchKeyword,
      submitSearch,
      logout,
    };
  },
};
</script>

<style scoped>
form.example input[type=text] {
  padding: 10px;
  font-size: 17px;
  border: 1px solid grey;
  float: left;
  width: 80%;
  background: #f1f1f1;
}

form.example button {
  float: left;
  width: 20%;
  padding: 10px;
  background: #2196F3;
  color: white;
  font-size: 17px;
  border: 1px solid grey;
  border-left: none;
  cursor: pointer;
}

form.example button:hover {
  background: #0b7dda;
}

form.example::after {
  content: "";
  clear: both;
  display: table;
}

.container {
  width: 100%;
}
</style>