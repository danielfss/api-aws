package br.com.danielfss.api_aws.sqs.dto;

/**
 * Payload que vai DENTRO da mensagem SQS (serializado como JSON).
 *
 * Carregamos o procedimentoId junto com o CEP para que, ao processar
 * a mensagem, a gente saiba EXATAMENTE qual registro no banco atualizar
 * — sem isso, não teríamos como religar mensagem -> linha da tabela.
 */
public class CepMensagemDTO {

    private Long procedimentoId;
    private String cep;

    public CepMensagemDTO() {
    }

    public CepMensagemDTO(Long procedimentoId, String cep) {
        this.procedimentoId = procedimentoId;
        this.cep = cep;
    }

    public Long getProcedimentoId() {
        return procedimentoId;
    }

    public void setProcedimentoId(Long procedimentoId) {
        this.procedimentoId = procedimentoId;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }
}
