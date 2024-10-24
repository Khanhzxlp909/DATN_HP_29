package com.example.DoAnTotNghiep_MiniatureCrafts.Controller.Product;


import com.example.DoAnTotNghiep_MiniatureCrafts.DTO.ProductDTO;
import com.example.DoAnTotNghiep_MiniatureCrafts.DTO.VariationDTO;
import com.example.DoAnTotNghiep_MiniatureCrafts.Entity.Users;
import com.example.DoAnTotNghiep_MiniatureCrafts.Repository.User.UserRepository;
import com.example.DoAnTotNghiep_MiniatureCrafts.Service.Product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("gunshop")
@CrossOrigin(origins = "*")
public class HomeController {

    @Autowired
    private ProductService productService;

    @Autowired
    private UserRepository userRepo;

    //    @GetMapping("")
//    public List<VariationDTO> home() {
//        List<VariationDTO> list = productService.getAll();
//        return list;
//    }
    @GetMapping("home")
    public Page<VariationDTO> home(Pageable pageable) {
        List<Users> list = userRepo.findAll();
        for (Users user : list) {
            System.out.println(user.getName());
        }

        return productService.getAll(pageable);
    }


}
