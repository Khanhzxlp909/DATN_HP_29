<template>
  <div class="app sidebar-mini rtl" @load="time">
    <!-- Main content -->
    <main class="app-content">
      <div class="row">
        <div class="col-md-12">
          <div class="app-title">
            <ul class="app-breadcrumb breadcrumb">
              <li class="breadcrumb-item"><a href="#"><b>Bảng điều khiển</b></a></li>
            </ul>
            <div id="clock">{{ clock }}</div>
          </div>
        </div>
      </div>

      <!-- Widgets -->
      <div class="row">
        <div class="col-md-12 col-lg-6">
          <div class="row">
            <div v-for="(stat, index) in stats" :key="index" class="col-md-6">
              <div class="widget-small coloured-icon" :class="stat.color">
                <i :class="['icon', stat.icon, 'fa-3x']"></i>
                <div class="info">
                  <h4>{{ stat.title }}</h4>
                  <p><b>{{ stat.value }}</b></p>
                  <p class="info-tong">{{ stat.description }}</p>
                </div>
              </div>
            </div>

            <!-- Orders Table -->
            <div class="col-md-12">
              <div class="tile">
                <h3 class="tile-title">Biến thể bán chạy</h3>
                <table class="table table-bordered">
                  <thead>
                  <tr>
                    <th>Tên biến thể</th>
                    <th>Ảnh biến thể</th>
                    <th>Danh mục</th>
                    <th>Giá</th>
                    <th>Đã bán</th>
                  </tr>
                  </thead>
                  <tbody>
                  <tr v-for="v in variations" :key="v.id">
                    <td>{{ v.name }}</td>
                    <td>
                      <img :src="getImagesUrl(v.images)" alt="" width="70px"/>
                    </td>
                    <td>{{ v.productID.categoryID.name }}</td>
                    <td>{{ formatCurrency(v.price) }}</td>
                    <td>{{ v.sold }}</td>
                  </tr>
                  </tbody>
                </table>
              </div>
            </div>

          </div>
        </div>

        <!-- Charts -->
        <div class="col-md-12 col-lg-6">
          <div class="row">
            <div class="col-md-12">
              <div class="tile">
                <h3 class="tile-title">Dữ liệu 6 tháng doanh thu</h3>
                <canvas ref="lineChart"></canvas>
              </div>
            </div>
            <div class="col-md-12">
              <div class="tile">
                <h3 class="tile-title">Thống kê doanh thu từng ngày trong tháng</h3>
                <canvas ref="barChart"></canvas>
              </div>
            </div>
          </div>
        </div>
      </div>

      <!-- Footer -->
      <div class="text-center" style="font-size: 13px">
        <p><b>Copyright {{ new Date().getFullYear() }} Phần mềm quản lý bán hàng | Dev By Trường</b></p>
      </div>
    </main>
  </div>
</template>

<script>
import axios from 'axios';
import Chart from "chart.js/auto";
import Cookies from 'js-cookie';


