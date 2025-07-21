<template>
  <div class="product">
    <div class="container">
      <div class="row">
        <div class="col-lg-3 col-12 hidden-xs hidden-sm">
          <div class="product__filter">
            <div class="product__filter-price">
              <h4 class="product__filter-heading">Khoảng giá <i class="fi-rs-minus"
                                                                @click="toggleFilter(1, 'khoanggia')" id="minus-1"></i>
                <i class="fi-rs-plus hidden" id="plus-1" @click="toggleFilter(1, 'khoanggia')"></i></h4>
              <ul id="khoanggia" class="product__filter-ckeckbox">
                <li class="product__filter-item" v-for="(priceRange, index) in priceRanges" :key="index">
                  <label class="form-check-label">
                    <input
                        type="radio"
                        class="form-check-input checkGia"
                        :id="'kg' + (index + 1)"
                        name="optradio"
                        :value="priceRange.value"
                        @click="filterByPrice(priceRange.value)">
                    <span>{{ priceRange.label }}</span>
                  </label>
                </li>
              </ul>
            </div>
            <div class="product__filter-trademark">
              <h4 class="product__filter-heading">
                Danh mục
                <i class="fi-rs-minus" @click="toggleFilter(2, 'danhmuc')" id="minus-2"></i>
                <i class="fi-rs-plus hidden" @click="toggleFilter(2, 'danhmuc')"></i>
              </h4>
              <ul id="danhmuc" class="product__filter-ckeckbox">
                <li class="product__filter-item" v-for="category in categories" :key="category.id">
                  <label class="form-check-label" :for="'cat' + category.id">
                    <input type="radio"
                           :id="'cat-category-' + category.id"
                           :name="'filterByCategory'"
                           v-model="selectedCategoryId"
                           :value="category.id"
                           @change="filterByCategory(category.id)"/>
                    <span>{{ category.name }}</span>
                  </label>
                </li>
              </ul>
            </div>
            <div class="product__filter-trademark">
              <h4 class="product__filter-heading">
                Thương hiệu
                <i class="fi-rs-minus" @click="toggleFilter(2, 'thuonghieu')" id="minus-2"></i>
                <i class="fi-rs-plus hidden" @click="toggleFilter(2, 'thuonghieu')"></i>
              </h4>
              <ul id="thuonghieu" class="product__filter-ckeckbox">
                <li class="product__filter-item" v-for="brand in brands" :key="brand.id">
                  <label class="form-check-label" :for="'cat' + brand.id">
                    <input type="radio"
                           :id="'cat-brand-' + brand.id"
                           :name="'filterByBrand'"
                           v-model="selectedBrandsID"
                           :value="brand.id"
                           @change="filterByBrands(brand.id)"/>

                    <span>{{ brand.name }}</span>
                  </label>
                </li>
              </ul>
            </div>
          </div>
        </div>
      <div class="col-lg-9 col-12">

        <div class="row row-product" id="products">
            <div class="col-lg-4 col-md-6 col-12 mb-20" v-for="v in variations" :key="v.id"
                 style="margin-bottom: 20px">
              <a @click="openDetail(v.productID.id)" class="product__new-item">
                <div class="card" style="width: 100%">
                  <div>
                    <img class="card-img-top" :src="`http://localhost:8080/upload/images/${v.images.cd_Images}`"
                         :alt="v.name">
                  </div>
                  <div class="card-body">
                    <h5 class="card-title custom__name-product">
                      {{ v.name }}
                    </h5>
                    <div class="product__price">
                      <p class="card-text brand-color">Thương hiệu: {{ v.productID.brandID.name }}</p>
                    </div>
                    <div class="product__price">
                      <p class="card-text brand-color">Danh mục: {{ v.productID.categoryID.name }}</p>
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
                        <i v-for="star in 5" :key="star"
                           :class="star <= v.rating ? 'home-product-item__star--gold fas fa-star' : 'fas fa-star'"></i>
                      </div>
                      <span class="home-product-item__sold">{{ v.sold }} đã bán</span>
                    </div>
                    <div class="sale-off" v-if="v.discount">
                      <span class="sale-off-percent">{{ v.discount }}%</span>
                      <span class="sale-off-label">GIẢM</span>
                    </div>
                  </div>
                </div>
              </a>
            </div>
        </div>
        <div class="loadmore">
          <div class="loadmore-container">
            <button
                @click="prevPage"
                class="loadmore-btn"
                :disabled="currentPage === 0"> PREVIEW
            </button>
            <h1>Trang {{ currentPage + 1 }} / {{ totalPages }}</h1>
            <button
                @click="nextPage"
                class="loadmore-btn"
                :disabled="currentPage === totalPages - 1">
              NEXT
            </button>
          </div>
        </div>

      </div>
    </div>
  </div>
  </div>
