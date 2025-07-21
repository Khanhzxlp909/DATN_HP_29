<template>
  <div class="container">
    <div class="wrapper">
      <div class="row">
        <div class="col-4">
          <div class="heading">
            <img src="../../assets/img/logo/avtusers.png" alt="" class="heading-img">
            <span class="heading-name_acc">{{ user.userInfo.name }}</span>
          </div>
          <div class="menu-manager">
            <div class="my-profile" @click="showProfile">
              <div class="my-profile-title" :class="{ active: activeTab === 'profile' }">
                <i class="fas fa-user"><span> Hồ sơ của tôi</span></i>
              </div>
            </div>
            <div class="my-order" @click="showOrders">
              <div class="my-order-title" :class="{ active: activeTab === 'orders' }">
                <i class="fas fa-shopping-bag"> <span> Đơn hàng của tôi</span></i>
              </div>
            </div>
            <div class="my-password" @click="showChangePassword">
              <div class="my-password-title" :class="{ active: activeTab === 'password' }">
                <i class="fas fa-key"><span> Đổi mật khẩu</span></i>
              </div>
            </div>
          </div>
        </div>
        <div class="col-8">
          <div v-if="activeTab === 'profile'" :key="activeTab" class="detail__my-profile">
            <div class="heading-edit-account">
              <h2>Hồ sơ của tôi</h2>
              <div class="form-group" v-for="(field, index) in profileFields" :key="index">
                <label :for="field.name" class="form-label">{{ field.label }}</label>
                <input
                    :id="field.name"
                    v-model="user.userInfo[field.name]"
                    :type="field.type"
                    :placeholder="field.placeholder"
                    class="form-control"
                />
                <span class="form-message"></span>
              </div>
              <button class="form-submit" style="color: #00cccc" @click="saveProfile">Lưu</button>
            </div>
          </div>

          <div v-if="activeTab === 'password'" :key="activeTab" class="detail__confirm-password">
            <div class="heading-edit-password">
              <h2>Đổi lại mật khẩu</h2>
            </div>
            <div
                class="form-group"
                v-for="(field, index) in passwordFields"
                :key="field.name"
                style="margin-bottom: 1.5rem;"
            >
              <div style="display: flex; justify-content: space-between;">
                <label :for="field.name" class="form-label">{{ field.label }}</label>
              </div>
              <div style="position: relative;">
                <input
                    :id="field.name"
                    v-model="field.value"
                    :type="field.showPassword ? 'text' : 'password'"
                    :placeholder="field.placeholder"
                    class="form-control"
                    style="width: 100%; padding-right: 40px;"
                />
                <i
                    :class="field.showPassword ? 'fas fa-eye' : 'fas fa-eye-slash'"
                    @click="togglePasswordVisibility(index)"
                    style="position: absolute; right: 10px; top: 50%; transform: translateY(-50%); cursor: pointer;"
                ></i>
              </div>
            </div>
            <button class="form-submit" style="color: #1dbfaf" @click="changePassword">Lưu</button>
          </div>
          <div v-if="activeTab === 'orders'" :key="activeTab" class="detail__my-order" style="font-size: 13px">
            <div class="heading-edit-password">
              <h2>Đơn hàng của bạn</h2>
            </div>
            <div class="filter-section">
              <label for="status-filter">
                <button class="loadmore-btn" @click="showOrders">Load more</button>
              </label>
              <label for="status-filter">Trạng thái:</label>
              <select v-model="selectedStatus" @change="filterOrders">
                <option value="">Tất cả</option>
                <option v-for="(label, key) in statusLabels" :key="key" :value="key">
                  {{ label }}
                </option>
              </select>
              <div class="total-amount">
                Tổng tiền: <strong>{{ formatCurrency(totalAmountByStatus) }}</strong>
              </div>
            </div>
            <div class="detail__my-order-content">
              <form action="">
                <div class="my-order-heading">
                  <div class="row">
                    <div class="col-2">Địa chỉ</div>
                    <div class="col-2">SDT</div>
                    <div class="col-2">Ngày mua hàng</div>
                    <div class="col-2">Tổng tiền</div>
                    <div class="col-2">Trạng thái</div>
                    <div class="col-2">Chi tiết</div>
                  </div>
                </div>
                <div class="detail__my-order-body" v-for="item in paginatedOrdersFiltered" :key="item.id">
                  <div class="row bd-bottom" @click="viewOrderDetails(item.id)">
                    <div class="col-2">{{ item.address }}</div>
                    <div class="col-2">{{ item.customerID.phone }}</div>
                    <div class="col-2">{{ formatDateTime(item.order_Time) }}</div>
                    <div class="col-2">{{ formatCurrency(item.total_Payment) }}</div>
                    <div class="col-2">
                      <span class="status-badge" :class="statusClasses[item.status]">
                        {{ statusLabels[item.status] }}
                      </span>
                    </div>

                    <div class="col-2">
                      <button class="loadmore-btn"
                              v-if="item.status !== 2  && item.status !== 3 && item.status !== 0 && item.status !== 4"
                              style="font-size: smaller;
                               margin-bottom: 5px"
                              @click="cancelOrder(item.id)">
                        Hủy đơn
                      </button>
                      <br>
                      <button class="loadmore-btn"
                              v-if="item.status !== 1  && item.status !== 3 && item.status !== 0 && item.status !== 4"
                              style="font-size: smaller"
                              @click="completeOrder(item.id)">
                        Đã nhận được hàng
                      </button>
                      <button class="loadmore-btn"
                              v-if="item.status === 3 &&
                              !isOver30Days(item.order_Time) &&
                              item.status !== 4 &&
                              !item.note?.includes('Trả hàng đơn #')"
                              style="font-size: smaller"
                              @click="initiateReturnOrder(item)">
                        Trả hàng
                      </button>
                    </div>
                  </div>
                </div>
                <div class="pagination">
                  <button type="button" @click="prevPage" class="loadmore-btn" :disabled="currentPage === 1">
                    « Trước
                  </button>
                  <span><h1>Trang {{ currentPage }} / {{ totalPages }}</h1></span>
                  <button type="button" @click="nextPage" class="loadmore-btn" :disabled="currentPage === totalPages">
                    Sau »
                  </button>
                </div>
              </form>
            </div>
          </div>
          <div v-if="activeTab === 'orderline'" class="detail__my-order" style="font-size: 13px">
            <div class="heading-edit-password"></div>
            <h2>Chi tiết đơn hàng</h2>
            <div class="filter-section">
              <label for="status-filter">
                <button class="loadmore-btn" @click="showOrders"> Trở lại</button>
              </label>
            </div>
            <div class="detail__my-order-content">
              <div class="product__variations mt-3">
                <h3>Sản phẩm</h3>
                <div
                    class="variation-item"
                    v-for="v in paginatedOrderDetails"
                    :key="v.id"
                    @click="viewProductDetail(v.variationID.productID.id)"
                    :style="{
                    border: '2px solid #13FBE6',
                    padding: '8px',
                    margin: '5px',
                    borderRadius: '6px',
                    cursor: 'pointer',
                    display: 'inline-block',
                    textAlign: 'center',
                    width: '230px',
                    height: '260px'
                  }"
                >
                  <!-- Ảnh biến thể: nếu có, hiển thị, còn không thì mặc định -->
                  <img
                      :src="`http://localhost:8080/upload/images/${v.variationID.images.cd_Images}`"
                      alt="Hình ảnh biến thể"
                      style="width: 65%; height: 65%; object-fit: cover;"
                  />
                  <div style="font-weight: bold; margin-top: 4px;">{{ v.variationID.name }}</div>
                  <div style="font-weight: bold; margin-top: 4px;">Số lượng: {{ v.quantity }}</div>
                  <div style="font-weight: bold; margin-top: 4px;">Giá bán: {{ formatCurrency(v.price) }}</div>
                </div>
              </div>
              <!-- Pagination controls -->
              <div class="pagination" v-if="totalOrderDetailPages > 1">
                <button type="button" @click="prevOrderDetailPage" class="loadmore-btn" :disabled="currentOrderDetailPage === 1">
                  « Trước
                </button>
                <span><h1>Trang {{ currentOrderDetailPage }} / {{ totalOrderDetailPages }}</h1></span>
                <button type="button" @click="nextOrderDetailPage" class="loadmore-btn" :disabled="currentOrderDetailPage === totalOrderDetailPages">
                  Sau »
                </button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import {ref, onMounted, computed, watch} from 'vue';
