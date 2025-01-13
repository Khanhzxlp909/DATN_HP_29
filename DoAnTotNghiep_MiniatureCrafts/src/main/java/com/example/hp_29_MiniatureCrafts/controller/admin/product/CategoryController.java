package com.example.hp_29_MiniatureCrafts.controller.admin.product;

import com.example.hp_29_MiniatureCrafts.dto.CategoryDTO;
import com.example.hp_29_MiniatureCrafts.entity.Category;
import com.example.hp_29_MiniatureCrafts.service.product.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("admin/category")
////@CrossOrigin(value = "*")
public class CategoryController {
    @Autowired
    CategoryService categoryService;
    @GetMapping("get")
    public Page<CategoryDTO> getALL(Pageable pageable) {
        return categoryService.getALLS(pageable);
    }
    @PostMapping("save")
    public Category save(@RequestBody CategoryDTO category) {
        return categoryService.add(category);
    }
    @PostMapping("update")
    public Category update(@RequestBody CategoryDTO category) {
        return categoryService.update(category);
    }
    @GetMapping("delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        categoryService.delete(id);
        return "done";
    }
    @GetMapping("search/{name}")
    public List<CategoryDTO> search(@PathVariable("name") String query) {
        return categoryService.search(query);
    }

}
