package br.com.danielfss.api_aws.sqs.client;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import br.com.danielfss.api_aws.sqs.dto.ViaCepResponseDTO;

/**
 * Cliente HTTP simples para a API pública ViaCEP.
 *
 * Equivalente a um Bean cliente JAX-WS/JAX-RS que você já usou antes —
 * aqui usamos RestClient (Spring 6+), a evolução do RestTemplate.
 */
@Component
public class ViaCepClient {

    private final RestClient restClient;

    public ViaCepClient() {
        this.restClient = RestClient.builder()
                .baseUrl("https://viacep.com.br/ws")
                .build();
    }

    /**
     * Consulta o CEP na API ViaCEP.
     *
     * @param cep apenas números, ex: "01001000"
     * @return dados do endereço
     * @throws IllegalArgumentException se o CEP não existir
     */
    public ViaCepResponseDTO consultar(String cep) {
        String cepLimpo = cep.replaceAll("[^0-9]", "");

        ViaCepResponseDTO response = restClient.get()
                .uri("/{cep}/json/", cepLimpo)
                .retrieve()
                .body(ViaCepResponseDTO.class);

        if (response == null || response.isErro()) {
            throw new IllegalArgumentException("CEP não encontrado: " + cep);
        }

        return response;
    }
}
