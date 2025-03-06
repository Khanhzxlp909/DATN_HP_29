package com.example.hp_29_MiniatureCrafts.service.account;

import com.example.hp_29_MiniatureCrafts.dto.ContactRequest;
import com.example.hp_29_MiniatureCrafts.dto.OrderLineDTO;
import com.example.hp_29_MiniatureCrafts.entity.Account;
import com.example.hp_29_MiniatureCrafts.repository.auth.AccountRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.List;

import java.text.NumberFormat;
import java.util.Locale;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    AccountRepository accountRepository;

    public void sendOrderEmailByUser(String to,
                               String customerName,
                               List<OrderLineDTO> orderItems,
                               String totalPrice,
                               String customerAddress,
                               String paymentMethod) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setTo(to);
        helper.setSubject("🛍️ Đơn hàng từ MiniatureCrafts");

        // Format tiền tệ VND
        NumberFormat currencyFormat = NumberFormat.getInstance(new Locale("vi", "VN"));
        String formattedTotalPrice = currencyFormat.format(Double.parseDouble(totalPrice)) + " VND";

        StringBuilder htmlContent = new StringBuilder(String.format("""
            <!DOCTYPE html>
            <html lang="vi">
            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>Thông tin đơn hàng</title>
                <style>
                    body { font-family: Arial, sans-serif; background-color: #f4f4f4; padding: 20px; }
                    .container { 
                        max-width: 600px; 
                        margin: auto; 
                        background: white; 
                        padding: 20px; 
                        border-radius: 10px; 
                        box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
                    }
                    h2 { color: #2c3e50; text-align: center; }
                    .order-summary {
                        background: #ecf0f1; 
                        padding: 15px; 
                        border-radius: 8px; 
                        margin-top: 15px;
                    }
                    .order-item {
                        display: flex; 
                        align-items: center; 
                        border-bottom: 1px solid #ddd; 
                        padding: 10px 0;
                    }
                    .order-item:last-child { border-bottom: none; }
                    .order-item img { width: 80px; height: auto; margin-right: 15px; border-radius: 5px; }
                    .item-details { flex: 1; }
                    .total {
                        font-size: 18px; 
                        font-weight: bold; 
                        color: #e74c3c;
                        text-align: right;
                        margin-top: 15px;
                    }
                    .footer {
                        text-align: center;
                        margin-top: 20px;
                        font-size: 14px;
                        color: #7f8c8d;
                    }
                </style>
            </head>
            <body>
                <div class="container">
                    <h2>🎉 Cảm ơn, %s!</h2>
                    <p>Đơn hàng của bạn đã được xác nhận. Dưới đây là chi tiết:</p>
                    <div class="order-summary">
                        <h3>🛒 Chi tiết đơn hàng:</h3>
            """, customerName));

        // Lặp qua danh sách sản phẩm để thêm vào email
        for (OrderLineDTO item : orderItems) {
            System.out.println("item log 102: "+item.getVariationID().getPrice());
            String price = currencyFormat.format(item.getPrice()) + " VND";

            htmlContent.append(String.format("""
                <div class="order-item">
                    <div class="item-details">
                        <p><strong>%s</strong></p>
                        <p>Đơn giá: %s</p>
                        <p>Số lượng: %d</p>
                        <p>Thành tiền: %s</p>
                        
                    </div>
                </div>
        """, item.getVariationID().getProductID().getName(), item.getVariationID().getPrice(), item.getQuantity(), price ));
        }

        // Thêm phần tổng tiền và thông tin giao hàng
        htmlContent.append(String.format("""
            </div>
            <p class="total">Tổng cộng: %s</p>
            <p><strong>📍 Địa chỉ giao hàng:</strong> %s</p>
            <p><strong>💳 Phương thức thanh toán:</strong> %s</p>
            <div class="footer">
                📞 Cần hỗ trợ? Liên hệ với chúng tôi tại <a href="mailto:support@miniaturecrafts.com">support@miniaturecrafts.com</a>
                <br>❤️ Cảm ơn bạn đã mua sắm tại MiniatureCrafts!
            </div>
                </div>
            </body>
            </html>
            """, formattedTotalPrice, customerAddress, paymentMethod));

        helper.setText(htmlContent.toString(), true);
        mailSender.send(message);
    }

    public void sendOrderEmailByAdmin(
            String customerName,
            List<OrderLineDTO> orderItems,
            String totalPrice,
            String customerAddress,
            String paymentMethod) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        List<Account> admins = accountRepository.findByRolesAdmin();
        for (Account admin : admins) {
            helper.setTo(admin.getEmail());
            helper.setSubject("Thông báo đơn hàng từ MiniatureCrafts");

            // Format tiền tệ VND
            NumberFormat currencyFormat = NumberFormat.getInstance(new Locale("vi", "VN"));
            String formattedTotalPrice = currencyFormat.format(Double.parseDouble(totalPrice)) + " VND";

            StringBuilder htmlContent = new StringBuilder(String.format("""
            <!DOCTYPE html>
            <html lang="vi">
            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>Thông tin đơn hàng</title>
                <style>
                    body { font-family: Arial, sans-serif; background-color: #f4f4f4; padding: 20px; }
                    .container { 
                        max-width: 600px; 
                        margin: auto; 
                        background: white; 
                        padding: 20px; 
                        border-radius: 10px; 
                        box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
                    }
                    h2 { color: #2c3e50; text-align: center; }
                    .order-summary {
                        background: #ecf0f1; 
                        padding: 15px; 
                        border-radius: 8px; 
                        margin-top: 15px;
                    }
                    .order-item {
                        display: flex; 
                        align-items: center; 
                        border-bottom: 1px solid #ddd; 
                        padding: 10px 0;
                    }
                    .order-item:last-child { border-bottom: none; }
                    .order-item img { width: 80px; height: auto; margin-right: 15px; border-radius: 5px; }
                    .item-details { flex: 1; }
                    .total {
                        font-size: 18px; 
                        font-weight: bold; 
                        color: #e74c3c;
                        text-align: right;
                        margin-top: 15px;
                    }
                    .footer {
                        text-align: center;
                        margin-top: 20px;
                        font-size: 14px;
                        color: #7f8c8d;
                    }
                </style>
            </head>
            <body>
                <div class="container">
                    
                    <p>Đơn hàng vừa được tạo bởi khách hàng  %s!. Dưới đây là chi tiết:</p>
                    <div class="order-summary">
                        <h3>🛒 Chi tiết đơn hàng:</h3>
            """, customerName));

            // Lặp qua danh sách sản phẩm để thêm vào email
            for (OrderLineDTO item : orderItems) {
                System.out.println("item log 102: "+item.getVariationID().getPrice());
                String price = currencyFormat.format(item.getPrice()) + " VND";

                htmlContent.append(String.format("""
                <div class="order-item">
                    <div class="item-details">
                        <p><strong>%s</strong></p>
                        <p>Đơn giá: %s</p>
                        <p>Số lượng: %d</p>
                        <p>Thành tiền: %s</p>
                        
                    </div>
                </div>
        """, item.getVariationID().getProductID().getName(), item.getVariationID().getPrice(), item.getQuantity(), price ));
            }

            // Thêm phần tổng tiền và thông tin giao hàng
            htmlContent.append(String.format("""
            </div>
            <p class="total">Tổng cộng: %s</p>
            <p><strong>📍 Địa chỉ giao hàng:</strong> %s</p>
            <p><strong>💳 Phương thức thanh toán:</strong> %s</p>
                </div>
            </body>
            </html>
            """, formattedTotalPrice, customerAddress, paymentMethod));

            helper.setText(htmlContent.toString(), true);
            mailSender.send(message);
        }
    }

    public void contactToAdmin(ContactRequest request) throws MessagingException {
        List<Account> admins = accountRepository.findByRolesAdmin();

        if (admins.isEmpty()) {
            throw new MessagingException("Không tìm thấy admin để gửi email!");
        }

        for (Account admin : admins) {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(admin.getEmail());
            helper.setSubject("📩 Thông báo liên hệ từ khách hàng");

            String emailContent = String.format("""
                <html>
                <body>
                    <div style="font-family: Arial, sans-serif; padding: 20px; background-color: #f4f4f4;">
                        <div style="max-width: 600px; margin: auto; background: white; padding: 20px; border-radius: 10px; box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);">
                            <h2 style="text-align: center; color: #2c3e50;">📢 Thông tin liên hệ</h2>
                            <p><strong>👤 Họ và tên:</strong> %s</p>
                            <p><strong>📧 Email:</strong> %s</p>
                            <p><strong>📞 Số điện thoại:</strong> %s</p>
                            <p><strong>📝 Nội dung:</strong> %s</p>
                            <p style="text-align: center; color: #7f8c8d;">📬 Vui lòng phản hồi lại sớm nhất có thể.</p>
                        </div>
                    </div>
                </body>
                </html>
            """, request.getFullName(), request.getEmail(), request.getPhone(), request.getContent());

            helper.setText(emailContent, true);
            mailSender.send(message);
        }
    }
}
