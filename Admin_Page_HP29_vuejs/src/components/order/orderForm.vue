<template>
  <div class="app sidebar-mini rtl">
    <!-- Navbar -->
    <header class="app-header">
      <ul class="app-nav">
        <li>
          <a class="app-nav__item" href="/order">
            <i class='bx bx-log-out bx-rotate-180'></i>
          </a>
        </li>
      </ul>
    </header>

    <main class="app app-ban-hang">
      <div class="row">
        <div class="col-md-12">
          <div class="app-title">
            <ul class="app-breadcrumb breadcrumb">
              <li class="breadcrumb-item"><a href="#"><b>POS bán hàng</b></a></li>
            </ul>
            <div id="clock"></div>
          </div>
        </div>
      </div>
      <div class="row">
        <div class="col-md-8">
          <div class="tile">
            <h3 class="tile-title">Phần mềm bán hàng</h3>
            <input type="text" id="myInput" ref="myInput" @input="myFunction"
                   placeholder="Nhập mã sản phẩm hoặc tên sản phẩm để tìm kiếm...">
            <div class="du--lieu-san-pham">
              <table class="table table-hover table-bordered">
                <thead>
                <tr>
                  <th class="so--luong">Tên sản phẩm</th>
                  <th class="so--luong">Màu sắc</th>
                  <th class="so--luong">Chất liệu</th>
                  <th class="so--luong">Size</th>
                  <th class="so--luong" width="10">Ảnh</th>
                  <th class="so--luong">Số lượng</th>
                  <th class="so--luong">Trạng thái</th>
                  <th class="so--luong">Giá bán</th>
                  <th class="so--luong text-center">Chủng loại</th>
                </tr>
                </thead>
                <tbody>
                <tr v-if="products.length === 0">
                  <td class="so--luong" colspan="7">Không tìm thấy sản phẩm nào.</td>
                </tr>
                <tr v-for="variation in products" :key="variation.id" @click="addToCart(variation)">
                  <td class="so--luong">{{ variation.productID.name }}</td>
                  <td class="so--luong">
                    <div class="color-box"
                        :style="{ backgroundColor: variation.color, width: '30px', height: '30px', borderRadius: '4px' }">
                    </div>
                  </td>
                  <td class="so--luong">{{ variation.material }}</td>
                  <td class="so--luong">{{ variation.size }}</td>
                  <td class="so--luong">
                    <img :src="getImagesUrl(variation.images)" alt="" width="70px"/>
                  </td>
                  <td class="so--luong">{{ variation.quantity }}</td>
                  <td class="so--luong">
                    <span :class="{'badge bg-success': variation.status, 'badge bg-danger': !variation.status}">
                      {{ variation.status ? "Còn hàng" : "Hết hàng" }}
                    </span>
                  </td>
                  <td class="so--luong">{{ formatCurrency(variation.price) }}</td>
                  <td class="so--luong">{{ variation.productID.categoryID.name }}</td>
                </tr>
                </tbody>
              </table>
            </div>
            <div class="alert">
              <div class="pagination">
                <button
                    class="page-button"
                    :disabled="currentPage === 0"
                    @click="changePage(currentPage - 1)"
                >
                  Previous
                </button>
                <span>Page {{ currentPage + 1 }} of {{ totalPages }}</span>
                <button
                    class="page-button"
                    :disabled="currentPage >= totalPages - 1"
                    @click="changePage(currentPage + 1)"
                >
                  Next
                </button>
              </div>
            </div>
            <div class="tile mt-3">
              <h3 class="tile-title">Giỏ hàng</h3>
              <table class="table table-hover table-bordered">
                <thead>
                <tr>
                  <th>Mã hàng</th>
                  <th>Tên sản phẩm</th>
                  <th>Số lượng</th>
                  <th>Giá bán</th>
                  <th>Tổng</th>
                  <th>Hành động</th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="item in cart" :key="item.id">
                  <td>{{ item.sku }}</td>
                  <td>{{ item.productID.name }}</td>
                  <td>
                    <button @click="decreaseQuantity(item)">-</button>
                    {{ item.quantity }}
                    <button @click="increaseQuantity(item)">+</button>
                  </td>
                  <td>{{ formatCurrency(item.price) }}</td>
                  <td>{{ formatPrice(item.quantity * item.price) }}</td>
                  <td>
                    <button class="btn btn-danger btn-sm" @click="removeFromCart(item)">Xóa</button>
                  </td>
                </tr>
                </tbody>
              </table>
            </div>
          </div>
        </div>
        <div class="col-md-4">
          <div class="tile">
            <h3 class="tile-title">Thông tin thanh toán</h3>
            <div class="row">
              <div class="form-group col-md-12">
                <label class="control-label">Khách hàng </label>
                <select class="form-control" v-model="selectedCustomer">
                  <option v-for="customer in sortedCustomers" :key="customer.id" :value="customer">
                    {{ customer.name }}
                  </option>
                </select>
              </div>

              <div class="form-group col-md-12">
                <label class="control-label">Ghi chú đơn hàng</label>
                <textarea class="form-control" rows="4" placeholder="Ghi chú thêm đơn hàng"
                          v-model="orderNote"></textarea>
              </div>
            </div>
            <div class="row">
              <div class="form-group col-md-12">
                <label class="control-label">Hình thức thanh toán</label>
                <select class="form-control" v-model="paymentMethod" required>
                  <option value="1">Quẹt thẻ</option>
                  <option value="2">Thanh toán trực tiếp</option>
                  <option value="3">Chuyển khoản</option>
                </select>
              </div>
              <div class="form-group col-md-6">
                <label class="control-label">Tạm tính tiền hàng: </label>
                <p class="control-all-money-tamtinh">= {{ formatPrice(totalAmount) }} VNĐ</p>
              </div>
              <div class="form-group col-md-6">
                <label class="control-label">Tổng cộng thanh toán: </label>
                <p class="control-all-money-total">= {{ formatPrice(finalAmount) }} VNĐ</p>
              </div>
              <div class="form-group col-md-6">
                <label class="control-label">Khách hàng đưa tiền (F8): </label>
                <input class="form-control" style="width: 100%" type="number" v-model="amountReceived" placeholder="Nhập số tiền khách đưa">
              </div>

              <div class="form-group col-md-6">
                <label class="control-label">Trả lại khách hàng: </label>
                <p class="control-all-money"> {{ formatPrice(changeDue) }} VNĐ</p>
              </div>
              <div class="tile-footer col-md-12">
                <button class="btn btn-primary luu-san-pham" type="button" @click="saveOrder">Lưu đơn hàng (F9)</button>
                <button class="btn btn-primary luu-va-in" type="button" @click="saveAndPrint">Lưu và in hóa đơn (F10)
                </button>
                <a class="btn btn-secondary luu-va-in" href="/">Quay về</a>
              </div>
            </div>
          </div>
        </div>
      </div>
    </main>
  </div>
