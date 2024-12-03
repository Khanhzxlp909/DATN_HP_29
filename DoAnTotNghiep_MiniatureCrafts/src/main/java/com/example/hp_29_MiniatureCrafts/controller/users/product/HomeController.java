package com.example.hp_29_MiniatureCrafts.controller.users.product;


import com.example.hp_29_MiniatureCrafts.dto.BrandDTO;
import com.example.hp_29_MiniatureCrafts.dto.CategoryDTO;
import com.example.hp_29_MiniatureCrafts.dto.CustomerDTO;
import com.example.hp_29_MiniatureCrafts.dto.VariationDTO;
import com.example.hp_29_MiniatureCrafts.entity.Account;
import com.example.hp_29_MiniatureCrafts.entity.Customer;
import com.example.hp_29_MiniatureCrafts.entity.ERole;
import com.example.hp_29_MiniatureCrafts.entity.Role;
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

    @GetMapping("home")
    public Page<VariationDTO> home(Pageable pageable) {
        return variationService.getAll(pageable);
    }

    @PostMapping("updateInfo")
    public Customer updateCustomer(@RequestBody CustomerDTO customerDTO) {
        return customerService.updateCustomer(customerDTO);
    }

    @PostMapping("updateAccount")
    public Account updateCustomer(@RequestBody Account account) {
        return accountService.updateAccount(account);
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
    public Page<VariationDTO> filterCategory(Pageable pageable, @PathVariable("category") Long category) {
        return variationService.getProductByCategory(pageable, category);
    }

    @GetMapping("brands/{brands}")
    public Page<VariationDTO> filterBrands(Pageable pageable, @PathVariable("brands") Long brands) {
        return variationService.getVariationByBrands(pageable, brands);
    }


    @GetMapping("categories")
    public List<CategoryDTO> getALL() {
        return categoryService.getALL();
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
