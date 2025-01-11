package com.example.hp_29_MiniatureCrafts.service.order;

import com.example.hp_29_MiniatureCrafts.dto.*;
import com.example.hp_29_MiniatureCrafts.entity.*;
import com.example.hp_29_MiniatureCrafts.repository.order.OrderLineRepository;
import com.example.hp_29_MiniatureCrafts.repository.order.OrderRepository;
import com.example.hp_29_MiniatureCrafts.repository.order.PaymentMethodRepository;
import com.example.hp_29_MiniatureCrafts.repository.order.voucher.VoucherRepository;
import com.example.hp_29_MiniatureCrafts.repository.product.ProductRepository;
import com.example.hp_29_MiniatureCrafts.service.product.VariationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    OrderRepository posOrderRepository;

    @Autowired
    OrderLineRepository orderLineRepository;

    @Autowired
    private VoucherRepository voucherRepository;

    @Autowired
    PaymentMethodRepository paymentMethodRepository;

    @Autowired
    VariationService variationService;

    @Autowired
    ProductRepository productRepository;

    //    @Transactional
    public void cancelOrder(Long orderId) {
        // Tìm đơn hàng theo ID
        POSOrder order = posOrderRepository.findByOrderID(orderId);
        // Lấy danh sách OrderLine liên quan đến đơn hàng
        List<OrderLine> orderLines = orderLineRepository.findAllOrderID(orderId);

        // Duyệt qua từng OrderLine để cập nhật số lượng cho Variation
        for (OrderLine orderLine : orderLines) {
            Variation variation = orderLine.getVariationID();
            int quantityOrdered = orderLine.getQuantity();
            orderLine.setStatus(false);
            orderLineRepository.save(orderLine);
            // Cập nhật số lượng cho Variation
            variation.setQuantity(variation.getQuantity() + quantityOrdered);
            variationService.save(variation); // Lưu lại sự thay đổi
        }

        // Xóa tất cả OrderLine liên quan đến đơn hàng
//        orderLineRepository.deleteAll(orderLines)
        order.setStatus(0);
        // Xóa đơn hàng
        posOrderRepository.save(order);
    }

    public List<OrderLineDTO> findOrderLine(Long orderId) {
        List<OrderLine> orderLines = orderLineRepository.findAllOrderID(orderId);

        // Chuyển đổi từ OrderLine sang OrderLineDTO
        return orderLines.stream()
                .map(this::mapOrderLineEntityToDTO)
                .collect(Collectors.toList());
    }


    public List<POSOrderDTO> getAllOrdersbyCustomer(Long customerID) {
        List<POSOrder> list = posOrderRepository.findAll();

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

    public Page<POSOrderDTO> findAllOrder(Pageable pageable) {
        Page<POSOrder> posOrders = posOrderRepository.findAll(pageable);

        return posOrders.map(entity -> {
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

        });
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
            return posOrderRepository.save(order);
        } catch (RuntimeException e) {
            e.printStackTrace();
            throw new RuntimeException("Đã xảy ra lỗi trong quá trình cập nhật: " + e.getMessage());
        }
    }


    public POSOrder orderInShop(POSOrderDTO dto) {
        POSOrder entity = new POSOrder();
        // Mapping từ DTO sang entity

        Customer customer = new Customer();
        customer.setID(dto.getCustomerID().getID());
        customer.setName(dto.getCustomerID().getName());
        customer.setAddress(dto.getCustomerID().getAddress());
        customer.setPhone(dto.getCustomerID().getPhone());
        customer.setNote(dto.getCustomerID().getNote());
        customer.setCreation_date(dto.getCustomerID().getCreation_date());
        customer.setEdit_Date(dto.getCustomerID().getEdit_Date());
        customer.setStatus(dto.getCustomerID().getStatus());

        entity.setCustomerID(customer);
        entity.setCode_Voucher(dto.getCode_Voucher());

        BigDecimal discountAmount = BigDecimal.ZERO; // Khởi tạo giá trị giảm giá là 0

        if (dto.getCode_Voucher() != null) {
            Voucher voucher = voucherRepository.findVoucherByCode(dto.getCode_Voucher());
            if (voucher == null) {
                throw new RuntimeException("Voucher không tồn tại với mã: " + dto.getCode_Voucher());
            }
            // Nếu voucher tồn tại, lấy giá trị giảm giá
            discountAmount = BigDecimal.valueOf(voucher.getDiscountValue());
            System.out.println("Discount Amount: " + discountAmount);
        } else {
            System.out.println("Không sử dụng voucher.");
        }

        entity.setDiscount_Amount(discountAmount);
        entity.setNote(dto.getNote());
        entity.setPaymentMethod(mapPaymentMethodDTOToEntity(dto.getPaymentMethod()));
        entity.setStatus(1);
        entity.setType_Oder(1);
        entity.setCreation_date(LocalDate.now());
        entity.setEdit_Date(LocalDate.now());

        POSOrder savedOrder = posOrderRepository.save(entity);

        if (dto.getOrderLine() != null) {
            for (OrderLineDTO orderLineDTO : dto.getOrderLine()) {
                System.out.println("OrderLineDTO: " + orderLineDTO.getVariationID().getID());
                // addOrderline(orderLineDTO, dto.getID());
            }
        }
        // Lưu vào database
        return savedOrder;
    }

    //
