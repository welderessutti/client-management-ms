package br.com.fiap.client_management_ms.core.port.out;

import br.com.fiap.client_management_ms.core.domain.Client;

public interface ClientEventPortOut {

    void sendClientCreatedEvent(Client client);
}
