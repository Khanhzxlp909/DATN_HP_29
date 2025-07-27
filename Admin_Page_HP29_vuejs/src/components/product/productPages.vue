<template>
  <div id="app" class="app sidebar-mini rtl">
    <main class="app-content">
      <div class="app-title">
        <ul class="app-breadcrumb breadcrumb side">
          <li class="breadcrumb-item active">
            <a href="#"><b>Thêm mới sản phẩm</b></a>
          </li>
        </ul>
      </div>

      <div class="row">
        <div class="col-md-12">
          <div class="tile">
            <div class="tile-body">
              <form @submit.prevent="isEditing ? updateProduct() : addProduct()" class="row">
                <div class="form-group col-md-4">
                  <label for="productName">Tên sản phẩm:</label>
                  <input id="productName" v-model="product.name" class="form-control"/>
                </div>

                <div class="form-group col-md-4">
                  <label>Danh mục</label>
                  <select v-model="product.categoryID.id" class="form-control">
                    <option v-for="item in categories" :key="item.id" :value="item.id">
                      {{ item.name }}
                    </option>
                  </select>
                </div>

                <div class="form-group col-md-4">
                  <label>Thương hiệu</label>
                  <select v-model="product.brandID.id" class="form-control">
                    <option v-for="item in brands" :key="item.id" :value="item.id">
                      {{ item.name }}
                    </option>
                  </select>
                </div>

                <div class="form-group col-md-12">
                  <h5>Upload 3 ảnh sản phẩm</h5>
                  <div v-for="(image, index) in images" :key="index" class="image-preview-wrapper">
                    <input type="file" @change="handleFileUpload($event, index)" class="form-control mb-2"/>
                    <img v-if="image.preview" :src="image.preview" class="img-thumbnail mt-2" style="max-width: 80px;"/>
                    <label class="radio-label">
                      <input type="radio" v-model="defaultImageIndex" :value="index"/> Ảnh mặc định
                    </label>
                  </div>
                </div>

                <div class="form-group col-md-4">
                  <button type="submit" class="btn btn-primary" style="font-size: large">
                    {{ isEditing ? 'Cập nhật sản phẩm' : 'Thêm sản phẩm' }}
                  </button>
                </div>
              </form>

              <table class="table table-hover table-bordered mt-4">
                <thead>
                <tr>
                  <th>ID</th>
                  <th>Tên sản phẩm</th>
                  <th>Danh mục</th>
                  <th>Thương hiệu</th>
                  <th>Chức năng</th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="item in products" :key="item.id">
                  <td>{{ item.id }}</td>
                  <td>{{ item.name }}</td>
                  <td>{{ item.categoryID.name }}</td>
                  <td>{{ item.brandID.name }}</td>
                  <td>
                    <button @click="editProduct(item)" class="btn btn-warning">Sửa</button>
                  </td>
                </tr>
                </tbody>
              </table>
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
      product: {
        id: "",
        name: "",
        categoryID: {id: ""},
        brandID: {id: ""},
        images: []
      },
      brands: [],
      products: [],
      categories: [],
      images: [
        {id: "", file: null, preview: ""},
        {id: "", file: null, preview: ""},
        {id: "", file: null, preview: ""}
      ],
      defaultImageIndex: 0,
      imageUrls: [],
      imagesDelete: [],
      id_images: [],
      isEditing: false
    };
  },
  created() {
    this.fetchCategories();
    this.fetchProducts();
    this.fetchBrands();
  },
  methods: {
    async fetchProducts() {
      const token = Cookies.get("authToken");
      if (!token) return this.$router.push("/login");

      try {
        const response = await axios.get("http://localhost:8080/admin/product/findAll", {
          headers: {Authorization: `Bearer ${token}`}
        });
        this.products = response.data;
        console.log(this.products)
      } catch (error) {
        console.error("Lỗi khi tải sản phẩm:", error);
      }
    },

    async fetchBrands() {
      const token = Cookies.get("token");
      if (!token) return this.$router.push("/login");

      try {
        const response = await axios.get("http://localhost:8080/admin/brands/getbrands", {
          headers: {Authorization: `Bearer ${token}`}
        });
        this.brands = response.data.content;
      } catch (error) {
        console.error("Lỗi khi fetch brands:", error);
      }
    },

    async fetchCategories() {
      const token = Cookies.get("token");
      if (!token) return this.$router.push("/login");

      try {
        const response = await axios.get("http://localhost:8080/admin/category/getcategory", {
          headers: {Authorization: `Bearer ${token}`}
        });
        this.categories = response.data.content;
      } catch (error) {
        console.error("Lỗi khi lấy danh mục:", error);
      }
    },

    handleFileUpload(event, index) {
      const file = event.target.files[0];
      if (file) {
        this.images[index].file = file;
        this.images[index].preview = URL.createObjectURL(file);
      }
    },

    validateForm() {
      if (!this.product.name.trim() || this.product.name.length > 50) {
        alert("Quá giới hạn ký tự")
        return false;
      }
      ;
      const uploadedFiles = this.images.filter(image => image.file !== null);
      return uploadedFiles.length === 3;
    },

    async addProduct() {
      const token = Cookies.get("token");
      if (!token) return this.$router.push("/login");
      if (!this.validateForm()) {
        console.log("Validation failed:", this.product, this.images);
        return;
      }

      try {
        console.log("Sending product data:", this.product);
        const productResponse = await axios.post(
            "http://localhost:8080/admin/product/saved",
            this.product,
            {headers: {Authorization: `Bearer ${token}`}}
        );
        console.log("Product added successfully:", productResponse.data);

        const productId = productResponse.data.id;
        const uploadedImages = [];
        for (const image of this.images) {
          const formData = new FormData();
          formData.append("file", image.file);

          const uploadResponse = await axios.post(
              "http://localhost:8080/admin/variation/images/upload",
              formData,
              {headers: {"Content-Type": "multipart/form-data"}}
          );
          uploadedImages.push(...uploadResponse.data.urls);
        }

        console.log("Uploaded images:", uploadedImages);
        const imageRequests = uploadedImages.map((imageUrl, index) => ({
          productID: productId,
          model: "Product",
          cd_Images: imageUrl,
          set_Default: index === this.defaultImageIndex
        }));

        await axios.post(
            "http://localhost:8080/admin/variation/images/setproduct",
            imageRequests,
            {headers: {Authorization: `Bearer ${token}`}}
        );

        this.fetchProducts();
      } catch (error) {
        console.error("Error while adding product:", error);
      }
    },

    async updateProduct() {
      const token = Cookies.get("authToken");
      if (!token) return this.$router.push("/login");
      if (!this.validateForm()) return;

      console.log("Deleting old images:", this.product.images);
      try {
        // Step 1: Delete old images

        await axios.post(
            "http://localhost:8080/admin/variation/images/delete",
            this.product.images.map(img => img.cd_Images),
            { headers: { Authorization: `Bearer ${token}` } }
        );

        // Step 2: Update product details
        await axios.post(
            "http://localhost:8080/admin/product/update",
            this.product,
            {headers: {Authorization: `Bearer ${token}`}}
        );

        // Step 3: Upload new images
        const uploadedImages = [];
        for (const image of this.images) {
          if (image.file) {
            const formData = new FormData();
            formData.append("file", image.file);

            const uploadResponse = await axios.post(
                "http://localhost:8080/admin/variation/images/upload",
                formData,
                {headers: {Authorization: `Bearer ${token}`, "Content-Type": "multipart/form-data"}}
            );

            uploadedImages.push(uploadResponse.data.urls[0]);
          }
        }

        // Step 4: Associate new images with the product
        const imageRequests = uploadedImages.map((imageUrl, index) => ({
          productID: this.product.id,
          model: "Product",
          cd_Images: imageUrl,
          set_Default: index === this.defaultImageIndex
        }));

        await axios.post(
            "http://localhost:8080/admin/variation/images/setproduct",
            imageRequests,
            {headers: {Authorization: `Bearer ${token}`}}
        );

        // Redirect or refresh the product list
        this.$router.push("/product");
      } catch (error) {
        console.error("Lỗi khi cập nhật sản phẩm:", error);
      }
    },

    editProduct(product) {
      this.product = {
        id: product.id,
        name: product.name,
        categoryID: {id: product.categoryID?.id || ""},
        brandID: {id: product.brandID?.id || ""},
        images: product.images || []
      };

      console.log(this.product)
      console.log("Deleting old images:", this.product.images);
      this.isEditing = true;
      this.images = [
        {id: "", file: null, preview: ""},
        {id: "", file: null, preview: ""},
        {id: "", file: null, preview: ""}
      ];

      if (product.imagesDTOS && product.imagesDTOS.length > 0) {
        this.images = product.imagesDTOS.map((img) => ({
          id: img.id,
          file: null,
          preview: img.cd_Images.includes("http")
              ? img.cd_Images
              : `http://localhost:8080/upload/images/${img.cd_Images}`
        }));

        while (this.images.length < 3) {
          this.images.push({id: "", file: null, preview: ""});
        }

        this.defaultImageIndex = product.imagesDTOS.findIndex(img => img.set_Default) || 0;
      }
    }
  }
};
</script>

<style scoped>
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

.image-preview-wrapper {
  display: flex;
  align-items: center;
  gap: 10px;
  margin-bottom: 15px;
}

.image-preview-wrapper img {
  max-width: 80px;
  border-radius: 8px;
  border: 1px solid #ccc;
}

.radio-label {
  font-size: 14px;
  margin-left: 10px;
  color: #555;
}
</style>
