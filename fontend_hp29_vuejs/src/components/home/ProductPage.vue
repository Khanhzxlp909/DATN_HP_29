<template>
  <div class="product">
    <div class="container">
      <div class="row">
        <div class="col-lg-3 col-12 hidden-xs hidden-sm">
          <div class="product__filter">
            <div class="product__filter-price">
              <h4 class="product__filter-heading">
                Khoảng giá
                <i class="fi-rs-minus" @click="toggleFilter(1, 'khoanggia')" id="minus-1"></i>
                <i class="fi-rs-plus hidden" id="plus-1" @click="toggleFilter(1, 'khoanggia')"></i>
              </h4>
              <ul id="khoanggia" class="product__filter-ckeckbox">
                <li class="product__filter-item" v-for="(priceRange, index) in priceRanges" :key="index">
                  <label class="form-check-label">
                    <input type="radio" class="form-check-input checkGia" :id="'kg' + (index + 1)" name="optradio"
                           :value="priceRange.value" @change="filterByPrice(priceRange.value)"/>
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
                           @change="filterByCategory(category.id)" />
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
                           @change="filterByBrands(brand.id)" />

                    <span>{{ brand.name }}</span>
                  </label>
                </li>
              </ul>
            </div>
          </div>
        </div>
        <div class="col-lg-9 col-12">
          <div class="sort-wrap row">
            <div class="sort-left col-12 col-lg-6">
              <h1 class="coll-name">Tất cả sản phẩm</h1>
            </div>
          </div>
          <div class="row row-product" id="products">
              <div class="col-lg-4 col-md-6 col-12 mb-20" v-for="v in variations" :key="v.id"
                   style="margin-bottom: 20px">
                <a @click="openDetail(v.id)" class="product__new-item">
                  <div class="card" style="width: 100%">
                    <div>
                      <img
                          class="card-img-top"
                          :src="`http://localhost:8080/upload/images/${v.defaultImage}`"
                          :alt="v.name"
                      />
                    </div>
                    <div class="card-body">
                      <h5 class="card-title custom__name-product">{{ v.name }}</h5>
                      <p class="card-text brand-color">Hãng: {{ v.brandID.name }}</p>
                      <p class="card-text brand-color">Danh mục: {{ v.categoryID.name }}</p>

                      <p class="card-text price-color product__price-new">
                        {{ formatCurrency(getMinPrice(v.variations)) }}
                      </p>

                      <div class="status-product">
                        Trạng thái:
                        <b class="text-success">Còn hàng</b>
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
                      <div class="sale-off" v-if="v.discount">
                        <span class="sale-off-percent">{{ v.discount }}%</span>
                        <span class="sale-off-label">GIẢM</span>
                      </div>
                    </div>
                  </div>
                </a>
              </div>
          </div>
          <div class="pagination">
            <button :disabled="currentPage === 1" @click="changePage(currentPage - 1)" class="btn btn-primary btn-sm">
              Trước
            </button>
            <button v-for="page in totalPages" :key="page" @click="changePage(page)"
                    :class="['btn', 'btn-sm', currentPage === page ? 'btn-primary' : 'btn-secondary']">{{ page }}
            </button>
            <button :disabled="currentPage === totalPages" @click="changePage(currentPage + 1)"
                    class="btn btn-primary btn-sm">Tiếp
            </button>
          </div>
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
            <li><a href="https://facebook.com" target="_blank">Facebook</a></li>
            <li><a href="https://twitter.com" target="_blank">Twitter</a></li>
            <li><a href="https://instagram.com" target="_blank">Instagram</a></li>
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
import {ref, onMounted} from 'vue'
import axios from 'axios'

