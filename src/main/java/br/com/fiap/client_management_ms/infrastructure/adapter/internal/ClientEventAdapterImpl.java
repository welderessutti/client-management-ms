package br.com.fiap.client_management_ms.infrastructure.adapter.internal;

import br.com.fiap.client_management_ms.core.domain.Client;
import br.com.fiap.client_management_ms.core.port.out.ClientEventPortOut;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.integration.support.MessageBuilder;

@Slf4j
public class ClientEventAdapterImpl implements ClientEventPortOut {

    private final StreamBridge streamBridge;

    public ClientEventAdapterImpl(StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
    }

    @Override
    public void sendClientCreatedEvent(Client client) {
        streamBridge.send("clientCreatedSupplier-out-0", MessageBuilder.withPayload(client).build());
        log.info("Created client event sent: {}", client.getId());
    }
}
