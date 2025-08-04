<template>
  <div class="cart">
    <div class="container">
      <div class="cart-wrap">
        <div class="cart-content">
          <form class="form-cart"  @submit.prevent>
            <div class="cart-body">
              <div class="cart-heading hidden-xs">
                <div class="row cart-row">
                  <div class="col-11" style="text-align: center;">
                    <div class="row">
                      <div class="col-5">Sản phẩm</div>
                      <div class="col-2">Đơn giá</div>
                      <div class="col-3">Số lượng</div>
                      <div class="col-2">Thành tiền</div>
                    </div>
                  </div>
                  <div class="col-1"></div>
                </div>
              </div>
              <div class="cart-body">
                <div
                    v-if="cart.length === 0"
                    class="empty-cart-message"
                    style="text-align: center; padding: 20px; font-size: 18px; color: gray;"
                >
                  Giỏ hàng của bạn đang trống.
                </div>
                <div
                    v-else
                    class="row cart-body-row"
                    v-for="item in cart"
                    :key="item.id"
                    style="align-items: center;"
                >
                  <div class="col-md-11 col-10" style="text-align: center;">
                    <div class="row card-info" style="align-items: center;">
                      <div class="col-md-2 col-12 card-info-img">
                        <a href="">
                          <img class="cart-img" :src="getDefaultImage(item.variation_id.images)" alt="Product">
                        </a>
                      </div>
                      <div class="col-md-3 col-12">
                        <a href="" class="cart-name">
                          <h3>{{ item.variation_id.name }}</h3>
                        </a>
                      </div>
                      <div class="col-md-2 col-12" style="font-size: 16px;">
                        <span>{{ formatCurrency(item.variation_id.price) }}</span>
                      </div>
                      <div class="col-md-3 col-12">
                        <div class="cart-quantity">
                          <input
                              type="button"
                              value="-"
                              class="control"
                              @click="updateQuantity(item.id, item.quantity - 1)"
                          >
                          <input
                              type="number"
                              :value="item.quantity"
                              @input="item.quantity = $event.target.value"
                              @change="updateQuantity(item.id, $event.target.value)"
                              class="text-input"
                          />

                          <input
                              type="button"
                              value="+"
                              class="control"
                              @click="updateQuantity(item.id, item.quantity + 1)"
                          >
                        </div>
                      </div>
                      <div class="col-md-2 col-12 hidden-xs" style="font-size: 15px;">
                        <span>{{ formatCurrency(item.quantity * item.variation_id.price) }}</span>
                      </div>
                    </div>
                  </div>
                  <div class="col-md-1 col-2 text-right">
                    <a @click="removeFromCart(item.id)"><i class="fas fa-trash"></i></a>
                  </div>
                </div>
              </div>
              <div class="cart-footer-right">
                <div class="row cart-footer-row align-items-center">
                  <!-- Nút tiếp tục mua sắm -->
                  <div class="col-md-4 col-12 continue mb-2 mb-md-0">
                    <a href="/product">
                      <i class="fas fa-chevron-left"></i> Tiếp tục mua sắm
                    </a>
                  </div>

                  <!-- Thành tiền -->
                  <div class="col-md-4 col-12 mb-2 mb-md-0 text-md-center text-start">
                    <h1 style="font-size: 25px; margin: 0;">
                      <label>Thành tiền:
                        <span class="total__price"> {{ formatCurrency(totalPrice) }}</span>
                      </label>

                    </h1>
                  </div>

                  <!-- Nút đặt hàng -->
                  <div class="col-md-4 col-12 text-md-end text-start">
                    <a v-if="cart.length > 0"
                       href="/pay"
                       class="chekout btn btn-primary"
                       style="display: inline-block;">
                      Đặt hàng ngay!
                    </a>
                    <button
                        v-else
                        @click.prevent="alert('Giỏ hàng của bạn đang trống.')"
                        class="chekout btn btn-secondary"
                        style="display: inline-block; cursor: not-allowed;">
                      Đặt hàng ngay!
                    </button>
                  </div>
                </div>
              </div>

            </div>
            <div class="cart-body-right">

            </div>
          </form>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios';
import {ref, onMounted} from 'vue';
import Cookies from 'js-cookie';
import {useRoute} from "vue-router";
import { useUser } from '@/components/composables/useUser';
export default {
  setup() {
    const cart = ref([]);
    const totalPrice = ref(0);
    const apiUrl = "http://localhost:8080/api/v1/cart";
    const route = useRoute();
    const token = Cookies.get("authToken");
    const customerId = route.query.userId; // Giả sử ID khách hàng là 1 (có thể thay đổi)
    const getDefaultImage = (images) => {
      if (!images || images.length === 0) return "default-image.jpg"; // Ảnh mặc định nếu không có ảnh nào

      const defaultImage = images;
      return defaultImage ? `http://localhost:8080/upload/images/${defaultImage.cd_Images}` : "default-image.jpg";
    };


    // Lấy giỏ hàng từ API
    const getCart = async () => {

      try {
        const response = await axios.get(`${apiUrl}/findall/${customerId}`,{
          headers: { Authorization: `Bearer ${token}` }
        });
        cart.value = response.data;
        console.log(cart.value)
        calculateTotal();

      } catch (error) {
        console.error("Lỗi khi lấy giỏ hàng:", error);
      }
    };


    const convertCurrencyToNumber = (price) => {
      if (!price) return 0;

      return parseFloat(
          price.replace(/\./g, "").replace(" ₫", "").trim()
      );
    }
    // Cập nhật tổng giá trị giỏ hàng
    const calculateTotal = () => {
      totalPrice.value = cart.value.reduce((total, item) => {
        return total + item.quantity * item.variation_id.price;
      }, 0);
    };

    const updateQuantity = async (cartItemId, newQuantity) => {
      newQuantity = parseInt(newQuantity);
      if (isNaN(newQuantity) || newQuantity <= 0) {
        alert("Số lượng không hợp lệ!");
        return;
      }

      try {
        const response = await axios.post(`${apiUrl}/editquantity/${cartItemId}/${newQuantity}`);
        if (response.status === 200) {
          await getCart(); // Reload lại dữ liệu
          await useUser();
          window.location.reload();
        }
      } catch (error) {
        console.error("Lỗi khi cập nhật số lượng:", error);
        if(error.response.data === "Not enough stock"){  // Khi số lượng khóa hợp lệ
          alert("Vượt quá số lượng sản phẩm còn lại!");
        }
      }
    };


    // Xóa sản phẩm khỏi giỏ hàng
    const removeFromCart = async (cartItemId) => {
      try {
        const response = await axios.get(`${apiUrl}/remove/${cartItemId}`);
        if (response.status === 200) {
          getCart(); // Cập nhật lại giỏ hàng
        }
      } catch (error) {
        console.error("Lỗi khi xóa sản phẩm:", error);
      }
    };

    // Định dạng tiền VNĐ
    const formatCurrency = (value) => {
      return value.toLocaleString("vi-VN") + " ₫";
    };

    onMounted(() => {
      getCart();
    });

    return {
      cart,
      getDefaultImage,
      totalPrice,
      convertCurrencyToNumber,
      updateQuantity,
      removeFromCart,
      formatCurrency,
    };
  },
};
</script>

<style>
.container {
  width: 100%;
}

</style>