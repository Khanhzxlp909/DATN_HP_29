<template>
  <div id="app" class="app sidebar-mini rtl">
    <header class="app-header">
      <a class="app-sidebar__toggle" href="#" data-toggle="sidebar" aria-label="Hide Sidebar"></a>
      <ul class="app-nav">
        <li>
          <a class="app-nav__item" href="/order">
            <i class="bx bx-log-out bx-rotate-180"></i>
          </a>
        </li>
      </ul>
    </header>

    <main class="app-content">
      <div class="app-title">
        <ul class="app-breadcrumb breadcrumb side">
          <li class="breadcrumb-item active">
            <a href="#"><b>Thêm mới sản phẩm</b></a>
          </li>
        </ul>
        <div id="clock">{{ currentTime }}</div>
      </div>
      <div class="row">
        <div class="col-md-12">
          <div class="tile">
            <div class="tile-body">
              <form @submit.prevent="isEditing ? updateVariation() : addVariation()" class="row">

                <div class="form-group col-md-3">
                  <label for="productName">Tên sản phẩm:</label>
                  <select id="productName" v-model="variation.id" class="form-control" @change="setCategoryAndBrands">
                    <option v-for="item in products" :key="item.id" :value="item.id">
                      {{ item.name }}
                    </option>
                  </select>
                </div>
                <div class="form-group col-md-3">
                  <label>Tên biến thể</label>
                  <input type="text" id="productColor" v-model="variation.name" class="form-control">
                </div>

                <div class="form-group col-md-3">
                  <label>Màu sắc</label>
                  <input type="color" id="productColor" v-model="variation.color" class="form-control">
                </div>
                <div class="form-group col-md-3">
                  <label for="productSize">Kích thước (? x ? x ? cm):</label>
                  <input type="text" id="productSize" v-model="variation.size" class="form-control"/>
                </div>
                <div class="form-group col-md-3">
                  <label for="productMaterial">Chất liệu:</label>
                  <input type="text" id="productMaterial" v-model="variation.material" class="form-control"/>
                </div>
                <div class="form-group col-md-3">
                  <label for="productCategory">Danh mục:</label>
                  <input type="text" id="productCategory" v-model="variation.categoryName" class="form-control"
                         readonly/>
                </div>
                <div class="form-group col-md-3">
                  <label for="productBrand">Thương hiệu:</label>
                  <input type="text" id="productBrand" v-model="variation.brandName" class="form-control" readonly/>
                </div>
                <div class="form-group col-md-3">
                  <label for="productPrice">Giá:</label>
                  <input type="number" id="productPrice" v-model.number="variation.price" class="form-control"
                         style="width: max-content"/>
                </div>
                <div class="form-group col-md-3">
                  <label for="productQuantity">Số lượng:</label>
                  <input type="number" id="productQuantity" v-model="variation.quantity" class="form-control"
                         style="width: max-content"/>
                </div>

                <div class="form-group col-md-3">
                  <label for="description" class="form-label fw-semibold">Mô tả sản phẩm</label>
                  <textarea
                      class="form-control"
                      id="description"
                      rows="5"
                      maxlength="300"
                      v-model="variation.description"
                      placeholder="Nhập mô tả chi tiết về sản phẩm, chất liệu, công dụng, v.v..."
                  ></textarea>
                </div>

                <!-- Upload Ảnh -->
                <div class="form-group col-md-3">
                  <h5>Upload ảnh đại diện</h5>
                  <input type="file" @change="handleSingleFileUpload" class="form-control mb-2"/>
                  <div v-if="singleImage.preview">
                    <img :src="singleImage.preview" class="img-thumbnail mt-2" style="max-width: 100px"/>
                  </div>
                </div>

                <div class="form-group col-md-4">
                  <button type="submit" class="btn btn-primary" style="font-size: large">{{
                      isEditing ? 'Cập nhật biến thể' : 'Thêm biến thể'
                    }}
                  </button>
                </div>
              </form>
              <div class="row element-button">
                <div class="col-sm-2">
                  <button class="btn btn-add btn-sm" @click="navigateToAddCategory" title="Thêm">
                    <i class="fas fa-plus"></i> Tạo mới danh mục
                  </button>
                </div>
                <div class="col-sm-2">
                  <button class="btn btn-add btn-sm" @click="navigateToAddBrands" title="Thêm">
                    <i class="fas fa-plus"></i> Tạo mới thương hiệu
                  </button>
                </div>
                <div class="col-sm-2">
                  <button class="btn btn-add btn-sm" @click="navigateToAddProduct" title="Thêm">
                    <i class="fas fa-plus"></i> Tạo mới sản phẩm
                  </button>
                </div>
              </div>
              <table class="table table-hover table-bordered" id="sampleTable">
                <thead>
                <tr>
                  <th width="10">
                    <input type="checkbox" id="all" @click="toggleAllCheckboxes"/>
                  </th>
                  <th>Mã sản phẩm</th>
                  <th>Tên sản phẩm</th>
                  <th>Ảnh</th>
                  <th>Số lượng</th>
                  <th>Đã bán</th>
                  <th>Màu sắc</th>
                  <th>Kích thước</th>
                  <th>Tình trạng</th>
                  <th>Giá tiền</th>
                  <th>Danh mục</th>
                  <th>Chức năng</th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="variation in variations" :key="variation.id">
                  <td width="10">
                    <input type="checkbox" v-model="variation.selected"/>
                  </td>
                  <td>{{ variation.id }}</td>
                  <td>{{ variation.name }}</td>
                  <td>
                    <img :src="getImagesUrl(variation.images)" alt="" width="70px;"/>
                  </td>
                  <td>{{ variation.quantity }}</td>
                  <td>{{ variation.sold }}</td>
                  <td>
                    <div
                        :style="{ backgroundColor: variation.color, width: '30px', height: '30px', borderRadius: '4px' }">

                    </div>
                  </td>
                  <td>{{ variation.size }}</td>
                  <td>
                    <span :class="{ 'badge bg-success': variation.status, 'badge bg-danger': !variation.status }">
                      {{ variation.status ? 'Còn hàng' : 'Hết hàng' }}
                    </span>
                  </td>
                  <td>{{ formatCurrency(variation.price) }}</td>
                  <td>{{ variation.productID.categoryID.name }}</td>
                  <td>
                    <button class="btn btn-edit" @click="editVariation(variation, variation.id)">Sửa</button>
                    <button class="btn btn-delete" @click="confirmDelete(variation)">Xóa</button>
                  </td>
                </tr>
                </tbody>
              </table>
              <div class="pagination">
                <button class="page-button" :disabled="currentPage === 0" @click="changePage(currentPage - 1)">
                  Previous
                </button>
                <span>Page {{ currentPage + 1 }} of {{ totalPages }}</span>
                <button class="page-button" :disabled="currentPage >= totalPages - 1"
                        @click="changePage(currentPage + 1)">Next
                </button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </main>
  </div>
