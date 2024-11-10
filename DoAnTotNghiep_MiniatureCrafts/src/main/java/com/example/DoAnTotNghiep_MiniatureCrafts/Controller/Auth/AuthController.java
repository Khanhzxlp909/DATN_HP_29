package com.example.DoAnTotNghiep_MiniatureCrafts.Controller.Auth;

import com.example.DoAnTotNghiep_MiniatureCrafts.Entity.ERole;
import com.example.DoAnTotNghiep_MiniatureCrafts.Entity.Role;
import com.example.DoAnTotNghiep_MiniatureCrafts.Entity.Users;
import com.example.DoAnTotNghiep_MiniatureCrafts.Repository.User.RoleRepository;
import com.example.DoAnTotNghiep_MiniatureCrafts.Repository.User.UserRepository;
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

import java.math.BigDecimal;
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
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    // Phương thức để xác thực người dùng khi đăng nhập
    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        // Xác thực người dùng với username và password
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        // Lưu thông tin xác thực vào SecurityContext
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Tạo token JWT cho người dùng sau khi đăng nhập thành công
        String jwt = jwtUtils.generateJwtToken(authentication);

        // Lấy thông tin người dùng hiện tại
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        // Lấy danh sách các vai trò của người dùng
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        BigDecimal totalAmount = new BigDecimal(90000);
        BigDecimal totalAmount2 = new BigDecimal(10000);
        BigDecimal totalAmount3 =  totalAmount.add(totalAmount2);
        System.out.println("tiền: "+totalAmount3);


        // Trả về thông tin JWT cùng với thông tin người dùng và các vai trò
        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));
    }

    // Phương thức để đăng ký người dùng mới
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        // Kiểm tra xem username đã tồn tại trong hệ thống chưa
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Lỗi: Tên người dùng đã tồn tại!"));
        }

        // Kiểm tra xem email đã được sử dụng chưa
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Lỗi: Email đã được sử dụng!"));
        }

        // Tạo tài khoản người dùng mới với các thông tin từ yêu cầu đăng ký
        Users user = new Users(signUpRequest.getName(),
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
                        Role modRole = roleRepository.findByName(ERole.MOD)
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
        userRepository.save(user);

        // Trả về thông báo đăng ký thành công
        return ResponseEntity.ok(new MessageResponse("Đăng ký người dùng thành công!"));
    }

    @PostMapping("/verifyToken")
    public ResponseEntity<?> verifyToken(@RequestBody String token) {
        // Kiểm tra tính hợp lệ của token
        if (jwtUtils.validateJwtToken(token)) {
            // Token hợp lệ, trả về phản hồi thành công
            return ResponseEntity.ok(new MessageResponse("done"));
        } else {
            // Token không hợp lệ, trả về phản hồi lỗi
            return ResponseEntity.badRequest().body(new MessageResponse("Token không hợp lệ!"));
        }
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
