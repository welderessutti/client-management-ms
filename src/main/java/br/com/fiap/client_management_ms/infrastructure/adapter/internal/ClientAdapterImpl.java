package br.com.fiap.client_management_ms.infrastructure.adapter.internal;

import br.com.fiap.client_management_ms.core.domain.Client;
import br.com.fiap.client_management_ms.core.domain.Cpf;
import br.com.fiap.client_management_ms.core.port.out.ClientPortOut;
import br.com.fiap.client_management_ms.infrastructure.entity.ClientEntity;
import br.com.fiap.client_management_ms.infrastructure.entity.CpfEntity;
import br.com.fiap.client_management_ms.infrastructure.mapper.ClientDomainEntityMapper;
import br.com.fiap.client_management_ms.infrastructure.mapper.CpfDomainEntityMapper;
import br.com.fiap.client_management_ms.infrastructure.repository.ClientRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public class ClientAdapterImpl implements ClientPortOut {

    private final ClientRepository clientRepository;

    public ClientAdapterImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }


    @Override
    @Transactional
    public Client createClient(Client client) {
        ClientEntity clientEntity = ClientDomainEntityMapper.toClientEntity(client);
        clientEntity.getAddress().setClient(clientEntity);
        return ClientDomainEntityMapper.createClientResponse(clientRepository.save(clientEntity));
    }

    @Override
    public Optional<Client> getClientById(Long clientId) {
        return clientRepository.findById(clientId)
                .map(ClientDomainEntityMapper::toClient);
    }

    @Override
    public Optional<Client> getClientByEmail(String email) {
        return clientRepository.findByEmail(email)
                .map(ClientDomainEntityMapper::toClient);
    }

    @Override
    public List<Client> getAllClients() {
        return ClientDomainEntityMapper.allClients(clientRepository.findAll());
    }

    @Override
    @Transactional
    public Client updateClient(Client outdatedClient) {
        ClientEntity outdatedClientEntity = ClientDomainEntityMapper.toClientEntity(outdatedClient);
        outdatedClientEntity.getAddress().setClient(outdatedClientEntity);
        return ClientDomainEntityMapper.toClient(clientRepository.save(outdatedClientEntity));
    }

    @Override
    @Transactional
    public void deleteClientById(Long clientId) {
        clientRepository.deleteById(clientId);
    }

    @Override
    public boolean existsClientById(Long clientId) {
        return clientRepository.existsById(clientId);
    }

    @Override
    public boolean existsClientByEmail(String email) {
        return clientRepository.existsByEmail(email);
    }

    @Override
    public boolean existsClientByCpf(Cpf cpf) {
        CpfEntity cpfEntity = CpfDomainEntityMapper.toCpfEntity(cpf);
        return clientRepository.existsByCpf(cpfEntity);
    }
}
