//package com.example.DoAnTotNghiep_MiniatureCrafts.Controller.user;
//
//
//import com.example.DoAnTotNghiep_MiniatureCrafts.DTO.JwtResponse;
//import com.example.DoAnTotNghiep_MiniatureCrafts.DTO.UsersDTO;
//import com.example.DoAnTotNghiep_MiniatureCrafts.Service.Security.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/users/")
//public class UsersController {
//
////    @Autowired
////    private UserService userService;
//
////    @Autowired
////    private JwtTokenProvider jwtTokenProvider;
//
////    @Autowired
////    private AuthenticationManager authenticationManager;
////
////    @PostMapping("login")
////    public String findAllByUsername(@RequestBody UsersDTO users) {
////        String username = users.getUsername();
////        String password = users.getPassword();
////        UsersDTO lstUsers =  userService.findAllByUsername(username, password);
////        return "done";
////    }
//
//
////    @PostMapping("login")
////    public ResponseEntity<?> findAllByUsername(@RequestBody UsersDTO users) {
////        String username = users.getUsername();
////        String password = users.getPassword();
////        UsersDTO lstUsers =  userService.findAllByUsername(username, password);;
////
////
////        Authentication authentication = authenticationManager.authenticate(
////                new UsernamePasswordAuthenticationToken(
////                        lstUsers.getUsername(),
////                        lstUsers.getPassword()
////                )
////        );
////
////        // Đặt thông tin xác thực vào SecurityContext
//////        SecurityContextHolder.getContext().setAuthentication(authentication);
//////
//////        // Tạo token JWT
//////        String token = jwtTokenProvider.generateToken(authentication);
////
////        // Trả token trong JSON response
////        return ResponseEntity.ok(new JwtResponse(token));
////    }
//
//}
