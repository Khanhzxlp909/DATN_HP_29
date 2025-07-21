package com.example.hp_29_MiniatureCrafts.controller.admin.variation;

import com.example.hp_29_MiniatureCrafts.dto.ImagesDTO;
import com.example.hp_29_MiniatureCrafts.dto.ProductDTO;
import com.example.hp_29_MiniatureCrafts.dto.VariationDTO;
import com.example.hp_29_MiniatureCrafts.entity.Variation;
import com.example.hp_29_MiniatureCrafts.service.product.VariationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
//import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("admin/variation")
public class VariationController {

    @Autowired
    private VariationService variationService;

    private final String IMAGE_DIR = System.getProperty("user.dir") + "/upload/images/";

    @PostMapping("images/delete")
    public ResponseEntity<?> deleteImages(@RequestBody List<String> cd_images) {
        try {
            List<String> deletedImages = new ArrayList<>();
            List<String> notFoundImages = new ArrayList<>();

            for (String cdImage : cd_images) {

                // Xóa file ảnh trong thư mục
                Path filePath = Paths.get(IMAGE_DIR + cdImage);
                Files.deleteIfExists(filePath);

                // Xóa ảnh trong database
                variationService.deleteImages(cdImage);
                deletedImages.add(cdImage);
            }

            return ResponseEntity.ok(Map.of(
                    "message", "Images processed",
                    "deletedImages", deletedImages,
                    "notFoundImages", notFoundImages
            ));
        } catch (Exception e) {
            e.printStackTrace(); // In ra lỗi trong console
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Error deleting images", "error", e.getMessage()));
        }
    }

    @PostMapping("images/upload")
    public ResponseEntity<?> uploadImage(@RequestParam("file") List<MultipartFile> listFiles) {
        String IMAGE_DIR = "D:/DoAnTotNghiep/DATN_HP_29/DoAnTotNghiep_MiniatureCrafts/upload/images/"; // Cấu hình thư mục lưu ảnh

        try {
            if (listFiles.isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of("message", "No files were uploaded"));
            }

            List<String> fileUrls = new ArrayList<>();

            for (MultipartFile file : listFiles) {
                if (file.isEmpty()) {
                    return ResponseEntity.badRequest().body(Map.of("message", "One of the files is empty"));
                }

                // Kiểm tra định dạng file hợp lệ
                String originalFileName = file.getOriginalFilename();
                if (originalFileName == null || !originalFileName.matches(".*\\.(png|jpg|jpeg|gif|webp)$")) {
                    return ResponseEntity.badRequest().body(Map.of("message", "Invalid file type. Only images are allowed"));
                }

                // Đặt tên file duy nhất
                String fileName = System.currentTimeMillis() + "_" + originalFileName;
                Path filePath = Paths.get(IMAGE_DIR + fileName);

                // Đảm bảo thư mục tồn tại
                Files.createDirectories(filePath.getParent());

                // Lưu file an toàn hơn
                try (InputStream inputStream = file.getInputStream()) {
                    Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
                }

                // Đường dẫn file trả về
                String fileUrl = "/images/" + fileName;
                fileUrls.add(fileName);

                System.out.println("Uploaded image URL: " + fileUrl);
            }

            return ResponseEntity.ok().body(Map.of(
                    "message", "Files uploaded successfully",
                    "urls", fileUrls
            ));
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", "Error while uploading files"));
        }
    }

    @GetMapping("images/findall")
    public List<ImagesDTO> findAll() {
        return variationService.findAll();
    }

    @PostMapping("images/setproduct")
    public ResponseEntity<?> setProductImages(@RequestBody List<ImagesDTO> imagesDTOList) {
        try {
            for (ImagesDTO imagesDTO : imagesDTOList) {
                variationService.saveImages(imagesDTO); // Lưu từng ảnh vào DB
            }
            return ResponseEntity.ok().body(Map.of("message", "Files uploaded successfully"));
        } catch (Exception e) {
            throw new RuntimeException("Error while saving images", e);
        }
    }

    @PostMapping("images/setvariation")
    public ResponseEntity<?> setVariationImages(@RequestBody List<ImagesDTO> imagesDTOList) {
        try {
            for (ImagesDTO imagesDTO : imagesDTOList) {
                variationService.saveImages(imagesDTO); // Lưu từng ảnh vào DB
            }
            return ResponseEntity.ok().body(Map.of("message", "Files uploaded successfully"));
        } catch (Exception e) {
            throw new RuntimeException("Error while saving images", e);
        }
    }

    @GetMapping("getproduct")
    public List<ProductDTO> getProductDTOS() {
        return variationService.getProducts();
    }

    @GetMapping("result/all")
    public Page<VariationDTO> home(Pageable pageable) {
        return variationService.getAll(pageable);
    }

    @GetMapping("get/all")
    public Page<VariationDTO> getByFormOrder(Pageable pageable) {
        return variationService.getVariationsBystatus(pageable);
    }

    @GetMapping("result/{name}")
    public Page<VariationDTO> findByName(Pageable pageable, @PathVariable("name") String name) {
        return variationService.findByName(pageable, name);
    }

    @GetMapping("category/{category}")
    public Page<VariationDTO> filterCategory(Pageable pageable, @PathVariable("category") Long category) {
        return variationService.getProductByCategory(pageable, category);
    }

    @PostMapping("add")
    public ResponseEntity<?> add(@RequestBody VariationDTO varDTO) {
        try {
            Variation savedVariation = variationService.add(varDTO);
            return ResponseEntity.ok(Map.of("id", savedVariation.getID()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to add variation", "details", e.getMessage()));
        }
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
