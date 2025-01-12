package com.example.hp_29_MiniatureCrafts.service.account.customer;

import com.example.hp_29_MiniatureCrafts.dto.CustomerDTO;
import com.example.hp_29_MiniatureCrafts.entity.Customer;
import com.example.hp_29_MiniatureCrafts.repository.auth.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    public CustomerDTO findbyPhone(String phone) {
        Customer entity = customerRepository.findByPhone(phone);
        System.out.println("Customer: "+entity.getName());
        return new CustomerDTO(entity);
    }

    public List<CustomerDTO> findbyName(String phone) {
        List<Customer> customers = customerRepository.findByName(phone);
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

        Customer customers = new Customer();
        customers.setID(customer.getID());
        customers.setName(customer.getName());
        customers.setAddress(customer.getAddress());
        customers.setPhone(customer.getPhone());
        customers.setNote(customer.getNote());
        customers.setCreation_date(customer.getCreation_date());
        customers.setEdit_Date(customer.getEdit_Date());
        customers.setStatus(true);

        return customerRepository.save(customers);
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
