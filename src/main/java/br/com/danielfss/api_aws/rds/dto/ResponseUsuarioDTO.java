package br.com.danielfss.api_aws.rds.dto;

public class ResponseUsuarioDTO {

    private Long id;
    private String nome;
    private String email;

    // Constructors
    public ResponseUsuarioDTO() {}

    public ResponseUsuarioDTO(Long id, String nome, String email) {
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