export default {
  name: "AdminDashboard",
  data() {

    return {
      clock: "",
      variations: [],
      revenueInYear: [],
      revenueInMonth: [],
      orderCancel: null,
      revenueInCurrentMonth: null,
      variationsQuantity: null,
      ordersQuantity: null,
      lowStock: null,
      totalquantitysold: null,
    };
  },
  computed: {
    stats() {
      return [
        {
          title: "Tổng sản phẩm đã bán",
          value: this.totalquantitysold !== null ? `${this.totalquantitysold} sản phẩm` : "Đang tải...",
          description: "Tổng số khách hàng được quản lý.",
          icon: "bx bxs-user-account",
          color: "primary"
        },
        {
          title: "Tổng sản phẩm",
          value: this.variationsQuantity !== null ? `${this.variationsQuantity} sản phẩm` : "Đang tải...",
          description: "Tổng số sản phẩm được quản lý.",
          icon: "bx bxs-data",
          color: "info"
        },
        {
          title: "Đơn hàng trong tháng",
          value: this.ordersQuantity !== null ? `${this.ordersQuantity} đơn hàng` : "Đang tải...",
          description: "Tổng số hóa đơn bán hàng trong tháng.",
          icon: "bx bxs-shopping-bags",
          color: "warning"
        },
        {
          title: "Sắp hết hàng",
          value: this.lowStock !== null ? `${this.lowStock} sản phẩm` : "Đang tải...",
          description: "Số sản phẩm cảnh báo hết cần nhập thêm.",
          icon: "bx bxs-error-alt",
          color: "danger"
        },
        {
          title: "Đơn hàng bị hủy",
          value: this.lowStock !== null ? `${this.orderCancel} đơn hàng` : "Đang tải...",
          description: "Số đơn hàng bị hủy trong tháng.",
          icon: "bx bxs-receipt",
          color: "danger"
        },
        {
          title: "Tổng doanh thu trong tháng",
          value: this.lowStock !== null ? `${this.formatCurrency(this.revenueInCurrentMonth)} ` : "Đang tải...",
          description: "Tổng doanh thu tháng.",
          icon: "bx bxs-chart",
          color: "primary"
        }
      ];
    }
  },
  mounted() {
    this.startClock();
    // this.renderCharts();
    this.fetchTopProducts();
    this.fetchOrderInMonth();
    this.fetchLowStockVariation();
    this.fetchTotalQuantitySold();
    this.fetchVariationsBestSeller();
    this.fetchRevenueInYear();
    this.fetchRevenueInMonth();
    this.countCancelOrderInmonth();
    this.fetchRevenueInCurrentMonth();
  },
  methods: {
    getImagesUrl(images) {
      return images
          ? `http://localhost:8080/upload/images/${images.cd_Images}`
          : "/img/default.jpg";
    },


    formatCurrency(value) {
      // Chuyển đổi giá trị thành chuỗi và định dạng với dấu phân cách hàng nghìn
      return value.toString().replace(/\B(?=(\d{3})+(?!\d))/g, '.') + ' ₫';
    },
    async fetchVariationsBestSeller() {
      const token = Cookies.get("token"); // Lấy token từ cookies
      console.log(token)
      try {
        const response = await axios.get('http://localhost:8080/api/admin/dashboard/variation/bestseller?page=0&size=4', {
              headers: {
                Authorization: `Bearer ${token}`
              }
            }
        );

        this.variations = response.data.content;
        console.log(this.variations)
      } catch (error) {
        console.error("Có lỗi xảy ra khi lấy danh sách nhân viên:", error);
      }
    },

    async fetchTopProducts() {
      const token = Cookies.get("token"); // Lấy token từ cookies
      console.log(token)
      try {
        const response = await axios.get('http://localhost:8080/api/admin/dashboard/products/count/quantity', {
              headers: {
                Authorization: `Bearer ${token}`
              }
            }
        );
        console.log(response.data)
        this.variationsQuantity = response.data.count;
      } catch (error) {
        console.error("Có lỗi xảy ra khi lấy danh sách nhân viên:", error);
      }
    },

    async fetchLowStockVariation() {
      const token = Cookies.get("token"); // Lấy token từ cookies
      console.log(token)
      try {
        const response = await axios.get('http://localhost:8080/api/admin/dashboard/low-stock/count?threshold=10', {
              headers: {
                Authorization: `Bearer ${token}`
              }
            }
        );
        console.log(response.data)
        this.lowStock = response.data.count;
      } catch (error) {
        console.error("Có lỗi xảy ra khi lấy danh sách nhân viên:", error);
      }
    },

    async fetchTotalQuantitySold() {
      const token = Cookies.get("token"); // Lấy token từ cookies
      console.log(token)
      try {
        const response = await axios.get('http://localhost:8080/api/admin/dashboard/total-quantity-sold', {
              headers: {
                Authorization: `Bearer ${token}`
              }
            }
        );
        console.log(response.data)
        this.totalquantitysold = response.data.count;
      } catch (error) {
        console.error("Có lỗi xảy ra khi lấy danh sách nhân viên:", error);
      }
    },

    async fetchOrderInMonth() {
      const token = Cookies.get("token"); // Lấy token từ cookies
      const now = new Date();
      const year = now.getFullYear();     // Năm hiện tại, ví dụ: 2025
      const month = now.getMonth() + 1;   // Tháng hiện tại (JS bắt đầu từ 0 → cần +1)

      try {
        const response = await axios.get(`http://localhost:8080/api/admin/dashboard/orders-in-month/count`, {
          headers: {
            Authorization: `Bearer ${token}`
          },
          params: {
            year: year,
            month: month
          }
        });
        console.log(response.data);
        this.ordersQuantity = response.data.count;
      } catch (error) {
        console.error("Có lỗi xảy ra khi lấy số lượng đơn hàng trong tháng:", error);
      }
    },

    async fetchRevenueInYear() {
      const token = Cookies.get("token"); // Lấy token từ cookies
      const now = new Date();
      const year = now.getFullYear();     // Năm hiện tại, ví dụ: 2025
      const month = now.getMonth() + 1;   // Tháng hiện tại (JS bắt đầu từ 0 → cần +1)

      try {
        const response = await axios.get(`http://localhost:8080/api/admin/dashboard/monthly-revenue?year=${year}`, {
          headers: {
            Authorization: `Bearer ${token}`
          }
        });

        this.revenueInYear = response.data;
        console.log(this.revenueInYear);
        this.renderCharts();
      } catch (error) {
        console.error("Có lỗi xảy ra khi lấy số lượng đơn hàng trong tháng:", error);
      }
    },
    async fetchRevenueInCurrentMonth() {
      const token = Cookies.get("token"); // Lấy token từ cookies
      const now = new Date();
      const year = now.getFullYear();     // Năm hiện tại, ví dụ: 2025
      const month = now.getMonth() + 1;   // Tháng hiện tại (JS bắt đầu từ 0 → cần +1)

      try {
        const response = await axios.get(`http://localhost:8080/api/admin/dashboard/paid-revenue/current-month`, {
          headers: {
            Authorization: `Bearer ${token}`
          }
        });

        this.revenueInCurrentMonth = response.data;
        console.log(this.revenueInCurrentMonth);
        this.renderCharts();
      } catch (error) {
        console.error("Có lỗi xảy ra khi lấy số lượng đơn hàng trong tháng:", error);
      }
    },

    async fetchRevenueInMonth() {
      const token = Cookies.get("token"); // Lấy token từ cookies
      console.log(token);
      const now = new Date();
      const year = now.getFullYear();     // Năm hiện tại, ví dụ: 2025
      const month = now.getMonth() + 1;   // Tháng hiện tại (JS bắt đầu từ 0 → cần +1)

      try {
        const response = await axios.get(`http://localhost:8080/api/admin/dashboard/day-revenue`, {
          headers: {
            Authorization: `Bearer ${token}`
          },
          params: {
            year: year,
            month: month
          }
        });
        this.revenueInMonth = response.data;
        console.log(this.revenueInMonth);
        this.renderCharts();
      } catch (error) {
        console.error("Có lỗi xảy ra khi lấy số lượng đơn hàng trong tháng:", error);
      }
    },

    async countCancelOrderInmonth() {
      const token = Cookies.get("token"); // Lấy token từ cookies
      console.log(token);
      const now = new Date();
      const year = now.getFullYear();     // Năm hiện tại, ví dụ: 2025
      const month = now.getMonth() + 1;   // Tháng hiện tại (JS bắt đầu từ 0 → cần +1)

      try {
        const response = await axios.get(`http://localhost:8080/api/admin/dashboard/orders-in-month/canceled`, {
          headers: {
            Authorization: `Bearer ${token}`
          },
          params: {
            year: year,
            month: month
          }
        });
        this.orderCancel = response.data;
        console.log(response.data);
        this.renderCharts();
      } catch (error) {
        console.error("Có lỗi xảy ra khi lấy số lượng đơn hàng trong tháng:", error);
      }
    },


    startClock() {
      setInterval(() => {
        const today = new Date();
        const weekday = ["Chủ Nhật", "Thứ Hai", "Thứ Ba", "Thứ Tư", "Thứ Năm", "Thứ Sáu", "Thứ Bảy"];
        const day = weekday[today.getDay()];
        const dd = String(today.getDate()).padStart(2, "0");
        const mm = String(today.getMonth() + 1).padStart(2, "0");
        const yyyy = today.getFullYear();
        const h = today.getHours();
        const m = String(today.getMinutes()).padStart(2, "0");
        const s = String(today.getSeconds()).padStart(2, "0");
        this.clock = `${day}, ${dd}/${mm}/${yyyy} - ${h} giờ ${m} phút ${s} giây`;
      }, 1000);
    },
    renderCharts() {
      const data6MonthFirst = this.revenueInYear.firstHalf;
      const data6MonthSecond = this.revenueInYear.secondHalf;

      const dataRevenueInYear = {
        labels: [
          "Tháng 1", "Tháng 2", "Tháng 3", "Tháng 4", "Tháng 5", "Tháng 6",
          "Tháng 7", "Tháng 8", "Tháng 9", "Tháng 10", "Tháng 11", "Tháng 12"
        ],
        datasets: [
          {
            label: "Doanh thu",
            backgroundColor: "rgba(9, 109, 239, 0.65)",
            borderColor: "rgb(9, 109, 239)",
            data: [...data6MonthFirst, ...data6MonthSecond] // gộp dữ liệu
          }
        ]
      };

      const data15FirstDay = this.revenueInMonth.firstHalf;
      const data15SecondDay = this.revenueInMonth.secondHalf;

      // Gộp dữ liệu doanh thu theo ngày
      const mergedData = [...data15FirstDay, ...data15SecondDay];

      // Số ngày trong tháng (dựa trên dữ liệu trả về)
      const daysInMonth = mergedData.length;

      // Tạo labels 1..daysInMonth
      const dayLabels = Array.from({length: daysInMonth}, (_, i) => `Ngày ${i + 1}`);

      const dataRevenueInMonth = {
        labels: dayLabels,
        datasets: [
          {
            label: "Doanh thu",
            backgroundColor: "rgba(9, 109, 239, 0.65)",
            borderColor: "rgb(9, 109, 239)",
            data: mergedData
          }
        ]
      };

      new Chart(this.$refs.lineChart, {type: "line", data: dataRevenueInYear});
      new Chart(this.$refs.barChart, {type: "bar", data: dataRevenueInMonth});
    }

  }
};
</script>

<style scoped>
@import "@/assets/main.css";
</style>
