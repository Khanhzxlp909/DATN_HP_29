package com.example.hp_29_MiniatureCrafts.service.product.warehouse;


import com.example.hp_29_MiniatureCrafts.dto.VariationDTO;
import com.example.hp_29_MiniatureCrafts.dto.WareHouseDetailsDTO;
import com.example.hp_29_MiniatureCrafts.entity.Variation;
import com.example.hp_29_MiniatureCrafts.entity.WareHouseDetails;
import com.example.hp_29_MiniatureCrafts.repository.product.VariationRepository;
import com.example.hp_29_MiniatureCrafts.repository.product.warehouse.WareHouseDetailRepository;
import com.example.hp_29_MiniatureCrafts.service.product.VariationService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
public class WareHouseDetailService {

    @Autowired
    static VariationService staticvariationService;

    @Autowired
    private VariationRepository variationRepository;

    @Autowired
    VariationService variationService;

    @Autowired
    WareHouseDetailRepository wareHouseDetailRepository;

    // Thêm mới WareHouseDetails
    public WareHouseDetails addWareHouseDetail(WareHouseDetailsDTO dto) {
        // Lấy Variation từ DTO
        Variation variation = variationService.findByIDEntity(dto.getVariation().getID());
        if (variation == null) {
            throw new RuntimeException("Variation không tồn tại!");
        }

        // Cập nhật số lượng Variation
        variation.setQuantity(variation.getQuantity() + dto.getQuantity());
        variationService.save(variation);

//         Lưu WareHouseDetails
        WareHouseDetails entity = mapWHDTtoEntity(dto);
        try {
            WareHouseDetails savedEntity = wareHouseDetailRepository.save(entity);
            System.out.println("Lưu thành công: " + savedEntity);
            return savedEntity;
        } catch (Exception e) {
            System.err.println("Lỗi khi lưu WareHouseDetails: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }

    }

    public WareHouseDetailsDTO updateWareHouseDetail(Integer id, WareHouseDetailsDTO dto) {

        // Tìm kiếm WareHouseDetails theo ID
        WareHouseDetails existingDetail = wareHouseDetailRepository.findByIDWHDT(id);
        if (existingDetail == null) {
            throw new EntityNotFoundException("Không tìm thấy WareHouseDetails với ID: " + id);
        }

        // Lấy Variation liên quan
        Long idvari = existingDetail.getVariation().getID();
        System.out.println("variation id: "+ idvari);
        Variation variation = variationRepository.findByIdVariation(idvari);
        if (variation == null) {
            throw new EntityNotFoundException("Không tìm thấy Variation!");
        }

        // Tính số lượng mới
        int newQuantity = variation.getQuantity() - existingDetail.getQuantity() + dto.getQuantity();
        if (newQuantity < 0) {
            throw new RuntimeException("Số lượng không hợp lệ!");
        }

        // Cập nhật số lượng trong Variation
        variation.setQuantity(newQuantity);
        variationService.save(variation);

        // Cập nhật thông tin WareHouseDetails
        existingDetail.setQuantity(dto.getQuantity());
        existingDetail.setPrice(dto.getPrice());
        existingDetail.setTotal_Amount(dto.getQuantity() * dto.getPrice());
        existingDetail.setNote(dto.getNote());
        existingDetail.setStatus(dto.getStatus());

        // Lưu thông tin
        WareHouseDetails updatedEntity = wareHouseDetailRepository.save(existingDetail);

        // Trả về DTO
        return toDto(updatedEntity);
    }


    // Xóa WareHouseDetails
    public void deleteWareHouseDetail(Integer id) {
        WareHouseDetails existingDetail = wareHouseDetailRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("WareHouseDetail không tồn tại!"));

        // Lấy Variation và hoàn trả số lượng
        Variation variation = variationService.findByIDEntity(existingDetail.getVariation().getID());
        if (variation != null) {
            variation.setQuantity(variation.getQuantity() - existingDetail.getQuantity());
            variationService.save(variation);
        }
        existingDetail.setStatus(false);
        // Xóa WareHouseDetails
        wareHouseDetailRepository.save(existingDetail);
    }

    public List<WareHouseDetailsDTO> getall(Long id_import) {
        List<WareHouseDetails> lstwarehouse = wareHouseDetailRepository.findByWarehouseId(id_import);
        return lstwarehouse.stream().map(
                wareHousedetails -> new WareHouseDetailsDTO(
                        wareHousedetails.getID(),
                        wareHousedetails.getImport(),
                        staticvariationService.mapVariationToVariationDTO(wareHousedetails.getVariation()),
                        wareHousedetails.getQuantity(),
                        wareHousedetails.getPrice(),
                        wareHousedetails.getTotal_Amount(),
                        wareHousedetails.getNote(),
                        wareHousedetails.getStatus()
                )
        ).collect(Collectors.toList());
    }


    public WareHouseDetailsDTO toDto(WareHouseDetails wareHouseDetails) {
        WareHouseDetailsDTO dto = new WareHouseDetailsDTO();
        dto.setID(wareHouseDetails.getID());
        dto.setImport(wareHouseDetails.getImport());
        dto.setVariation(staticvariationService.mapVariationToVariationDTO(wareHouseDetails.getVariation())); // Mapping đơn giản cho Variation
        dto.setQuantity(wareHouseDetails.getQuantity());
        dto.setPrice(wareHouseDetails.getPrice());
        dto.setTotal_Amount(wareHouseDetails.getTotal_Amount());
        dto.setNote(wareHouseDetails.getNote());
        dto.setStatus(wareHouseDetails.getStatus());
        return dto;
    }

    public static WareHouseDetails mapWHDTtoEntity(WareHouseDetailsDTO dto) {
        WareHouseDetails entity = new WareHouseDetails();
        entity.setID(dto.getID());
        entity.setImport(dto.getImport());
        entity.setVariation(mapVariationDTOToEntity(dto.getVariation()));

        entity.setQuantity(dto.getQuantity());
        entity.setPrice(dto.getPrice());
        entity.setTotal_Amount(dto.getTotal_Amount());
        entity.setNote(dto.getNote());
        entity.setStatus(dto.getStatus());
        return entity;
    }

    public static Variation mapVariationDTOToEntity(VariationDTO dto) {
        if (dto == null) {
            return null;  // Nếu DTO là null, trả về null
        }

        Variation entity = new Variation();
        entity.setID(dto.getID());

        // Ánh xạ ProductID từ DTO sang Entity (cần lấy thông tin Product từ ProductDTO)
        if (dto.getProductID() != null) {
            entity.setProductID(staticvariationService.mapProductDTOtoProduct(dto.getProductID()));  // Lấy Product từ ProductDTO
        }

        entity.setSKU(dto.getSKU());
        entity.setPrice(parsePrice(dto.getPrice()));  // Chuyển đổi Price từ String sang Double
        entity.setQuantity(dto.getQuantity());

        // Ánh xạ BrandID từ DTO sang Entity
        if (dto.getBrandID() != null) {
            entity.setBrandID(staticvariationService.mapBrandDTOtoBrand(dto.getBrandID()));  // Lấy Brand từ BrandDTO
        }

        entity.setMaterial(dto.getMaterial());
        entity.setWeight(dto.getWeight());
        entity.setStatus(dto.getStatus());

        return entity;
    }


    public static VariationDTO mapVariationEntityToDTO(Variation entity) {
        if (entity == null) {
            return null;  // Nếu Entity là null, trả về null
        }

        VariationDTO dto = new VariationDTO();
        dto.setID(entity.getID());

        // Ánh xạ Product từ Entity sang ProductDTO
        if (entity.getProductID() != null) {
            dto.setProductID(staticvariationService.mapProductToProductDTO(entity.getProductID()));  // Lấy ProductDTO từ Product
        }

        dto.setSKU(entity.getSKU());
        dto.setPrice(formatPrice(entity.getPrice()));  // Chuyển đổi Price từ Double sang String
        dto.setQuantity(entity.getQuantity());

        // Ánh xạ Brand từ Entity sang BrandDTO
        if (entity.getBrandID() != null) {
            dto.setBrandID(staticvariationService.mapBrandToBrandDTO(entity.getBrandID()));  // Lấy BrandDTO từ Brand
        }

        dto.setMaterial(entity.getMaterial());
        dto.setWeight(entity.getWeight());
        dto.setStatus(entity.getStatus());

        return dto;
    }


    // Chuyển từ String (đơn vị tiền tệ) sang double
    public static double parsePrice(String price) {
        if (price == null || price.isBlank()) {
            return 0; // Giá trị mặc định nếu price null hoặc rỗng
        }
        try {
            return NumberFormat.getCurrencyInstance(new Locale("vi", "VN"))
                    .parse(price)
                    .doubleValue();
        } catch (ParseException e) {
            e.printStackTrace();
            return 0; // Giá trị mặc định khi lỗi
        }
    }


    // Chuyển từ double sang String (đơn vị tiền tệ VND)
    public static String formatPrice(double price) {
        return NumberFormat.getCurrencyInstance(new Locale("vi", "VN")).format(price);
    }

}
