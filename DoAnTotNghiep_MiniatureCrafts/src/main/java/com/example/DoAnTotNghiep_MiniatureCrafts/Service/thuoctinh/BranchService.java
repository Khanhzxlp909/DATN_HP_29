package com.example.DoAnTotNghiep_MiniatureCrafts.Service.thuoctinh;

import com.example.DoAnTotNghiep_MiniatureCrafts.DTO.BrandDTO;
import com.example.DoAnTotNghiep_MiniatureCrafts.Entity.Brand;
import com.example.DoAnTotNghiep_MiniatureCrafts.Repository.thuoctinh.BrandsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BranchService {
    @Autowired
    BrandsRepository brandsRepository;

    public List<BrandDTO> getAll(){
        List<Brand> brands = brandsRepository.findAll();

        return brands.stream().map(brand -> new BrandDTO(brand)).collect(Collectors.toList());
    }
}
