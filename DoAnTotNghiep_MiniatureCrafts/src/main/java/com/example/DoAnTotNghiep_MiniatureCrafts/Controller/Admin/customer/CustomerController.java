package com.example.DoAnTotNghiep_MiniatureCrafts.Controller.Admin.customer;


import com.example.DoAnTotNghiep_MiniatureCrafts.DTO.CustomerDTO;
import com.example.DoAnTotNghiep_MiniatureCrafts.Entity.Customer;
import com.example.DoAnTotNghiep_MiniatureCrafts.Service.AccountService.Customer.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("admin/customer")
@CrossOrigin(value = "*")
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @GetMapping("result/all")
    public List<CustomerDTO> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @PostMapping("save")
    public Customer saveCustomer(@RequestBody CustomerDTO customerDTO) {
        return customerService.createCustomer(customerDTO);
    }

    @GetMapping("edit/{id}")
    public CustomerDTO editCustomer(@PathVariable("id") Long id) {
        return customerService.editCustomer(id);
    }

    @PostMapping("update")
    public Customer updateCustomer(@RequestBody CustomerDTO customerDTO) {
        return customerService.updateCustomer(customerDTO);
    }

    @GetMapping("delete/{id}")
    public String deleteCustomer(@PathVariable("id") Long id) {
        customerService.deleteCustomer(id);
        return "delete done";
    }
}
