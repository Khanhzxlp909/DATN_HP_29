package com.example.DoAnTotNghiep_MiniatureCrafts.Controller.Auth;

import com.example.DoAnTotNghiep_MiniatureCrafts.Entity.*;
import com.example.DoAnTotNghiep_MiniatureCrafts.Repository.User.CustomerRepository;
import com.example.DoAnTotNghiep_MiniatureCrafts.Repository.User.EmployeeRepository;
import com.example.DoAnTotNghiep_MiniatureCrafts.Repository.User.RoleRepository;
import com.example.DoAnTotNghiep_MiniatureCrafts.Repository.User.AccountRepository;
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

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/users/")
public class AuthController {

    // Khai báo các thành phần cần thiết và sử dụng @Autowired để tiêm các thành phần vào lớp Controller này
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    CustomerRepository customerRepository;


    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    // Phương thức để xác thực người dùng khi đăng nhập
    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Long userId = userDetails.getId();

        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

//        String userRole = roles.stream()
//                .filter(role -> role.equals("CUSTOMER") || role.equals("ADMIN") || role.equals("USERS"))
//                .map(role -> {
//                    if (role.equals("CUSTOMER") && customerRepository.existsById(userId)) {
//                        return "customer";
//                    } else if (role.equals("ADMIN") && employeeRepository.existsById(userId)) {
//                        return "admin";
//                    } else if (role.equals("USERS") && employeeRepository.existsById(userId)) {
//                        return "users";
//                    }
//                    return "unknown"; // Giá trị mặc định nếu vai trò không hợp lệ
//                })
//                .findFirst()
//                .orElse("unknown");

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles

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
        Account user = new Account(
                signUpRequest.getId(),
                signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));

        // Lấy danh sách vai trò từ yêu cầu đăng ký
        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        // Kiểm tra và gán vai trò cho người dùng
        if (strRoles == null) {
            // Nếu không có vai trò trong yêu cầu, gán vai trò mặc định là USER
            Role userRole = roleRepository.findByName(ERole.USER)
                    .orElseThrow(() -> new RuntimeException("Lỗi: Không tìm thấy vai trò."));
            roles.add(userRole);
        } else {
            // Gán vai trò dựa trên yêu cầu
            strRoles.forEach(role -> {
                switch (role) {
                    case "1":
                        Role adminRole = roleRepository.findByName(ERole.ADMIN)
                                .orElseThrow(() -> new RuntimeException("Lỗi: Không tìm thấy vai trò."));
                        roles.add(adminRole);
                        break;
                    case "2":
                        Role modRole = roleRepository.findByName(ERole.CUSTOMER)
                                .orElseThrow(() -> new RuntimeException("Lỗi: Không tìm thấy vai trò."));
                        roles.add(modRole);
                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.USER)
                                .orElseThrow(() -> new RuntimeException("Lỗi: Không tìm thấy vai trò."));
                        roles.add(userRole);
                }
            });
        }

        // Thiết lập vai trò cho người dùng
        user.setRoles(roles);
        // Lưu người dùng mới vào cơ sở dữ liệu
        accountRepository.save(user);

        // Trả về thông báo đăng ký thành công
        return ResponseEntity.ok(new MessageResponse("Đăng ký người dùng thành công!"));
    }

    @GetMapping("/validateToken")
    public ResponseEntity<?> validateToken(@RequestHeader(value = "Authorization", required = false) String token) {
        if (token == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Authorization header is missing");
        }

        String jwtToken = token.startsWith("Bearer ") ? token.substring(7) : token;

        if (jwtUtils.validateJwtToken(jwtToken)) {
            return ResponseEntity.ok("done");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or expired token");
        }
    }

}
