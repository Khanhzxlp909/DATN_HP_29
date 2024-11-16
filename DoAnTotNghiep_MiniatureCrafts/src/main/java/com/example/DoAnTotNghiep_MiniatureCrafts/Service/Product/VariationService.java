package com.example.DoAnTotNghiep_MiniatureCrafts.Service.Product;

import com.example.DoAnTotNghiep_MiniatureCrafts.DTO.BrandDTO;
import com.example.DoAnTotNghiep_MiniatureCrafts.DTO.ProductDTO;
import com.example.DoAnTotNghiep_MiniatureCrafts.DTO.VariationDTO;
import com.example.DoAnTotNghiep_MiniatureCrafts.Entity.Brand;
import com.example.DoAnTotNghiep_MiniatureCrafts.Entity.Product;
import com.example.DoAnTotNghiep_MiniatureCrafts.Entity.Variation;
import com.example.DoAnTotNghiep_MiniatureCrafts.Repository.Product.VariationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;
import java.util.UUID;

@Service
public class VariationService {
    // Chuyển đổi đơn vị tiền tệ thành VND
    NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));


    @Autowired
    private VariationRepository prdRepo;

    // thêm sản phẩm mới
    public Variation add(VariationDTO varDTO) {
        Variation variation = new Variation();

        // Chuyển từ ProductDTO sang Product entity
        Product product = new Product();
        product.setID(varDTO.getProductID().getId());
        product.setName(varDTO.getProductID().getName());
        product.setCategoryID(varDTO.getProductID().getCategoryID());


        // Chuyển từ BrandDTO sang Brand entity
        Brand brand = new Brand();
        brand.setID(varDTO.getBrandID().getID());
        brand.setName(varDTO.getBrandID().getName());
        brand.setNote(varDTO.getBrandID().getNote());
        brand.setStatus(varDTO.getBrandID().getStatus());


        //tryền DTO vào entity
        variation.setProductID(product);
        variation.setSKU(UUID.randomUUID().toString());

        try {
            String currency = varDTO.getPrice();
            double amount = parseCurrency(currency);
            variation.setPrice(amount);

            System.out.println(amount); // In ra: 1500000.0

        } catch (ParseException e) {
            e.printStackTrace();
        }

        variation.setBrandID(brand);
        variation.setQuantity(varDTO.getQuantity());
        variation.setMaterial(varDTO.getMaterial());
        variation.setWeight(varDTO.getWeight());
        variation.setStatus(varDTO.getStatus());

        return prdRepo.save(variation);
    }

    //update sarn pham
    public Variation update(VariationDTO varDTO) {
        Variation variation = new Variation();

        // Chuyển từ ProductDTO sang Product entity
        Product product = new Product();
        product.setID(varDTO.getProductID().getId());
        product.setName(varDTO.getProductID().getName());
        product.setCategoryID(varDTO.getProductID().getCategoryID());


        // Chuyển từ BrandDTO sang Brand entity
        Brand brand = new Brand();
        brand.setID(varDTO.getBrandID().getID());
        brand.setName(varDTO.getBrandID().getName());
        brand.setNote(varDTO.getBrandID().getNote());
        brand.setStatus(varDTO.getBrandID().getStatus());


        //tryền DTO vào entity
        variation.setID(varDTO.getID());
        variation.setProductID(product);
        variation.setSKU(UUID.randomUUID().toString());

        try {
            String currency = varDTO.getPrice();
            double amount = parseCurrency(currency);
            variation.setPrice(amount);
            System.out.println(amount); // In ra: 1500000.0

        } catch (ParseException e) {
            e.printStackTrace();
        }

        variation.setBrandID(brand);
        variation.setQuantity(varDTO.getQuantity());
        variation.setMaterial(varDTO.getMaterial());
        variation.setWeight(varDTO.getWeight());
        variation.setStatus(varDTO.getStatus());

        return prdRepo.save(variation);
    }

    public static double parseCurrency(String currency) throws ParseException {
        NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        Number number = formatter.parse(currency);
        return number.doubleValue();
    }

    public void delete(Long id) {
        prdRepo.deleteById(id);
    }


    // lấy sản phaarm và phân trang
    public Page<VariationDTO> getAll(Pageable pageable) {
        // Truy vấn các Variations theo Status và phân trang
        Page<Variation> variations = prdRepo.findAll(pageable);

        NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
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

            // chuyển đổi giá từ biến thể qua double
            double price = variation.getPrice();
            System.out.println(price);
            // chuyển đổi từ double sang dạng string và đưa ra dưới dạng giá + VND
            dto.setPrice(formatter.format(price));

            dto.setBrandID(mapBrandToBrandDTO(variation.getBrandID()));

            dto.setQuantity(variation.getQuantity());

            dto.setMaterial(variation.getMaterial());
            dto.setWeight(variation.getWeight());
            dto.setStatus(variation.getStatus());
            return dto;
        });
    }

    // Chuyển từ ProductDTO sang Product entity
    public Product mapProductDTOtoProduct(ProductDTO dto) {
        return new Product(
                dto.getId(),
                dto.getName(),
                dto.getCategoryID()
        );
    }

    // Chuyển từ Product entity sang ProductDTO
    public ProductDTO mapProductToProductDTO(Product entity) {
        return new ProductDTO(
                entity.getID(),
                entity.getName(),
                entity.getCategoryID()
        );
    }

    // Chuyển từ BrandDTO sang Brand entity
    public Brand mapBrandDTOtoBrand(BrandDTO dto) {
        return new Brand(
                dto.getID(),
                dto.getName(),
                dto.getNote(),
                dto.getStatus()
        );
    }

    // Chuyển từ Brand entity sang BrandDTO
    public BrandDTO mapBrandToBrandDTO(Brand entity) {
        return new BrandDTO(
                entity.getID(),
                entity.getName(),
                entity.getNote(),
                entity.getStatus()
        );
    }

    // Chuyển từ VariationDTO sang Variation entity
    public Variation mapVariationDTOtoVariation(VariationDTO dto) {
        return new Variation(
                dto.getID(),
                mapProductDTOtoProduct(dto.getProductID()),
                dto.getSKU(),
                parsePrice(dto.getPrice()),
                dto.getQuantity(),
                mapBrandDTOtoBrand(dto.getBrandID()),
                dto.getMaterial(),
                dto.getWeight(),
                dto.getStatus()

        );
    }

    // Chuyển từ Variation entity sang VariationDTO
    public VariationDTO mapVariationToVariationDTO(Variation entity) {
        return new VariationDTO(
                entity.getID(),
                mapProductToProductDTO(entity.getProductID()),
                entity.getSKU(),
                formatPrice(entity.getPrice()),
                entity.getQuantity(),
                mapBrandToBrandDTO(entity.getBrandID()),
                entity.getMaterial(),
                entity.getWeight(),
                entity.getStatus()

        );
    }


    public Page<VariationDTO> findByName(Pageable pageable, String id) {
        // Truy vấn các Variations theo Status và phân trang
        Page<Variation> variations = prdRepo.findByName(pageable, id);

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
            // chuyển đổi giá từ biến thể qua double
            double price = variation.getPrice();
            System.out.println(price);
            // chuyển đổi từ double sang dạng string và đưa ra dưới dạng giá + VND
            dto.setPrice(formatter.format(price));

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

    // Chuyển từ String (đơn vị tiền tệ) sang double
    public double parsePrice(String price) {
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
    public String formatPrice(double price) {
        return NumberFormat.getCurrencyInstance(new Locale("vi", "VN")).format(price);
    }

}
