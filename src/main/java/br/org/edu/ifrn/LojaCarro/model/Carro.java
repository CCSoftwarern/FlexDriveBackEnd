package br.org.edu.ifrn.LojaCarro.model;

import jakarta.persistence.*;

@Entity
public class Carro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String marca;
    private String modelo;
    private int anofabricacao;
    private int ano;
    private String cor;
    private int quilometragem;
    private String tipoCombustivel;
    private String tipoTransmissao;
    private double preco;
    @Column(length = 1000)
    private String descricao;
    private String situacao;
    private String fotografiaPrincipal;

    // No-arg constructor necessário para desserialização (Jackson) e JPA
    public Carro() {
    }

    public Carro(String modelo, int ano, String marca, int anofabricacao) {
        this.modelo = modelo;
        this.ano = ano;
        this.preco = 0.0;
        this.marca = marca;
        this.anofabricacao = anofabricacao;
    }

    public Carro(String modelo, int ano, double preco, String marca, int anofabricacao) {
        this.modelo = modelo;
        this.ano = ano;
        this.preco = preco;
        this.marca = marca;
        this.anofabricacao = anofabricacao;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public int getAnofabricacao() {
        return anofabricacao;
    }

    public void setAnofabricacao(int anofabricacao) {
        this.anofabricacao = anofabricacao;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public int getQuilometragem() {
        return quilometragem;
    }

    public void setQuilometragem(int quilometragem) {
        this.quilometragem = quilometragem;
    }

    public String getTipoCombustivel() {
        return tipoCombustivel;
    }

    public void setTipoCombustivel(String tipoCombustivel) {
        this.tipoCombustivel = tipoCombustivel;
    }

    public String getTipoTransmissao() {
        return tipoTransmissao;
    }

    public void setTipoTransmissao(String tipoTransmissao) {
        this.tipoTransmissao = tipoTransmissao;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    public String getFotografiaPrincipal() {
        return fotografiaPrincipal;
    }

    public void setFotografiaPrincipal(String fotografiaPrincipal) {
        this.fotografiaPrincipal = fotografiaPrincipal;
    }
}