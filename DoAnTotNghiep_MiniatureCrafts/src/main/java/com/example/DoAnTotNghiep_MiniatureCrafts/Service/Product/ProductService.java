package com.example.DoAnTotNghiep_MiniatureCrafts.Service.Product;

import com.example.DoAnTotNghiep_MiniatureCrafts.DTO.BrandDTO;
import com.example.DoAnTotNghiep_MiniatureCrafts.DTO.ProductDTO;
import com.example.DoAnTotNghiep_MiniatureCrafts.DTO.VariationDTO;
import com.example.DoAnTotNghiep_MiniatureCrafts.Entity.Variation;
import com.example.DoAnTotNghiep_MiniatureCrafts.Repository.Product.VariationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    @Autowired
    private VariationRepository prdRepo;

    public Page<VariationDTO> getAll(Pageable pageable) {
        // Truy vấn các Variations theo Status và phân trang
        Page<Variation> variations = prdRepo.findAll(pageable);

        // Chuyển đổi từ Variation sang VariationDTO và duy trì phân trang
        return variations.map(variation -> {
            VariationDTO dto = new VariationDTO();
            dto.setID(variation.getID());
            dto.setSKU(variation.getSKU());
            dto.setProductID(new ProductDTO(
                    variation.getProductID().getID(),
                    variation.getProductID().getName(),
                    variation.getProductID().getCategoryID()
            ));
            dto.setPrice(variation.getPrice());
            dto.setQuantity(variation.getQuantity());
            dto.setBrandID(new BrandDTO(
                    variation.getBrandID().getID(),
                    variation.getBrandID().getName(),
                    variation.getBrandID().getNote(),
                    variation.getBrandID().getStatus()
            ));
            dto.setMaterial(variation.getMaterial());
            dto.setWeight(variation.getWeight());
            dto.setStatus(variation.getStatus());
            return dto;
        });
    }
}
