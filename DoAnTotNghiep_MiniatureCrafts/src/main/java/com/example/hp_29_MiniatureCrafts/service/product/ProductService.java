package com.example.hp_29_MiniatureCrafts.service.product;

import com.example.hp_29_MiniatureCrafts.dto.ProductDTO;
import com.example.hp_29_MiniatureCrafts.entity.Product;
import com.example.hp_29_MiniatureCrafts.repository.product.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    VariationService variationService;

    public List<ProductDTO> getAllProduct(){
        List<Product> products = productRepository.findAll();

        return products.stream().map(product-> new ProductDTO(
                product.getID(),
                product.getName(),
                variationService.mapCategoryToDTO(product.getCategoryID())
        )).toList();
    }

}
