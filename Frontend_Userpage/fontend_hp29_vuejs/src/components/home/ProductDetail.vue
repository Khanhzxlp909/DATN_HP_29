<template>
  <div class="container">
    <div class="product__detail" v-if="selectedProduct">
      <div class="row product__detail-row">
        <div class="col-lg-6 col-12 daonguoc">
          <div class="img-product">
            <ul class="all-img">
              <li
                  class="img-item"
                  v-for="image in selectedProduct.images"
                  :key="image.id"
              >
                <img
                    class="small-img"
                    :src="`http://localhost:8080/upload/images/${image.cd_Images}`"
                    alt="Product Image"
                    @click="changeImg(image.cd_Images)"
                />
              </li>
            </ul>
          </div>
          <div id="main-img" style="cursor: pointer">
            <img
                :src="`http://localhost:8080/upload/images/${selectedImage}`"
                class="big-img"
                alt="ảnh chính"
                id="img-main"
            />
            <div class="sale-off sale-off-2">
              <span class="sale-off-percent">20%</span>
              <span class="sale-off-label">GIẢM</span>
            </div>
          </div>
        </div>
        <div class="col-lg-6 col-12 product__info">
          <div class="product__name">
            <h2>{{ selectedProduct.name }}</h2>
          </div>
          <div class="status-product">
            Trạng thái:
            <b v-if="activeVariation?.status" class="text-success">Còn hàng</b>
            <b v-else class="text-danger">Hết hàng</b>
          </div>
          <div class="infor-oder">
            Loại sản phẩm:
            <b>{{ selectedProduct.categoryID.name }}</b>
          </div>
          <div class="product__price">
            <h2>{{ formatCurrency(activeVariation?.price || 0) }}</h2>
          </div>

          <div class="product__size d-flex" style="align-items: center">
            <div class="title" style="font-size: 16px; margin-right: 10px">
              Size: {{ activeVariation?.size || 0 }}
            </div>
          </div>

          <div class="product__size d-flex" style="align-items: center">
            <div class="title" style="font-size: 16px; margin-right: 10px">
              Chất liệu: {{ activeVariation?.material || 0 }}
            </div>
          </div>

          <div class="product__size d-flex" style="align-items: center">
            <div class="title" style="font-size: 16px; margin-right: 10px">
              Màu sắc:
            </div>
            <div
                v-if="activeVariation?.color"
                :style="{ backgroundColor: activeVariation.color, width: '20px', height: '20px', borderRadius: '50%', border: '1px solid #ccc' }"
                title="Màu sắc"
            ></div>
            <div v-else>
              Không có màu
            </div>
          </div>

          <div class="product__size d-flex" style="align-items: center">
            <div class="title" style="font-size: 16px; margin-right: 10px">
              Mô tả: {{ activeVariation?.description || 0 }}
            </div>
          </div>

          <div class="product__size d-flex" style="align-items: center">
            <div class="title" style="font-size: 16px; margin-right: 10px">
              Đã bán: {{ activeVariation?.sold || 0 }}
            </div>
          </div>

          <div class="product__size d-flex" style="align-items: center">
            <div class="title" style="font-size: 16px; margin-right: 10px">
              Số lượng còn lại: {{ activeVariation?.quantity || 0 }}
            </div>
          </div>

          <!-- Biến thể sản phẩm đơn giản -->
          <div class="product__variations mt-3">
            <h3>Biến thể</h3>
            <div
                class="variation-item"
                v-for="v in selectedProduct.variations"
                :key="v.id"
                @click="selectVariation(v)"
                :style="{
                border: activeVariation?.id === v.id ? '2px solid #007bff' : '1px solid #ccc',
                padding: '8px',
                margin: '5px',
                borderRadius: '6px',
                cursor: 'pointer',
                display: 'inline-block',
                textAlign: 'center',
                width: '120px',
                height: '120px'
              }"
            >
              <!-- Ảnh biến thể: nếu có, hiển thị, còn không thì mặc định -->
              <img
                  v-if="v.images && v.images.cd_Images"
                  :src="`http://localhost:8080/upload/images/${v.images.cd_Images}`"
                  alt="Hình ảnh biến thể"
                  width="60"
              />
              <img
                  v-else
                  src="/img/default.jpg"
                  alt="Hình ảnh mặc định"
                  width="60"
              />
              <div style="font-weight: bold; margin-top: 4px;">{{ v.name }}</div>
            </div>
          </div>


          <div class="product__wrap">
            <div class="product__amount">
              <label for="">Số lượng: </label>
              <input type="button" value="-" class="control" @click="changeQuantity(-1)"/>
              <input type="text" v-model="quantity" class="text-input"/>
              <input type="button" value="+" class="control" @click="changeQuantity(1)"/>
            </div>
            <button class="btn-view-details" style="color: #0e2a47" @click="addToCart()"
                    :disabled="!activeVariation?.status">
              Thêm vào giỏ
            </button>
          </div>

          <div class="product__shopnow">
            <button class="shopnow" style="color: white" :disabled="!activeVariation?.status">
              Mua ngay
            </button>
            <span class="home-product-item__like home-product-item__like--liked">
              <i class="home-product-item__like-icon-empty far fa-heart" style="font-size: 24px; margin-top: 7px"></i>
              <i class="home-product-item__like-icon-fill fas fa-heart" style="font-size: 24px; margin-top: 7px"></i>
            </span>
          </div>
        </div>
      </div>
    </div>

    <div v-else>
      <p>Loading product details...</p>
    </div>
    <div class="product__relateto">
      <div class="container">
        <h3 class="product__relateto-heading">Sản phẩm liên quan</h3>
        <div class="row">
          <div class="col-lg-3 col-md-6 col-sm-12 mb-20"
               v-for="(v, index) in bestSellers"
               :key="index"
               style="margin-top: 20px">
            <a @click="openDetail(v.productID.id)" class="product__new-item">
              <div class="card" style="width: 100%">
                <div>
                  <img class="card-img-top"
                       :src="`http://localhost:8080/upload/images/${v.images.cd_Images}`"
                       :alt="v.name">

                </div>
                <div class="card-body">
                  <h5 class="card-title custom__name-product">
                     {{ v.name }}
                  </h5>
                  <div class="product__price">
                      <p class="card-text brand-color">{{ v.productID.brandID.name }}</p>
                  </div>
                  <div class="product__price">
                    <p class="card-text price-color product__price-new">{{ formatCurrency(v.price) }} VND</p>
                  </div>
                  <div class="status-product">
                    Trạng thái:
                    <b v-if="v.status" class="text-success">Còn hàng</b>
                    <b v-else class="text-danger">Hết hàng</b>
                  </div>
                  <div class="home-product-item__action">
                    <span class="home-product-item__like home-product-item__like--liked">
                      <i class="home-product-item__like-icon-empty far fa-heart"></i>
                      <i class="home-product-item__like-icon-fill fas fa-heart"></i>
                    </span>
                    <div class="home-product-item__rating">
                      <i class="home-product-item__star--gold fas fa-star"></i>
                      <i class="home-product-item__star--gold fas fa-star"></i>
                      <i class="home-product-item__star--gold fas fa-star"></i>
                      <i class="home-product-item__star--gold fas fa-star"></i>
                      <i class="fas fa-star"></i>
                    </div>
                    <span class="home-product-item__sold">{{ v.sold }} đã bán</span>
                  </div>

                </div>
              </div>
            </a>
          </div>
        </div>
        <div class="seemore">
          <a href="/product">Xem thêm</a>
        </div>
      </div>
    </div>
  </div>
  <footer class="footer">
    <div class="container">
      <div class="row">
        <div class="col-md-4">
          <h5>Thông tin liên hệ</h5>
          <p>Địa chỉ: 123 Đường ABC, Thành phố XYZ</p>
          <p>Điện thoại: (012) 345-6789</p>
          <p>Email: contact@example.com</p>
        </div>
        <div class="col-md-4">
          <h5>Liên kết nhanh</h5>
          <ul>
            <li><a href="/about">Giới thiệu</a></li>
            <li><a href="/products">Sản phẩm</a></li>
            <li><a href="/contact">Liên hệ</a></li>
            <li><a href="/policy">Chính sách</a></li>
          </ul>
        </div>
        <div class="col-md-4">
          <h5>Mạng xã hội</h5>
          <ul class="social-media">
            <li>
              <a href="https://facebook.com" target="_blank">Facebook</a>
            </li>
            <li><a href="https://twitter.com" target="_blank">Twitter</a></li>
            <li>
              <a href="https://instagram.com" target="_blank">Instagram</a>
            </li>
          </ul>
        </div>
      </div>
      <div class="text-center">
        <p>&copy; 2023 Công ty ABC. Bảo lưu mọi quyền.</p>
      </div>
    </div>
  </footer>