import axios from 'axios';
import Cookies from 'js-cookie';
import dayjs from 'dayjs'
import { useRouter } from 'vue-router';

export default {
  setup() {
    const router = useRouter();
    const apiUrl = "http://localhost:8080/MiniatureCrafts/";
    const user = ref(JSON.parse(Cookies.get("customers") || '{}'));

    console.log("ten: " + user.value.userInfo.name);
    console.log("ten: " + user.value.userInfo.address);
    console.log("ten: " + user.value.userInfo.phone);
    console.log("ten: " + user.value.userInfo.note);
    const orders = ref([]);
    const selectedProduct = ref({});
    const activeTab = ref('orders');
    const selectedStatus = ref(""); // Giá trị lọc trạng thái
    const currentPage = ref(1);
    const itemsPerPage = 5; // Số đơn hàng mỗi trang

    watch(activeTab, (newVal, oldVal) => {
      console.log(`🟢 activeTab changed: ${oldVal} -> ${newVal}`);
      console.log("🔍 Giá trị hiện tại của activeTab:", activeTab.value);
    });
    const paginatedOrdersFiltered = computed(() => {
      return paginatedOrders.value.filter(
          item => !item.note?.includes("Tr? hàng đơn #")
      );
    });
    const formatDateTime = (dateTime) => {
      if (!dateTime) {
        return 'Chưa thanh toán';
      }
      return dayjs(dateTime).format('YYYY-MM-DD HH:mm:ss');
    };

    const totalAmountByStatus = computed(() => {
      return filteredOrders.value.reduce((sum, order) =>
          sum + convertCurrencyToNumber(order.total_Payment), 0);
    });

    const convertCurrencyToNumber = (currencyString) => {
      if (!currencyString) return 0;

      // Chuẩn hóa chuỗi tiền tệ: Loại bỏ ký tự không cần thiết (đơn vị tiền)
      let cleanedString = currencyString.replace(/[^\d,.]/g, "");

      // Nếu có dấu chấm hoặc phẩy, kiểm tra và xử lý đúng định dạng
      if (cleanedString.includes(",")) {
        cleanedString = cleanedString.replace(/\./g, "").replace(",", ".");
      } else {
        cleanedString = cleanedString.replace(/,/g, ""); // Loại bỏ dấu phân tách hàng nghìn
      }

      return parseFloat(cleanedString);
    };
    // Nhãn trạng thái
    const statusLabels = ref({
      0: "Hủy đơn",
      1: "Chờ xác nhận",
      2: "Đang giao hàng",
      3: "Giao hàng thành công",
      4: "Trả hàng"
    });
    // Lớp CSS cho trạng thái
    const statusClasses = ref({
      0: "red",
      1: "blue",
      2: "orange",
      3: "green",
      4: "black"
    });
    const filteredOrders = computed(() => {
      if (!selectedStatus.value) return orders.value;
      return orders.value.filter(order => order.status === Number(selectedStatus.value));
    });
    // Chuyển trang
    const prevPage = () => {
      if (currentPage.value > 1) currentPage.value--;
    };

    const nextPage = () => {
      if (currentPage.value < totalPages.value) currentPage.value++;
    };

    // Tổng số trang
    const totalPages = computed(() => Math.ceil(filteredOrders.value.length / itemsPerPage));

    // Lấy danh sách đơn hàng của trang hiện tại
    const paginatedOrders = computed(() => {
      const start = (currentPage.value - 1) * itemsPerPage;
      return filteredOrders.value.slice(start, start + itemsPerPage);
    });

    const profileFields = ref([
      {name: 'name', label: 'Họ và tên', type: 'text', placeholder: 'Nhập họ và tên'},
      {name: 'address', label: 'Địa chỉ', type: 'text', placeholder: 'Nhập địa chỉ'},
      {name: 'phone', label: 'Số điện thoại', type: 'text', placeholder: 'Nhập số điện thoại'},
    ]);

    const passwordFields = ref([
      {name: 'oldPassword', label: 'Mật khẩu cũ', value: '', placeholder: 'Nhập mật khẩu cũ', showPassword: false},
      {name: 'newPassword', label: 'Mật khẩu mới', value: '', placeholder: 'Nhập mật khẩu mới', showPassword: false},
      {
        name: 'confirmPassword',
        label: 'Xác nhận mật khẩu mới',
        value: '',
        placeholder: 'Xác nhận mật khẩu mới',
        showPassword: false
      },
    ]);

// Hàm toggle trạng thái hiển thị mật khẩu của từng trường
    const togglePasswordVisibility = (index) => {
      passwordFields.value[index].showPassword = !passwordFields.value[index].showPassword;
    };

    const isModalVisible = ref(false);
    const selectedOrder = ref({});
    const itemsPerOrderDetailPage = 3; // Number of products per page in order details
    const currentOrderDetailPage = ref(1);

    const totalOrderDetailPages = computed(() => {
      return Math.ceil(selectedOrder.value.length / itemsPerOrderDetailPage);
    });

    const paginatedOrderDetails = computed(() => {
      const start = (currentOrderDetailPage.value - 1) * itemsPerOrderDetailPage;
      return selectedOrder.value.slice(start, start + itemsPerOrderDetailPage);
    });

    const prevOrderDetailPage = () => {
      if (currentOrderDetailPage.value > 1) currentOrderDetailPage.value--;
    };

    const nextOrderDetailPage = () => {
      if (currentOrderDetailPage.value < totalOrderDetailPages.value) currentOrderDetailPage.value++;
    };

    const viewOrderDetails = async (orderId) => {
      try {
        const response = await axios.get(`${apiUrl}history/getprd/${orderId}`);
        selectedOrder.value = response.data;

        if (activeTab.value !== "orderline") {
          activeTab.value = "orderline"; // Chỉ cập nhật nếu chưa là orderline
        }
      } catch (error) {
        console.error("Lỗi khi lấy chi tiết đơn hàng:", error);
      }
    };

    const viewProductDetail = (id) => {
      sessionStorage.setItem("idvariation", id);
      router.push(`/product/${id}`);
    };

    const closeModal = () => {
      isModalVisible.value = false;
    };

    const showProfile = () => {
      activeTab.value = 'profile';
    };

    const showOrders = () => {
      console.log("Chuyển về danh sách đơn hàng, trước khi đổi tab:", activeTab.value);

      if (activeTab.value !== "orders") {
        activeTab.value = "orders";
        console.log("Sau khi đổi tab:", activeTab.value);
      }
    };


    const showOrdersLine = (orderID) => {
      viewOrderDetails(orderID).then(() => {
        activeTab.value = 'orderline';
      });
    };

    const showChangePassword = () => {
      activeTab.value = 'password';
    };

    const cancelOrder = async (orderid) => {
      const token = Cookies.get("authToken");
      const apiCancel = `${apiUrl}cancelOrder/${orderid}`;
      try {
        await axios.get(apiCancel, {
          headers: {
            Authorization: `Bearer ${token}`
          }
        });
        alert("Đơn hàng đã được hủy thành công!");
        await getOrder();
      } catch (error) {
        console.error("Lỗi khi hủy đơn hàng:", error);
      }
    };

    const completeOrder = async (orderid) => {
      const token = Cookies.get("authToken");
      const apicomplete = `${apiUrl}completeOrder/${orderid}`;
      if (confirm("Bạn đã nhận được hàng?")) {
        try {
          await axios.get(apicomplete, {
            headers: {
              Authorization: `Bearer ${token}`
            }
          });
          alert("Đơn hàng đã được giao thành công!");
          await getOrder();
        } catch (error) {
          console.error("Lỗi khi hủy đơn hàng:", error);
        }
      }
    };

    const initiateReturnOrder = async (order) =>  {
      console.log("trả hàng: "+order);
      try {
        // Prompt the user for the return reason
        const returnReason = prompt("Nhập lý do trả hàng:");
        if (!returnReason) {
          alert("Bạn phải nhập lý do trả hàng!");
          return;
        }

        // 1. Lấy danh sách sản phẩm trong đơn hàng gốc
        const res = await axios.get(`http://localhost:8080/admin/orders/history/getprd/${order.id}`);
        const originalLines = res.data;

        // 2. Tạo dữ liệu đơn trả hàng mới
        const returnOrder = {
          address: order.address,
          note: `Trả hàng đơn #${order.id}. Lý do: ${returnReason}`,
          customerID: {
            id: order.customerID.id
          },
          paymentMethod: {
            id: order.paymentMethod.id
          },
          orderLine: originalLines.map(line => ({
            variationID: { id: line.variationID.id },
            quantity: line.quantity
          }))
        };

        // 3. Gửi yêu cầu tạo đơn trả hàng
        const createRes = await axios.post(`http://localhost:8080/admin/orders/return/${order.id}`, returnOrder);

        if (createRes.status === 200) {
          alert("Tạo đơn trả hàng thành công!");
          this.fetchOrder(this.currentPage, this.pageSize); // Refresh danh sách đơn hàng
        } else {
          alert("Không thể tạo đơn trả hàng!");
        }
      } catch (error) {
        console.error("Lỗi khi tạo đơn trả hàng:", error);
        alert("Đã xảy ra lỗi khi tạo đơn trả hàng!");
      }
    };

    const getOrder = async () => {
      const customer = JSON.parse(Cookies.get('customers') || '{}');
      if (!customer || !customer.id) {
        console.error("Người dùng chưa đăng nhập.");
        return;
      }
      const idCustomer = customer.id;
      const token = customer.token;
      const apiUrls = `${apiUrl}history/${idCustomer}`;
      try {
        const response = await axios.get(apiUrls, {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        });
        orders.value = response.data.content;
        console.log(orders.value);
      } catch (error) {
        console.error("Lỗi khi lấy dữ liệu đơn hàng:", error);
      }
    };

    const formatCurrency = (value) => {
      if (!value) return '';
      return new Intl.NumberFormat('vi-VN', {
        style: 'currency',
        currency: 'VND',
      }).format(value);
    };

    const changePassword = async () => {
      if (passwordFields.value[1].value !== passwordFields.value[2].value) {
        alert("Mật khẩu mới và xác nhận mật khẩu không trùng khớp!");
        return;
      }

      const token = Cookies.get("authToken");
      const apiCheckPassword = `${apiUrl}check-password`;
      const apiChangePassword = `${apiUrl}changepassword/` + user.value.username;

      const payload = {
        oldPassword: passwordFields.value[0].value,
        newPassword: passwordFields.value[1].value,
      };

      const loginRequest = {
        username: user.value.username,
        password: passwordFields.value[0].value,
      };

      const data = {
        username: user.value.username,
        password: payload.newPassword
      };

      console.log("loginRequest: " + loginRequest.username);
      console.log("loginRequest: " + loginRequest.password);
      try {
        // Xác minh mật khẩu cũ
        const checkResponse = await axios.post(apiCheckPassword, loginRequest, {
          headers: {Authorization: `Bearer ${token}`}
        });

        if (checkResponse.data != null) {
          // Nếu mật khẩu cũ đúng, gửi yêu cầu đổi mật khẩu
          await axios.post(apiChangePassword, data, {
            headers: {Authorization: `Bearer ${token}`}
          });
          alert("Đổi mật khẩu thành công!");
          passwordFields.value.forEach(field => field.value = ''); // Xóa dữ liệu nhập
        } else {
          alert("Mật khẩu cũ không đúng, vui lòng thử lại!");
        }
      } catch (error) {
        if (error.response && error.response.status === 401) {
          alert("Mật khẩu cũ không chính xác!"); // Hiển thị lỗi rõ ràng
          console.log("Lỗi xác thực:", error);
        } else {
          console.error("Lỗi không xác định:", error);
        }
      }
    };

  const isOver30Days = (dateString) => {
    if (!dateString) return false;

    const createdDate = dayjs(dateString, "YYYY-MM-DD HH:mm:ss"); // Parse đúng format
    const today = dayjs();
    const diffDays = today.diff(createdDate, 'day');
    console.log("created:", createdDate.format(), "now:", today.format(), "diff:", diffDays);

    return diffDays > 30;
  }

    const saveProfile = async () => {
      const token = Cookies.get("authToken");
      console.log("token: " + token);
      const apiUpdateProfile = `${apiUrl}updateInfo/${user.value.id}`;
      const {name, address, phone} = user.value.userInfo;

      // Input validation
      if (!name || !address || !phone) {
        alert("Vui lòng điền đầy đủ thông tin!");
        return;
      }
      if (!/^\d{10,11}$/.test(phone)) {
        alert("Số điện thoại không hợp lệ!");
        return;
      }

      const payload = {
        id: user.value.id,
        name,
        address,
        phone
      };

      try {
        await axios.post(apiUpdateProfile, payload, {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        });
        alert("Cập nhật hồ sơ thành công!");

        Cookies.set('Token', token);
        const apiUrls = `${apiUrl}user`;
        try {
          await axios.get(apiUrls, {
            headers: {
              Authorization: `Bearer ${token}`,
            },
          }).then((response) => {
            const rawData = response.data;
            Cookies.set("customers", JSON.stringify(rawData));
          })
        } catch (error) {
          console.error("Lỗi khi lấy lại hồ sơ:", error);
          alert("Đã xảy ra lỗi, vui lòng thử lại.");
        }
        location.reload();
      } catch (error) {
        console.error("Lỗi khi cập nhật hồ sơ:", error);
        alert("Đã xảy ra lỗi, vui lòng thử lại.");
      }
    };


    onMounted(() => {
      getOrder();
    });

    return {
      isOver30Days,
      initiateReturnOrder,
      viewProductDetail,
      selectedStatus,
      currentPage,
      itemsPerPage,
      totalPages,
      paginatedOrders,
      statusLabels,
      statusClasses,
      prevPage,
      nextPage,
      user,
      orders,
      selectedProduct,
      saveProfile,
      changePassword,
      cancelOrder,
      completeOrder,
      showProfile,
      showOrders,
      showOrdersLine,
      showChangePassword,
      activeTab,
      profileFields,
      passwordFields,
      viewOrderDetails,
      closeModal,
      formatCurrency,
      isModalVisible,
      selectedOrder,
      togglePasswordVisibility,
      formatDateTime,
      totalAmountByStatus,
      paginatedOrdersFiltered,
      itemsPerOrderDetailPage,
      currentOrderDetailPage,
      totalOrderDetailPages,
      paginatedOrderDetails,
      prevOrderDetailPage,
      nextOrderDetailPage,
    };
  },
};
</script>


