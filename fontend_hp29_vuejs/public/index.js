// import axios from "axios";
// import {ref} from "vue";
//
// const user = ref(null);
//
// const getUserInfo = async () => {
//     const token = document.cookie.replace();
//     console.log(token);
//     if (!token) return;
//
//     try {
//         const response = await axios.get("http://localhost:8080/MiniatureCrafts/user", {
//             headers: { Authorization: `Bearer ${token}` },
//         });
//         user.value = response.data;
//         console.log("Thông tin user:", user.value);
//     } catch (error) {
//         console.error("Lỗi khi lấy thông tin user:", error);
//         user.value = null;
//     }
// };
//
// getUserInfo();