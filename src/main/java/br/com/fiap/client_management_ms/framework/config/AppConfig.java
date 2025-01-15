package br.com.fiap.client_management_ms.framework.config;

import br.com.fiap.client_management_ms.core.port.in.ClientPortIn;
import br.com.fiap.client_management_ms.core.port.out.AddressPortOut;
import br.com.fiap.client_management_ms.core.port.out.ClientPortOut;
import br.com.fiap.client_management_ms.core.service.AddressService;
import br.com.fiap.client_management_ms.core.service.ClientServiceImpl;
import br.com.fiap.client_management_ms.infrastructure.adapter.external.AddressAdapterImpl;
import br.com.fiap.client_management_ms.infrastructure.adapter.internal.ClientAdapterImpl;
import br.com.fiap.client_management_ms.infrastructure.api.ViaCepApiClient;
import br.com.fiap.client_management_ms.infrastructure.repository.ClientRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public ClientPortOut clientAdapter(ClientRepository clientRepository) {
        return new ClientAdapterImpl(clientRepository);
    }

    @Bean
    public ClientPortIn clientService(AddressService addressService, ClientPortOut clientPortOut) {
        return new ClientServiceImpl(addressService, clientPortOut);
    }

    @Bean
    public AddressPortOut addressAdapter(ViaCepApiClient viaCepApiClient) {
        return new AddressAdapterImpl(viaCepApiClient);
    }

    @Bean
    public AddressService addressService(AddressPortOut addressPortOut) {
        return new AddressService(addressPortOut);
    }
}
