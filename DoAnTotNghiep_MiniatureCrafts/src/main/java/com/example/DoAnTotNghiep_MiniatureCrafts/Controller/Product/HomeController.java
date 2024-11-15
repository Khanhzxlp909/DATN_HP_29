package com.example.DoAnTotNghiep_MiniatureCrafts.Controller.Product;


import com.example.DoAnTotNghiep_MiniatureCrafts.DTO.VariationDTO;
import com.example.DoAnTotNghiep_MiniatureCrafts.Entity.Variation;
import com.example.DoAnTotNghiep_MiniatureCrafts.Service.Product.VariationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("shop")
@CrossOrigin(origins = "*")
public class HomeController {

    @Autowired
    private VariationService variationService;

    @GetMapping("all")
    public Page<VariationDTO> home(Pageable pageable) {
        return variationService.getAll(pageable);
    }

    @GetMapping("result/{name}")
    public Page<VariationDTO> findByName(Pageable pageable,@PathVariable("name") String name) {
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
