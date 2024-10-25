package com.example.DoAnTotNghiep_MiniatureCrafts.Controller.Product;


import com.example.DoAnTotNghiep_MiniatureCrafts.DTO.VariationDTO;
import com.example.DoAnTotNghiep_MiniatureCrafts.Entity.Variation;
import com.example.DoAnTotNghiep_MiniatureCrafts.Repository.User.UserRepository;
import com.example.DoAnTotNghiep_MiniatureCrafts.Service.Product.VariationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("gunshop")
@CrossOrigin(origins = "*")
public class HomeController {

    @Autowired
    private VariationService variationService;

    @Autowired
    private UserRepository userRepo;

    @GetMapping("home")
    public Page<VariationDTO> home(Pageable pageable) {
        return variationService.getAll(pageable);
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
