package com.example.hp_29_MiniatureCrafts.security.jwt;

import com.example.hp_29_MiniatureCrafts.security.services.UsersDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class AuthTokenFilter extends OncePerRequestFilter {
    // AuthTokenFilter là một bộ lọc (filter) chỉ chạy một lần mỗi yêu cầu HTTP.

    @Autowired
    private JwtUtils jwtUtils;
    // Tự động tiêm lớp JwtUtils để làm việc với token JWT.

    @Autowired
    private UsersDetailsServiceImpl userDetailsService;
    // Tự động tiêm UserDetailsServiceImpl để lấy thông tin người dùng từ username.

    private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);
    // Tạo logger để ghi log các sự kiện hoặc lỗi.

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // Phương thức doFilterInternal được gọi cho mỗi yêu cầu HTTP.

        try {
            String jwt = parseJwt(request);
            // Gọi phương thức parseJwt để lấy token JWT từ request.

            if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
                // Nếu JWT không rỗng và hợp lệ, thực hiện giải mã để lấy username.

                String username = jwtUtils.getUserNameFromJwtToken(jwt);
                // Lấy username từ token.

                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                // Tải thông tin người dùng từ username.

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities());
                // Tạo đối tượng xác thực chứa thông tin người dùng và quyền hạn.

                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                // Thiết lập thông tin chi tiết từ request cho đối tượng xác thực.

                SecurityContextHolder.getContext().setAuthentication(authentication);
                // Đặt đối tượng xác thực vào SecurityContext để xác định người dùng hiện tại.
            }
        } catch (Exception e) {
            logger.error("Cannot set user authentication: {}", e);
            // Ghi log lỗi nếu có ngoại lệ xảy ra khi xác thực người dùng.
        }

        filterChain.doFilter(request, response);
        // Tiếp tục chuỗi filter để xử lý request.
    }

    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");
        // Lấy thông tin từ header "Authorization" của request.

        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            System.out.println("🔍 [API] Parsing JWT from Authorization header: " + headerAuth);
            return headerAuth.substring(7);
            // Nếu header có nội dung và bắt đầu bằng "Bearer ", cắt chuỗi để lấy token JWT.
        }

        return null;
        // Nếu không có token, trả về null.
    }
}
