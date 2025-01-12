package com.example.hp_29_MiniatureCrafts.controller.admin.product;

import com.example.hp_29_MiniatureCrafts.dto.CategoryDTO;
import com.example.hp_29_MiniatureCrafts.entity.Category;
import com.example.hp_29_MiniatureCrafts.service.product.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("admin/category")
////@CrossOrigin(value = "*")
public class CategoryController {
    @Autowired
    CategoryService categoryService;
    @GetMapping("get")
    public List<CategoryDTO> getALL() {
        return categoryService.getALL();
    }
    @PostMapping("save")
    public Category save(@RequestBody CategoryDTO category) {
        return categoryService.add(category);
    }
    @PostMapping("update")
    public Category update(@RequestBody CategoryDTO category) {
        return categoryService.update(category);
    }
    @PostMapping("delete")
    public String delete(@PathVariable("id") Long id) {
        categoryService.delete(id);
        return "done";
    }
    @GetMapping("search/{name}")
    public List<CategoryDTO> search(@PathVariable("name") String query) {
        return categoryService.search(query);
    }

}
