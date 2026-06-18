package br.com.danielfss.api_aws.sqs.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.danielfss.api_aws.sqs.dto.CepMensagemDTO;
import br.com.danielfss.api_aws.sqs.model.Procedimento;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.DeleteMessageRequest;
import software.amazon.awssdk.services.sqs.model.Message;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageRequest;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;
import software.amazon.awssdk.services.sqs.model.SendMessageResponse;

/**
 * Camada de integração com o SQS.
 *
 * Diferença principal da versão anterior: agora a mensagem enviada
 * é um JSON estruturado {procedimentoId, cep} em vez de texto puro,
 * e o "send" retorna o ID do PROCEDIMENTO (regra de negócio), não
 * o messageId do SQS (que é só um detalhe de infraestrutura).
 */
@Service
public class SqsService {

    private final SqsClient sqsClient;
    private final ProcedimentoService procedimentoService;
    private final ObjectMapper objectMapper;

    private static final String QUEUE_URL =
            "https://sqs.us-east-2.amazonaws.com/746486152349/aula-aws-java-pleno-sqs";

    public SqsService(SqsClient sqsClient,
                       ProcedimentoService procedimentoService,
                       ObjectMapper objectMapper) {
        this.sqsClient = sqsClient;
        this.procedimentoService = procedimentoService;
        this.objectMapper = objectMapper;
    }

    /**
     * Fluxo de envio:
     * 1. Cria o procedimento no banco (status PENDENTE)
     * 2. Monta o payload JSON {procedimentoId, cep}
     * 3. Envia pro SQS
     * 4. Retorna o ID do PROCEDIMENTO pro cliente da API
     */
    public Long enviarCep(String cep) {

        Procedimento procedimento = procedimentoService.criarPendente(cep);

        try {
            CepMensagemDTO payload = new CepMensagemDTO(procedimento.getId(), cep);
            String mensagemJson = objectMapper.writeValueAsString(payload);

            SendMessageRequest request = SendMessageRequest.builder()
                    .queueUrl(QUEUE_URL)
                    .messageBody(mensagemJson)
                    .build();

            SendMessageResponse response = sqsClient.sendMessage(request);

            procedimentoService.vincularMensagemSqs(procedimento.getId(), response.messageId());

        } catch (Exception e) {
            throw new RuntimeException("Erro ao enviar mensagem para o SQS", e);
        }

        // Aqui está o ponto-chave do exercício: devolvemos o ID do
        // PROCEDIMENTO (nosso ID de negócio), não o messageId do SQS.
        return procedimento.getId();
    }

    /**
     * Consome mensagens da fila manualmente (chamado via GET /sqs/process).
     * Para cada mensagem: desserializa o JSON, processa via ProcedimentoService
     * (que consulta o ViaCEP e salva no banco), e remove da fila se OK.
     */
    public List<Map<String, String>> receiveAndProcess() {

        ReceiveMessageRequest request = ReceiveMessageRequest.builder()
                .queueUrl(QUEUE_URL)
                .maxNumberOfMessages(10)
                .waitTimeSeconds(2)
                .visibilityTimeout(30)
                .build();

        List<Message> messages = sqsClient.receiveMessage(request).messages();

        List<Map<String, String>> processadas = new ArrayList<>();

        for (Message message : messages) {
            try {
                CepMensagemDTO payload = objectMapper.readValue(
                        message.body(), CepMensagemDTO.class);

                procedimentoService.processar(payload.getProcedimentoId(), payload.getCep());

                deleteMessage(message.receiptHandle());

                processadas.add(Map.of(
                        "messageId", message.messageId(),
                        "procedimentoId", String.valueOf(payload.getProcedimentoId()),
                        "cep", payload.getCep(),
                        "status", "PROCESSADA E REMOVIDA"
                ));

            } catch (Exception e) {
                // Não remove da fila — a mensagem volta após o visibilityTimeout
                // para uma nova tentativa (igual redelivery em JMS/MDB).
                processadas.add(Map.of(
                        "messageId", message.messageId(),
                        "body", message.body(),
                        "status", "FALHA - SERÁ REPROCESSADA",
                        "erro", String.valueOf(e.getMessage())
                ));
            }
        }

        return processadas;
    }

    public List<String> peekMessages() {

        ReceiveMessageRequest request = ReceiveMessageRequest.builder()
                .queueUrl(QUEUE_URL)
                .maxNumberOfMessages(10)
                .waitTimeSeconds(2)
                .build();

        return sqsClient.receiveMessage(request).messages()
                .stream()
                .map(Message::body)
                .toList();
    }

    private void deleteMessage(String receiptHandle) {
        DeleteMessageRequest request = DeleteMessageRequest.builder()
                .queueUrl(QUEUE_URL)
                .receiptHandle(receiptHandle)
                .build();

        sqsClient.deleteMessage(request);
    }
}
