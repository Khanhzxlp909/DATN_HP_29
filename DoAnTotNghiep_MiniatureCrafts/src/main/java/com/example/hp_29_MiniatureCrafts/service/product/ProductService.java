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

    // dùng find by product để get ra list ảnh, sau đó map sang dto và gán list ảnh này vào imagesDTOS của product
    public List<ProductDTO> getAllProduct() {
        List<Product> products = productRepository.findAll(); // Lấy danh sách sản phẩm từ DB
        List<ProductDTO> productDTOList = new ArrayList<>();

        for (Product product : products) {
            ProductDTO productDTO = new ProductDTO(product); // Chuyển đổi Product -> DTO

            // Lấy danh sách ảnh từ DB theo Product
            List<Images> imagesList = imagesRepository.findByProduct(product.getID());

            // Chuyển đổi danh sách ảnh sang DTO
            List<ImagesDTO> imagesDTOList = mapImagestoDTO(imagesList);

            // Gán danh sách ảnh vào sản phẩm DTO
            productDTO.setImagesDTOS(imagesDTOList);

            // Thêm vào danh sách kết quả
            productDTOList.add(productDTO);
        }

        return productDTOList;
    }


    public List<ImagesDTO> mapImagestoDTO(List<Images> imagesList) {
        List<ImagesDTO> imagesDTOList = new ArrayList<>();

        for (Images image : imagesList) {
            ImagesDTO dto = new ImagesDTO();
            dto.setID(image.getID()); // Lấy ID ảnh
            dto.setProduct(new ProductDTO(image.getProduct())); // Chuyển đổi Product thành DTO
            dto.setCd_Images(image.getCd_Images()); // Lấy đường dẫn ảnh
            dto.setSet_Default(image.getSet_Default()); // Cờ đánh dấu ảnh mặc định

            imagesDTOList.add(dto); // Thêm vào danh sách kết quả
        }

        return imagesDTOList;
    }


}
