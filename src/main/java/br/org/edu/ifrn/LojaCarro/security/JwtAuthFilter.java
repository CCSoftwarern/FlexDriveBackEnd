package br.org.edu.ifrn.LojaCarro.security;

import br.org.edu.ifrn.LojaCarro.repository.UsuarioRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    @Autowired private JwtService jwtService;
    @Autowired private UsuarioRepository usuarioRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        String header = request.getHeader("Authorization");

        if (header != null && header.startsWith("Bearer ")
                && SecurityContextHolder.getContext().getAuthentication() == null) {

            String token = header.substring(7);

            if (jwtService.isValid(token)) {
                usuarioRepository.findByNome(jwtService.extractNome(token)).ifPresent(usuario -> {
                    var auth = new UsernamePasswordAuthenticationToken(
                            usuario, null,
                            List.of(new SimpleGrantedAuthority("ROLE_" + usuario.getPerfil())));
                    SecurityContextHolder.getContext().setAuthentication(auth);
                });
            }
        }

        chain.doFilter(request, response);
    }
}