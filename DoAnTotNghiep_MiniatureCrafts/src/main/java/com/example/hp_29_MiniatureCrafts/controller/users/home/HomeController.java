package com.example.hp_29_MiniatureCrafts.controller.users.home;


import com.example.hp_29_MiniatureCrafts.dto.*;
import com.example.hp_29_MiniatureCrafts.entity.*;
import com.example.hp_29_MiniatureCrafts.payload.request.LoginRequest;
import com.example.hp_29_MiniatureCrafts.payload.request.SignupRequest;
import com.example.hp_29_MiniatureCrafts.payload.response.JwtResponse;
import com.example.hp_29_MiniatureCrafts.payload.response.MessageResponse;
import com.example.hp_29_MiniatureCrafts.repository.auth.AccountRepository;
import com.example.hp_29_MiniatureCrafts.repository.auth.CustomerRepository;
import com.example.hp_29_MiniatureCrafts.repository.auth.EmployeeRepository;
import com.example.hp_29_MiniatureCrafts.repository.auth.RoleRepository;
import com.example.hp_29_MiniatureCrafts.security.jwt.JwtUtils;
import com.example.hp_29_MiniatureCrafts.security.services.UserDetailsImpl;
import com.example.hp_29_MiniatureCrafts.service.account.AccountService;
import com.example.hp_29_MiniatureCrafts.service.account.customer.CustomerService;
import com.example.hp_29_MiniatureCrafts.service.product.BrandsService;
import com.example.hp_29_MiniatureCrafts.service.product.CategoryService;
import com.example.hp_29_MiniatureCrafts.service.product.ProductService;
import com.example.hp_29_MiniatureCrafts.service.product.VariationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("MiniatureCrafts")
//@CrossOrigin(value = "*")
public class HomeController {

    @Autowired
    private VariationService variationService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private AccountService accountService;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CategoryService categoryService;

    @Autowired
    BrandsService brandsService;

    @Autowired
    ProductService productService;

    // phương thức login
    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        // Xác thực người dùng
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        System.out.println("username: " + loginRequest.getUsername());
        System.out.println("password: " + loginRequest.getPassword());

        // Lấy thông tin người dùng từ đối tượng xác thực
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        String accountRole = userDetails.getAccountRole();  // Lấy vai trò hiện tại
        Long id = userDetails.getId();

        Object userInfo;

        // Kiểm tra vai trò và truy xuất thông tin từ bảng tương ứng
        if ("ADMIN".equals(accountRole) || "USERS".equals(accountRole)) {
            userInfo = employeeRepository.findEmployeeByUsers(id);
        } else if ("CUSTOMER".equals(accountRole)) {
            userInfo = customerRepository.findByUsers(id);
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Vai trò không hợp lệ!");
        }

