package com.example.hp_29_MiniatureCrafts.controller.users.product;


import com.example.hp_29_MiniatureCrafts.dto.CustomerDTO;
import com.example.hp_29_MiniatureCrafts.dto.VariationDTO;
import com.example.hp_29_MiniatureCrafts.entity.Account;
import com.example.hp_29_MiniatureCrafts.entity.Customer;
import com.example.hp_29_MiniatureCrafts.service.account.AccountService;
import com.example.hp_29_MiniatureCrafts.service.account.customer.CustomerService;
import com.example.hp_29_MiniatureCrafts.service.product.VariationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("MiniatureCrafts")
@CrossOrigin(value = "*")
public class HomeController {

    @Autowired
    private VariationService variationService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private AccountService accountService;


    @GetMapping("home")
    public Page<VariationDTO> home(Pageable pageable) {
        return variationService.getAll(pageable);
    }

    @PostMapping("updateInfo")
    public Customer updateCustomer(@RequestBody CustomerDTO customerDTO) {
        return customerService.updateCustomer(customerDTO);
    }

    @PostMapping("updateAccount")
    public Account updateCustomer(@RequestBody Account account) {
        return accountService.updateAccount(account);
    }

    @GetMapping("result/{name}")
    public Page<VariationDTO> findByName(Pageable pageable, @PathVariable("name") String name) {
        return variationService.findByName(pageable, name);

    }

    @GetMapping("category/{category}")
    public Page<VariationDTO> filterCategory(Pageable pageable, @PathVariable("category") Long category) {
        return variationService.getProductByCategory(pageable,category);
    }

    @GetMapping("brands/{brands}")
    public Page<VariationDTO> filterBrands(Pageable pageable, @PathVariable("brands") Long brands) {
        return variationService.getVariationByBrands(pageable,brands);
    }
}
