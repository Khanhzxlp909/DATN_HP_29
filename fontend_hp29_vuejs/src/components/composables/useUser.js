import { ref } from 'vue';
import axios from 'axios';
import Cookies from 'js-cookie';

const user = ref(null);
const cart = ref(null);
const searchKeyword = ref('');
const apiUrl = "http://localhost:8080/api/v1/cart";
const token = Cookies.get("authToken");

const getUserInfo = async () => {
    if (!token) {
        console.error("No token found, please log in!");
        return;
    }

    try {
        const response = await axios.get("http://localhost:8080/MiniatureCrafts/user", {
            headers: {
                Authorization: `Bearer ${token}`,
            },
        });
        user.value = response.data;
        await getCart(); // gọi cart sau khi user có data
    } catch (error) {
        console.error("Error fetching user info:", error);
        user.value = null;
    }
};

const getCart = async () => {
    if (!user.value) return;

    try {
        const response = await axios.get(`${apiUrl}/count/${user.value.userInfo.id}`, {
            headers: {
                Authorization: `Bearer ${token}`,
            },
        });
        cart.value = response.data;
        console.log("cart count:", cart.value);
    } catch (error) {
        console.error("Lỗi khi lấy giỏ hàng:", error);
    }
};

const logout = () => {
    Cookies.remove("authToken");
    Cookies.remove("customers");
    user.value = null;
    window.location.href = "/"; // Redirect to login page
};

export function useUser() {
    return {
        user,
        cart,
        searchKeyword,
        getUserInfo,
        getCart,
        logout,
    };
}
