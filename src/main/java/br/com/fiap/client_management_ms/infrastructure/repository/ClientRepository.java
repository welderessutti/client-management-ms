package br.com.fiap.client_management_ms.infrastructure.repository;

import br.com.fiap.client_management_ms.infrastructure.entity.ClientEntity;
import br.com.fiap.client_management_ms.infrastructure.entity.CpfEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<ClientEntity, Long> {

    Optional<ClientEntity> findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByCpf(CpfEntity cpf);
}
