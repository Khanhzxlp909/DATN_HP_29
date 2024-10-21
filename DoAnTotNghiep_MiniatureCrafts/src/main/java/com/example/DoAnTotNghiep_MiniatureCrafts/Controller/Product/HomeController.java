package com.example.DoAnTotNghiep_MiniatureCrafts.Controller.Product;


import com.example.DoAnTotNghiep_MiniatureCrafts.DTO.ProductDTO;
import com.example.DoAnTotNghiep_MiniatureCrafts.DTO.VariationDTO;
import com.example.DoAnTotNghiep_MiniatureCrafts.Service.Product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("gunshop")
@CrossOrigin(origins = "*")
public class HomeController {

    @Autowired
    private ProductService productService;

    @GetMapping("")
    public List<VariationDTO> home() {
        List<VariationDTO> list = productService.getAll();

        // Otherwise, print all the SKUs and return the SKU of the first item
        for (VariationDTO var : list) {
            System.out.println(var.getSKU());
        }

        VariationDTO var = list.get(0); // Safely get the first element now
        return list;
    }

}
