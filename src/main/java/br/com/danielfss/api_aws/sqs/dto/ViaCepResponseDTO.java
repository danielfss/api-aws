package br.com.danielfss.api_aws.sqs.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Espelha o JSON retornado por https://viacep.com.br/ws/{cep}/json/
 *
 * Exemplo de resposta real:
 * {
 *   "cep": "01001-000",
 *   "logradouro": "Praça da Sé",
 *   "bairro": "Sé",
 *   "localidade": "São Paulo",
 *   "uf": "SP"
 * }
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ViaCepResponseDTO {

    private String cep;
    private String logradouro;
    private String bairro;
    private String localidade;
    private String uf;
    private boolean erro; // ViaCEP retorna {"erro": true} quando o CEP não existe

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getLocalidade() {
        return localidade;
    }

    public void setLocalidade(String localidade) {
        this.localidade = localidade;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public boolean isErro() {
        return erro;
    }

    public void setErro(boolean erro) {
        this.erro = erro;
    }
}