        // Trả về JWT và thông tin của người dùng
        return ResponseEntity.ok(new JwtResponse(
                jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                userInfo,
                accountRole
        ));
    }

    // Phương thức để đăng ký người dùng mới
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        // Kiểm tra xem username đã tồn tại trong hệ thống chưa
        if (accountRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Lỗi: Tên người dùng đã tồn tại!"));
        }

        // Kiểm tra xem email đã được sử dụng chưa
        if (accountRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Lỗi: Email đã được sử dụng!"));
        }

        // Tạo tài khoản người dùng mới với các thông tin từ yêu cầu đăng ký
        String accountRole = "CUSTOMER"; // Đặt giá trị mặc định cho accountRole là "CUSTOMER"
        Account user = new Account(
                signUpRequest.getUsersid(),
                signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()),
                accountRole
        );

        // Lấy danh sách vai trò từ yêu cầu đăng ký
        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        // Kiểm tra và gán vai trò cho người dùng
        if (strRoles == null || strRoles.isEmpty()) {
            // Nếu không có vai trò trong yêu cầu, gán vai trò mặc định là CUSTOMER
            Role customerRole = roleRepository.findByName(ERole.CUSTOMER)
                    .orElseThrow(() -> new RuntimeException("Lỗi: Không tìm thấy vai trò CUSTOMER."));
            roles.add(customerRole);
        } else {
            // Gán vai trò dựa trên yêu cầu
            for (String role : strRoles) {
                switch (role) {
                    case "1":
                        Role adminRole = roleRepository.findByName(ERole.ADMIN)
                                .orElseThrow(() -> new RuntimeException("Lỗi: Không tìm thấy vai trò ADMIN."));
                        roles.add(adminRole);
                        accountRole = "ADMIN"; // Cập nhật accountRole thành ADMIN
                        break;
                    case "2":
                        Role userRole = roleRepository.findByName(ERole.USER)
                                .orElseThrow(() -> new RuntimeException("Lỗi: Không tìm thấy vai trò USER."));
                        roles.add(userRole);
                        accountRole = "USER"; // Cập nhật accountRole thành USER
                        break;
                    default:
                        Role customerRole = roleRepository.findByName(ERole.CUSTOMER)
                                .orElseThrow(() -> new RuntimeException("Lỗi: Không tìm thấy vai trò CUSTOMER."));
                        roles.add(customerRole);
                        accountRole = "CUSTOMER"; // Cập nhật accountRole thành CUSTOMER
                }
            }
        }

        // Thiết lập vai trò cho người dùng
        user.setRoles(roles);
        // Cập nhật lại role vào account trước khi lưu
        user.setAccountRole(accountRole);

        user.setActive(true);

        user.setCreation_date(LocalDate.now());
        // Lưu người dùng mới vào cơ sở dữ liệu
        accountRepository.save(user);

        // Trả về thông báo đăng ký thành công
        return ResponseEntity.ok(user);
    }

    @PostMapping("check-password")
    public ResponseEntity<?> checkPassword(@RequestBody LoginRequest account) {
        Account account1 = accountService.checkPassword(account.getUsername(), account.getPassword());
        System.out.println("username log 205: "+ account.getUsername());
        System.out.println("password log 206: "+ account.getPassword());
        if (account1 != null) {
            return ResponseEntity.ok(account1);
        } else {
            RuntimeException ex = new RuntimeException();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }


    @PostMapping("/registerinfo")
    public ResponseEntity<?> registerInfo(@Valid @RequestBody CustomerDTO registerInfo) {
        // Kiểm tra xem username đã tồn tại trong hệ thống chưa
        if (customerRepository.existsByPhone(registerInfo.getPhone())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Lỗi: Số điện thoại đã tồn tại!"));
        }
        CustomerDTO customerDTO = new CustomerDTO();
        // Lấy danh sách vai trò từ yêu cầu đăng ký
        System.out.println(registerInfo.getName());
        System.out.println(registerInfo.getAddress());
        System.out.println(registerInfo.getNote());
        System.out.println(registerInfo.getPhone());

        customerDTO.setName(registerInfo.getName());
        customerDTO.setAddress(registerInfo.getAddress());
        customerDTO.setPhone(registerInfo.getPhone());
        customerDTO.setNote(registerInfo.getNote());

        customerDTO.setStatus(true);
        LocalDate date = LocalDate.now();
        customerDTO.setCreation_date(date);
        // Lưu người dùng mới vào cơ sở dữ liệu
        customerService.createCustomer(customerDTO);

        // Trả về thông báo đăng ký thành công
        // trả về thông tin user mới đăng ký
        CustomerDTO customer = customerService.findbyPhone(registerInfo.getPhone());
        return ResponseEntity.ok(customer);
    }

    @GetMapping("home")
    public Page<VariationDTO> home(Pageable pageable) {
        return variationService.getAll(pageable);
    }

    @GetMapping("product/findByID/{id}")
    public ProductDTO getProductByID(@PathVariable Long id) {
        return productService.getProductByID(id);
    }

    @GetMapping("fetch_products")
    public Page<ProductDTO> getProductDTOS(Pageable pageable) {
        return variationService.getProducts(pageable);
    }

    @GetMapping("new")
    public Page<VariationDTO> newVariation(Pageable pageable) {
        return variationService.newVariation(pageable);
    }

    @GetMapping("bestseller")
    public Page<VariationDTO> bestSeller(Pageable pageable) {
        return variationService.getVariationsByBestseller(pageable);
    }

    @GetMapping("filterByPrice")
    public Page<VariationDTO> home(Pageable pageable,
                                   @RequestParam(required = false) Double minPrice,
                                   @RequestParam(required = false) Double maxPrice) {
        return variationService.filterPrice(pageable, minPrice, maxPrice);
    }

    @PostMapping("updateInfo/{id}")
    public Customer updateCustomer(@RequestBody CustomerDTO customerDTO) {
        return customerService.updateCustomer(customerDTO);
    }

    @PostMapping("updateAccount")
    public Account updateCustomer(@RequestBody Account account) {
        return accountService.updateAccount(account);
    }

    @PostMapping("changepassword/{username}")
    public Account changePassword(@PathVariable("username") String username, @RequestBody Account account) {
        return accountService.changePassword(username, account.getPassword());
    }

    @GetMapping("result/{name}")
    public Page<VariationDTO> findByName(Pageable pageable, @PathVariable("name") String name) {
        return variationService.findByName(pageable, name);
    }

    @GetMapping("findid/{id}")
    public VariationDTO findByid(@PathVariable("id") Long id) {
        return variationService.findByid(id);

    }

    @GetMapping("category/{category}")
    public Page<ProductDTO> filterCategory(Pageable pageable, @PathVariable("category") Long category) {
        return variationService.fillterByCategory(pageable, category);
    }

    @GetMapping("brands/{brand}")
    public Page<ProductDTO> fillterBrand(Pageable pageable, @PathVariable("brand") Long brand) {
        return variationService.fillterByBrand(pageable, brand);
    }

    @GetMapping("categories")
    public List<CategoryDTO> fillCategory() {
        return categoryService.getALL();
    }

    @GetMapping("brands")
    public List<BrandDTO> fillBrands() {
        return brandsService.getALL();
    }

    @GetMapping("brand")
    public List<BrandDTO> getALLbrand() {
        return brandsService.getALL();
    }


    @GetMapping("/user")
    public ResponseEntity<?> validateToken(@RequestHeader(value = "Authorization", required = false) String token) {
        if (token == null || !token.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new MessageResponse("Lỗi: Header Authorization thiếu hoặc không hợp lệ"));
        }

        // Lấy token từ Authorization header
        String jwtToken = token.substring(7);  // Cắt bỏ "Bearer " khỏi token

        // Kiểm tra tính hợp lệ của token
        if (!jwtUtils.validateJwtToken(jwtToken)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new MessageResponse("Lỗi: Token không hợp lệ hoặc đã hết hạn"));
        }

        // Lấy username từ token
        String username = jwtUtils.getUserNameFromJwtToken(jwtToken);

        // Tìm Account từ username
        Account account = accountRepository.findByUsername(username);

        // Lấy vai trò của người dùng từ UserDetailsImpl
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String accountRole = userDetails.getAccountRole();  // Vai trò hiện tại của người dùng
        Long id = userDetails.getId();

        Object userInfo;

        // Kiểm tra vai trò và truy xuất thông tin từ bảng tương ứng
        if ("ADMIN".equals(accountRole) || "USERS".equals(accountRole)) {
            userInfo = employeeRepository.findEmployeeByUsers(id);
        } else if ("CUSTOMER".equals(accountRole)) {
            userInfo = customerRepository.findByUsers(id);
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Vai trò không hợp lệ!");
        }

        // Trả về JWT và thông tin của người dùng
        return ResponseEntity.ok(new JwtResponse(
                jwtToken,  // Trả về chính token được xác minh
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                userInfo,
                accountRole
        ));
    }
}
