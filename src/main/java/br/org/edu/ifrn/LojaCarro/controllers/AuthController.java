package br.org.edu.ifrn.LojaCarro.controllers;

import br.org.edu.ifrn.LojaCarro.model.Usuario;
import br.org.edu.ifrn.LojaCarro.repository.UsuarioRepository;
import br.org.edu.ifrn.LojaCarro.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired private UsuarioRepository usuarioRepository;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private JwtService jwtService;

    public record Credenciais(String nome, String senha) {}
    public record TokenResponse(String token, String tipo, String perfil) {}

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody Credenciais req) {
        if (req.nome() == null || req.nome().isBlank()
                || req.senha() == null || req.senha().length() < 6) {
            return ResponseEntity.badRequest().body("Informe nome e senha (mínimo 6 caracteres).");
        }
        if (usuarioRepository.existsByNome(req.nome().trim())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Nome já cadastrado.");
        }

        Usuario u = new Usuario();
        u.setNome(req.nome().trim());
        u.setSenha(passwordEncoder.encode(req.senha())); // BCrypt
        u.setPerfil("CLIENTE");                          // cadastro público nunca é ADMIN
        usuarioRepository.save(u);

        return ResponseEntity.status(HttpStatus.CREATED).body("Cliente cadastrado.");
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody Credenciais req) {
        Usuario u = usuarioRepository.findByNome(req.nome()).orElse(null);

        if (u == null || req.senha() == null || !passwordEncoder.matches(req.senha(), u.getSenha())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Usuário ou senha inválidos.");
        }

        return ResponseEntity.ok(new TokenResponse(jwtService.generateToken(u), "Bearer", u.getPerfil()));
    }
}