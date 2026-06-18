package br.com.danielfss.api_aws.sqs.service;

import org.springframework.stereotype.Service;

import br.com.danielfss.api_aws.sqs.client.ViaCepClient;
import br.com.danielfss.api_aws.sqs.dto.ViaCepResponseDTO;
import br.com.danielfss.api_aws.sqs.model.Procedimento;
import br.com.danielfss.api_aws.sqs.repository.ProcedimentoRepository;

/**
 * Regra de negócio do "procedimento de consulta de CEP".
 *
 * Equivalente, no seu mundo legado, a um @Stateless Session Bean
 * que orquestra: criar registro -> chamar serviço externo -> persistir resultado.
 */
@Service
public class ProcedimentoService {

    private final ProcedimentoRepository repository;
    private final ViaCepClient viaCepClient;

    public ProcedimentoService(ProcedimentoRepository repository, ViaCepClient viaCepClient) {
        this.repository = repository;
        this.viaCepClient = viaCepClient;
    }

    /**
     * Cria o registro do procedimento em estado PENDENTE.
     * Chamado ANTES de enviar a mensagem pro SQS — assim já temos
     * um ID de negócio pra devolver ao cliente da API.
     */
    public Procedimento criarPendente(String cep) {
        Procedimento procedimento = new Procedimento(cep);
        return repository.save(procedimento);
    }

    /**
     * Vincula o messageId do SQS ao procedimento já criado.
     * Útil para rastreabilidade/auditoria (qual mensagem gerou qual registro).
     */
    public void vincularMensagemSqs(Long procedimentoId, String sqsMessageId) {
        repository.findById(procedimentoId).ifPresent(p -> {
            p.setSqsMessageId(sqsMessageId);
            repository.save(p);
        });
    }

    /**
     * Processa de fato o procedimento: consulta o ViaCEP e salva o resultado.
     * Chamado pelo SqsService quando uma mensagem é consumida da fila.
     */
    public void processar(Long procedimentoId, String cep) {
        Procedimento procedimento = repository.findById(procedimentoId)
                .orElseThrow(() -> new IllegalStateException(
                        "Procedimento não encontrado: " + procedimentoId));

        procedimento.marcarComoProcessando();
        repository.save(procedimento);

        try {
            ViaCepResponseDTO endereco = viaCepClient.consultar(cep);

            procedimento.marcarComoConcluido(
                    endereco.getLogradouro(),
                    endereco.getBairro(),
                    endereco.getLocalidade(),
                    endereco.getUf());

        } catch (Exception e) {
            procedimento.marcarComoFalha(e.getMessage());
        }

        repository.save(procedimento);
    }

    /**
     * Consulta o resultado do procedimento — usado pela API de leitura.
     */
    public Procedimento buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Procedimento não encontrado: " + id));
    }
}