</template>

<script>
import axios from 'axios';
import Cookies from 'js-cookie';

export default {
  data() {
    return {
      products: [],
      customers: [],
      default_customer: {},
      cart: [],
      selectedEmployee: '',
      currentPage: 0, // Số trang hiện tại
      pageSize: 4, // Số sản phẩm mỗi trang
      orderNote: '',
      codevoucher: '',
      status: '',
      paymentMethod: '',
      discount: 0,
      amountReceived: 0,
    };
  },
  computed: {
    totalAmount() {
      return this.cart.reduce((total, item) => {
        return total + (item.price * item.quantity);
      }, 0);
    },
    finalAmount() {
      return this.totalAmount - this.discount;
    },
    changeDue() {
      return Math.max(this.amountReceived - this.finalAmount, 0); // Không để số âm
    },
    sortedCustomers() {
      if (!this.customers.length) return [];
      const lastCustomer = this.customers[this.customers.length - 1]; // Lấy phần tử cuối cùng
      const remainingCustomers = this.customers.slice(0, -1); // Lấy các phần tử còn lại
      return [lastCustomer, ...remainingCustomers]; // Đưa phần tử cuối lên đầu
    }
  },
  mounted() {
    this.fetchProducts(this.currentPage, this.pageSize); // Gọi hàm để lấy tất cả sản phẩm khi component được mount
    this.getCustomer(); // Gọi hàm để lấy tất cả khách hàng khi component được mount
  },
  methods: {
    selectid(id) {
      console.log("Biến thể: " + id);
    },

    async myFunction() {
      const searchKeyword = this.$refs.myInput.value; // Lấy giá trị từ ô tìm kiếm
      console.log(searchKeyword);
      try {
        if (!searchKeyword.trim()) {
          // Nếu input tìm kiếm bị xóa trắng, gọi fetchProducts để lấy lại toàn bộ sản phẩm
          return this.fetchProducts(this.currentPage, this.pageSize);
        }

        const response = await axios.get(`http://localhost:8080/MiniatureCrafts/result/${searchKeyword}`);
        this.products = [...response.data.content];
        console.log("Dữ liệu sản phẩm:", this.products);
      } catch (error) {
        console.error("Lỗi khi lấy sản phẩm:", error);
      }
    },

    async fetchProducts(page, size) {
      try {
        const response = await axios.get(`http://localhost:8080/admin/variation/get/all?page=${page}&size=${size}`);
        this.products = response.data.content; // Lấy danh sách sản phẩm
        this.totalPages = response.data.page.totalPages; // Tổng số trang
        console.log("Dữ liệu sản phẩm:", this.products);
      } catch (error) {
        console.error("Có lỗi xảy ra khi lấy dữ liệu sản phẩm:", error);
      }
    },
    async getCustomer() {
      try {
        const response = await axios.get(`http://localhost:8080/admin/customer/result/all`);
        this.customers = response.data;

        if (this.customers.length > 0) {
          this.selectedCustomer = this.customers[this.customers.length - 1]; // Gán phần tử cuối cùng
        }
      } catch (error) {
        console.error("Lỗi khi lấy danh sách khách hàng:", error);
      }
    },
    increaseQuantity(item) {
      item.quantity += 1;
    },
    decreaseQuantity(item) {
      if (item.quantity > 1) {
        item.quantity -= 1;
      }
    },
    removeFromCart(item) {
      this.cart = this.cart.filter(product => product.id !== item.id);
    },
    addToCart(product) {
      const existingProduct = this.cart.find(item => item.id === product.id);
      console.log(product.quantity)
      if (existingProduct) {
        if (existingProduct.quantity >= product.quantity) {
          // Tăng số lượng nếu sản phẩm đã có
          alert("Không thể thêm quá số lượng sản phẩm hiện có")
          return;
        } else {
          existingProduct.quantity += 1;
        }

      } else {
        this.cart.push({...product, quantity: 1});  // Thêm mới nếu chưa có
      }
    },
    parsePrice(priceString) {
      // Chuyển đổi giá từ chuỗi sang số
      return parseFloat(priceString.replace(/\./g, '').replace(' ₫', ''));
    },
    formatPrice(price) {
      // Định dạng giá thành chuỗi
      return price.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ".") + " ₫";
    },
    changePage(page) {
      if (page < 0 || page >= this.totalPages) return; // Kiểm tra giới hạn trang
      this.currentPage = page;
      this.fetchProducts(this.currentPage, this.pageSize); // Tải dữ liệu trang mới
    },
    formatCurrency(value) {
      // Chuyển đổi giá trị thành chuỗi và định dạng với dấu phân cách hàng nghìn
      return value.toString().replace(/\B(?=(\d{3})+(?!\d))/g, '.') + ' ₫';
    },
    getImagesUrl(images) {
      return images
          ? `http://localhost:8080/upload/images/${images.cd_Images}`
          : "/img/default.jpg";
    },

    async saveOrder() {
      // Kiểm tra xem giỏ hàng có sản phẩm không
      if (this.cart.length === 0) {
        alert("Giỏ hàng trống! Vui lòng thêm sản phẩm trước khi lưu đơn hàng.");
        return;
      }

      // Kiểm tra xem khách hàng đã được chọn chưa
      if (!this.selectedCustomer || !this.selectedCustomer.id) {
        alert("Vui lòng chọn khách hàng!");
        return;
      }

      // Tạo dữ liệu đơn hàng theo cấu trúc JSON mẫu
      const orderData = {
        customerID: {
          id: this.selectedCustomer.id // ID khách hàng được chọn từ dropdown
        },
        code_Voucher: this.codevoucher, // Mã voucher (có thể thay đổi theo logic của bạn)
        note: this.orderNote, // Ghi chú đơn hàng
        paymentMethod: {
          id: this.paymentMethod // Phương thức thanh toán
        },
        status: "", // Trạng thái đơn hàng (có thể thay đổi theo logic của bạn)
        type_Oder: "", // Loại đơn hàng (có thể thay đổi theo logic của bạn)
        orderLine: this.cart.map(item => ({
          variationID: {
            id: item.id
          }, // ID sản phẩm
          quantity: item.quantity // Số lượng
        })),
      };
      console.log(orderData)
      try {
        const token = Cookies.get("token"); // Lấy token từ cookies
        const response = await axios.post("http://localhost:8080/admin/orders/newOrder", orderData, {
          headers: {
            Authorization: `Bearer ${token}` // Thêm token vào header
          }
        });

        if (response.status === 201) {
          alert("Đơn hàng đã được lưu thành công!");
          this.fetchProducts(this.currentPage, this.pageSize);
          // Reset các trường dữ liệu nếu cần
          this.cart = [];
          this.orderNote = '';
          this.paymentMethod = '';
          this.selectedCustomer = null; // Reset khách hàng đã chọn

        } else if (response.status === 400) {
          console.error("Lỗi khi lưu đơn hàng:", error);
          alert("Đơn hàng thiếu phương thức thanh toán hoặc khách hàng!");
        } else {
          alert("Không thể lưu đơn hàng. Vui lòng thử lại!");
        }
      } catch (error) {
        console.error("Lỗi khi lưu đơn hàng:", error);
        alert("Đơn hàng thiếu phương thức thanh toán hoặc khách hàng!");
      }
    },
    saveAndPrint() {
      // Implement save and print functionality
    },
    calculateChange() {
      // Implement change calculation
    },
    calculateTotal() {
      // Implement discount calculation if needed
    }
  }
};
</script>