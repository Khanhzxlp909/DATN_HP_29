package com.example.hp_29_MiniatureCrafts.service.product;

import com.example.hp_29_MiniatureCrafts.dto.*;
import com.example.hp_29_MiniatureCrafts.entity.Images;
import com.example.hp_29_MiniatureCrafts.entity.Product;
import com.example.hp_29_MiniatureCrafts.entity.Variation;
import com.example.hp_29_MiniatureCrafts.repository.product.ImagesRepository;
import com.example.hp_29_MiniatureCrafts.repository.product.ProductRepository;
import com.example.hp_29_MiniatureCrafts.repository.product.VariationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    ImagesRepository imagesRepository;

    @Autowired
    VariationService variationService;

    @Autowired
    VariationRepository variationRepository;

    // dùng find by product để get ra list ảnh, sau đó map sang dto và gán list ảnh này vào images của product
    public List<ProductDTO> getAllProduct() {
        List<Product> products = productRepository.findAll();
        List<ProductDTO> productDTOList = new ArrayList<>();

        for (Product product : products) {
            ProductDTO productDTO = new ProductDTO(product);

            // 1. Ảnh trực tiếp thuộc sản phẩm
            List<Images> directImages = imagesRepository.findByModelAndProductID("Product", product.getID());

            // 2. Ảnh thuộc các variation của sản phẩm
            List<Long> variationIDs = variationService.getVariationIDsByProductID(product.getID());
            List<Images> variationImages = new ArrayList<>();
            for (Long varId : variationIDs) {
                variationImages.addAll(imagesRepository.findByModelAndProductID("Variation", varId));
            }

            // 3. Gộp tất cả ảnh
            List<Images> allImages = new ArrayList<>();
            allImages.addAll(directImages);
            allImages.addAll(variationImages);

            // 4. Map ảnh sang DTO
            List<ImagesDTO> imagesDTOList = mapImagestoDTO(allImages);
            productDTO.setImages(imagesDTOList);

            productDTOList.add(productDTO);
        }

        return productDTOList;
    }

    public ProductDTO getProductByID(Long id) {
        Product product = productRepository.findById(id).orElse(null);
        if (product == null) return null;

        // Lấy danh sách ảnh của sản phẩm
        List<Images> images = imagesRepository.findByModelAndProductID("Product", product.getID());
        List<ImagesDTO> imageDTOs = images.stream()
                .map(ImagesDTO::new)
                .collect(Collectors.toList());

        // Lấy danh sách biến thể
        List<Variation> variations = variationRepository.findByProductID(product.getID());
        List<VariationDTO> varDTO  = variations.stream().map(entity -> {
                    VariationDTO dto = new VariationDTO();
                    dto.setID(entity.getID());
                    dto.setSKU(entity.getSKU());
                    dto.setProductID(new ProductDTO(
                            entity.getProductID().getID(),
                            entity.getProductID().getName(),
                            variationService.mapCategoryToDTO(entity.getProductID().getCategoryID()),
                            variationService.mapBrandToDTO(entity.getProductID().getBrandID()),
                            variationService.getProductImages("Variation", entity.getProductID().getID())
                    ));

                    dto.setName(entity.getName());
                    dto.setPrice(entity.getPrice());
                    dto.setQuantity(entity.getQuantity());
                    dto.setColor(entity.getColor());
                    dto.setMaterial(entity.getMaterial());
                    dto.setSize(entity.getSize());
                    dto.setDescription(entity.getDescription());
                    dto.setSold(entity.getSold());
                    dto.setStatus(entity.getStatus());
                    dto.setImagesDTO(variationService.getVariationImage("Variation", entity.getID()));
                    return dto;
                }).collect(Collectors.toList());

        return new ProductDTO(
                product.getID(),
                product.getName(),
                varDTO,
                new CategoryDTO(product.getCategoryID()),
                new BrandDTO(product.getBrandID()),
                imageDTOs
        );
    }


    public List<ImagesDTO> mapImagestoDTO(List<Images> imagesList) {
        List<ImagesDTO> imagesDTOList = new ArrayList<>();

        for (Images image : imagesList) {
            ImagesDTO dto = new ImagesDTO();

            dto.setID(image.getID());
            dto.setCd_Images(image.getCd_Images());
            dto.setSet_Default(image.getSet_Default());
            dto.setModel(image.getModel());
            dto.setProductID(image.getEntity_ID());

            imagesDTOList.add(dto);
        }

        return imagesDTOList;
    }


}
