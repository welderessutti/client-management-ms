package br.com.fiap.client_management_ms.infrastructure.repository;

import br.com.fiap.client_management_ms.infrastructure.entity.ClientEntity;
import br.com.fiap.client_management_ms.infrastructure.entity.CpfEntity;
import br.com.fiap.client_management_ms.utils.ClientHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class ClientRepositoryTest {

    @Mock
    private ClientRepository clientRepository;

    AutoCloseable openMocks;

    @BeforeEach
    void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Nested
    class CreateClientEntity {

        @Test
        void shouldCreateClientEntity() {
            // Arrange
            Long id = 1L;
            ClientEntity clientEntity = ClientHelper.createClientEntityObject();
            clientEntity.setId(id);

            when(clientRepository.save(any(ClientEntity.class))).thenReturn(clientEntity);

            // Act
            ClientEntity clientEntityCreated = clientRepository.save(clientEntity);

            // Assert
            assertThat(clientEntityCreated).isNotNull().isEqualTo(clientEntity).isInstanceOf(ClientEntity.class);
            assertThat(clientEntityCreated.getId()).isNotNull();
            assertThat(clientEntityCreated.getFullName()).isNotNull().isEqualTo(clientEntity.getFullName());
            assertThat(clientEntityCreated.getEmail()).isNotNull().isEqualTo(clientEntity.getEmail());
            assertThat(clientEntityCreated.getCpf()).isNotNull().isEqualTo(clientEntity.getCpf());
            assertThat(clientEntityCreated.getAddress()).isNotNull().isEqualTo(clientEntity.getAddress());
            verify(clientRepository, times(1)).save(any(ClientEntity.class));
        }
    }

    @Nested
    class FindClientEntity {

        @Test
        void shouldFindClientEntityById() {
            // Arrange
            Long id = 1L;
            ClientEntity clientEntity = ClientHelper.createClientEntityObject();
            clientEntity.setId(id);

            when(clientRepository.findById(any(Long.class))).thenReturn(Optional.of(clientEntity));

            // Act
            Optional<ClientEntity> clientEntityOptional = clientRepository.findById(id);

            // Assert
            assertThat(clientEntityOptional).isPresent().containsSame(clientEntity);
            clientEntityOptional.ifPresent(c -> {
                assertThat(c).isInstanceOf(ClientEntity.class);
                assertThat(c.getId()).isEqualTo(id);
                assertThat(c.getFullName()).isNotNull();
                assertThat(c.getEmail()).isNotNull();
                assertThat(c.getCpf()).isNotNull();
                assertThat(c.getAddress()).isNotNull();
            });
            verify(clientRepository, times(1)).findById(any(Long.class));
        }

        @Test
        void shouldReturnAllClientEntities() {
            // Arrange
            ClientEntity clientEntity1 = ClientHelper.createClientEntityObject();
            ClientEntity clientEntity2 = ClientHelper.createClientEntityObject();
            List<ClientEntity> clientEntityList = Arrays.asList(clientEntity1, clientEntity2);

            when(clientRepository.findAll()).thenReturn(clientEntityList);

            // Act
            List<ClientEntity> clientEntitiesList = clientRepository.findAll();

            // Assert
            assertThat(clientEntitiesList)
                    .isNotNull()
                    .hasSize(2)
                    .containsExactlyInAnyOrder(clientEntity1, clientEntity2);
            verify(clientRepository, times(1)).findAll();
        }

        @Test
        void shouldFindClientEntityByEmail() {
            // Arrange
            Long id = 1L;
            ClientEntity clientEntity = ClientHelper.createClientEntityObject();
            clientEntity.setId(id);
            String email = clientEntity.getEmail();

            when(clientRepository.findByEmail(any(String.class))).thenReturn(Optional.of(clientEntity));

            // Act
            Optional<ClientEntity> clientEntityOptional = clientRepository.findByEmail(email);

            // Assert
            assertThat(clientEntityOptional).isPresent().containsSame(clientEntity);
            clientEntityOptional.ifPresent(c -> {
                assertThat(c).isInstanceOf(ClientEntity.class);
                assertThat(c.getId()).isNotNull().isEqualTo(id);
                assertThat(c.getFullName()).isNotNull();
                assertThat(c.getEmail()).isNotNull().isEqualTo(email);
                assertThat(c.getCpf()).isNotNull();
                assertThat(c.getAddress()).isNotNull();
            });
            verify(clientRepository, times(1)).findByEmail(any(String.class));
        }
    }

    @Nested
    class verifyIfClientEntityExists {

        @Test
        void shouldVerifyIfClientEntityExistsByEmail() {
            // Arrange
            ClientEntity clientEntity = ClientHelper.createClientEntityObject();
            String email = clientEntity.getEmail();

            when(clientRepository.existsByEmail(any(String.class))).thenReturn(true);

            // Act
            boolean existedClientEntity = clientRepository.existsByEmail(email);

            // Assert
            assertThat(existedClientEntity).isTrue();
            verify(clientRepository, times(1)).existsByEmail(any(String.class));
        }

        @Test
        void shouldReturnFalseWhenClientEntityExistsByEmailAndClientEntityDoesNotExist() {
            // Arrange
            ClientEntity clientEntity = ClientHelper.createClientEntityObject();
            String email = clientEntity.getEmail();

            when(clientRepository.existsByEmail(any(String.class))).thenReturn(false);

            // Act
            boolean existedClientEntity = clientRepository.existsByEmail(email);

            // Assert
            assertThat(existedClientEntity).isFalse();
            verify(clientRepository, times(1)).existsByEmail(any(String.class));
        }

        @Test
        void shouldVerifyIfClientEntityExistsByCpf() {
            // Arrange
            ClientEntity clientEntity = ClientHelper.createClientEntityObject();
            CpfEntity cpfEntity = clientEntity.getCpf();

            when(clientRepository.existsByCpf(any(CpfEntity.class))).thenReturn(true);

            // Act
            boolean existedClientEntity = clientRepository.existsByCpf(cpfEntity);

            // Assert
            assertThat(existedClientEntity).isTrue();
            verify(clientRepository, times(1)).existsByCpf(any(CpfEntity.class));
        }

        @Test
        void shouldReturnFalseWhenClientEntityExistsByCpfAndClientEntityDoesNotExist() {
            // Arrange
            ClientEntity clientEntity = ClientHelper.createClientEntityObject();
            CpfEntity cpfEntity = clientEntity.getCpf();

            when(clientRepository.existsByCpf(any(CpfEntity.class))).thenReturn(false);

            // Act
            boolean existedClientEntity = clientRepository.existsByCpf(cpfEntity);

            // Assert
            assertThat(existedClientEntity).isFalse();
            verify(clientRepository, times(1)).existsByCpf(any(CpfEntity.class));
        }
    }

    @Nested
    class DeleteClientEntity {

        @Test
        void shouldDeleteClientEntityById() {
            // Arrange
            Long id = 1L;

            doNothing().when(clientRepository).deleteById(any(Long.class));

            // Act
            clientRepository.deleteById(id);

            // Assert
            verify(clientRepository, times(1)).deleteById(any(Long.class));
        }
    }
}
