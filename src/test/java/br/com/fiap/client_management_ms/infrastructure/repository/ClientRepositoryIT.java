package br.com.fiap.client_management_ms.infrastructure.repository;

import br.com.fiap.client_management_ms.infrastructure.entity.ClientEntity;
import br.com.fiap.client_management_ms.infrastructure.entity.CpfEntity;
import br.com.fiap.client_management_ms.utils.ClientHelper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase
@Transactional
class ClientRepositoryIT {

    @Autowired
    private ClientRepository clientRepository;

    @Nested
    class CreateTable {

        @Test
        void shouldCreateTable() {
            Long count = clientRepository.count();
            assertThat(count).isPositive();
        }
    }

    @Nested
    class CreateClientEntity {

        @Test
        void shouldCreateClientEntity() {
            // Assert
            ClientEntity clientEntity = ClientHelper.createClientEntityObject();

            // Act
            ClientEntity clientEntitySaved = clientRepository.save(clientEntity);

            // Assert
            assertThat(clientEntitySaved)
                    .isNotNull()
                    .isEqualTo(clientEntity)
                    .isInstanceOf(ClientEntity.class);
            assertThat(clientEntitySaved.getId()).isNotNull();
            assertThat(clientEntitySaved.getFullName()).isNotNull().isEqualTo(clientEntity.getFullName());
            assertThat(clientEntitySaved.getEmail()).isNotNull().isEqualTo(clientEntity.getEmail());
            assertThat(clientEntitySaved.getMobilePhoneNumber()).isNotNull().isEqualTo(clientEntity.getMobilePhoneNumber());
            assertThat(clientEntitySaved.getCpf()).isNotNull().isEqualTo(clientEntity.getCpf());
            assertThat(clientEntitySaved.getAddress()).isNotNull().isEqualTo(clientEntity.getAddress());
        }
    }

    @Nested
    class FindClientEntity {

        @Test
        void shouldFindClientEntityById() {
            // Arrange
            Long id = 1L;

            // Act
            Optional<ClientEntity> clientEntityOptional = clientRepository.findById(id);

            // Assert
            assertThat(clientEntityOptional).isPresent();
            clientEntityOptional.ifPresent(c -> {
                assertThat(c).isInstanceOf(ClientEntity.class);
                assertThat(c.getId()).isNotNull().isEqualTo(id);
                assertThat(c.getFullName()).isNotNull();
                assertThat(c.getEmail()).isNotNull();
                assertThat(c.getMobilePhoneNumber()).isNotNull();
                assertThat(c.getCpf()).isNotNull();
                assertThat(c.getAddress()).isNotNull();
            });
        }

        @Test
        void shouldReturnAllClientEntities() {
            // Act
            List<ClientEntity> clientEntitiesList = clientRepository.findAll();

            // Assert
            assertThat(clientEntitiesList).hasSizeGreaterThan(0);
        }

        @Test
        void shouldFindClientEntityByEmail() {
            // Arrange
            String email = "joao@silva.com.br";

            // Act
            Optional<ClientEntity> clientEntityOptional = clientRepository.findByEmail(email);

            // Assert
            assertThat(clientEntityOptional).isPresent();
            clientEntityOptional.ifPresent(c -> {
                assertThat(c).isInstanceOf(ClientEntity.class);
                assertThat(c.getId()).isNotNull();
                assertThat(c.getFullName()).isNotNull();
                assertThat(c.getEmail()).isNotNull().isEqualTo(email);
                assertThat(c.getMobilePhoneNumber()).isNotNull();
                assertThat(c.getCpf()).isNotNull();
                assertThat(c.getAddress()).isNotNull();
            });
        }
    }

    @Nested
    class verifyIfClientEntityExists {

        @Test
        void shouldVerifyIfClientEntityExistsByEmail() {
            // Arrange
            String email = "joao@silva.com.br";

            // Act
            boolean existedClientEntity = clientRepository.existsByEmail(email);

            // Assert
            assertThat(existedClientEntity).isTrue();
        }

        @Test
        void shouldReturnFalseWhenVerifyIfClientEntityDoesNotExistByEmail() {
            // Arrange
            String email = "not@exists.com.br";

            // Act
            boolean existedClientEntity = clientRepository.existsByEmail(email);

            // Assert
            assertThat(existedClientEntity).isFalse();
        }

        @Test
        void shouldVerifyIfClientEntityExistsByCpf() {
            // Arrange
            CpfEntity cpfEntity = new CpfEntity();
            cpfEntity.setDocumentNumber("55426210152");

            // Act
            boolean existedClientEntity = clientRepository.existsByCpf(cpfEntity);

            // Assert
            assertThat(existedClientEntity).isTrue();
        }

        @Test
        void shouldReturnFalseWhenVerifyIfClientEntityDoesNotExistByCpf() {
            // Arrange
            CpfEntity cpfEntity = new CpfEntity();
            cpfEntity.setDocumentNumber("13184731014");

            // Act
            boolean existedClientEntity = clientRepository.existsByCpf(cpfEntity);

            // Assert
            assertThat(existedClientEntity).isFalse();
        }
    }

    @Nested
    class DeleteClientEntity {

        @Test
        void shouldDeleteClientEntityById() {
            // Arrange
            Long id = 2L;

            // Act
            clientRepository.deleteById(id);
            Optional<ClientEntity> clientEntityOptional = clientRepository.findById(id);

            // Assert
            assertThat(clientEntityOptional).isNotPresent().isEmpty();
        }
    }
}
