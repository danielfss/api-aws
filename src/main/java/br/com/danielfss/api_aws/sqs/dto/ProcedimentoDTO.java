package br.com.danielfss.api_aws.sqs.dto;

import br.com.danielfss.api_aws.sqs.model.StatusProcedimento;

public class ProcedimentoDTO {

    private Long id;
    private String cep;
    private StatusProcedimento status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public StatusProcedimento getStatus() {
        return status;
    }

    public void setStatus(StatusProcedimento status) {
        this.status = status;
    }
}
