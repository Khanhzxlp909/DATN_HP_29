package com.example.DoAnTotNghiep_MiniatureCrafts.Service.Product;

import com.example.DoAnTotNghiep_MiniatureCrafts.DTO.BrandDTO;
import com.example.DoAnTotNghiep_MiniatureCrafts.DTO.ProductDTO;
import com.example.DoAnTotNghiep_MiniatureCrafts.DTO.VariationDTO;
import com.example.DoAnTotNghiep_MiniatureCrafts.Entity.Variation;
import com.example.DoAnTotNghiep_MiniatureCrafts.Repository.Product.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
    @Autowired
    private ProductRepository prdRepo;

    public List<VariationDTO> getAll() {
        List<Variation> variations = prdRepo.findAll();

        // Chuyển đổi từ Variation sang VariationDTO
        return variations.stream().map(variation -> {
            VariationDTO dto = new VariationDTO();
            dto.setID(variation.getID());
            dto.setSKU(variation.getSKU());
            dto.setPrice(variation.getPrice());
            dto.setQuantity(variation.getQuantity());
            dto.setBrandID(new BrandDTO(variation.getBrandID().getID(), variation.getBrandID().getName(), variation.getBrandID().getNote(), variation.getBrandID().getStatus()));
            dto.setMaterial(variation.getMaterial());
            dto.setWeight(variation.getWeight());
            dto.setStatus(variation.getStatus());
            return dto;
        }).collect(Collectors.toList());
    }
}
