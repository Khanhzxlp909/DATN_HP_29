package com.example.DoAnTotNghiep_MiniatureCrafts.Service.Product;

import com.example.DoAnTotNghiep_MiniatureCrafts.DTO.CategoryDTO;
import com.example.DoAnTotNghiep_MiniatureCrafts.Entity.Category;
import com.example.DoAnTotNghiep_MiniatureCrafts.Repository.thuoctinh.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

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

        // Use Spring Data JPA's [findBy](cci:1://file:///D:/git-clone/DATN_HP_29/DoAnTotNghiep_MiniatureCrafts/src/main/java/com/example/DoAnTotNghiep_MiniatureCrafts/Repository/Product/VariationRepository.java:13:4-14:78) methods to search for categories that match the query
        return list.stream().map(category -> new CategoryDTO(category)).collect(Collectors.toList());
    }

}
