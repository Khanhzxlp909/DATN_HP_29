package com.example.hp_29_MiniatureCrafts.service.product;

import com.example.hp_29_MiniatureCrafts.dto.*;
import com.example.hp_29_MiniatureCrafts.entity.*;
import com.example.hp_29_MiniatureCrafts.repository.product.ImagesRepository;
import com.example.hp_29_MiniatureCrafts.repository.product.ProductRepository;
import com.example.hp_29_MiniatureCrafts.repository.product.VariationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.NumberFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class VariationService {
    // Chuyển đổi đơn vị tiền tệ thành VND
    NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));

    @Autowired
    private VariationRepository variationRepository;

    @Autowired
    static ImagesRepository staticImagesRepository;

    @Autowired
    ImagesRepository imagesRepository;

    @Autowired
    ProductRepository productRepository;

    public ImagesDTO saveImages(ImagesDTO imagesDTO) {
        // Kiểm tra model
        String model = imagesDTO.getModel();
        Long refId = imagesDTO.getProductID();

        if (model == null || refId == null) {
            throw new IllegalArgumentException("Model và ProductID không được null");
        }

        // Tạo entity
        Images images = new Images();
        images.setModel(imagesDTO.getModel());
        images.setProductID(refId);
        images.setCd_Images(imagesDTO.getCd_Images());
        images.setSet_Default(imagesDTO.getSet_Default());

        // Lưu và trả DTO
        return new ImagesDTO(imagesRepository.save(images));
    }


    public void deleteImages(String cd_images) {
        imagesRepository.deleteByCd_Images(cd_images);
    }

    public List<ImagesDTO> findAll() {
        List<Images> images = imagesRepository.findAll();
        return images.stream().map(dto -> new ImagesDTO(dto)).collect(Collectors.toList());
    }

    // save truoc, lay id tu respone set va imagedto
    public ProductDTO saveProduct(ProductDTO productDTO) {
        Product product = new Product();
        product.setID(productDTO.getID());
        product.setName(productDTO.getName());
        product.setCategoryID(mapCategoryToEntity(productDTO.getCategoryID()));
        product.setBrandID(mapBrandtoEntity(productDTO.getBrandID()));
        return new ProductDTO(productRepository.save(product));
    }


    public List<ProductDTO> getProducts() {
        List<Product> productList = productRepository.findAll();

        return productList.stream().map(product -> {
            List<Images> images = imagesRepository.findByModelAndProductID("Product", product.getID());

            List<ImagesDTO> image = images.stream()
                    .map(ImagesDTO::new)  // assuming you have ImagesDTO(Images entity)
                    .collect(Collectors.toList());

            return new ProductDTO(
                    product.getID(),
                    product.getName(),
                    new CategoryDTO(product.getCategoryID()),
                    new BrandDTO(product.getBrandID()),
                    image
            );
        }).collect(Collectors.toList());
    }


    public Variation findByIDEntity(Long id) {
        Variation variation = variationRepository.findByIdVariation(id);
        System.out.println("Product Name :" + variation.getProductID().getName());
        return variation;
    }

    // thêm sản phẩm mới
    public Variation add(VariationDTO dto) {
        Variation entity = new Variation();

        // Chuyển từ ProductDTO sang Product entity
        Product product = productRepository.findById(dto.getProductID().getID()).orElseThrow();

        //tryền DTO vào entity
        entity.setProductID(product);
        entity.setName(product.getName() +" " +  dto.getName());
        entity.setSKU(UUID.randomUUID().toString());
        entity.setPrice(dto.getPrice());
        entity.setQuantity(dto.getQuantity());
        entity.setColor(dto.getColor());
        entity.setMaterial(dto.getMaterial());
        entity.setSize(dto.getSize());
        entity.setStatus(dto.getStatus());
        entity.setSold(0);

        System.out.println(
                "Adding Variation: " +
                        "\nProduct ID: " + entity.getProductID().getID() +
                        "\nProduct Name: " + entity.getProductID().getName() +
                        "\nVariation Name: " + entity.getName() +
                        "\nVariation SKU: " + entity.getSKU() +
                        "\nVariation Price: " + formatter.format(entity.getPrice()) +
                        "\nVariation Quantity: " + entity.getQuantity() +
                        "\nVariation Color: " + entity.getColor() +
                        "\nVariation Material: " + entity.getMaterial() +
                        "\nVariation Size: " + entity.getSize() +
                        "\nVariation Status: " + entity.getStatus()

        );
        return variationRepository.save(entity);
//        return entity;
    }

    //update sarn pham
    public Variation update(VariationDTO dto) {
        Variation entity = variationRepository.findByID(dto.getID());

        // Chuyển từ ProductDTO sang Product entity
        Product product = new Product();
        product.setID(dto.getProductID().getID());
        product.setName(dto.getProductID().getName());
        product.setCategoryID(mapCategoryToEntity(dto.getProductID().getCategoryID()));
        product.setBrandID(mapBrandtoEntity(dto.getProductID().getBrandID()));

        //tryền DTO vào entity
        entity.setProductID(product);
        entity.setName(dto.getName());
        entity.setSKU(UUID.randomUUID().toString());
        entity.setPrice(dto.getPrice());
        entity.setQuantity(dto.getQuantity());
        entity.setColor(dto.getColor());
        entity.setMaterial(dto.getMaterial());
        entity.setSize(dto.getSize());
        entity.setStatus(dto.getStatus());
        entity.setSold(0);
        return variationRepository.save(entity);
    }

    public void delete(Long id) {
        Variation variation = variationRepository.findByID(id);
        variation.setQuantity(0);
        variation.setStatus(false);
        // Xóa đơn hàng
        variationRepository.save(variation);
    }


    // lấy sản phẩm theo Status và phân trang
    public Page<VariationDTO> getAll(Pageable pageable) {
        // Truy vấn các Variations theo Status và phân trang
        Page<Variation> variation = variationRepository.findAll(pageable);

        // Chuyển đổi từ Variation sang VariationDTO và duy trì phân trang
        return variation.map(entity -> {
            VariationDTO dto = new VariationDTO();
            dto.setID(entity.getID());
            dto.setSKU(entity.getSKU());
            dto.setProductID(new ProductDTO(
                    entity.getProductID().getID(),
                    entity.getProductID().getName(),
                    mapCategoryToDTO(entity.getProductID().getCategoryID()),
                    mapBrandToDTO(entity.getProductID().getBrandID()),
                    getProductImages("Variation", entity.getProductID().getID())
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
            dto.setImagesDTO(getVariationImage("Variation", entity.getID()));
            return dto;
        });
    }


    public Page<VariationDTO> getVariationsBystatus(Pageable pageable) {

        // Truy vấn các Variations theo Status và phân trang
        Page<Variation> variation = variationRepository.getVariationByStatus(pageable);

        return variation.map(entity -> {
            VariationDTO dto = new VariationDTO();
            dto.setID(entity.getID());
            dto.setSKU(entity.getSKU());
            dto.setProductID(new ProductDTO(
                    entity.getProductID().getID(),
                    entity.getProductID().getName(),
                    mapCategoryToDTO(entity.getProductID().getCategoryID()),
                    mapBrandToDTO(entity.getProductID().getBrandID()),
                    getProductImages("Variation", entity.getProductID().getID())
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
            dto.setImagesDTO(getVariationImage("Variation", entity.getID()));
            return dto;
        });
    }
    
    public Page<VariationDTO> getVariationsByBestseller(Pageable pageable) {

        // Truy vấn các Variations theo Status và phân trang
        Page<Variation> variation = variationRepository.top6BestSeller(pageable);

        NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        // Chuyển đổi từ Variation sang VariationDTO và duy trì phân trang
        return variation.map(entity -> {
            VariationDTO dto = new VariationDTO();
            dto.setID(entity.getID());
            dto.setSKU(entity.getSKU());
            dto.setProductID(new ProductDTO(
                    entity.getProductID().getID(),
                    entity.getProductID().getName(),
                    mapCategoryToDTO(entity.getProductID().getCategoryID()),
                    mapBrandToDTO(entity.getProductID().getBrandID()),
                    getProductImages("Variation", entity.getProductID().getID())
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
            dto.setImagesDTO(getVariationImage("Variation", entity.getID()));
            return dto;
        });
    }

    // lấy sản phaarm và phân trang
    public Page<VariationDTO> newVariation(Pageable pageable) {
        // Truy vấn các Variations mới nhất và phân trang
        Page<Variation> variation = variationRepository.newVariation(pageable);

        return variation.map(entity -> {
            VariationDTO dto = new VariationDTO();
            dto.setID(entity.getID());
            dto.setSKU(entity.getSKU());
            dto.setProductID(new ProductDTO(
                    entity.getProductID().getID(),
                    entity.getProductID().getName(),
                    mapCategoryToDTO(entity.getProductID().getCategoryID()),
                    mapBrandToDTO(entity.getProductID().getBrandID()),
                    getProductImages("Variation", entity.getProductID().getID())
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
            dto.setImagesDTO(getVariationImage("Variation", entity.getID()));
            return dto;
        });
    }

    public Page<VariationDTO> findByName(Pageable pageable, String id) {
        // Truy vấn các Variations theo Status và phân trang
        Page<Variation> variation = variationRepository.findByName(pageable, id);

        return variation.map(entity -> {
            VariationDTO dto = new VariationDTO();
            dto.setID(entity.getID());
            dto.setSKU(entity.getSKU());
            dto.setProductID(new ProductDTO(
                    entity.getProductID().getID(),
                    entity.getProductID().getName(),
                    mapCategoryToDTO(entity.getProductID().getCategoryID()),
                    mapBrandToDTO(entity.getProductID().getBrandID()),
                    getProductImages("Variation", entity.getProductID().getID())
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
            dto.setImagesDTO(getVariationImage("Variation", entity.getID()));
            return dto;
        });
    }

    public Page<VariationDTO> filterPrice(Pageable pageable, Double minprice, Double maxprice) {
        // Truy vấn các Variations theo Status và phân trang
        Page<Variation> variation = variationRepository.findByPriceRange(pageable, minprice, maxprice);

        return variation.map(entity -> {
            VariationDTO dto = new VariationDTO();
            dto.setID(entity.getID());
            dto.setSKU(entity.getSKU());
            dto.setProductID(new ProductDTO(
                    entity.getProductID().getID(),
                    entity.getProductID().getName(),
                    mapCategoryToDTO(entity.getProductID().getCategoryID()),
                    mapBrandToDTO(entity.getProductID().getBrandID()),
                    getProductImages("Variation", entity.getProductID().getID())
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
            dto.setImagesDTO(getVariationImage("Variation", entity.getID()));
            return dto;
        });
    }


    public VariationDTO findByid(Long id) {
        // Truy vấn các Variations theo Status và phân trang
        Variation entity = variationRepository.findByIdVariation(id);

        // Chuyển đổi từ Variation sang VariationDTO và duy trì phân trang
        VariationDTO dto = new VariationDTO();
        dto.setID(entity.getID());
        dto.setSKU(entity.getSKU());
        dto.setProductID(new ProductDTO(
                entity.getProductID().getID(),
                entity.getProductID().getName(),
                mapCategoryToDTO(entity.getProductID().getCategoryID()),
                mapBrandToDTO(entity.getProductID().getBrandID()),
                getProductImages("Variation",entity.getProductID().getID())
        ));

        dto.setName(entity.getName());
        dto.setColor(entity.getColor());
        dto.setSize(entity.getSize());
        dto.setPrice(entity.getPrice());

        dto.setQuantity(entity.getQuantity());
        dto.setMaterial(entity.getMaterial());
        dto.setStatus(entity.getStatus());
        dto.setDescription(entity.getDescription());
        dto.setSold(entity.getSold());
        dto.setImagesDTO(getVariationImage("Variation", entity.getID()));
        return dto;
    }

    public Page<VariationDTO> getProductByCategory(Pageable pageable, Long category) {
        Page<Variation> variation = variationRepository.findProductbyCatrgory(pageable, category);

        // Chuyển đổi từ Variation sang VariationDTO và duy trì phân trang
        return variation.map(entity -> {
            VariationDTO dto = new VariationDTO();
            dto.setID(entity.getID());
            dto.setSKU(entity.getSKU());
            dto.setProductID(new ProductDTO(
                    entity.getProductID().getID(),
                    entity.getProductID().getName(),
                    mapCategoryToDTO(entity.getProductID().getCategoryID()),
                    mapBrandToDTO(entity.getProductID().getBrandID()),
                    getProductImages("Variation", entity.getProductID().getID())
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
            dto.setImagesDTO(getVariationImage("Variation", entity.getID()));
            return dto;
        });
    }

    public List<Long> getVariationIDsByProductID(Long productID) {
        return variationRepository.findByProductID(productID)
                .stream()
                .map(Variation::getID)
                .collect(Collectors.toList());
    }


    // Chuyển từ ProductDTO sang Product entity
    public static Product mapProductDTOtoProduct(ProductDTO dto) {
        return new Product(
                dto.getID(),
                dto.getName(),
                mapCategoryToEntity(dto.getCategoryID()),
                mapBrandtoEntity(dto.getBrandID())
        );
    }

    // Chuyển từ Product entity sang ProductDTO
    public ProductDTO mapProductToProductDTO(Product entity) {
        return new ProductDTO(
                entity.getID(),
                entity.getName(),
                mapCategoryToDTO(entity.getCategoryID()),
                mapBrandToDTO(entity.getBrandID()),
                getProductImages( "Product", entity.getID())
        );
    }


    // Chuyển từ BrandDTO sang Brand entity
    public static Brand mapBrandtoEntity(BrandDTO dto) {
        return new Brand(
                dto.getID(),
                dto.getName(),
                dto.getNote(),
                dto.getStatus()
        );
    }

    // Chuyển từ Brand entity sang BrandDTO
    public static BrandDTO mapBrandToDTO(Brand entity) {
        return new BrandDTO(
                entity.getID(),
                entity.getName(),
                entity.getNote(),
                entity.getStatus()
        );
    }

    // Chuyển từ VariationDTO sang Variation entity
    public static Variation mapVariationDTOtoVariation(VariationDTO dto) {
        return new Variation(
                dto.getID(),
                mapProductDTOtoProduct(dto.getProductID()),
                dto.getName(),
                dto.getSKU(),
                dto.getPrice(),
                dto.getQuantity(),
                dto.getColor(),
                dto.getMaterial(),
                dto.getSize(),
                dto.getDescription(),
                dto.getSold(),
                dto.getStatus()
        );
    }



    // Chuyển từ Variation entity sang VariationDTO
    public VariationDTO mapVariationToDTO(Variation entity) {
        ProductDTO productDTO = mapProductToProductDTO(entity.getProductID());
        return new VariationDTO(
                entity.getID(),
                mapProductToProductDTO(entity.getProductID()),
                entity.getName(),
                entity.getSKU(),
                entity.getPrice(),
                entity.getQuantity(),
                entity.getColor(),
                entity.getMaterial(),
                entity.getSize(),
                entity.getDescription(),
                entity.getSold(),
                entity.getStatus(),
                getVariationImage("Variation", entity.getID())

        );
    }

    public List<ImagesDTO> getProductImages(String model, Long productID) {
        List<Images> list = imagesRepository.findByModelAndProductID(model,productID);
        return list.stream().map(images -> new ImagesDTO(images)).collect(Collectors.toList());
    }

    public ImagesDTO getVariationImage(String model, Long productId) {
        Images image = imagesRepository.findImageByModelAndProductID(model, productId);
        return image != null ? new ImagesDTO(image) : null;
    }


//    public Page<VariationDTO> getVariationByBrands(Pageable pageable, Long brands) {
//        Page<Variation> variation = variationRepository.findByBrand(pageable, brands);
//
//        // Chuyển đổi từ Variation sang VariationDTO và duy trì phân trang
//        return variation.map(variation -> {
//            VariationDTO dto = new VariationDTO();
//            dto.setID(variation.getID());
//            dto.setSKU(variation.getSKU());
//            dto.setProductID(new ProductDTO(
//                    variation.getProductID().getID(),
//                    variation.getProductID().getName(),
//                    mapCategoryToDTO(variation.getProductID().getCategoryID()),
//                    getImageByProduct(variation.getProductID().getID())
//            ));
//            System.out.println("ID Product for Images: " + getImageByProduct(variation.getProductID().getID()));
//
//            // chuyển đổi giá từ biến thể qua double
//            double price = variation.getPrice();
//            System.out.println(price);
//            // chuyển đổi từ double sang dạng string và đưa ra dưới dạng giá + VND
//            dto.setPrice(formatter.format(price));
//
//            dto.setQuantity(variation.getQuantity());
//            dto.setBrandID(new BrandDTO(
//                    variation.getBrandID().getID(),
//                    variation.getBrandID().getName(),
//                    variation.getBrandID().getNote(),
//                    variation.getBrandID().getStatus()
//            ));
//            dto.setMaterial(variation.getMaterial());
//            dto.setWeight(variation.getWeight());
//            dto.setStatus(variation.getStatus());
//            dto.setNote(variation.getDescription());
//            return dto;
//        });
//    }

    // Chuyển từ double sang String (đơn vị tiền tệ VND)
    public static String formatPrice(double price) {
        return NumberFormat.getCurrencyInstance(new Locale("vi", "VN")).format(price);
    }

    public static CategoryDTO mapCategoryToDTO(Category category) {
        if (category == null) {
            return null;
        }
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setID(category.getID());
        categoryDTO.setName(category.getName());
        categoryDTO.setStatus(category.getStatus());
        return categoryDTO;
    }

    // Map từ DTO sang Entity
    public static Category mapCategoryToEntity(CategoryDTO categoryDTO) {
        if (categoryDTO == null) {
            return null;
        }
        Category category = new Category();
        category.setID(categoryDTO.getID());
        category.setName(categoryDTO.getName());
        category.setStatus(categoryDTO.getStatus());
        return category;
    }
}
