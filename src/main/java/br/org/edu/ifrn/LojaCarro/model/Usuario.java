package br.org.edu.ifrn.LojaCarro.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nome;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) // aceita no JSON, nunca devolve
    @Column(nullable = false)
    private String senha;

    @Column(nullable = false)
    private String perfil = "CLIENTE";

    String cargo;
    double salario;
}
