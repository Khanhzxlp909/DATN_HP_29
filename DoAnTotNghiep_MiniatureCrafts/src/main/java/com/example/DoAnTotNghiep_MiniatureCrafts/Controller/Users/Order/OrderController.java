package com.example.DoAnTotNghiep_MiniatureCrafts.Controller.Users.Order;


import com.example.DoAnTotNghiep_MiniatureCrafts.DTO.POSOrderDTO;
import com.example.DoAnTotNghiep_MiniatureCrafts.Service.Order.OrderService;
import com.example.DoAnTotNghiep_MiniatureCrafts.Service.Order.Voucher.VoucherService;
import com.example.DoAnTotNghiep_MiniatureCrafts.Service.Product.VariationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("vmgKtShop")
public class OrderController {
    @Autowired
    private VariationService variationService;

    @Autowired
    private VoucherService voucherService;

    @Autowired
    OrderService orderService;

    @GetMapping("history/{id}")
    public List<POSOrderDTO> getAllOrder(@PathVariable("id") Long id){
        List<POSOrderDTO> oder = orderService.getAllOrders(id);
        return oder.stream().toList();
    }

}