//    @Transactional
    public OrderLine addOrderline(OrderLineDTO orderLineDTO, Long orderId) {
        OrderLine orderLine = new OrderLine();

        // Mapping dữ liệu từ DTO sang entity
        orderLine.setOderID(orderId);

        Variation variation = variationService.findByIDEntity(orderLineDTO.getVariationID().getID());
        if (variation == null) {
            throw new RuntimeException("Không tìm thấy Variation với ID: " + orderLineDTO.getVariationID().getID());
        }

        // Kiểm tra số lượng trong kho
        int availableQuantity = variation.getQuantity();
        int requestedQuantity = orderLineDTO.getQuantity();
        if (requestedQuantity > availableQuantity) {
            throw new RuntimeException("Số lượng trong kho không đủ cho sản phẩm: " + variation.getProductID().getName());
        }

        // Cập nhật số lượng trong kho
        variation.setQuantity(availableQuantity - requestedQuantity);
        variationService.save(variation); // Giả sử bạn có hàm save() trong service để cập nhật database

        // Mapping các thông tin còn lại
        orderLine.setVariationID(variation);
        orderLine.setVariationName(variation.getProductID().getName());
        orderLine.setMarterial(variation.getMaterial());
        orderLine.setUnit_Price(variation.getPrice());

        // Tính toán giá và lưu dữ liệu
        double unit_price = variation.getPrice();
        double price = requestedQuantity * unit_price;
        orderLine.setPrice(price);
        orderLine.setQuantity(requestedQuantity);

        orderLine.setStatus(true);
        orderLine.setCreation_date(LocalDate.now());
        orderLine.setEdit_Date(LocalDate.now());

        // Lưu OrderLine vào database
        return orderLineRepository.save(orderLine);
    }


    public POSOrder orderInPOS(POSOrderDTO dto) {
        try {
            // Tạo một đối tượng POSOrder mới
            POSOrder entity = new POSOrder();

            // Mapping từ DTO sang Entity
            Customer customer = mapCustomerDTOToEntity(dto.getCustomerID());
            entity.setCustomerID(customer);

            // Kiểm tra và gán Voucher nếu tồn tại
            String voucherCode = dto.getCode_Voucher();
            if (voucherCode != null && !voucherCode.isEmpty()) {
                Voucher voucher = voucherRepository.findVoucherByCode(voucherCode);
                if (voucher == null) {
                    throw new RuntimeException("Voucher không tồn tại với mã: " + voucherCode);
                }
                entity.setCode_Voucher(voucherCode);
                BigDecimal discountAmount = BigDecimal.valueOf(voucher.getDiscountValue());
                entity.setDiscount_Amount(discountAmount);
            } else {
                entity.setDiscount_Amount(BigDecimal.ZERO); // Nếu không có voucher, đặt giảm giá là 0
            }

            // Gán thông tin PaymentMethod
            PaymentMethod paymentMethod = mapPaymentMethodDTOToEntity(dto.getPaymentMethod());
            entity.setPaymentMethod(paymentMethod);

            // Gán thông tin khác
            entity.setStatus(1); // Đơn hàng mặc định ở trạng thái "Hoàn thành"
            entity.setType_Oder(dto.getType_Oder() != null ? dto.getType_Oder() : 0); // Đơn hàng POS (type_Oder = 0)
            entity.setCreation_date(LocalDate.now());
            entity.setEdit_Date(LocalDate.now());
            entity.setNote(dto.getNote());

            // Lưu thông tin đơn hàng vào database
            POSOrder savedOrder = posOrderRepository.save(entity);

            // Xử lý danh sách OrderLine
            if (dto.getOrderLine() != null) {
                for (OrderLineDTO orderLineDTO : dto.getOrderLine()) {
                    OrderLine orderLine = new OrderLine();
                    orderLine.setOderID(savedOrder.getID());

                    // Tìm Variation dựa trên ID
                    Variation variation = variationService.findByIDEntity(orderLineDTO.getVariationID().getID());
                    if (variation == null) {
                        throw new RuntimeException("Không tìm thấy sản phẩm với ID: " + orderLineDTO.getVariationID().getID());
                    }

                    // Kiểm tra số lượng tồn kho
                    int availableQuantity = variation.getQuantity();
                    int requestedQuantity = orderLineDTO.getQuantity();
                    if (requestedQuantity > availableQuantity) {
                        throw new RuntimeException("Số lượng tồn kho không đủ cho sản phẩm: " + variation.getProductID().getName());
                    }

                    // Cập nhật số lượng tồn kho
                    variation.setQuantity(availableQuantity - requestedQuantity);
                    variationService.save(variation);

                    // Mapping thông tin OrderLine
                    orderLine.setVariationID(variation);
                    orderLine.setVariationName(variation.getProductID().getName());
                    orderLine.setMarterial(variation.getMaterial());
                    orderLine.setUnit_Price(variation.getPrice());
                    orderLine.setQuantity(requestedQuantity);
                    orderLine.setPrice(requestedQuantity * variation.getPrice());
                    orderLine.setStatus(true);
                    orderLine.setCreation_date(LocalDate.now());
                    orderLine.setEdit_Date(LocalDate.now());

                    // Lưu OrderLine vào database
                    orderLineRepository.save(orderLine);
                }
            }

            return savedOrder; // Trả về đối tượng POSOrder đã lưu
        } catch (RuntimeException e) {
            e.printStackTrace();
            throw new RuntimeException("Đã xảy ra lỗi khi đặt hàng POS: " + e.getMessage());
        }
    }


    public OrderLineDTO mapOrderLineEntityToDTO(OrderLine entity) {
        return new OrderLineDTO(
                entity.getID(),
                entity.getOderID(),
                variationService.mapVariationToVariationDTO(entity.getVariationID()),
                entity.getVariationName(),
                entity.getMarterial(),
                entity.getQuantity(),
                entity.getUnit_Price(),
                entity.getPrice(),
                entity.getCreation_date(),
                entity.getEdit_Date(),
                entity.getStatus()
        );
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
        // Lấy danh sách OrderLine từ repository và chuyển đổi sang DTO
        List<OrderLineDTO> orderLineDTOs = orderLineRepository.findAllOrderID(entity.getID())
                .stream()
                .map(this::mapOrderLineEntityToDTO) // Chuyển từng OrderLine Entity sang DTO
                .toList();
        dto.setOrderLine(orderLineDTOs);
        dto.setCode_Voucher(entity.getCode_Voucher());
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


//    public POSOrder addSHOP(POSOrderDTO dto) {
//
//        POSOrder entity = new POSOrder();
//        entity.setID(dto.getID());
//
//        // chuyển thể từ Customer DTO sang customer
//        Customer customer = mapCustomerDTOToEntity(dto.getCustomerID());
//        System.out.println("Customer ID: " + customer.getID());
//
//        // gán customer vào customerID
//        entity.setCustomerID(customer);
//
//
//        entity.setCode_Voucher(dto.getCode_Voucher());
//
//        Voucher voucher = voucherRepository.findVoucherByCode(dto.getCode_Voucher());
//        // giá đc giảm
//        BigDecimal discountAmount = BigDecimal.valueOf(voucher.getDiscountValue());
//        System.out.println("Discount Amount: " + discountAmount);
//        entity.setDiscount_Amount(discountAmount);
//
//
//        PaymentMethod payment = mapPaymentMethodDTOToEntity(dto.getPaymentMethod());
//        entity.setPaymentMethod(payment);
//
//        LocalDate today = LocalDate.now();
//        entity.setCreation_date(today);
//
//        entity.setNote(dto.getNote());
//
//        entity.setType_Oder(1);
//
//        // json body bao gồm:
//        //customerID.ID
//        //status
//        //type_Oder
//        //creation_date
//        //paymentMethod.id
//        //code_Voucher
//        //note
//        return posOrderRepository.save(entity);
//
//    }
}
