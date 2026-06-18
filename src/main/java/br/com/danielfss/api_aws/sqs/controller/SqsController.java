package br.com.danielfss.api_aws.sqs.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.danielfss.api_aws.sqs.service.SqsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/sqs")
@Tag(name = "SQS", description = "Endpoints de integração com AWS SQS")
public class SqsController {

    private final SqsService service;

    public SqsController(SqsService service) {
        this.service = service;
    }

    /**
     * "API que envia uma mensagem de CEP" — deve retornar o ID do procedimento.
     * Exemplo: POST /sqs/send?cep=01001000
     */
    @PostMapping("/send")
    @Operation(summary = "Envia CEP para a fila SQS")
    @ApiResponse(responseCode = "200", description = "CEP enviado com sucesso")
    public ResponseEntity<Map<String, Long>> send(@RequestParam String cep) {
        Long procedimentoId = service.enviarCep(cep);
        return ResponseEntity.ok(Map.of("procedimentoId", procedimentoId));
    }

    /**
     * "API que lê a base de dados" — dispara o processamento manual da fila.
     * Exemplo: GET /sqs/process
     */
    @GetMapping("/process")
    @Operation(summary = "Processa mensagens da fila SQS")
    @ApiResponse(responseCode = "200", description = "Mensagens processadas com sucesso")
    public ResponseEntity<List<Map<String, String>>> process() {
        return ResponseEntity.ok(service.receiveAndProcess());
    }

    /**
    * "API que lê a base de dados" — apenas lê as mensagens da fila, sem processar.
    * Exemplo: GET /sqs/peek
    */
    @GetMapping("/peek")
    @Operation(summary = "Lê mensagens da fila SQS")
    @ApiResponse(responseCode = "200", description = "Mensagens lidas com sucesso")
    public ResponseEntity<List<String>> peek() {
        return ResponseEntity.ok(service.peekMessages());
    }
}
