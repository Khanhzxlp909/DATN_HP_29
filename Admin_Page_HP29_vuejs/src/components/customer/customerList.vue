<template>
  <div id="app" class="app sidebar-mini rtl">
    <header class="app-header">
      <a
          class="app-sidebar__toggle"
          href="#"
          data-toggle="sidebar"
          aria-label="Hide Sidebar"
      ></a>
      <ul class="app-nav">
        <li>
          <a class="app-nav__item" href="/index.html">
            <i class="bx bx-log-out bx-rotate-180"></i>
          </a>
        </li>
      </ul>
    </header>

    <main class="app-content">
      <div class="app-title">
        <ul class="app-breadcrumb breadcrumb side">
          <li class="breadcrumb-item active">
            <a href="#"><b>Tạo mới khách hàng</b></a>
          </li>
        </ul>
      </div>

      <div class="row">
        <div class="col-md-12">
          <div class="tile">
            <h3 class="tile-title">Thông tin khách hàng</h3>
            <div class="tile-body">
              <form @submit.prevent="submitForm">
                <div class="row">
                  <div class="form-group col-md-4">
                    <label for="customerId">ID khách hàng:</label>
                    <input
                        v-model="customer.id"
                        class="form-control"
                        type="text"
                        id="customerId"
                        disabled
                    />
                  </div>
                  <div class="form-group col-md-4">
                    <label for="customerName">Họ và tên:</label>
                    <input
                        v-model="customer.name"
                        class="form-control"
                        type="text"
                        id="customerName"
                         
                    />
                  </div>
                  <div class="form-group col-md-4">
                    <label for="customerAddress">Địa chỉ:</label>
                    <input
                        v-model="customer.address"
                        class="form-control"
                        type="text"
                        id="customerAddress"
                         
                    />
                  </div>
                  <div class="form-group col-md-4">
                    <label for="customerPhone">Số điện thoại:</label>
                    <input
                        v-model="customer.phone"
                        class="form-control"
                        type="text"
                        id="customerPhone"
                         
                    />
                  </div>
                  <div class="form-group col-md-4">
                    <label for="customerNote">Ghi chú:</label>
                    <input
                        v-model="customer.note"
                        class="form-control"
                        type="text"
                        id="customerNote"
                         
                    />
                  </div>
                  <div class="form-group col-md-4">
                    <label for="customerStatus">Trạng thái:</label>
                    <select
                        v-model="customer.status"
                        class="form-control"
                        id="customerStatus"
                         
                    >
                      <option value="true">Hoạt động</option>
                      <option value="false">Không hoạt động</option>
                    </select>
                  </div>
                  <div class="form-group col-md-4">
                    <label for="creationDate">Ngày tạo:</label>
                    <input
                        v-model="customer.creation_date"
                        class="form-control"
                        type="date"
                        id="creationDate"
                        disabled
                    />
                  </div>
                </div>
                <div class="form-group">
                  <button class="btn btn-primary" type="submit">Lưu lại</button>
                </div>
              </form>
            </div>
          </div>

          <div class="tile">
            <h3 class="tile-title">Danh sách khách hàng</h3>
            <div class="tile-body">
              <div class="form-group col-md-4">
                <label for="searchCustomer">Tìm kiếm:</label>
                <input
                    v-model="findCustomer"
                    @input="searchCustomer"
                    class="form-control"
                    type="text"
                    id="searchCustomer"
                    placeholder="Nhập tên khách hàng..."
                />
              </div>

              <div class="row element-button">
                <div class="col-sm-2">
                  <router-link
                      class="btn btn-add btn-sm"
                      to="/customer/add"
                      title="Thêm"
                  >
                    <i class="fas fa-plus"></i> Tạo mới khách hàng
                  </router-link>
                </div>
              </div>
              <table
                  class="table table-hover table-bordered js-copytextarea"
                  cellpadding="0"
                  cellspacing="0"
                  border="0"
                  id="customerTable"
              >
                <thead>
                <tr>
                  <th width="30">ID</th>
                  <th width="300">Họ và tên</th>
                  <th width="300">Địa chỉ</th>
                  <th>Ngày tạo</th>
                  <th>SĐT</th>
                  <th>Trạng thái</th>
                  <th>Ghi chú</th>
                  <th width="100">Tính năng</th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="customer in paginatedCustomers" :key="customer.id">
                  <td @click="goToEdit(customer.id)">{{ customer.id }}</td>
                  <td @click="goToEdit(customer.id)">{{ customer.name }}</td>
                  <td @click="goToEdit(customer.id)">{{ customer.address }}</td>
                  <td @click="goToEdit(customer.id)">{{ customer.creation_date }}</td>
                  <td @click="goToEdit(customer.id)">{{ customer.phone }}</td>
                  <td @click="goToEdit(customer.id)">
                    <span :class="{'badge bg-success': customer.status, 'badge bg-danger': !customer.status}">
                        {{ customer.status ? "Đang hoạt động" : "Không hoạt động" }}
                      </span>
                  </td>
                  <td @click="goToEdit(customer.id)">{{ customer.note }}</td>
                  <td class="table-td-center">
                    <button
                        class="btn btn-primary btn-sm trash"
                        type="button"
                        title="Xóa"
                        @click="confirmDelete(customer.id)"
                    >
                      <i class="fas fa-trash-alt"></i> Xóa
                    </button>
                  </td>
                </tr>
                </tbody>
              </table>

              <!-- Phân trang -->
              <div class="pagination">
                <button
                    :disabled="currentPage === 1"
                    @click="changePage(currentPage - 1)"
                    class="btn btn-primary btn-sm"
                >
                  Trước
                </button>
                <button
                    v-for="page in totalPages"
                    :key="page"
                    @click="changePage(page)"
                    :class="['btn', 'btn-sm', currentPage === page ? 'btn-primary' : 'btn-secondary']"
                >
                  {{ page }}
                </button>
                <button
                    :disabled="currentPage === totalPages"
                    @click="changePage(currentPage + 1)"
                    class="btn btn-primary btn-sm"
                >
                  Tiếp
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
import {ref, computed, onMounted} from "vue";
import axios from "axios";
import Cookies from "js-cookie";

