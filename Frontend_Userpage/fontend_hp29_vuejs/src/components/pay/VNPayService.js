import axios from "axios";

const API_URL = "http://localhost:8080/api/vnpay";

export const createVNPayPayment = async (amount, orderId) => {
    try {
        const response = await axios.get(`${API_URL}/create_payment`, {
            params: { amount, orderId, ipAddr: "127.0.0.1" }
        });
        return response.data.paymentUrl;
    } catch (error) {
        console.error("Lỗi tạo thanh toán VNPay:", error);
        return null;
    }
};
