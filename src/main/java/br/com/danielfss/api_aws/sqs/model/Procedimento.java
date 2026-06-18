package br.com.danielfss.api_aws.sqs.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Representa um "procedimento" de consulta de CEP.
 *
 * Equivalente, no seu mundo legado, a uma linha de controle/auditoria
 * que acompanha um processamento assíncrono — como uma tabela de
 * "solicitações" que um MDB (Message Driven Bean) iria atualizar
 * conforme processava a mensagem JMS.
 */
@Entity
@Table(name = "procedimentos")
public class Procedimento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 8)
    private String cep;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private StatusProcedimento status;

    // Dados retornados pela consulta de CEP (preenchidos após o processamento)
    private String logradouro;
    private String bairro;
    private String localidade;
    private String uf;

    @Column(name = "mensagem_erro")
    private String mensagemErro;

    @Column(name = "sqs_message_id")
    private String sqsMessageId;

    @Column(name = "criado_em", nullable = false)
    private LocalDateTime criadoEm;

    @Column(name = "atualizado_em")
    private LocalDateTime atualizadoEm;

    public Procedimento() {
    }

    public Procedimento(String cep) {
        this.cep = cep;
        this.status = StatusProcedimento.PENDENTE;
        this.criadoEm = LocalDateTime.now();
    }

    // -------------------------------------------------------------
    // Métodos de transição de estado — equivalente a métodos de
    // negócio numa Entity Bean, em vez de só setters soltos
    // -------------------------------------------------------------

    public void marcarComoProcessando() {
        this.status = StatusProcedimento.PROCESSANDO;
        this.atualizadoEm = LocalDateTime.now();
    }

    public void marcarComoConcluido(String logradouro, String bairro,
                                     String localidade, String uf) {
        this.status = StatusProcedimento.CONCLUIDO;
        this.logradouro = logradouro;
        this.bairro = bairro;
        this.localidade = localidade;
        this.uf = uf;
        this.atualizadoEm = LocalDateTime.now();
    }

    public void marcarComoFalha(String mensagemErro) {
        this.status = StatusProcedimento.FALHA;
        this.mensagemErro = mensagemErro;
        this.atualizadoEm = LocalDateTime.now();
    }

    // Getters e Setters

    public Long getId() {
        return id;
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

    public String getLogradouro() {
        return logradouro;
    }

    public String getBairro() {
        return bairro;
    }

    public String getLocalidade() {
        return localidade;
    }

    public String getUf() {
        return uf;
    }

    public String getMensagemErro() {
        return mensagemErro;
    }

    public String getSqsMessageId() {
        return sqsMessageId;
    }

    public void setSqsMessageId(String sqsMessageId) {
        this.sqsMessageId = sqsMessageId;
    }

    public LocalDateTime getCriadoEm() {
        return criadoEm;
    }

    public LocalDateTime getAtualizadoEm() {
        return atualizadoEm;
    }
}