<style scoped>
.container {
   width: 100%;
}
/* Style cho select box trạng thái */
.filter-section {
  margin-bottom: 15px;
  display: flex;
  align-items: center;
}

.filter-section label {
  font-weight: bold;
  margin-right: 10px;
}

.filter-section select {
  padding: 8px 12px;
  font-size: 14px;
  border: 1px solid #ccc;
  border-radius: 5px;
  background: white;
  cursor: pointer;
  transition: all 0.3s;
}

.filter-section select:hover {
  border-color: #00cccc;
}

/* Style cho nút chuyển trang */
.pagination {
  display: flex;
  align-items: center;
  justify-content: center;
  margin-top: 15px;
}

.loadmore-btn {
  padding: 8px 15px;
  font-size: 14px;
  font-weight: bold;
  color: white;
  background-color: #00cccc;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  transition: all 0.3s;
  margin: 0 5px;
}

.loadmore-btn:hover {
  background-color: #009999;
}

.loadmore-btn:disabled {
  background-color: #cccccc;
  cursor: not-allowed;
}

/* Căn giữa số trang */
.pagination span {
  font-size: 16px;
  font-weight: bold;
  margin: 0 10px;
}

.pagination h1 {
  font-size: 16px;
  margin: 0;
  font-weight: normal;
}

.total-amount {
  font-size: 16px;
  font-weight: bold;
  color: #ff5733;
  margin-left: 15px;
}

.status-badge {
  color: white;
  display: inline-block;
  padding: 4px 10px;
  border-radius: 20px;
  font-size: 12px;
  font-weight: 600;
  text-align: center;
  min-width: 100px;
  text-transform: uppercase;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
  transition: all 0.2s ease;
}

/* Mỗi trạng thái - bạn có thể tùy chỉnh tiếp */
.bg-cancelled {
  background-color: #f8d7da;
  color: #721c24;
}

.bg-pending {
  background-color: #fff3cd;
  color: #856404;
}

.bg-shipping {
  background-color: #cce5ff;
  color: #004085;
}

.bg-completed {
  background-color: #d4edda;
  color: #155724;
}

.bg-returned {
  background-color: #ffe0b3;
  color: #663c00;
}

</style>
