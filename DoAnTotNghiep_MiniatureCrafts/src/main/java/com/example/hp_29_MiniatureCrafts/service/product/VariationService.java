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
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
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
        Images images = new Images();
        images.setProduct(mapProductDTOtoProduct(imagesDTO.getProduct()));
        images.setCd_Images(imagesDTO.getCd_Images());
        return new ImagesDTO(imagesRepository.save(images));
    }

    public List<ImagesDTO> findAll() {
        List<Images> images = imagesRepository.findAll();
        return images.stream().map(dto -> new ImagesDTO(dto)).collect(Collectors.toList());
    }

    // save truoc, lay id tu respone set va imagedto
    public ProductDTO saveProduct(ProductDTO productDTO) {
        Product product = new Product();
        product.setName(productDTO.getName());
        product.setCategoryID(mapCategoryToEntity(productDTO.getCategoryID()));
        return new ProductDTO(productRepository.save(product));
    }

    public ProductDTO updateProduct(ProductDTO productDTO) {
        Product product = new Product();
        product.setID(productDTO.getId());
        product.setName(productDTO.getName());
        product.setCategoryID(mapCategoryToEntity(productDTO.getCategoryID()));
        return new ProductDTO(productRepository.save(product));
    }


    public List<ProductDTO> getProducts() {
        List<Product> productList = productRepository.findAll();
        return productList.stream().map(product -> new ProductDTO(product)).collect(Collectors.toList());
    }

    public VariationDTO editVariation(Long id) {
        Variation variation = variationRepository.findByIdVariation(id);
        return new VariationDTO(variation);
    }

    public VariationDTO findByIDDTO(Long id) {
        Variation variation = variationRepository.findById(id).orElseThrow();
        return new VariationDTO(variation);
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
        Product product = new Product();
        product.setID(dto.getProductID().getId());
        product.setName(dto.getProductID().getName());
        product.setCategoryID(mapCategoryToEntity(dto.getProductID().getCategoryID()));


        // Chuyển từ BrandDTO sang Brand entity
        Brand brand = new Brand();
        brand.setID(dto.getBrandID().getID());
        brand.setName(dto.getBrandID().getName());
        brand.setNote(dto.getBrandID().getNote());
        brand.setStatus(dto.getBrandID().getStatus());


        //tryền DTO vào entity
        entity.setProductID(product);
        entity.setSKU(UUID.randomUUID().toString());

        String currency = dto.getPrice();
        double amount = parsePrice(currency);
        entity.setPrice(amount);

        System.out.println(amount); // In ra: 1500000.0


        entity.setBrandID(brand);
        entity.setQuantity(dto.getQuantity());
        entity.setMaterial(dto.getMaterial());
        entity.setWeight(dto.getWeight());
        entity.setStatus(dto.getStatus());

        return variationRepository.save(entity);
    }

    public Variation save(Variation variation) {
        return variationRepository.save(variation);
    }

    //update sarn pham
    public Variation update(VariationDTO varDTO) {
        Variation variation = new Variation();

        // Chuyển từ ProductDTO sang Product entity
        Product product = new Product();
        product.setID(varDTO.getProductID().getId());
        product.setName(varDTO.getProductID().getName());
        product.setCategoryID(mapCategoryToEntity(varDTO.getProductID().getCategoryID()));


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

        String currency = varDTO.getPrice();
        double amount = parsePrice(currency);
        variation.setPrice(amount);
        System.out.println(amount); // In ra: 1500000.0

        variation.setBrandID(brand);
        variation.setQuantity(varDTO.getQuantity());
        variation.setMaterial(varDTO.getMaterial());
        variation.setWeight(varDTO.getWeight());
        variation.setStatus(varDTO.getStatus());

        return variationRepository.save(variation);
    }

    public static double parseCurrency(String currency) throws ParseException {
        NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        Number number = formatter.parse(currency);
        return number.doubleValue();
    }

    // Chuyển từ String (đơn vị tiền tệ) sang double
    public static double parsePrice(String price) {
        if (price == null || price.isBlank()) {
            return 0; // Giá trị mặc định nếu price null hoặc rỗng
        }
        try {
            // Xóa ký tự không mong muốn như " ₫" hoặc khoảng trắng
            String sanitizedPrice = price.replaceAll("[^\\d.,]", "").replace(",", "");
            // Chuyển thành số dạng double
            return Double.parseDouble(sanitizedPrice);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return 0; // Giá trị mặc định khi lỗi
        }
    }


    public void delete(Long id) {
        Variation variation = variationRepository.findByID(id);
        variation.setStatus(false);
        // Xóa đơn hàng
        variationRepository.save(variation);
    }


    // lấy sản phaarm và phân trang
    public Page<VariationDTO> getAll(Pageable pageable) {
        // Truy vấn các Variations theo Status và phân trang
        Page<Variation> variations = variationRepository.findAll(pageable);

        NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        // Chuyển đổi từ Variation sang VariationDTO và duy trì phân trang
        return variations.map(variation -> {
            VariationDTO dto = new VariationDTO();
            dto.setID(variation.getID());
            dto.setSKU(variation.getSKU());
            dto.setProductID(new ProductDTO(
                    variation.getProductID().getID(),
                    variation.getProductID().getName(),
                    mapCategoryToDTO(variation.getProductID().getCategoryID()),
                    getImageByProduct(variation.getProductID().getID())
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
            dto.setNote(variation.getNote());
            dto.setSold(variation.getSold());
            dto.setSupplier(mapSupplierToSupplierDTO(variation.getSupplier()));
            return dto;
        });
    }// lấy sản phaarm và phân trang


    public Page<VariationDTO> getVariationsBystatus(Pageable pageable) {

        // Truy vấn các Variations theo Status và phân trang
        Page<Variation> variations = variationRepository.getVariationByStatus(pageable);

        NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        // Chuyển đổi từ Variation sang VariationDTO và duy trì phân trang
        return variations.map(variation -> {
            VariationDTO dto = new VariationDTO();
            dto.setID(variation.getID());
            dto.setSKU(variation.getSKU());
            dto.setProductID(new ProductDTO(
                    variation.getProductID().getID(),
                    variation.getProductID().getName(),
                    mapCategoryToDTO(variation.getProductID().getCategoryID()),
                    getImageByProduct(variation.getProductID().getID())
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
            dto.setNote(variation.getNote());
            dto.setSold(variation.getSold());
            dto.setSupplier(mapSupplierToSupplierDTO(variation.getSupplier()));
            return dto;
        });
    }

    public Page<VariationDTO> getVariationsByBestseller(Pageable pageable) {

        // Truy vấn các Variations theo Status và phân trang
        Page<Variation> variations = variationRepository.top6BestSeller(pageable);

        NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        // Chuyển đổi từ Variation sang VariationDTO và duy trì phân trang
        return variations.map(variation -> {
            VariationDTO dto = new VariationDTO();
            dto.setID(variation.getID());
            dto.setSKU(variation.getSKU());
            dto.setProductID(new ProductDTO(
                    variation.getProductID().getID(),
                    variation.getProductID().getName(),
                    mapCategoryToDTO(variation.getProductID().getCategoryID()),
                    getImageByProduct(variation.getProductID().getID())
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
            dto.setNote(variation.getNote());
            dto.setSold(variation.getSold());
            dto.setSupplier(mapSupplierToSupplierDTO(variation.getSupplier()));
            return dto;
        });
    }

    // lấy sản phaarm và phân trang
    public Page<VariationDTO> newVariation(Pageable pageable) {
        // Truy vấn các Variations theo Status và phân trang
        Page<Variation> variations = variationRepository.newVariation(pageable);

        NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        // Chuyển đổi từ Variation sang VariationDTO và duy trì phân trang
        return variations.map(variation -> {
            VariationDTO dto = new VariationDTO();
            dto.setID(variation.getID());
            dto.setSKU(variation.getSKU());
            dto.setProductID(new ProductDTO(
                    variation.getProductID().getID(),
                    variation.getProductID().getName(),
                    mapCategoryToDTO(variation.getProductID().getCategoryID()),
                    getImageByProduct(variation.getProductID().getID())
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
            dto.setNote(variation.getNote());
            dto.setSold(variation.getSold());
            dto.setSupplier(mapSupplierToSupplierDTO(variation.getSupplier()));
            return dto;
        });
    }

    // Chuyển từ ProductDTO sang Product entity
    public static Product mapProductDTOtoProduct(ProductDTO dto) {
        return new Product(
                dto.getId(),
                dto.getName(),
                mapCategoryToEntity(dto.getCategoryID())
        );
    }

    // Chuyển từ Product entity sang ProductDTO
    public ProductDTO mapProductToProductDTO(Product entity) {
        return new ProductDTO(
                entity.getID(),
                entity.getName(),
                mapCategoryToDTO(entity.getCategoryID()),
                getImageByProduct(entity.getID())

        );
    }
    public static Supplier mapSupplierDTOToSupplier(SupplierDTO entity) {
        return new Supplier(
                entity.getId(),
                entity.getName(),
                entity.getAddress(),
                entity.getPhone(),
                entity.getNote(),
                entity.getStatus(),
                entity.getCreationDate(),
                entity.getEditDate()
        );
    }
    public static SupplierDTO mapSupplierToSupplierDTO(Supplier entity) {
        return new SupplierDTO(
                entity.getID(),
                entity.getName(),
                entity.getAddress(),
                entity.getPhone(),
                entity.getNote(),
                entity.getStatus(),
                entity.getCreation_date(),
                entity.getEdit_Date()
        );
    }

    // Chuyển từ BrandDTO sang Brand entity
    public static Brand mapBrandDTOtoBrand(BrandDTO dto) {
        return new Brand(
                dto.getID(),
                dto.getName(),
                dto.getNote(),
                dto.getStatus()
        );
    }

    // Chuyển từ Brand entity sang BrandDTO
    public static BrandDTO mapBrandToBrandDTO(Brand entity) {
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
                dto.getSKU(),
                parsePrice(dto.getPrice()),
                dto.getQuantity(),
                mapBrandDTOtoBrand(dto.getBrandID()),
                dto.getMaterial(),
                dto.getWeight(),
                dto.getStatus(),
                dto.getNote(),
                dto.getSold(),
                mapSupplierDTOToSupplier(dto.getSupplier())
        );
    }



    // Chuyển từ Variation entity sang VariationDTO
    public VariationDTO mapVariationToVariationDTO(Variation entity) {
        ProductDTO productDTO = mapProductToProductDTO(entity.getProductID());
        System.out.println("CD Images: " + getImageByProduct(entity.getProductID().getID()));
        return new VariationDTO(
                entity.getID(),
                mapProductToProductDTO(entity.getProductID()),
                entity.getSKU(),
                formatPrice(entity.getPrice()),
                entity.getQuantity(),
                mapBrandToBrandDTO(entity.getBrandID()),
                entity.getMaterial(),
                entity.getWeight(),
                entity.getStatus(),
                entity.getNote(),
                entity.getSold(),
                mapSupplierToSupplierDTO(entity.getSupplier()),
                getImageByProduct(entity.getProductID().getID())

        );
    }

    public List<ImagesDTO> getImageByProduct(Long productID) {
        List<Images> list = imagesRepository.findByProduct(productID);
        return list.stream().map(images -> new ImagesDTO(images)).collect(Collectors.toList());
    }


    public Page<VariationDTO> findByName(Pageable pageable, String id) {
        // Truy vấn các Variations theo Status và phân trang
        Page<Variation> variations = variationRepository.findByName(pageable, id);

        // Chuyển đổi từ Variation sang VariationDTO và duy trì phân trang
        return variations.map(variation -> {
            VariationDTO dto = new VariationDTO();
            dto.setID(variation.getID());
            dto.setSKU(variation.getSKU());
            dto.setProductID(new ProductDTO(
                    variation.getProductID().getID(),
                    variation.getProductID().getName(),
                    mapCategoryToDTO(variation.getProductID().getCategoryID()),
                    getImageByProduct(variation.getProductID().getID())
            ));
            System.out.println("ID Product for Images: " + getImageByProduct(variation.getProductID().getID()));

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
            dto.setNote(variation.getNote());
            return dto;
        });
    }

    public Page<VariationDTO> filterPrice(Pageable pageable, Double minprice, Double maxprice) {
        // Truy vấn các Variations theo Status và phân trang
        Page<Variation> variations = variationRepository.findByPriceRange(pageable, minprice, maxprice);

        // Chuyển đổi từ Variation sang VariationDTO và duy trì phân trang
        return variations.map(variation -> {
            VariationDTO dto = new VariationDTO();
            dto.setID(variation.getID());
            dto.setSKU(variation.getSKU());
            dto.setProductID(new ProductDTO(
                    variation.getProductID().getID(),
                    variation.getProductID().getName(),
                    mapCategoryToDTO(variation.getProductID().getCategoryID()),
                    getImageByProduct(variation.getProductID().getID())
            ));
            System.out.println("ID Product for Images: " + getImageByProduct(variation.getProductID().getID()));

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
            dto.setNote(variation.getNote());
            return dto;
        });
    }


    public VariationDTO findByid(Long id) {
        // Truy vấn các Variations theo Status và phân trang
        Variation variation = variationRepository.findByIdVariation(id);

        // Chuyển đổi từ Variation sang VariationDTO và duy trì phân trang
        VariationDTO dto = new VariationDTO();
        dto.setID(variation.getID());
        dto.setSKU(variation.getSKU());
        dto.setProductID(new ProductDTO(
                variation.getProductID().getID(),
                variation.getProductID().getName(),
                mapCategoryToDTO(variation.getProductID().getCategoryID()),
                getImageByProduct(variation.getProductID().getID())
        ));
        System.out.println("ID Product for Images: " + getImageByProduct(variation.getProductID().getID()));

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
        dto.setNote(variation.getNote());
        return dto;
    }

    public Page<VariationDTO> getProductByCategory(Pageable pageable, Long category) {
        Page<Variation> variations = variationRepository.findProductbyCatrgory(pageable, category);

        // Chuyển đổi từ Variation sang VariationDTO và duy trì phân trang
        return variations.map(variation -> {
            VariationDTO dto = new VariationDTO();
            dto.setID(variation.getID());
            dto.setSKU(variation.getSKU());
            dto.setProductID(new ProductDTO(
                    variation.getProductID().getID(),
                    variation.getProductID().getName(),
                    mapCategoryToDTO(variation.getProductID().getCategoryID()),
                    getImageByProduct(variation.getProductID().getID())
            ));
            System.out.println("ID Product for Images: " + getImageByProduct(variation.getProductID().getID()));

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
            dto.setNote(variation.getNote());
            return dto;
        });
    }

    public Page<VariationDTO> getVariationByBrands(Pageable pageable, Long brands) {
        Page<Variation> variations = variationRepository.findByBrand(pageable, brands);

        // Chuyển đổi từ Variation sang VariationDTO và duy trì phân trang
        return variations.map(variation -> {
            VariationDTO dto = new VariationDTO();
            dto.setID(variation.getID());
            dto.setSKU(variation.getSKU());
            dto.setProductID(new ProductDTO(
                    variation.getProductID().getID(),
                    variation.getProductID().getName(),
                    mapCategoryToDTO(variation.getProductID().getCategoryID()),
                    getImageByProduct(variation.getProductID().getID())
            ));
            System.out.println("ID Product for Images: " + getImageByProduct(variation.getProductID().getID()));

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
            dto.setNote(variation.getNote());
            return dto;
        });
    }

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
