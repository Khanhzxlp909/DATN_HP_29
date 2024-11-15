package com.example.DoAnTotNghiep_MiniatureCrafts.Service.Product;

import com.example.DoAnTotNghiep_MiniatureCrafts.DTO.BrandDTO;
import com.example.DoAnTotNghiep_MiniatureCrafts.Entity.Brand;
import com.example.DoAnTotNghiep_MiniatureCrafts.Repository.thuoctinh.BrandsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BrandsService {
    @Autowired
    BrandsRepository brandsRepository;

    public List<BrandDTO> getALL(){
        List<Brand> list = brandsRepository.findAll();


        return list.stream().map(brand -> new BrandDTO(brand)).collect(Collectors.toList());
    }
  public Brand add(BrandDTO brandDTO){
   return brandsRepository.save(new Brand(brandDTO));
  }

    public Brand update(BrandDTO brand) {
        return brandsRepository.save(new Brand(brand));
    }
    public void delete(Long id) {
        Brand brand = brandsRepository.findById(id).get();
        brandsRepository.delete(brand);
        ;

    }


}
