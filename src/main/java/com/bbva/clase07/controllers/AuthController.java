package com.bbva.clase07.controllers;

import com.bbva.clase07.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public CompletableFuture<Map<String, String>> login(@RequestBody Map<String, String> user) {
        return CompletableFuture.supplyAsync(() -> {
            String username = user.get("username");
            String password = user.get("password");
            // Autenticación simple (puedes mejorarla con base de datos)
            if ("admin".equals(username) && "admin".equals(password)) {
                String token = jwtUtil.generateToken(username);
                Map<String, String> response = new HashMap<>();
                response.put("token", token);
                return response;
            } else {
                throw new RuntimeException("Credenciales inválidas");
            }
        });
    }
}
