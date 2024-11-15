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
@RequestMapping("admin")
public class VariationController {


    @Autowired
    private VariationService variationService;

    @Autowired
    private VoucherService voucherService;


    @GetMapping("result/all")
    public Page<VariationDTO> home(Pageable pageable) {
//        List<VoucherDTO> list = voucherService.getAll();
//        for (VoucherDTO voucherDTO : list) {
//            if (voucherDTO.getTypeVoucher()){
//                System.out.println("Voucher trừ trực tiếp: "+voucherDTO.getID());
//                System.out.println("Voucher trừ trực tiếp: "+voucherDTO.getCode());
//                System.out.println("Voucher trừ trực tiếp: "+voucherDTO.getDiscountValue());
//            }else {
//                System.out.println("Voucher trừ theo %: "+voucherDTO.getID());
//                System.out.println("Voucher trừ theo %: "+voucherDTO.getCode());
//                System.out.println("Voucher trừ theo %: "+voucherDTO.getDiscountValue());
//            }
//        }
        double giaDemo = 900000;

        VoucherDTO voucher = voucherService.findByCode("f0aee30f-98e8-4794-ab7a-0a91f9e75ebd");
        if (voucher.getTypeVoucher()){
            double gia = giaDemo - voucher.getDiscountValue();
            System.out.println("Giá: "+ gia);
        }else {
            double gia = giaDemo - (giaDemo * (voucher.getDiscountValue()/100));
            System.out.println("theo %: "+gia);
        }
        return variationService.getAll(pageable);
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