</template>

<script>
import axios from "axios";
import Cookies from "js-cookie";

export default {

  data() {
    return {
      currentTime: "",
      currentPage: 0,
      pageSize: 10,
      totalPages: 0,
      variations: [],
      products: [],
      variationID: "",
      variation: {
        id: "",
        name: "",
        sku: "",
        price: 0,
        quantity: 0,
        color: "",
        material: "",
        size: "",
        description: "",
        weight: "",
        status: true,
        category: "",
        brand: "",
        categoryName: "",
        brandName: ""
      },
      isEditing: false,

      id_images: [],
      singleImage: {
        file: null,
        preview: ""
      },
      defaultImageIndex: 0,
      imageUrls: [],
    };
  },
  created() {

    this.updateTime();
    this.fetchVariations(this.currentPage, this.pageSize);
    this.fetchProducts();
  },
  methods: {

    // ──────────────────────────
    // 🔁 NAVIGATION
    navigateToAddCategory() {
      this.$router.push("/category");
    },
    navigateToAddBrands() {
      this.$router.push("/brands");
    },
    navigateToAddProduct() {
      this.$router.push("/product");
    },

    // ──────────────────────────
    // ✅ VALIDATION
    formatCurrency(value) {
      // Chuyển đổi giá trị thành chuỗi và định dạng với dấu phân cách hàng nghìn
      return value.toString().replace(/\B(?=(\d{3})+(?!\d))/g, '.') + ' ₫';
    },

    isEmpty(value) {
      return value == null || value.toString().trim() === "";
    },
    validateForm() {
      if (this.isEmpty(this.variation.id)) return alert("Tên sản phẩm không được để trống!");
      if (this.variation.id.length > 25) return alert("Tên sản phẩm không được quá 25 ký tự!");
      if (this.isEmpty(this.variation.category)) return alert("Danh mục không được để trống!");
      if (this.isEmpty(this.variation.price) || isNaN(this.variation.price) || this.variation.price <= 0)
        return alert("Giá sản phẩm phải là số lớn hơn 0!");
      if (this.isEmpty(this.variation.material) || this.variation.material.length > 25)
        return alert("Chất liệu không hợp lệ!");
      return true;
    },

    // ──────────────────────────
    // ✏️ FORM LOGIC

    handleSingleFileUpload(event) {
      const file = event.target.files[0];
      if (file) {
        this.singleImage.file = file;
        this.singleImage.preview = URL.createObjectURL(file);
      }
    },

    async addVariation() {
      const token = Cookies.get("token");
      if (!token) return this.$router.push("/login");

      const data = {
        name: this.variation.name,
        sku: this.variation.sku,
        price: parseFloat(this.variation.price) || 0,
        quantity: parseInt(this.variation.quantity) || 0,
        color: this.variation.color,
        material: this.variation.material,
        size: this.variation.size,
        description: this.variation.description,
        status: true,
        productID: {
          id: this.variation.id
        }
      };

      console.log("🔼 [FRONTEND] Sending data:", data);

      try {
        const variationResponse = await axios.post(
            "http://localhost:8080/admin/variation/add",
            data,
            {
              headers: {
                Authorization: `Bearer ${token}`,
                "Content-Type": "application/json"
              }
            }
        );

        console.log("✅ [FRONTEND] Add response:", variationResponse.data);

        const variationId = variationResponse.data.id;

        console.log("🆔 Variation ID:", variationId);

        if (this.singleImage.file) {
          const formData = new FormData();
          formData.append("file", this.singleImage.file);

          const uploadResponse = await axios.post(
              "http://localhost:8080/admin/variation/images/upload",
              formData,
              {
                headers: {
                  "Content-Type": "multipart/form-data"
                }
              }
          );

          const imageUrl = uploadResponse.data.urls[0];
          console.log("🖼️ Uploaded Image URL:", imageUrl);

          await axios.post(
              "http://localhost:8080/admin/variation/images/setproduct",
              [
                {
                  productID: variationId,
                  model: "Variation",
                  cd_Images: imageUrl,
                  set_Default: true
                }
              ],
              {
                headers: {
                  Authorization: `Bearer ${token}`,
                  "Content-Type": "application/json"
                }
              }
          );

          console.log("📌 Image successfully assigned to variation.");
        }
        this.fetchVariations(this.currentPage, this.pageSize);
        alert("✅ Thêm biến thể thành công!");
        this.resetForm()
      } catch (err) {
        console.error("❌ Add variation error:", err.response?.data || err);
        alert("❌ Đã xảy ra lỗi khi thêm biến thể!");
      }
    },


    async updateVariation() {
      const token = Cookies.get("authToken");
      console.log(">> Token:", token); // Kiểm tra xem có null hoặc sai định dạng không
      if (!token) return this.$router.push("/login");
      if (!this.validateForm()) return;

      try {
        // ✅ Nếu muốn xoá ảnh cũ (tùy logic), bạn có thể thêm đoạn này:
        if (this.imageUrls?.length > 0) {
          await axios.post(
              "http://localhost:8080/admin/variation/images/delete",
              this.imageUrls,
              {
                headers: {
                  Authorization: `Bearer ${token}`,
                  "Content-Type": "application/json"
                }
              }
          );
        }

        // ✅ Gửi dữ liệu cập nhật variation
        const variationData = {
          id: this.variationID,
          name: this.variation.name,
          sku: this.variation.sku,
          price: parseFloat(this.variation.price),
          quantity: parseInt(this.variation.quantity),
          color: this.variation.color,
          material: this.variation.material,
          size: this.variation.size,
          description: this.variation.description,
          status: this.variation.status,
          productID: {
            id: this.variation.id,
            categoryID: { id: this.variation.category },
            brandID: { id: this.variation.brand }
          }
        };

        await axios.post(
            "http://localhost:8080/admin/variation/update",
            variationData,
            {
              headers: {
                Authorization: `Bearer ${token}`,
                "Content-Type": "application/json"
              }
            }
        );

        // ✅ Upload ảnh nếu có
        const uploadedImages = [];
        if (this.singleImage.file) {
          const formData = new FormData();
          formData.append("file", this.singleImage.file);

          const uploadResponse = await axios.post(
              "http://localhost:8080/admin/variation/images/upload",
              formData,
              {
                headers: {
                  Authorization: `Bearer ${token}`,
                  "Content-Type": "multipart/form-data"
                }
              }
          );

          uploadedImages.push(...uploadResponse.data.urls);
        }

        // ✅ Gán ảnh mới (nếu có ảnh)
        if (uploadedImages.length > 0) {
          const imageRequests = uploadedImages.map((url, index) => ({
            productID: this.variationID,
            model: "Variation",
            cd_Images: url,
            set_Default: true  // chỉ có 1 ảnh nên luôn default
          }));

          await axios.post(
              "http://localhost:8080/admin/variation/images/setproduct",
              imageRequests,
              {
                headers: {
                  Authorization: `Bearer ${token}`,
                  "Content-Type": "application/json"
                }
              }
          );
        }

        alert("✅ Cập nhật biến thể thành công!");
        // Optional: Redirect or refresh variation list
        this.fetchVariations?.(this.currentPage, this.pageSize);

      } catch (error) {
        console.error("❌ Lỗi khi cập nhật biến thể:", error.response?.data || error);
        alert("❌ Đã xảy ra lỗi khi cập nhật biến thể!");
      }
    },


    editVariation(variation, variationID) {
      console.log(variation);
      this.variationID = variationID;
      this.variation = {
        id: variation.productID.id,
        name: variation.name,
        sku: variation.sku,
        price: variation.price,
        quantity: variation.quantity,
        color: variation.color,
        material: variation.material,
        size: variation.size,
        description: variation.description,
        status: variation.status,
        category: variation.productID.categoryID?.id || "",
        categoryName: variation.productID.categoryID?.name || "",
        brand: variation.brandID?.id || "",
        brandName: variation.brandID?.name || ""
      };

      this.isEditing = true;

      // 🔁 Gán lại ảnh đại diện (nếu có)
      if (variation.images && variation.images.cd_Images) {
        this.imageUrls = [variation.images.cd_Images]; // để xoá khi cập nhật ảnh mới
        this.singleImage.preview = `http://localhost:8080/upload/images/${variation.images.cd_Images}`;
        this.singleImage.file = null; // reset file để tránh upload lại khi không chọn mới
      } else {
        this.imageUrls = [];
        this.singleImage.preview = "";
        this.singleImage.file = null;
      }
    },

    resetForm() {
      this.variation = {
        id: "",
        name: "",
        sku: "",
        price: 0,
        quantity: 0,
        color: "",
        material: "",
        size: "",
        description: "",
        weight: "",
        status: true,
        category: "",
        brand: "",
        categoryName: "",
        brandName: ""
      };

      this.variationID = "";
      this.isEditing = false;

      this.singleImage = {
        file: null,
        preview: ""
      };

      this.imageUrls = [];
    },

    // ──────────────────────────
    // 🔗 FETCHERS
    async fetchProducts() {
      const token = Cookies.get("token");
      if (!token) return this.$router.push("/login");

      try {
        const response = await axios.get("http://localhost:8080/admin/variation/getproduct");
        this.products = response.data;
        console.log(this.products);
      } catch (error) {
        console.error("Lỗi khi fetch products:", error);
        alert("Không thể tải danh sách sản phẩm.");
      }
    },

    fetchVariations(page = 0, size = 10) {
      axios
          .get(`http://localhost:8080/admin/variation/result/all?page=${page}&size=${size}`)
          .then((response) => {
            this.variations = response.data.content;
            this.totalPages = response.data.page.totalPages;
            console.log(this.variations);
          })
          .catch((error) => {
            console.error("Có lỗi xảy ra khi lấy dữ liệu sản phẩm:", error);
          });
    },

    // ──────────────────────────
    // 🛠 UTILITIES
    getImagesUrl(images) {
      const defaultImage = images;
      return defaultImage
          ? `http://localhost:8080/upload/images/${defaultImage.cd_Images}`
          : "/img/default.jpg";
    },

    parsePrice(priceString) {
      return parseFloat(priceString.replace(/\./g, '').replace(' ₫', ''));
    },

    confirmDelete(variation) {
      if (confirm(`Bạn có chắc chắn muốn xóa sản phẩm "${variation.productID.name}"?`)) {
        this.deleteProduct(variation.id);
      }
    },

    deleteProduct(productId) {
      const token = Cookies.get("token");
      if (!token) return alert("Token không hợp lệ hoặc không tồn tại.");

      axios
          .get(`http://localhost:8080/admin/variation/delete/${productId}`, {
            headers: {Authorization: `Bearer ${token}`},
          })
          .then(() => this.fetchVariations(this.currentPage, this.pageSize))
          .catch((error) => console.error("Có lỗi xảy ra khi xóa sản phẩm:", error));
    },

    changePage(page) {
      this.currentPage = page;
      this.fetchVariations(page, this.pageSize);
    },

    toggleAllCheckboxes() {
      const allChecked = this.variations.every((v) => v.selected);
      this.variations.forEach((v) => (v.selected = !allChecked));
    },

    setCategoryAndBrands() {
      const selectedProduct = this.products.find(p => p.id === this.variation.id);
      this.variation.category = selectedProduct?.categoryID.id || "";
      this.variation.categoryName = selectedProduct?.categoryID.name || "";
      this.variation.brand = selectedProduct?.brandID.id || "";
      this.variation.brandName = selectedProduct?.brandID.name || "";
    },

    updateTime() {
      const today = new Date();
      const options = {
        weekday: "long", year: "numeric", month: "2-digit", day: "2-digit",
        hour: "2-digit", minute: "2-digit", second: "2-digit"
      };
      this.currentTime = today.toLocaleDateString("vi-VN", options);
    }
  }
};
</script>

<style>
.form-group {
  margin-bottom: 20px;
}

.btn-primary {
  background-color: #007bff;
  border: none;
  padding: 10px 20px;
  border-radius: 5px;
  cursor: pointer;
}

.btn-primary:hover {
  background-color: #0056b3;
}

.pagination {
  display: flex;
  justify-content: center;
  align-items: center;
  margin-top: 20px;
}

.page-button {
  background-color: #007bff;
  color: white;
  border: none;
  padding: 10px 15px;
  margin: 0 5px;
  border-radius: 5px;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.page-button:hover {
  background-color: #0056b3;
}

.page-button:disabled {
  background-color: #cccccc;
  cursor: not-allowed;
}

.page-button.active {
  background-color: #28a745;
  color: white;
}
</style>