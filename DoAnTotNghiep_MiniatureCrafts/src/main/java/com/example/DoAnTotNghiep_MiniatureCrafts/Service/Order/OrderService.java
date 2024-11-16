package com.example.DoAnTotNghiep_MiniatureCrafts.Service.Order;

import com.example.DoAnTotNghiep_MiniatureCrafts.DTO.*;
import com.example.DoAnTotNghiep_MiniatureCrafts.Entity.*;
import com.example.DoAnTotNghiep_MiniatureCrafts.Repository.Order.OrderLineRepository;
import com.example.DoAnTotNghiep_MiniatureCrafts.Repository.Order.OrderRepository;
import com.example.DoAnTotNghiep_MiniatureCrafts.Repository.Order.PaymentMethodRepository;
import com.example.DoAnTotNghiep_MiniatureCrafts.Repository.Order.voucher.VoucherRepository;
import com.example.DoAnTotNghiep_MiniatureCrafts.Service.Product.VariationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
public class OrderService {
    // Chuyển đổi đơn vị tiền tệ thành VND
    NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));


    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderLineRepository orderLineRepository;

    @Autowired
    private VoucherRepository voucherRepository;

    @Autowired
    PaymentMethodRepository paymentMethodRepository;

    @Autowired
    VariationService variationService;
    public List<POSOrderDTO> getAllOrders(Long customerID) {
        List<POSOrder> list = orderRepository.findAll();

        return list.stream()
                .filter(order -> order.getCustomerID().getID().equals(customerID)) // Lọc các đơn hàng theo customerID
                .map(entity -> {
                    POSOrderDTO dto = new POSOrderDTO();
                    dto.setID(entity.getID());
                    dto.setCustomerID(mapCustomerEntityToDTO(entity.getCustomerID()));
                    dto.setCode_Voucher(entity.getCode_Voucher());
                    dto.setTotal_Amount(entity.getTotal_Amount() != null ? entity.getTotal_Amount().toString() : "0");
                    dto.setDiscount_Amount(entity.getDiscount_Amount() != null ? entity.getDiscount_Amount().toString() : "0");
                    dto.setTotal_Payment(entity.getTotal_Payment() != null ? entity.getTotal_Payment().toString() : "0");
                    dto.setPaymentMethod(new PaymentMethodDTO(
                            entity.getPaymentMethod().getID(),
                            entity.getPaymentMethod().getType(),
                            entity.getPaymentMethod().getNote(),
                            entity.getPaymentMethod().getStatus()
                    ));
                    dto.setCreation_date(entity.getCreation_date());
                    dto.setEdit_Date(entity.getEdit_Date());
                    dto.setType_Oder(entity.getType_Oder());
                    dto.setNote(entity.getNote());
                    dto.setStatus(entity.getStatus());
                    return dto; // Trả về dto trong lambda
                })
                .collect(Collectors.toList()); // Chuyển stream thành danh sách
    }

    // nếu như trả về lỗi, thì trang chi tiết sp sẽ báo k tìm thấy sản phẩm này
    // order phải trả về 1 list orderline
    public List<OrderLineDTO> getOrdersLine(Long orderID) {
        List<OrderLine> list = orderLineRepository.findAllOrderID(orderID);

        return list.stream()
                .map(orderLine -> new OrderLineDTO(orderLine)) // Truyền từng `OrderLine` vào constructor
                .collect(Collectors.toList());
    }

    public POSOrder update(POSOrderDTO posOrderDTO) {
        try {
            // Lấy Order hiện có từ CSDL
            POSOrder order = new POSOrder();
            // Chuyển đổi CustomerDTO sang Entity
            Customer customer = mapCustomerDTOToEntity(posOrderDTO.getCustomerID());
            order.setCustomerID(customer);

            // Kiểm tra và gán Voucher
            String voucherCode = posOrderDTO.getCode_Voucher();
            Voucher voucher = voucherRepository.findVoucherByCode(voucherCode);
            if (voucher == null) {
                throw new RuntimeException("Voucher không tồn tại: " + voucherCode);
            }
            order.setCode_Voucher(voucherCode);

            // Gán PaymentMethod
            PaymentMethodDTO paymentMethodDTO = posOrderDTO.getPaymentMethod();

            PaymentMethod paymentMethod = paymentMethodRepository.findById(paymentMethodDTO.getID())
                    .orElseThrow(() -> new RuntimeException("PaymentMethod không tồn tại với ID: " + paymentMethodDTO.getID()));
            order.setPaymentMethod(paymentMethod);

            // Cập nhật các trường khác
            order.setEdit_Date(LocalDate.now());
            order.setNote(posOrderDTO.getNote());

            // Lưu vào CSDL
            return orderRepository.save(order);
        } catch (RuntimeException e) {
            e.printStackTrace();
            throw new RuntimeException("Đã xảy ra lỗi trong quá trình cập nhật: " + e.getMessage());
        }
    }



    public OrderLine addOrderline(OrderLineDTO dto, Long orderID) {

        return orderLineRepository.save(
                new OrderLine(
                        dto.getID(),
                        orderID,
                        variationService.mapVariationDTOtoVariation(dto.getVariationID()),
                        dto.getVariationName(),
                        dto.getMarterial(),
                        dto.getQuantity(),
                        dto.getUnit_Price(),
                        dto.getPrice(),
                        dto.getCreation_date(),
                        dto.getEdit_Date(),
                        dto.getStatus()
                )
        );
    }

    public POSOrder addSHOP(POSOrderDTO posOrderDTO) {

        POSOrder order = new POSOrder();
        order.setID(posOrderDTO.getID());

        // chuyển thể từ Customer DTO sang customer
        Customer customer = mapCustomerDTOToEntity(posOrderDTO.getCustomerID());
        System.out.println("Customer ID: " + customer.getID());

        // gán customer vào customerID
        order.setCustomerID(customer);


        order.setCode_Voucher(posOrderDTO.getCode_Voucher());

        Voucher voucher = voucherRepository.findVoucherByCode(posOrderDTO.getCode_Voucher());
        // giá đc giảm
        BigDecimal discountAmount = BigDecimal.valueOf(voucher.getDiscountValue());
        System.out.println("Discount Amount: " + discountAmount);
        order.setDiscount_Amount(discountAmount);


        PaymentMethod payment = mapPaymentMethodDTOToEntity(posOrderDTO.getPaymentMethod());
        order.setPaymentMethod(payment);

        LocalDate today = LocalDate.now();
        order.setCreation_date(today);

        order.setNote(posOrderDTO.getNote());

        // json body bao gồm:
        //customerID.ID
        //status
        //type_Oder
        //creation_date
        //paymentMethod.id
        //code_Voucher
        //note
        return orderRepository.save(order);

    }

    public POSOrder addPOS(POSOrderDTO posOrderDTO) {

        POSOrder entity = new POSOrder();
        entity.setID(posOrderDTO.getID());

        // chuyển thể từ Customer DTO sang customer
        Customer customer = mapCustomerDTOToEntity(posOrderDTO.getCustomerID());
        System.out.println("Customer ID: " + customer.getID());

        // gán customer vào customerID
        entity.setCustomerID(customer);

        entity.setCode_Voucher(posOrderDTO.getCode_Voucher());

        Voucher voucher = voucherRepository.findVoucherByCode(posOrderDTO.getCode_Voucher());
        // giá đc giảm
        BigDecimal discountAmount = BigDecimal.valueOf(voucher.getDiscountValue());
        System.out.println("Discount Amount: " + discountAmount);
        entity.setDiscount_Amount(discountAmount);


        PaymentMethod payment = mapPaymentMethodDTOToEntity(posOrderDTO.getPaymentMethod());
        entity.setPaymentMethod(payment);

        LocalDate today = LocalDate.now();
        entity.setCreation_date(today);

        entity.setNote(posOrderDTO.getNote());

        // json body bao gồm:
        //customerID.ID
        //status
        //type_Oder
        //creation_date
        //paymentMethod.id
        //code_Voucher
        //note
        return orderRepository.save(entity);

    }


    public OrderLine mapOrderLineDTOToEntity(OrderLineDTO dto) {
        return new OrderLine(
                dto.getID(),
                dto.getOderID(),
                variationService.mapVariationDTOtoVariation(dto.getVariationID()),
                dto.getVariationName(),
                dto.getMarterial(),
                dto.getQuantity(),
                dto.getUnit_Price(),
                dto.getPrice(),
                dto.getCreation_date(),
                dto.getEdit_Date(),
                dto.getStatus()
        );
    }


    private Customer mapCustomerDTOToEntity(CustomerDTO dto) {
        return new Customer(
                    dto.getID(),
                    dto.getName(),
                    dto.getAddress(),
                    dto.getPhone(),
                    dto.getNote(),
                    dto.getStatus(),
                    dto.getCreation_date(),
                    dto.getEdit_Date()
        );
    }

    private CustomerDTO mapCustomerEntityToDTO(Customer entity) {
        return new CustomerDTO(
                    entity.getID(),
                    entity.getName(),
                    entity.getAddress(),
                    entity.getPhone(),
                    entity.getNote(),
                    entity.getStatus(),
                    entity.getCreation_date(),
                    entity.getEdit_Date()
        );
    }


    public POSOrderDTO mapOrderEntityToDTO(POSOrder entity) {
        POSOrderDTO dto = new POSOrderDTO();
        dto.setID(entity.getID());
        dto.setCustomerID(mapCustomerEntityToDTO(entity.getCustomerID()));
        dto.setCode_Voucher(entity.getCode_Voucher());
//        dto.setTotal_Amount(entity.getTotal_Amount().toString());
        dto.setDiscount_Amount(entity.getDiscount_Amount().toString());
        dto.setNote(entity.getNote());
        dto.setPaymentMethod(mapPaymentEntityToDTO(entity.getPaymentMethod()));
        dto.setStatus(entity.getStatus());
        dto.setType_Oder(entity.getType_Oder());
        return dto;
    }

    // Chuyển PaymentMethod entity sang DTO
    private PaymentMethodDTO mapPaymentEntityToDTO(PaymentMethod entity) {
        PaymentMethodDTO dto = new PaymentMethodDTO();
        dto.setID(entity.getID());
        dto.setType(entity.getType());
        dto.setNote(entity.getNote());
        dto.setStatus(entity.getStatus());
        return dto;
    }


    // Chuyển PaymentMethod entity sang DTO
    private PaymentMethod mapPaymentMethodDTOToEntity(PaymentMethodDTO dto) {
        PaymentMethod entity = new PaymentMethod();
        entity.setID(dto.getID());
        entity.setType(dto.getType());
        entity.setNote(dto.getNote());
        entity.setStatus(dto.getStatus());
        return entity;
    }




    public static double parseCurrency(String currency) throws ParseException {
        NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        Number number = formatter.parse(currency);
        return number.doubleValue();
    }
}
