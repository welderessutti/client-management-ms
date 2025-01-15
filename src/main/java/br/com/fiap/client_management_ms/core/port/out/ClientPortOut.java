package br.com.fiap.client_management_ms.core.port.out;

import br.com.fiap.client_management_ms.core.domain.Client;
import br.com.fiap.client_management_ms.core.domain.Cpf;

import java.util.List;
import java.util.Optional;

public interface ClientPortOut {

    Client createClient(Client client);

    Optional<Client> getClientById(Long clientId);

    Optional<Client> getClientByEmail(String email);

    List<Client> getAllClients();

    Client updateClient(Client outdatedClient);

    void deleteClientById(Long clientId);

    boolean existsClientById(Long clientId);

    boolean existsClientByEmail(String email);

    boolean existsClientByCpf(Cpf cpf);
}
