package com.example.hp_29_MiniatureCrafts.controller.admin.variation;

import com.example.hp_29_MiniatureCrafts.dto.ImagesDTO;
import com.example.hp_29_MiniatureCrafts.dto.ProductDTO;
import com.example.hp_29_MiniatureCrafts.dto.VariationDTO;
import com.example.hp_29_MiniatureCrafts.entity.Variation;
import com.example.hp_29_MiniatureCrafts.service.product.CategoryService;
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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("admin/variation")
//@CrossOrigin(value = "*")
public class VariationController {


    @Autowired
    private VariationService variationService;


    private final String IMAGE_DIR = "src/main/resources/static/images/";

    @PostMapping("images/upload")
    public ResponseEntity<?> uploadImage(@RequestParam("file") List<MultipartFile> listfile) {
        try {
            // Kiểm tra danh sách file có rỗng không
            if (listfile.isEmpty()) {
                return ResponseEntity.badRequest().body("No files were uploaded");
            }

            List<String> fileUrls = new ArrayList<>();

            for (MultipartFile file : listfile) {
                if (file.isEmpty()) {
                    return ResponseEntity.badRequest().body("File is empty");
                }

                // Tạo tên file duy nhất
                String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
                Path filePath = Paths.get(IMAGE_DIR + fileName);

                // Lưu file
                Files.write(filePath, file.getBytes());

                // Trả về đường dẫn file
                String fileUrl = "/images/" + fileName;
                fileUrls.add(fileUrl);

                System.out.println("Uploaded image URL: " + fileUrl);
            }

            return ResponseEntity.ok().body(Map.of("message", "Files uploaded successfully", "urls", fileUrls));
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error while uploading files");
        }
    }


    @GetMapping("images/findall")
    public List<ImagesDTO> findAll() {
        return variationService.findAll();
    }

    @PostMapping("images/setproduct")
    public ResponseEntity<?> setProductImages(@RequestBody ImagesDTO imagesDTO) {
        try {
            variationService.saveImages(imagesDTO);
            return ResponseEntity.ok().body(Map.of("message", "File uploaded successfully"));
        } catch (Exception e) {
            throw new RuntimeException(e);
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

    @GetMapping("brands/{brands}")
    public Page<VariationDTO> filterBrands(Pageable pageable, @PathVariable("brands") Long brands) {
        return variationService.getVariationByBrands(pageable, brands);
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
