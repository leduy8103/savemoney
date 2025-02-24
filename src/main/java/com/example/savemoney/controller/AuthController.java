package com.example.savemoney.controller;

import com.example.savemoney.model.MyUser;
import com.example.savemoney.payload.request.LoginRequest;
import com.example.savemoney.payload.request.RegisterRequest;
import com.example.savemoney.payload.response.JwtResponse;
import com.example.savemoney.payload.response.MessageResponse;
import com.example.savemoney.repositories.UserRepo;
import com.example.savemoney.security.JwtUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserRepo userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            // Tìm user theo email
            MyUser user = userRepository.findByEmail(loginRequest.getEmail());
            if (user == null) {
                throw new BadCredentialsException("Invalid email or password");
            }

            // Xác thực với email và password
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(), // Sử dụng email cho authentication
                            loginRequest.getPassword())
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Tạo token JWT
            String jwt = jwtUtil.generateToken(user.getEmail());

            // Trả về jwt token và thông tin user
            return ResponseEntity.ok(new JwtResponse(
                    jwt,
                    (long) user.getUserId(),
                    user.getUsername(),
                    user.getEmail()
            ));

        } catch (BadCredentialsException e) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new MessageResponse("Error: Invalid email or password"));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new MessageResponse("Error: " + e.getMessage()));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest registerRequest) {
        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body("Error: Email is already in use!");
        }

        // Create a new user object
        MyUser user = new MyUser();
        user.setUsername(registerRequest.getFullName());
        user.setEmail(registerRequest.getEmail());
        user.setPasswordHash(passwordEncoder.encode(registerRequest.getPassword()));
        // Set other fields as necessary, e.g., fullName if added

        // Save the user to the repository
        userRepository.save(user);

        return ResponseEntity.ok("User registered successfully!");
    }
}