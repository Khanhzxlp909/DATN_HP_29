package com.example.DoAnTotNghiep_MiniatureCrafts.Service.Order;

import com.example.DoAnTotNghiep_MiniatureCrafts.DTO.CustomerDTO;
import com.example.DoAnTotNghiep_MiniatureCrafts.DTO.POSOrderDTO;
import com.example.DoAnTotNghiep_MiniatureCrafts.DTO.PaymentMethodDTO;
import com.example.DoAnTotNghiep_MiniatureCrafts.Entity.POSOrder;
import com.example.DoAnTotNghiep_MiniatureCrafts.Repository.Order.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    OrderRepository orderRepository;

    public List<POSOrderDTO> getAllOrders(Long customerID) {
        List<POSOrder> list = orderRepository.findAll();

        return list.stream()
                .filter(order -> order.getCustomerID().getID().equals(customerID)) // Lọc các đơn hàng theo customerID
                .map(order -> {
                    POSOrderDTO dto = new POSOrderDTO();
                    dto.setID(order.getID());
                    dto.setCustomerID(new CustomerDTO(
                            order.getCustomerID().getID(),
                            order.getCustomerID().getName(),
                            order.getCustomerID().getAddress(),
                            order.getCustomerID().getPhone(),
                            order.getCustomerID().getStatus(),
                            order.getCustomerID().getCreation_date(),
                            order.getCustomerID().getEdit_Date()
                    ));
                    dto.setCode_Voucher(order.getCode_Voucher());
                    dto.setTotal_Amount(order.getTotal_Amount());
                    dto.setDiscount_Amount(order.getDiscount_Amount());
                    dto.setTotal_Payment(order.getTotal_Payment());
                    dto.setPaymentMethod(new PaymentMethodDTO(
                            order.getPaymentMethod().getID(),
                            order.getPaymentMethod().getType(),
                            order.getPaymentMethod().getNote(),
                            order.getPaymentMethod().getStatus()
                    ));
                    dto.setCreation_date(order.getCreation_date());
                    dto.setEdit_Date(order.getEdit_Date());
                    dto.setType_Oder(order.getType_Oder());
                    dto.setNote(order.getNote());
                    dto.setStatus(order.getStatus());
                    return dto; // Trả về dto trong lambda
                })
                .collect(Collectors.toList()); // Chuyển stream thành danh sách
    }
}
