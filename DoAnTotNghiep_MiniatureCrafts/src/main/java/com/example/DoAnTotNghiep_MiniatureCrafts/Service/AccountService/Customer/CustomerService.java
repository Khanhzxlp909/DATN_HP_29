package com.example.DoAnTotNghiep_MiniatureCrafts.Service.AccountService.Customer;

import com.example.DoAnTotNghiep_MiniatureCrafts.DTO.CustomerDTO;
import com.example.DoAnTotNghiep_MiniatureCrafts.Entity.Customer;
import com.example.DoAnTotNghiep_MiniatureCrafts.Repository.Auth.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    public List<CustomerDTO> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        return customers.stream().map(customer -> {
            CustomerDTO dto = new CustomerDTO();
            dto.setID(customer.getID());
            dto.setName(customer.getName());
            dto.setAddress(customer.getAddress());
            dto.setPhone(customer.getPhone());
            dto.setNote(customer.getNote());
            dto.setStatus(customer.getStatus());
            dto.setCreation_date(customer.getCreation_date());
            dto.setEdit_Date(customer.getEdit_Date());
            return dto; // Quan trọng: Cần trả về DTO từ hàm map
        }).collect(Collectors.toList()); // Thu thập kết quả thành danh sách
    }


    public CustomerDTO editCustomer(Long id) {

        Customer entity = customerRepository.findById(id).orElseThrow();
        System.out.println("Customer: "+entity.getName());
        return new CustomerDTO(entity);
    }

    public Customer createCustomer(CustomerDTO customer) {
        return customerRepository.save(new Customer(
                customer.getID(),
                customer.getName(),
                customer.getAddress(),
                customer.getPhone(),
                customer.getNote(),
                customer.getStatus(),
                customer.getCreation_date(),
                customer.getEdit_Date()
        ));
    }

    public Customer updateCustomer(CustomerDTO customer) {
        return customerRepository.save(new Customer(
                customer.getID(),
                customer.getName(),
                customer.getAddress(),
                customer.getPhone(),
                customer.getNote(),
                customer.getStatus(),
                customer.getCreation_date(),
                customer.getEdit_Date()
        ));
    }

    public boolean deleteCustomer(Long id) {
        try {
            Customer customer = customerRepository.findById(id).orElseThrow();
            customerRepository.delete(customer);
        } catch (Exception e) {
            throw new RuntimeException();
        }
        return true;
    }
}
