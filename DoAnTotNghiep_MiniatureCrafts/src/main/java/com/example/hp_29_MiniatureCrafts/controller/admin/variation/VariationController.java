package com.example.hp_29_MiniatureCrafts.controller.admin.variation;

import com.example.hp_29_MiniatureCrafts.dto.ProductDTO;
import com.example.hp_29_MiniatureCrafts.dto.VariationDTO;
import com.example.hp_29_MiniatureCrafts.entity.Variation;
import com.example.hp_29_MiniatureCrafts.service.product.CategoryService;
import com.example.hp_29_MiniatureCrafts.service.product.VariationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
//import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("admin/variation")
@CrossOrigin(value = "*")
public class VariationController {


    @Autowired
    private VariationService variationService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("result/all")
    public Page<VariationDTO> home(Pageable pageable) {
        return variationService.getAll(pageable);
    }



    @GetMapping("category/{category}")
    public Page<VariationDTO> filterCategory(Pageable pageable, @PathVariable("category") Long category) {
        return variationService.getProductByCategory(pageable, category);
    }

    @GetMapping("brands/{brands}")
    public Page<VariationDTO> filterBrands(Pageable pageable, @PathVariable("brands") Long brands) {
        return variationService.getVariationByBrands(pageable, brands);
    }

    @GetMapping("result/{name}")
    public Page<VariationDTO> findByName(Pageable pageable, @PathVariable("name") String name) {
        return variationService.findByName(pageable, name);
    }

    @PostMapping("add")
    public Variation add(@RequestBody VariationDTO varDTO) {
        return variationService.add(varDTO);
    }

    @PostMapping("update")
    public Variation update(@RequestBody VariationDTO varDTO) {
        return variationService.update(varDTO);
    }

    @GetMapping("delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        variationService.delete(id);
        return "done";
    }
}
