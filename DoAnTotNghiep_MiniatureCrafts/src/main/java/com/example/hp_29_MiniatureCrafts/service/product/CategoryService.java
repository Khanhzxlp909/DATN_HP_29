package com.example.hp_29_MiniatureCrafts.service.product;

import com.example.hp_29_MiniatureCrafts.dto.CategoryDTO;
import com.example.hp_29_MiniatureCrafts.dto.ProductDTO;
import com.example.hp_29_MiniatureCrafts.entity.Category;
import com.example.hp_29_MiniatureCrafts.entity.Product;
import com.example.hp_29_MiniatureCrafts.repository.product.ProductRepository;
import com.example.hp_29_MiniatureCrafts.repository.product.VariationRepository;
import com.example.hp_29_MiniatureCrafts.repository.thuoctinh.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    private VariationRepository variationRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    static VariationService staticVariationService;

    public List<CategoryDTO> getALL() {
        List<Category> list = categoryRepository.findAll();
        return list.stream().map(category -> new CategoryDTO(category)).collect(Collectors.toList());
    }
    public Category add(CategoryDTO categoryDTO) {
        return categoryRepository.save(new Category(categoryDTO));
    }
    public Category update(CategoryDTO categoryDTO) {
        return categoryRepository.save(new Category(categoryDTO));
    }
    public void delete(Long id) {
        Category category = categoryRepository.findById(id).get();
        categoryRepository.delete(category);
    }
    public List<CategoryDTO> search(String query) {
       List<Category> list = categoryRepository.findByName(query);

       return list.stream().map(category -> new CategoryDTO(category)).collect(Collectors.toList());
    }




}