</template>

<script>
import { ref, onMounted, watch } from "vue";
import axios from "axios";
import { useRoute } from "vue-router";

export default {
  setup() {
    const route = useRoute();
    const selectedProduct = ref(null);
    const selectedImage = ref("");
    const currentPage = ref(0);
    const pageSize = ref(9);
    const totalPages = ref(0);
    const categories = ref([]);
    const brands = ref([]);
    const variations = ref([]);
    const selectedCategoryId = ref(null);
    const selectedBrandsID = ref(null);
    const priceRanges = ref([
      { value: "0-1000000", label: "Dưới 1,000,000đ" },
      { value: "1000000-2000000", label: "1,000,000đ->2,000,000đ" },
      { value: "2000000-3000000", label: "2,000,000đ->3,000,000đ" },
      { value: "3000000-4000000", label: "3,000,000đ->4,000,000đ" },
      { value: "4000000-100000000", label: "Trên 4,000,000đ" },
    ]);
    const user = ref(null);
    const searchedVariations = ref([]);
    const selectedPriceRange = ref(null);

    watch(() => route.query.search, async (newSearch) => {
      if (newSearch) {
        await getProducts(newSearch);
      }
    });
    function openDetail(id) {
      window.location.href = '/product/' + id
    }

    const getProducts = async (searchKeyword) => {
      try {
        const response = await axios.get(
            `http://localhost:8080/MiniatureCrafts/result/${searchKeyword}?page=${currentPage.value}&size=${pageSize.value}`
        );
        searchedVariations.value = response.data.content || [];
        variations.value = searchedVariations.value;
        totalPages.value = response.data.page?.totalPages || 1;
      } catch (error) {
        console.error("Lỗi khi lấy danh sách sản phẩm:", error);
      }
    };

    const filterByCategory = (id) => {
      selectedCategoryId.value = id;
      applyFilters();
    };

    const filterByBrands = (id) => {
      selectedBrandsID.value = id;
      applyFilters();
    };

    const filterByPrice = (priceRange) => {
      selectedPriceRange.value = priceRange;
      applyFilters();
    };

    const applyFilters = () => {
      let filteredVariations = searchedVariations.value;

      if (selectedCategoryId.value) {
        filteredVariations = filteredVariations.filter(
            (product) => product.productID.categoryID.id === selectedCategoryId.value
        );
      }

      if (selectedBrandsID.value) {
        filteredVariations = filteredVariations.filter(
            (product) => product.productID.brandID.id === selectedBrandsID.value
        );
      }

      if (selectedPriceRange.value) {
        const [minPrice, maxPrice] = selectedPriceRange.value
            .split("-")
            .map(Number);
        filteredVariations = filteredVariations.filter((product) => {

          return product.price >= minPrice && product.price <= maxPrice;
        });
      }

      variations.value = filteredVariations;
      totalPages.value = Math.ceil(filteredVariations.length / pageSize.value);
      currentPage.value = 0;
    };

    const getCategories = async () => {
      try {
        const response = await axios.get(
            "http://localhost:8080/MiniatureCrafts/categories"
        );
        categories.value = response.data || [];
      } catch (error) {
        console.error("Lỗi khi lấy danh mục sản phẩm:", error);
      }
    };

    const getBrands = async () => {
      try {
        const response = await axios.get(
            "http://localhost:8080/MiniatureCrafts/brands"
        );
        brands.value = response.data || [];
      } catch (error) {
        console.error("Lỗi khi lấy danh sách thương hiệu:", error);
      }
    };

    const nextPage = async () => {
      if (currentPage.value < totalPages.value - 1) {
        currentPage.value++;
        await getProducts(route.query.search || "");
      }
    };

    const prevPage = async () => {
      if (currentPage.value > 0) {
        currentPage.value--;
        await getProducts(route.query.search || "");
      }
    };
    function formatCurrency(value) {
      const number = Number(value);
      if (isNaN(number)) return '0 ₫';
      return number.toLocaleString('vi-VN') + ' ₫';
    }
    onMounted(async () => {
      const searchKeyword = route.query.search;
      if (searchKeyword) {
        await getProducts(searchKeyword);
      }
      await getCategories();
      await getBrands();
    });

    return {
      openDetail,
      formatCurrency,
      selectedProduct,
      selectedImage,
      currentPage,
      pageSize,
      totalPages,
      categories,
      brands,
      variations,
      selectedCategoryId,
      selectedBrandsID,
      priceRanges,
      user,
      filterByCategory,
      filterByBrands,
      filterByPrice,
      nextPage,
      prevPage,
    };
  },
};
</script>


