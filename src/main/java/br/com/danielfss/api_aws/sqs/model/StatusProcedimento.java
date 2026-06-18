package br.com.danielfss.api_aws.sqs.model;

/**
 * Estados possíveis de um procedimento de consulta de CEP.
 *
 *   PENDENTE   -> mensagem enviada pra fila, ainda não processada
 *   PROCESSANDO -> /sqs/process está consumindo a mensagem agora
 *   CONCLUIDO  -> CEP consultado e salvo com sucesso
 *   FALHA      -> deu erro ao consultar o CEP ou processar a mensagem
 */
public enum StatusProcedimento {
    PENDENTE,
    PROCESSANDO,
    CONCLUIDO,
    FALHA
}
