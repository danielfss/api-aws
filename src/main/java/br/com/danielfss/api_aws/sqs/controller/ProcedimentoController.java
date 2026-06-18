package br.com.danielfss.api_aws.sqs.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.danielfss.api_aws.sqs.dto.ProcedimentoDTO;
import br.com.danielfss.api_aws.sqs.model.Procedimento;
import br.com.danielfss.api_aws.sqs.service.ProcedimentoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/procedimentos")
@Tag(name = "Procedimentos", description = "Endpoints para gerenciamento de procedimentos")
public class ProcedimentoController {

    private final ProcedimentoService service;

    public ProcedimentoController(ProcedimentoService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca procedimento por ID")
    @ApiResponse(responseCode = "200", description = "Procedimento encontrado")
    @ApiResponse(responseCode = "404", description = "Procedimento não encontrado")
    public ResponseEntity<ProcedimentoDTO> buscarPorId(@PathVariable Long id) {
        Procedimento procedimento = this.service.buscarPorId(id);
        if (procedimento != null) {
            ProcedimentoDTO dto = new ProcedimentoDTO();
            dto.setId(procedimento.getId());
            dto.setCep(procedimento.getCep());
            dto.setStatus(procedimento.getStatus());
            return ResponseEntity.ok(dto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
