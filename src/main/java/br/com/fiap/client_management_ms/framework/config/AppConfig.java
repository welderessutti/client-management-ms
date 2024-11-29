package br.com.fiap.client_management_ms.framework.config;

import br.com.fiap.client_management_ms.core.port.in.ClientService;
import br.com.fiap.client_management_ms.core.port.out.AddressAdapter;
import br.com.fiap.client_management_ms.core.port.out.ClientAdapter;
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
    public ClientAdapter clientAdapter(ClientRepository clientRepository) {
        return new ClientAdapterImpl(clientRepository);
    }

    @Bean
    public ClientService clientService(AddressService addressService, ClientAdapter clientAdapter) {
        return new ClientServiceImpl(addressService, clientAdapter);
    }

    @Bean
    public AddressAdapter addressAdapter(ViaCepApiClient viaCepApiClient) {
        return new AddressAdapterImpl(viaCepApiClient);
    }

    @Bean
    public AddressService addressService(AddressAdapter addressAdapter) {
        return new AddressService(addressAdapter);
    }
}
