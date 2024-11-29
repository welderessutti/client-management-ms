package br.com.fiap.client_management_ms.infrastructure.mapper;

import br.com.fiap.client_management_ms.core.domain.Client;
import br.com.fiap.client_management_ms.infrastructure.entity.ClientEntity;

import java.util.ArrayList;
import java.util.List;

public class ClientDomainEntityMapper {

    private ClientDomainEntityMapper() {
    }

    public static ClientEntity toClientEntity(Client client) {
        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setId(client.getId());
        clientEntity.setFullName(client.getFullName());
        clientEntity.setEmail(client.getEmail());
        clientEntity.setMobilePhoneNumber(client.getMobilePhoneNumber());
        clientEntity.setCpf(CpfDomainEntityMapper.toCpfEntity(client.getCpf()));
        clientEntity.setAddress(AddressDomainEntityMapper.toAddressEntity(client.getAddress()));
        return clientEntity;
    }

    public static Client toClient(ClientEntity clientEntity) {
        Client client = new Client();
        client.setId(clientEntity.getId());
        client.setFullName(clientEntity.getFullName());
        client.setEmail(clientEntity.getEmail());
        client.setMobilePhoneNumber(clientEntity.getMobilePhoneNumber());
        client.setCpf(CpfDomainEntityMapper.toCpf(clientEntity.getCpf()));
        client.setAddress(AddressDomainEntityMapper.toAddress(clientEntity.getAddress()));
        return client;
    }

    public static Client createClientResponse(ClientEntity clientEntity) {
        Client client = new Client();
        client.setId(clientEntity.getId());
        return client;
    }

    public static List<Client> allClients(List<ClientEntity> clientEntityList) {
        List<Client> clientList = new ArrayList<>();
        clientEntityList.forEach(clientEntity -> {
            Client client = toClient(clientEntity);
            clientList.add(client);
        });
        return clientList;
    }
}
