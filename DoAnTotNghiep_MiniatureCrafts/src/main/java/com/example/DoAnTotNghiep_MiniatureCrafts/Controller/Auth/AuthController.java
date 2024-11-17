package com.example.DoAnTotNghiep_MiniatureCrafts.Controller.Auth;

import com.example.DoAnTotNghiep_MiniatureCrafts.Entity.*;
import com.example.DoAnTotNghiep_MiniatureCrafts.Repository.Auth.CustomerRepository;
import com.example.DoAnTotNghiep_MiniatureCrafts.Repository.Auth.EmployeeRepository;
import com.example.DoAnTotNghiep_MiniatureCrafts.Repository.Auth.RoleRepository;
import com.example.DoAnTotNghiep_MiniatureCrafts.Repository.Auth.AccountRepository;
import com.example.DoAnTotNghiep_MiniatureCrafts.Service.AccountService.AuthService;
import com.example.DoAnTotNghiep_MiniatureCrafts.payload.request.LoginRequest;
import com.example.DoAnTotNghiep_MiniatureCrafts.payload.request.SignupRequest;
import com.example.DoAnTotNghiep_MiniatureCrafts.payload.response.JwtResponse;
import com.example.DoAnTotNghiep_MiniatureCrafts.payload.response.MessageResponse;
import com.example.DoAnTotNghiep_MiniatureCrafts.security.jwt.JwtUtils;
import com.example.DoAnTotNghiep_MiniatureCrafts.security.services.UserDetailsImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/users/")
@CrossOrigin(value = "*")
public class AuthController {

    // Khai báo các thành phần cần thiết và sử dụng @Autowired để tiêm các thành phần vào lớp Controller này
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    AuthService authService;


    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    // search cột role trong db, nếu thỏa mãn điều kiện AcccoutRole là ADMIN hoặc USERS, thì sẽ gọi hàm tìm kiếm bảng Employee theo USERS id
//    nếu thỏa mãn điều kiện là CUSTOMER sẽ tìm kiếm bên bảng customer theo Users id
    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        // Xác thực người dùng
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        // Lấy thông tin người dùng từ đối tượng xác thực
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        String accountRole = userDetails.getAccountRole();  // Lấy vai trò hiện tại
        Long id = userDetails.getId();

        List<?> userInfo;

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
        String accountRole = "USERS"; // Đặt giá trị mặc định cho accountRole
        Account user = new Account(
                signUpRequest.getId(),
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
            // Nếu không có vai trò trong yêu cầu, gán vai trò mặc định là USER
            Role userRole = roleRepository.findByName(ERole.USER)
                    .orElseThrow(() -> new RuntimeException("Lỗi: Không tìm thấy vai trò."));
            roles.add(userRole);
        } else {
            // Gán vai trò dựa trên yêu cầu
            for (String role : strRoles) {
                switch (role) {
                    case "1":
                        Role adminRole = roleRepository.findByName(ERole.ADMIN)
                                .orElseThrow(() -> new RuntimeException("Lỗi: Không tìm thấy vai trò ADMIN."));
                        roles.add(adminRole);
                        accountRole = "ADMIN";
                        break;
                    case "2":
                        Role customerRole = roleRepository.findByName(ERole.CUSTOMER)
                                .orElseThrow(() -> new RuntimeException("Lỗi: Không tìm thấy vai trò CUSTOMER."));
                        roles.add(customerRole);
                        accountRole = "CUSTOMER";
                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.USER)
                                .orElseThrow(() -> new RuntimeException("Lỗi: Không tìm thấy vai trò USER."));
                        roles.add(userRole);
                        accountRole = "USERS";
                }
            }
        }

        System.out.println("Role hiện tại: " + accountRole);

        // Thiết lập vai trò cho người dùng
        user.setRoles(roles);
        // Cập nhật lại role vào account trước khi lưu
        user.setAccountRole(accountRole);

        // Lưu người dùng mới vào cơ sở dữ liệu
        accountRepository.save(user);

        // Trả về thông báo đăng ký thành công
        return ResponseEntity.ok(new MessageResponse("Đăng ký người dùng thành công!"));
    }


    @GetMapping("/validateToken")
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

        List<?> userInfo;

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
