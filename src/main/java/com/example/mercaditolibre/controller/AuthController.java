package com.example.mercaditolibre.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.mercaditolibre.dto.AuthRequest;
import com.example.mercaditolibre.dto.AuthResponse;
import com.example.mercaditolibre.dto.RegistroRequest;
import com.example.mercaditolibre.models.UsuarioEntity;
import com.example.mercaditolibre.repository.UsuarioRepository;
import com.example.mercaditolibre.security.JwtTokenProvider;
import com.example.mercaditolibre.service.UsuarioService;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UsuarioRepository usuarioRepository;
    private final UsuarioService usuarioService;
    private final JwtTokenProvider tokenProvider;

    public AuthController(AuthenticationManager authenticationManager,
            UsuarioRepository usuarioRepository, UsuarioService usuarioService,
            JwtTokenProvider tokenProvider) {
        this.authenticationManager = authenticationManager;
        this.usuarioRepository = usuarioRepository;
        this.usuarioService = usuarioService;
        this.tokenProvider = tokenProvider;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String username = authentication.getName();
        UsuarioEntity usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        String role = usuario.getRole().name();
        String token = tokenProvider.generateToken(authentication);

        AuthResponse response = new AuthResponse();
        response.setToken(token);
        response.setUsername(usuario.getUsername());
        response.setNombre(usuario.getNombre());
        response.setRole(role);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/registro")
    public ResponseEntity<?> register(@RequestBody RegistroRequest request) {
        try {
            UsuarioEntity usuario = usuarioService.saveUsuario(request);
            return ResponseEntity.ok().body(java.util.Map.of("message", "Usuario registrado exitosamente"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(java.util.Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(java.util.Map.of("error", "Error al registrar el usuario"));
        }
    }
}
