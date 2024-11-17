package com.example.DoAnTotNghiep_MiniatureCrafts.Controller.Admin.variation;

import com.example.DoAnTotNghiep_MiniatureCrafts.DTO.VariationDTO;
import com.example.DoAnTotNghiep_MiniatureCrafts.DTO.VoucherDTO;
import com.example.DoAnTotNghiep_MiniatureCrafts.Entity.Variation;
import com.example.DoAnTotNghiep_MiniatureCrafts.Entity.Voucher;
import com.example.DoAnTotNghiep_MiniatureCrafts.Repository.Order.voucher.VoucherRepository;
import com.example.DoAnTotNghiep_MiniatureCrafts.Service.Order.Voucher.VoucherService;
import com.example.DoAnTotNghiep_MiniatureCrafts.Service.Product.VariationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
//import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("admin/variation")
@CrossOrigin(value = "*")
public class VariationController {


    @Autowired
    private VariationService variationService;

    @GetMapping("result/all")
    public Page<VariationDTO> home(Pageable pageable) {
        return variationService.getAll(pageable);
    }

    @GetMapping("result/{name}")
    public Page<VariationDTO> findByName(Pageable pageable, @PathVariable("name") String name) {
        return variationService.findByName(pageable, name);
    }

    @PostMapping("add/variation")
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
