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
            dto.setId(variation.getId());
            dto.setSku(variation.getSku());
            dto.setPrice(variation.getPrice());
            dto.setQuantity(variation.getQuantity());
            dto.setBrand(new BrandDTO(variation.getBrand().getId(), variation.getBrand().getName(), variation.getBrand().getNote(), variation.getBrand().getStatus()));
            dto.setMaterial(variation.getMaterial());
            dto.setWeight(variation.getWeight());
            dto.setStatus(variation.isStatus());
            return dto;
        }).collect(Collectors.toList());
    }
}
