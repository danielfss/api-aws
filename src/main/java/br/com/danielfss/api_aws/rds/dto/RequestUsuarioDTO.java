package br.com.danielfss.api_aws.rds.dto;

import jakarta.validation.constraints.NotBlank;

public class RequestUsuarioDTO {

    private Long id;

    @NotBlank(message = "O campo 'nome' não pode estar em branco.")
    private String nome;
    
    @NotBlank(message = "O campo 'email' não pode estar em branco.")
    private String email;

    public RequestUsuarioDTO() {
    }

    public RequestUsuarioDTO(String nome, String email) {
        this.nome = nome;
        this.email = email;
    }

    public RequestUsuarioDTO(Long id, String nome, String email) {
        this.id = id;
        this.nome = nome;
        this.email = email;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
}
