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
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

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
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ADMIN)
                                .orElseThrow(() -> new RuntimeException("Lỗi: Không tìm thấy vai trò."));
                        roles.add(adminRole);
                        break;
                    case "mod":
                        Role modRole = roleRepository.findByName(ERole.MODERATOR)
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
}