<style scoped>
.product {
  margin: 30px 0;
}

.product__filter-heading {
  display: flex;
  justify-content: space-between;
  font-size: 16px;
  font-weight: 500;
}

.product__filter-ckeckbox {
  padding: 0;
  margin-left: 10px;
  font-size: 16px;
  list-style: none;
}

.form-check-input {
  width: 18px;
  height: 18px;
  padding-right: 10px;
}

.fi-rs-minus {
  font-size: 16px;
  font-weight: 500;
  color: var(--black-color);
  cursor: pointer;
}

.fi-rs-plus {
  font-size: 16px;
  font-weight: 500;
  color: var(--black-color);
  cursor: pointer;
}

.product__filter-item {
  padding: 10px 0;
}

.product__filter-item span {
  margin-left: 20px;
  color: var(--text-color);
  text-transform: uppercase;
}

.sort-wrap {
  display: flex;
  justify-content: space-between;
  margin-bottom: 20px;
}

.coll-name {
  font-size: 4em;
  margin: 0;
  margin-top: -10px;
}

.sortby {
  display: flex;
  float: right;
}

.sortby label {
  margin-right: 10px;
  font-size: 16px;
}


.dropdown-toggle {
  height: 30px;
  font-size: 14px;
  border-radius: unset;
  background-color: unset;
  color: var(--black-color);
  border: 1px solid var(--black-color);
}

.dropdown-item {
  font-size: 14px;
}

.row-product {
  margin-bottom: 20px;
}

.loadmore-container {
  display: flex; /* Sử dụng Flexbox */
  justify-content: center; /* Căn giữa các nút */
  gap: 20px; /* Khoảng cách giữa các nút */
}

.loadmore {
  text-align: center;
  margin: 40px 0;
}

.loadmore-btn {
  padding: 10px 40px;
  border: 2px solid var(--black-color);
  color: var(--text-color);
  font-size: 16px;
  text-transform: uppercase;
  position: relative;
  overflow: hidden;
  transition: 1s all ease;
}

.loadmore-btn::before {
  background-color: var(--black-color);
  content: "";
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  z-index: -1;
  transition: all 0.6s ease;
  width: 0;
  height: 100%;
}

.loadmore-btn:hover::before {
  width: 100%;
}

.loadmore-btn:hover {
  text-decoration: none;
  color: var(--white-color);
}

.filter-mobile {
  padding: 15px 15px;
  height: 100%;
  overflow-y: auto;
  width: 270px;
  position: fixed;
  background-color: #fff;
  z-index: 999999 !important;
  top: 0 !important;
  right: 0 !important;
  transition: transform 0.8s;
  transform: translateX(270px);
}

.xyz {
  transform: translateX(0);
}

.overlay2 {
  background-color: rgba(0, 0, 0, 0.5);
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  z-index: 9999;
  cursor: pointer;
}
</style>