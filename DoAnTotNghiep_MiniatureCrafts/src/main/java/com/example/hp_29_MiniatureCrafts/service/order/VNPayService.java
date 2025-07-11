package com.example.hp_29_MiniatureCrafts.service.order;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
public class VNPayService {

    @Value("${vnpay.tmnCode}")
    private String vnp_TmnCode;

    @Value("${vnpay.hashSecret}")
    private String vnp_HashSecret;

    @Value("${vnpay.url}")
    private String vnp_PayUrl;

    @Value("${vnpay.returnUrl}")
    private String vnp_ReturnUrl;

    public String createPaymentUrl(long amount, String orderId, String ipAddr) {
        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", "2.1.0");
        vnp_Params.put("vnp_Command", "pay");
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount * 100)); // VNPay yêu cầu nhân 100
        vnp_Params.put("vnp_CurrCode", "VND");
        vnp_Params.put("vnp_TxnRef", orderId);
        vnp_Params.put("vnp_OrderInfo", "Thanh toán đơn hàng " + orderId);
        vnp_Params.put("vnp_OrderType", "billpayment");
        vnp_Params.put("vnp_Locale", "vn");
        vnp_Params.put("vnp_ReturnUrl", vnp_ReturnUrl);
        vnp_Params.put("vnp_IpAddr", ipAddr);
        vnp_Params.put("vnp_CreateDate", new java.text.SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));

        // ✅ Gọi buildQuery và bao gồm chữ ký `vnp_SecureHash`
        String queryUrl = buildQuery(vnp_Params, true);

        return vnp_PayUrl + "?" + queryUrl;
    }


    public String paymentCallback(Map<String, String> params) {
        String secureHash = params.get("vnp_SecureHash");
        params.remove("vnp_SecureHash"); // ✅ Loại bỏ trước khi tạo hashData

        String hashData = buildQuery(params, false); // ✅ Không tạo lại secureHash
        String computedHash = hmacSHA512(vnp_HashSecret, hashData);

        if (!computedHash.equalsIgnoreCase(secureHash)) { // ✅ So sánh không phân biệt hoa thường
            return "Lỗi xác thực giao dịch!";
        }

        String responseCode = params.get("vnp_ResponseCode");
        if ("00".equals(responseCode)) {
            return "Thanh toán thành công!";
        } else {
            return "Giao dịch thất bại! Mã lỗi: " + responseCode;
        }
    }



    private String buildQuery(Map<String, String> params, boolean includeSecureHash) {
        List<String> keys = new ArrayList<>(params.keySet());
        Collections.sort(keys); // ✅ Sắp xếp tham số theo thứ tự ABC

        StringBuilder query = new StringBuilder();
        StringBuilder hashData = new StringBuilder();

        for (String key : keys) {
            String value = params.get(key);
            if (value != null && !value.isEmpty()) {
                query.append(URLEncoder.encode(key, StandardCharsets.UTF_8))
                        .append("=")
                        .append(URLEncoder.encode(value, StandardCharsets.UTF_8))
                        .append("&");

                // ✅ Chỉ dùng để tạo hashData (không encode lại giá trị)
                hashData.append(key).append("=").append(value).append("&");
            }
        }

        // ✅ Xóa dấu `&` cuối cùng
        if (query.length() > 0) query.setLength(query.length() - 1);
        if (hashData.length() > 0) hashData.setLength(hashData.length() - 1);

        if (includeSecureHash) {
            String secureHash = hmacSHA512(vnp_HashSecret, hashData.toString());
            query.append("&vnp_SecureHash=").append(secureHash);
        }

        return query.toString();
    }



    private String hmacSHA512(String key, String data) {
        try {
            Mac mac = Mac.getInstance("HmacSHA512");
            SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "HmacSHA512");
            mac.init(secretKey);
            byte[] hash = mac.doFinal(data.getBytes(StandardCharsets.UTF_8));

            // ✅ Chuyển từ byte array sang HEX string
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0'); // ✅ Đảm bảo 2 ký tự cho mỗi byte
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi tạo chữ ký SHA512", e);
        }
    }

}
