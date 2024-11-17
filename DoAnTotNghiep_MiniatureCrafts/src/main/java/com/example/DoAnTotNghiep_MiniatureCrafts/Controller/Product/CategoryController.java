package com.example.DoAnTotNghiep_MiniatureCrafts.Controller.Product;

import com.example.DoAnTotNghiep_MiniatureCrafts.DTO.CategoryDTO;
import com.example.DoAnTotNghiep_MiniatureCrafts.Entity.Category;
import com.example.DoAnTotNghiep_MiniatureCrafts.Service.Product.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
@CrossOrigin(value = "*")
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
