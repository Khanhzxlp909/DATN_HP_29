package com.example.hp_29_MiniatureCrafts.service.product;

import com.example.hp_29_MiniatureCrafts.dto.ImagesDTO;
import com.example.hp_29_MiniatureCrafts.dto.ProductDTO;
import com.example.hp_29_MiniatureCrafts.entity.Images;
import com.example.hp_29_MiniatureCrafts.entity.Product;
import com.example.hp_29_MiniatureCrafts.repository.product.ImagesRepository;
import com.example.hp_29_MiniatureCrafts.repository.product.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    ImagesRepository imagesRepository;

    @Autowired
    VariationService variationService;

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



    public List<ImagesDTO> mapImagestoDTO(List<Images> imagesList) {
        List<ImagesDTO> imagesDTOList = new ArrayList<>();

        for (Images image : imagesList) {
            ImagesDTO dto = new ImagesDTO();

            dto.setID(image.getID());
            dto.setCd_Images(image.getCd_Images());
            dto.setSet_Default(image.getSet_Default());
            dto.setModel(image.getModel());
            dto.setProductID(image.getProductID());

            imagesDTOList.add(dto);
        }

        return imagesDTOList;
    }



}