</template>

<script>
import axios from "axios";
import { ref, onMounted } from "vue";
import Cookies from "js-cookie";
import { useRouter, useRoute } from "vue-router";

export default {
  setup() {
    const router = useRouter();
    const route = useRoute(); // Import and use the route object
    const selectedProduct = ref(null);
    const selectedImage = ref("");
    const quantity = ref(1);
    const colors = ref(["Red", "Blue", "Green"]);
    const sizes = ref([]);
    const selectedColor = ref(colors.value[0]);
    const selectedSize = ref(null);
    const variations = ref(null);
    const activeVariation = ref({});

    const getProductDetail = async () => {
      const id = route.params.id; // Get the product ID from the route parameter
      console.log("ID sản phẩm từ URL:", id);
      const api = `http://localhost:8080/MiniatureCrafts/product/findByID/${id}`;

      try {
        console.log(api)
        const response = await axios.get(api);
        selectedProduct.value = response.data;
        console.log("Product data:", selectedProduct.value);

        // Thiết lập ảnh chính mặc định
        const defaultImage = selectedProduct.value.images.find((img) => img.set_Default);
        selectedImage.value = defaultImage
            ? defaultImage.cd_Images
            : selectedProduct.value.images[0]?.cd_Images;

        // ✅ TỰ ĐỘNG CHỌN BIẾN THỂ ĐẦU TIÊN
        if (selectedProduct.value.variations && selectedProduct.value.variations.length > 0) {
          selectVariation(selectedProduct.value.variations[0]);
        }

      } catch (error) {
        console.error("Lỗi khi lấy chi tiết sản phẩm:", error);
      }
    };

    function selectVariation(variation) {
      activeVariation.value = variation;
      console.log("Đã chọn biến thể:", activeVariation.value.id);
      if (variation.images && variation.images.cd_Images) {
        selectedImage.value = variation.images.cd_Images;
      } else {
        // Nếu không có, dùng ảnh mặc định của sản phẩm
        const defaultImage = selectedProduct.value.images.find((img) => img.set_Default);
        selectedImage.value = defaultImage
            ? defaultImage.cd_Images
            : selectedProduct.value.images[0]?.cd_Images;
      }

      console.log("Đã chọn biến thể:", activeVariation.value);
    }

    const changeImg = (imageName) => {
      selectedImage.value = imageName;
    };

    const changeQuantity = (value) => {
      const newQuantity = quantity.value + value;
      if (newQuantity > 0) {
        quantity.value = newQuantity;
      }
    };

    const addToCart = async () => {
      const customerCookie = Cookies.get("customers");

      if (!customerCookie) {
        alert("Vui lòng đăng nhập!");
        window.location.href = '/login';
        return;
      }

      const customer = JSON.parse(customerCookie);

      const cartItem = {
        customer_id: {
          id: customer.id,
        },
        variation_id: {
          id: activeVariation.value.id,
        },
        quantity: quantity.value,
      };

      try {
        const response = await axios.post("http://localhost:8080/api/v1/cart/addtocart", cartItem);
        if (response.status === 200) {
          alert("Thêm sản phẩm vào giỏ hàng thành công!");
        }
      } catch (error) {
        alert("Hiện đã hết hàng, không thể thêm sản phẩm vào giỏ hàng!");
      }
    };

    const openDetail = (id) => {
      sessionStorage.setItem("idvariation", id);
      router.push({ path: `/product/${id}` }).then(() => {
        location.reload();
      });
    };

    const processData = (data) => {
      return data.map((item) => {
        item.productID.defaultImage =
            item.productID.imagesDTOS?.length > 0
                ? item.productID.imagesDTOS[0].cd_Images
                : "default.png";
        item.sold = item.sold || 0;
        return item;
      });
    };

    const getProducts = async () => {
      try {
        const response = await axios.get(
            "http://localhost:8080/MiniatureCrafts/bestseller?page=0&size=4"
        );
        variations.value = processData(response.data.content || []);
        console.log("Dữ liệu sản phẩm:", variations.value);
      } catch (error) {
        console.error("Lỗi khi lấy danh sách sản phẩm:", error);
      }
    };

    function formatCurrency(value) {
      return value.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ".") + " ₫";
    }

    onMounted(() => {
      getProductDetail();
      getProducts();
    });

    return {
      selectVariation,
      openDetail,
      bestSellers: variations,
      formatCurrency,
      selectedProduct,
      selectedImage,
      quantity,
      colors,
      sizes,
      selectedColor,
      selectedSize,
      changeImg,
      changeQuantity,
      getProductDetail,
      addToCart,
      activeVariation,
    };
  },
};
</script>

<style>
.container {
   width: 100%;
}
</style>