export default {
  setup() {
    const customer = ref({
      id: null,
      name: "",
      address: "",
      phone: "",
      note: "",
      status: true,
      creation_date: new Date().toISOString().split("T")[0],
    });
    const customers = ref([]);
    const currentPage = ref(1);
    const itemsPerPage = ref(5);
    const totalItems = ref(0);

    const findCustomer = ref(""); // Biến lưu giá trị tìm kiếm

    const searchCustomer = async () => {
      if (findCustomer.value.trim() === "") {
        fetchCustomers(); // Nếu rỗng, load lại toàn bộ danh sách
        return;
      }

      try {
        const response = await axios.get(`http://localhost:8080/admin/customer/result/${findCustomer.value}`);

        customers.value = response.data; // Cập nhật danh sách tìm kiếm
        totalItems.value = customers.value.length;
      } catch (error) {
        console.error("Lỗi khi tìm kiếm khách hàng:", error);
        alert("Không thể tìm kiếm khách hàng.");
      }
    };

    const fetchCustomers = async () => {
      try {
        const response = await axios.get(`http://localhost:8080/admin/customer/result/all`);
        customers.value = response.data;
        totalItems.value = customers.value.length;
      } catch (error) {
        console.error("Có lỗi xảy ra khi lấy dữ liệu khách hàng:", error);
      }
    };

    const paginatedCustomers = computed(() => {
      const start = (currentPage.value - 1) * itemsPerPage.value;
      const end = start + itemsPerPage.value;
      return customers.value.slice(start, end);
    });

    const totalPages = computed(() => Math.ceil(totalItems.value / itemsPerPage.value));

    const changePage = (page) => {
      currentPage.value = page;
    };

    const goToEdit = (id) => {
      window.location.href = `/customer/edit?id=${id}`;
    };

    const confirmDelete = (id) => {
      if (confirm("Bạn có chắc chắn muốn xóa khách hàng này?")) {
        deleteCustomer(id);
      }
    };

    const deleteCustomer = async (id) => {
      try {
        const token = Cookies.get("token"); // Lấy token từ cookies
        const url = `http://localhost:8080/admin/customer/delete/${id}`;
        await axios.get(url,{
          headers: {
            Authorization: `Bearer ${token}` // Thêm token vào header
          }
        });
        fetchCustomers(); // Refresh danh sách sau khi xóa
        alert("Khách hàng đã được xóa thành công.");
      } catch (error) {
        console.error("Có lỗi xảy ra khi xóa khách hàng:", error);
        alert("Xóa khách hàng thất bại. Vui lòng thử lại.");
      }
    };

    const submitForm = async () => {
      // Custom validation
      if (!customer.value.name.trim()) {
        alert("Họ và tên không được để trống.");
        return;
      }

      if (!customer.value.address.trim()) {
        alert("Địa chỉ không được để trống.");
        return;
      }

      const phonePattern = /^[0-9]{10,15}$/; // Example pattern for phone numbers
      if (!phonePattern.test(customer.value.phone)) {
        alert("Số điện thoại không hợp lệ. Vui lòng nhập từ 10 đến 15 chữ số.");
        return;
      }

      if (!customer.value.note.trim()) {
        alert("Ghi chú không được để trống.");
        return;
      }

      try {
        console.log("Bắt đầu gửi form...");
        console.log("Dữ liệu khách hàng:", customer.value);

        const token = Cookies.get("token");

        if (!token) {
          console.error("Token không tồn tại hoặc người dùng chưa đăng nhập.");
          alert("Chưa đăng nhập hoặc token không tồn tại!");
          return;
        }
        console.log("Token lấy từ cookies:", token);
        const api = "http://localhost:8080/admin/customer/save";
        const payload = {
          name: customer.value.name,
          address: customer.value.address,
          phone: customer.value.phone,
          note: customer.value.note,
          status: customer.value.status,
          edit_Date: null,
        };
        console.log("Payload gửi tới API:", payload);

        const response = await axios.post(api, payload, {
          headers: {
            Authorization: `Bearer ${token}`,
          },
          withCredentials: true,
        });

        console.log("Phản hồi từ API:", response.data);
        alert("Tạo khách hàng thành công!");
        fetchCustomers(); // Refresh danh sách sau khi tạo mới
      } catch (error) {
        console.error("Lỗi khi gửi form:", error);
        alert("Có lỗi xảy ra, vui lòng thử lại!");
      }
    };

    onMounted(() => {
      fetchCustomers();
    });

    return {
      customer,
      paginatedCustomers,
      currentPage,
      totalPages,
      fetchCustomers,
      changePage,
      confirmDelete,
      goToEdit,
      submitForm,
      findCustomer,   // Thêm biến tìm kiếm
      searchCustomer, // Thêm hàm tìm kiếm
    };
  },
};
</script>

<style scoped>

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