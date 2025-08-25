package com.example.hp_29_MiniatureCrafts.service.order;

import com.example.hp_29_MiniatureCrafts.dto.*;
import com.example.hp_29_MiniatureCrafts.entity.*;
import com.example.hp_29_MiniatureCrafts.repository.order.OrderLineRepository;
import com.example.hp_29_MiniatureCrafts.repository.order.OrderRepository;
import com.example.hp_29_MiniatureCrafts.repository.order.PaymentMethodRepository;
import com.example.hp_29_MiniatureCrafts.repository.product.ProductRepository;
import com.example.hp_29_MiniatureCrafts.repository.product.VariationRepository;
import com.example.hp_29_MiniatureCrafts.service.product.VariationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.NumberFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
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
    PaymentMethodRepository paymentMethodRepository;

    @Autowired
    VariationService variationService;

    @Autowired
    private VariationRepository variationRepository;

    @Autowired
    ProductRepository productRepository;

    public POSOrderDTO findOrderById(Long orderId) {
        // Tìm đơn hàng theo ID
        POSOrder order = posOrderRepository.findByOrderID(orderId);

        // Kiểm tra xem đơn hàng có tồn tại không
        if (order == null) {
            throw new RuntimeException("Không tìm thấy đơn hàng với ID: " + orderId);
        }

        // Chuyển đổi từ POSOrder sang POSOrderDTO
        return mapOrderEntityToDTOBysendEmail(order);
    }

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
            variationRepository.save(variation); // Lưu lại sự thay đổi
        }
        order.setStatus(0);
        // Xóa đơn hàng
        posOrderRepository.save(order);
    }

    public void successOrder(Long orderId) {
        // Tìm đơn hàng theo ID
        POSOrder order = posOrderRepository.findByOrderID(orderId);
        order.setStatus(2);
        posOrderRepository.save(order);
    }

    public void accpectReturn(Long orderId, String note) {
        // Tìm đơn hàng theo ID
        POSOrder order = posOrderRepository.findByOrderID(orderId);
        order.setNote(note);
        order.setStatus(5);
        posOrderRepository.save(order);
    }

    public void completeOrder(Long orderId) {
        // Tìm đơn hàng theo ID
        POSOrder order = posOrderRepository.findByOrderID(orderId);
        order.setPayment_Time(LocalDateTime.now());
        order.setStatus(3);
        posOrderRepository.save(order);
    }

    public void completeReturnOrder(Long orderId) {
        // Tìm đơn hàng theo ID
        POSOrder order = posOrderRepository.findByOrderID(orderId);
        order.setPayment_Time(LocalDateTime.now());
        order.setStatus(4);
        posOrderRepository.save(order);
    }

    public List<OrderLineDTO> findOrderLine(Long orderId) {
        List<OrderLine> orderLines = orderLineRepository.findAllOrderID(orderId);

        // Chuyển đổi từ OrderLine sang OrderLineDTO
        return orderLines.stream().map(this::mapOrderLineEntityToDTO).collect(Collectors.toList());
    }


    public Page<POSOrderDTO> getAllOrdersbyCustomer(Pageable pageable, Long customerID) {
        Page<POSOrder> list = posOrderRepository.findAllBYDesc(pageable, customerID);

        return list.map(entity -> {
            POSOrderDTO dto = new POSOrderDTO();
            dto.setID(entity.getID());
            dto.setCustomerID(mapCustomerEntityToDTO(entity.getCustomerID()));
            dto.setAddress(entity.getAddress());
//            dto.setCode_Voucher(entity.getCode_Voucher());
            dto.setTotal_Amount(entity.getTotal_Amount() != null ? entity.getTotal_Amount().toString() : "0");
//            dto.setDiscount_Amount(entity.getDiscount_Amount() != null ? entity.getDiscount_Amount().toString() : "0");
            dto.setTotal_Payment(entity.getTotal_Payment() != null ? entity.getTotal_Payment().toString() : "0");
            dto.setPaymentMethod(new PaymentMethodDTO(entity.getPaymentMethod().getID(), entity.getPaymentMethod().getType(), entity.getPaymentMethod().getNote(), entity.getPaymentMethod().getStatus()));
            dto.setOrder_Time(entity.getOrder_Time());
            dto.setPayment_Time(entity.getPayment_Time());
            dto.setType_Oder(entity.getType_Oder());
            dto.setNote(entity.getNote());
            dto.setStatus(entity.getStatus());
            return dto; // Trả về dto trong lambda
        });
        // Chuyển stream thành danh sách
    }

    public POSOrderDTO getOrderById(Long orderID) {
        POSOrder entity = posOrderRepository.findByOrderID(orderID);

        if (entity == null) {
            throw new RuntimeException("Không tìm thấy đơn hàng với ID: " + orderID);
        }

        POSOrderDTO dto = new POSOrderDTO();
        dto.setID(entity.getID());
        dto.setCustomerID(mapCustomerEntityToDTO(entity.getCustomerID()));
        dto.setAddress(entity.getAddress());
        dto.setTotal_Amount(entity.getTotal_Amount() != null ? entity.getTotal_Amount().toString() : "0");
        dto.setTotal_Payment(entity.getTotal_Payment() != null ? entity.getTotal_Payment().toString() : "0");
        dto.setPaymentMethod(new PaymentMethodDTO(entity.getPaymentMethod().getID(), entity.getPaymentMethod().getType(), entity.getPaymentMethod().getNote(), entity.getPaymentMethod().getStatus()));
        dto.setOrder_Time(entity.getOrder_Time());
        dto.setPayment_Time(entity.getPayment_Time());
        dto.setType_Oder(entity.getType_Oder());
        dto.setNote(entity.getNote());
        dto.setStatus(entity.getStatus());
        dto.setOrderLine(getAllOrderLinesByOrderId(entity.getID()));
        return dto;
    }

    public List<OrderLineDTO> getAllOrderLinesByOrderId(Long orderId) {
        List<OrderLine> orderLines = orderLineRepository.findAllOrderID(orderId);

        // Chuyển đổi từ OrderLine sang OrderLineDTO
        return orderLines.stream().map(this::mapOrderLineEntityToDTO).collect(Collectors.toList());
    }

    public Page<POSOrderDTO> findAllOrder(Pageable pageable) {
        Page<POSOrder> posOrders = posOrderRepository.findAllDESC(pageable);

        return posOrders.map(entity -> {
            POSOrderDTO dto = new POSOrderDTO();
            dto.setID(entity.getID());
            dto.setCustomerID(mapCustomerEntityToDTO(entity.getCustomerID()));
            dto.setAddress(entity.getAddress());
//            dto.setCode_Voucher(entity.getCode_Voucher());
            dto.setTotal_Amount(entity.getTotal_Amount() != null ? entity.getTotal_Amount().toString() : "0");
//            dto.setDiscount_Amount(entity.getDiscount_Amount() != null ? entity.getDiscount_Amount().toString() : "0");
            dto.setTotal_Payment(entity.getTotal_Payment() != null ? entity.getTotal_Payment().toString() : "0");
            dto.setPaymentMethod(new PaymentMethodDTO(entity.getPaymentMethod().getID(), entity.getPaymentMethod().getType(), entity.getPaymentMethod().getNote(), entity.getPaymentMethod().getStatus()));
            dto.setOrder_Time(entity.getOrder_Time());
            dto.setPayment_Time(entity.getPayment_Time());
            dto.setType_Oder(entity.getType_Oder());
            dto.setNote(entity.getNote());
            dto.setStatus(entity.getStatus());

            // 💡 Gán statusText dựa vào trạng thái
            dto.setStatusText(mapStatusToText(entity.getStatus()));

            return dto; // Trả về DTO
        });
    }

    private String mapStatusToText(int status) {
        Map<Integer, String> statusMap = new HashMap<>();
        statusMap.put(0, "Đã hủy");
        statusMap.put(1, "Chờ xác nhận");
        statusMap.put(2, "Đang giao hàng");
        statusMap.put(3, "Đã giao hàng thành công");
        statusMap.put(4, "Trả hàng");
        statusMap.put(5, "Chờ duyệt trả hàng");

        return statusMap.getOrDefault(status, "Không xác định"); // Mặc định nếu không có trong danh sách
    }


    public List<OrderLineDTO> getOrdersLine(Long orderId) {
        List<OrderLine> orderLines = orderLineRepository.findAllOrderID(orderId);
        List<OrderLineDTO> dtos = new ArrayList<>();

        for (OrderLine line : orderLines) {
            OrderLineDTO dto = new OrderLineDTO();
            dto.setID(line.getID());
            dto.setOderID(line.getOderID());
            dto.setVariationName(line.getVariationName());
            dto.setMarterial(line.getMarterial());
            dto.setUnit_Price(line.getUnit_Price());
            dto.setQuantity(line.getQuantity());
            dto.setPrice(line.getPrice());
            dto.setStatus(line.getStatus());
            dto.setCreation_date(line.getCreation_date());
            dto.setEdit_Date(line.getEdit_Date());

            // Lấy variation đầy đủ từ ID
            Variation variation = variationService.findByIDEntity(line.getVariationID().getID());

            // Tạo VariationDTO đầy đủ
            VariationDTO variationDTO = new VariationDTO();
            variationDTO.setID(variation.getID());

            // Gán ProductDTO + ảnh
            ProductDTO productDTO = new ProductDTO(
                    variation.getProductID().getID(),
                    variation.getProductID().getName(),
                    variationService.mapCategoryToDTO(variation.getProductID().getCategoryID()),
                    variationService.mapBrandToDTO(variation.getProductID().getBrandID()),
                    variationService.getProductImages("Product", variation.getProductID().getID())
            );
            variationDTO.setProductID(productDTO);
            // Gán các thuộc tính khác của VariationDTO
            variationDTO.setSKU(variation.getSKU());
            variationDTO.setName(variation.getName());
            variationDTO.setPrice(variation.getPrice());

            variationDTO.setQuantity(variation.getQuantity());
            variationDTO.setColor(variation.getColor());
            variationDTO.setMaterial(variation.getMaterial());
            variationDTO.setSize(variation.getSize());


            variationDTO.setDescription(variation.getDescription());
            variationDTO.setSold(variation.getSold());
            variationDTO.setStatus(variation.getStatus());
            variationDTO.setImagesDTO(variationService.getVariationImage("Variation", variationDTO.getID()));
            // Gán variationDTO vào OrderLineDTO
            dto.setVariationID(variationDTO);

            dtos.add(dto);
        }

        return dtos;
    }


    public POSOrder update(POSOrderDTO posOrderDTO) {
        try {
            // Lấy Order hiện có từ CSDL
            POSOrder order = new POSOrder();
            // Chuyển đổi CustomerDTO sang Entity
            Customer customer = mapCustomerDTOToEntity(posOrderDTO.getCustomerID());
            order.setCustomerID(customer);
            order.setAddress(posOrderDTO.getAddress());
            // Gán PaymentMethod
            PaymentMethodDTO paymentMethodDTO = posOrderDTO.getPaymentMethod();

            PaymentMethod paymentMethod = paymentMethodRepository.findById(paymentMethodDTO.getID()).orElseThrow(() -> new RuntimeException("PaymentMethod không tồn tại với ID: " + paymentMethodDTO.getID()));
            order.setPaymentMethod(paymentMethod);

            // Cập nhật các trường khác
            order.setPayment_Time(LocalDateTime.now());
            order.setNote(posOrderDTO.getNote());

            // Lưu vào CSDL
            return posOrderRepository.save(order);
        } catch (RuntimeException e) {
            e.printStackTrace();
            throw new RuntimeException("Đã xảy ra lỗi trong quá trình cập nhật: " + e.getMessage());
        }
    }

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
        if (requestedQuantity > availableQuantity || availableQuantity == 0) {
            throw new RuntimeException("Số lượng trong kho không đủ cho sản phẩm: " + variation.getProductID().getName());
        }

        // Cập nhật số lượng trong kho
        variation.setQuantity(availableQuantity - requestedQuantity);
        variationRepository.save(variation); // Giả sử bạn có hàm save() trong service để cập nhật database

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


    public POSOrder accpectReturn(POSOrderDTO dto) {
        try {
            System.out.println("Đơn hàng bị trả lại với ID: " + dto.getID());
            return handleReturnOrder(dto);
        } catch (RuntimeException e) {
            e.printStackTrace();
            throw new RuntimeException("Đã xảy ra lỗi khi trả hàng: " + e.getMessage());
        }
    }

    private POSOrder handleReturnOrder(POSOrderDTO dto) {
        try {
            POSOrder order = buildBaseOrderFromDTO(dto);

            POSOrder savedOrder = posOrderRepository.save(order);
            processOrderLines(dto, savedOrder);
            return savedOrder;
        } catch (RuntimeException e) {
            e.printStackTrace();
            throw new RuntimeException("Đã xảy ra lỗi khi xử lý đơn trả hàng: " + e.getMessage());
        }
    }

    private POSOrder buildBaseOrderFromDTO(POSOrderDTO dto) {
        Customer customer = mapCustomerDTOToEntity(dto.getCustomerID());
        PaymentMethod paymentMethod = mapPaymentMethodDTOToEntity(dto.getPaymentMethod());

        if (customer == null) {
            throw new IllegalArgumentException("Không tìm thấy khách hàng với ID: " + dto.getCustomerID());
        }

        if (paymentMethod == null) {
            throw new IllegalArgumentException("Không tìm thấy phương thức thanh toán: " + dto.getPaymentMethod());
        }

        POSOrder order = new POSOrder();
        order.setCustomerID(customer);
        order.setAddress(dto.getAddress());
        order.setPaymentMethod(paymentMethod);
        order.setStatus(3); // Dùng hằng số enum
        order.setType_Oder(1); // Dùng enum hoặc constant
        order.setOrder_Time(LocalDateTime.now());
        order.setPayment_Time(LocalDateTime.now());
        order.setNote(dto.getNote());

        return order;
    }

    private void processOrderLines(POSOrderDTO dto, POSOrder savedOrder) {
        if (dto.getOrderLine() == null || dto.getOrderLine().isEmpty()) return;

        for (OrderLineDTO lineDTO : dto.getOrderLine()) {
            Variation variation = variationService.findByIDEntity(lineDTO.getVariationID().getID());

            if (variation == null) {
                throw new RuntimeException("Không tìm thấy sản phẩm với ID: " + lineDTO.getVariationID().getID());
            }

            int returnQuantity = lineDTO.getQuantity();
            variation.setQuantity(variation.getQuantity() + returnQuantity);
            variation.setSold(variation.getSold() - returnQuantity);
            variationRepository.save(variation);

            OrderLine line = buildOrderLine(lineDTO, savedOrder, variation, returnQuantity);
            orderLineRepository.save(line);
        }
    }

    private OrderLine buildOrderLine(OrderLineDTO dto, POSOrder order, Variation variation, int quantity) {
        OrderLine line = new OrderLine();
        line.setOderID(order.getID());
        line.setVariationID(variation);
        line.setVariationName(variation.getProductID().getName());
        line.setMarterial(variation.getMaterial());
        line.setUnit_Price(variation.getPrice());
        line.setQuantity(quantity);

        try {
            int availableQuantity = variation.getQuantity();
            int requestedQuantity = dto.getQuantity();
            line.setPrice(requestedQuantity * variation.getPrice());
        } catch (ArithmeticException e) {
            System.out.println("Lỗi khi tính giá: " + variation.getPrice() + " x " + quantity);
            throw e;
        }

        line.setStatus(false);
        line.setCreation_date(LocalDate.now());
        line.setEdit_Date(LocalDate.now());

        return line;
    }

    public POSOrder orderInPOS(POSOrderDTO dto) {
        try {
            POSOrder entity = new POSOrder();

            Customer customer = mapCustomerDTOToEntity(dto.getCustomerID());
            entity.setCustomerID(customer);
            entity.setAddress(dto.getAddress());

            PaymentMethod paymentMethod = mapPaymentMethodDTOToEntity(dto.getPaymentMethod());
            entity.setPaymentMethod(paymentMethod);

            entity.setStatus(3); // Trạng thái "Hoàn thành"
            entity.setType_Oder(1); // Đơn hàng POS
            entity.setOrder_Time(LocalDateTime.now());
            entity.setPayment_Time(LocalDateTime.now());
            entity.setNote(dto.getNote());

            POSOrder savedOrder = posOrderRepository.save(entity);

            if (dto.getOrderLine() != null) {
                for (OrderLineDTO orderLineDTO : dto.getOrderLine()) {
                    OrderLine orderLine = new OrderLine();
                    orderLine.setOderID(savedOrder.getID());

                    Variation variation = variationService.findByIDEntity(orderLineDTO.getVariationID().getID());
                    if (variation == null) {
                        throw new RuntimeException("Không tìm thấy sản phẩm với ID: " + orderLineDTO.getVariationID().getID());
                    }

                    int availableQuantity = variation.getQuantity();
                    int requestedQuantity = orderLineDTO.getQuantity();
                    if (requestedQuantity > availableQuantity) {
                        throw new RuntimeException("Sản phẩm: " + variation.getProductID().getName() + " hiện đang hết hàng.");
                    }

                    variation.setQuantity(availableQuantity - requestedQuantity);
                    variationRepository.save(variation);

                    orderLine.setVariationID(variation);
                    orderLine.setVariationName(variation.getProductID().getName());
                    orderLine.setMarterial(variation.getMaterial());
                    orderLine.setUnit_Price(variation.getPrice());
                    orderLine.setQuantity(requestedQuantity);

                    orderLine.setPrice(requestedQuantity * variation.getPrice());

                    orderLine.setStatus(true);
                    orderLine.setCreation_date(LocalDate.now());
                    orderLine.setEdit_Date(LocalDate.now());

                    orderLineRepository.save(orderLine);
                }
            }

            return savedOrder;
        } catch (RuntimeException e) {
            e.printStackTrace();
            throw new RuntimeException("Đã xảy ra lỗi khi đặt hàng POS: " + e.getMessage());
        }
    }


    public POSOrder orderInShop(POSOrderDTO dto) {
        try {
            POSOrder entity = new POSOrder();

            Customer customer = mapCustomerDTOToEntity(dto.getCustomerID());
            entity.setCustomerID(customer);
            entity.setAddress(dto.getAddress());


            PaymentMethod paymentMethod = mapPaymentMethodDTOToEntity(dto.getPaymentMethod());
            entity.setPaymentMethod(paymentMethod);

            entity.setStatus(1); // Trạng thái "Chờ"
            entity.setType_Oder(2); // Đơn hàng Shop
            entity.setOrder_Time(LocalDateTime.now());
            entity.setNote(dto.getNote());

            POSOrder savedOrder = posOrderRepository.save(entity);

            if (dto.getOrderLine() != null) {
                for (OrderLineDTO orderLineDTO : dto.getOrderLine()) {
                    OrderLine orderLine = new OrderLine();
                    orderLine.setOderID(savedOrder.getID());

                    Variation variation = variationService.findByIDEntity(orderLineDTO.getVariationID().getID());
                    if (variation == null) {
                        throw new RuntimeException("Không tìm thấy sản phẩm với ID: " + orderLineDTO.getVariationID().getID());
                    }

                    int availableQuantity = variation.getQuantity();
                    int requestedQuantity = orderLineDTO.getQuantity();
                    if (requestedQuantity > availableQuantity) {
                        throw new RuntimeException("Sản phẩm: " + variation.getProductID().getName() + " hiện đang hết hàng.");
                    }

                    variation.setQuantity(availableQuantity - requestedQuantity);
                    variationRepository.save(variation);

                    orderLine.setVariationID(variation);
                    orderLine.setVariationName(variation.getProductID().getName());
                    orderLine.setMarterial(variation.getMaterial());
                    orderLine.setUnit_Price(variation.getPrice());
                    orderLine.setQuantity(requestedQuantity);

                    orderLine.setPrice(requestedQuantity * variation.getPrice());

                    orderLine.setStatus(true);
                    orderLine.setCreation_date(LocalDate.now());
                    orderLine.setEdit_Date(LocalDate.now());

                    orderLineRepository.save(orderLine);
                }
            }

            return savedOrder;
        } catch (RuntimeException e) {
            e.printStackTrace();
            throw new RuntimeException("Đã xảy ra lỗi khi đặt hàng: " + e.getMessage());
        }
    }


    public OrderLineDTO mapOrderLineEntityToDTO(OrderLine entity) {
        return new OrderLineDTO(entity.getID(), entity.getOderID(), variationService.mapVariationToDTO(entity.getVariationID()), entity.getVariationName(), entity.getMarterial(), entity.getQuantity(), entity.getUnit_Price(), entity.getPrice(), entity.getCreation_date(), entity.getEdit_Date(), entity.getStatus());
    }

    public OrderLine mapOrderLineDTOToEntity(OrderLineDTO dto) {
        return new OrderLine(dto.getID(), dto.getOderID(), VariationService.mapListDTOtoVariationEntity(dto.getVariationID()), dto.getVariationName(), dto.getMarterial(), dto.getQuantity(), dto.getUnit_Price(), dto.getPrice(), dto.getCreation_date(), dto.getEdit_Date(), dto.getStatus());
    }


    public Customer mapCustomerDTOToEntity(CustomerDTO dto) {
        return new Customer(dto.getID(), dto.getName(), dto.getAddress(), dto.getPhone(), dto.getNote(), dto.getStatus(), dto.getCreation_date(), dto.getEdit_Date());
    }

    public CustomerDTO mapCustomerEntityToDTO(Customer entity) {
        return new CustomerDTO(entity.getID(), entity.getName(), entity.getAddress(), entity.getPhone(), entity.getNote(), entity.getStatus(), entity.getCreation_date(), entity.getEdit_Date());
    }


    public POSOrderDTO mapOrderEntityToDTO(POSOrder entity) {
        POSOrderDTO dto = new POSOrderDTO();
        dto.setID(entity.getID());
        dto.setCustomerID(mapCustomerEntityToDTO(entity.getCustomerID()));
        dto.setAddress(entity.getAddress());
        // Lấy danh sách OrderLine từ repository và chuyển đổi sang DTO
        List<OrderLineDTO> orderLineDTOs = orderLineRepository.findAllOrderID(entity.getID()).stream().map(this::mapOrderLineEntityToDTO) // Chuyển từng OrderLine Entity sang DTO
                .toList();
        dto.setOrderLine(orderLineDTOs);
        dto.setNote(entity.getNote());
        dto.setPaymentMethod(mapPaymentEntityToDTO(entity.getPaymentMethod()));
        dto.setStatus(entity.getStatus());
        dto.setType_Oder(entity.getType_Oder());
        return dto;
    }

    public POSOrderDTO mapOrderEntityToDTOBysendEmail(POSOrder entity) {
        POSOrderDTO dto = new POSOrderDTO();
        dto.setID(entity.getID());
        dto.setCustomerID(mapCustomerEntityToDTO(entity.getCustomerID()));
        dto.setAddress(entity.getAddress());
        // Lấy danh sách OrderLine từ repository và chuyển đổi sang DTO
        List<OrderLineDTO> orderLineDTOs = orderLineRepository.findAllOrderID(entity.getID()).stream().map(this::mapOrderLineEntityToDTO) // Chuyển từng OrderLine Entity sang DTO
                .toList();
        dto.setOrderLine(orderLineDTOs);
        dto.setNote(entity.getNote());
        dto.setPaymentMethod(mapPaymentEntityToDTO(entity.getPaymentMethod()));
        dto.setOrder_Time(entity.getOrder_Time());
        dto.setPayment_Time(entity.getPayment_Time());
        dto.setTotal_Payment(entity.getTotal_Payment().toString());
        dto.setStatus(entity.getStatus());
        dto.setType_Oder(entity.getType_Oder());
        return dto;
    }


    // Chuyển PaymentMethod entity sang DTO
    public PaymentMethodDTO mapPaymentEntityToDTO(PaymentMethod entity) {
        PaymentMethodDTO dto = new PaymentMethodDTO();
        dto.setID(entity.getID());
        dto.setType(entity.getType());
        dto.setNote(entity.getNote());
        dto.setStatus(entity.getStatus());
        return dto;
    }


    // Chuyển PaymentMethod entity sang DTO
    public PaymentMethod mapPaymentMethodDTOToEntity(PaymentMethodDTO dto) {
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