export default {
  setup() {
    const currentPage = ref(1)
    const pageSize = ref(9)
    const totalPages = ref(0)
    const categories = ref([])
    const brands = ref([])
    const selectedPriceRange = ref(null)
    const selectedCategoryId = ref(null)
    const selectedBrandsID = ref(null)
    const priceRanges = ref([
      {value: '0-1000000', label: 'Dưới 1,000,000đ'},
      {value: '1000000-2000000', label: '1,000,000đ->2,000,000đ'},
      {value: '2000000-3000000', label: '2,000,000đ->3,000,000đ'},
      {value: '3000000-4000000', label: '3,000,000đ->4,000,000đ'},
      {value: '4000000-100000000', label: 'Trên 4,000,000đ'}
    ])
    const variations = ref([])

    function getMinPrice(variations) {
      if (!Array.isArray(variations) || variations.length === 0) return 0;
      return variations.reduce((min, v) => v.price < min ? v.price : min, variations[0].price);
    }

    // Process fetched data and assign defaultImage on each variation
    function processData(data) {
      return data.map(item => {
        // assign default image logic
        const imgs = item.images || []
        let imgObj = imgs.find(i => i.set_Default)
        if (!imgObj && imgs.length) imgObj = [...imgs].sort((a, b) => a.id - b.id)[0]
        item.defaultImage = imgObj ? imgObj.cd_Images : 'default.png'
        item.sold = item.sold || 0
        return item
      })
    }

    function formatCurrency(value) {
      const number = Number(value);
      if (isNaN(number)) return '0 ₫';
      return number.toLocaleString('vi-VN') + ' ₫';
    }

    async function getProducts(page = 0) {
      try {
        const {data} = await axios.get('http://localhost:8080/MiniatureCrafts/fetch_products', {
          params: {
            page,
            size: pageSize.value
          }
        })
        variations.value = processData(data.content || [])
        console.log('Fetched products:', variations.value)
        totalPages.value = data.page?.totalPages || 1
        currentPage.value = page + 1
      } catch (error) {
        console.error('Error fetching products:', error)
      }
    }

    function filterByPrice(range) {
      selectedPriceRange.value = range;
      const [min, max] = range.split('-').map(Number);
      variations.value = variations.value.filter((v) => {
        const price = getMinPrice(v.variations);
        return price >= min && price <= max;
      });
    }

    async function filterByCategory(id, page = currentPage.value - 1) {
      selectedCategoryId.value = id
      try {
        const {data} = await axios.get(`http://localhost:8080/MiniatureCrafts/category/${id}`, {
          params: {
            page,
            size: pageSize.value
          }
        })
        variations.value = processData(data.content || [])
        totalPages.value = data.page?.totalPages || 1
      } catch (error) {
        console.error('Error filtering by category:', error)
      }
    }

    async function filterByBrands(id, page = currentPage.value - 1) {
      selectedBrandsID.value = id
      try {
        const {data} = await axios.get(`http://localhost:8080/MiniatureCrafts/brands/${id}`, {
          params: {
            page,
            size: pageSize.value
          }
        })
        variations.value = processData(data.content || [])
        totalPages.value = data.page?.totalPages || 1
      } catch (error) {
        console.error('Error filtering by category:', error)
      }
    }

    function changePage(page) {
      if (page < 1 || page > totalPages.value) return
      if (selectedCategoryId.value) return filterByCategory(selectedCategoryId.value, page - 1)
      if (selectedBrandsID.value) return filterByBrands(selectedBrandsID.value, page - 1)
      if (selectedPriceRange.value) return filterByPrice(selectedPriceRange.value, page - 1)
      getProducts(page - 1)
    }

    function openDetail(id) {
      window.location.href = '/product/' + id
    }

    async function getCategories() {
      try {
        const {data} = await axios.get('http://localhost:8080/MiniatureCrafts/categories')
        categories.value = data
      } catch (error) {
        console.error('Error fetching categories:', error)
      }
    }
    async function getBrands() {
      try {
        const {data} = await axios.get('http://localhost:8080/MiniatureCrafts/brands')
        brands.value = data
      } catch (error) {
        console.error('Error fetching brands:', error)
      }
    }

    onMounted(async () => {
      await getCategories()
      await getBrands()
      await getProducts()
    })

    return {
      getMinPrice,
      formatCurrency,
      currentPage,
      totalPages,
      categories,
      brands,
      priceRanges,
      variations,
      selectedPriceRange,
      selectedCategoryId,
      selectedBrandsID,
      changePage,
      filterByPrice,
      filterByCategory,
      filterByBrands,
      openDetail
    }
  }
}
</script>

<style scoped>
.container {
   width: 100%;
}
.pagination {
  display: flex;
  justify-content: center;
  margin-top: 20px;
}

.pagination button {
  background-color: #f0f0f0;
  color: #333;
  border: 1px solid #ccc;
  border-radius: 5px;
  padding: 5px 10px;
  margin: 0 5px;
  font-size: 14px;
  cursor: pointer;
  transition: all 0.3s ease;
}

.pagination button:hover {
  background-color: #007bff;
  color: #fff;
}

.pagination button:disabled {
  background-color: #ddd;
  color: #aaa;
  cursor: not-allowed;
}

.pagination .btn-primary {
  background-color: #007bff;
  color: #fff;
  border: none;
}

.pagination .btn-primary:hover {
  background-color: #0056b3;
}

.pagination .btn-secondary {
  background-color: #f0f0f0;
  color: #333;
  border: 1px solid #ccc;
}

.pagination .btn-secondary:hover {
  background-color: #e0e0e0;
}
</style>
