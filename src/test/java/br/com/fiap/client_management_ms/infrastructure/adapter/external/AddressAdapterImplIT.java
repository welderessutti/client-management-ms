package br.com.fiap.client_management_ms.infrastructure.adapter.external;

import br.com.fiap.client_management_ms.core.domain.Address;
import br.com.fiap.client_management_ms.core.exception.AddressAdapterApiException;
import br.com.fiap.client_management_ms.core.port.out.AddressPortOut;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@AutoConfigureTestDatabase
@Transactional
class AddressAdapterImplIT {

    @Autowired
    AddressPortOut addressPortOut;

    @Nested
    class GetAddressByApi {

        @Test
        void shouldGetAddressByApi() {
            // Arrange
            String cep = "01001-000";

            // Act
            Address returnedAddress = addressPortOut.getAddressByApi(cep);

            // Assert
            assertThat(returnedAddress).isNotNull().isInstanceOf(Address.class);
            assertThat(returnedAddress.getId()).isNull();
            assertThat(returnedAddress.getCep()).isNotNull();
            assertThat(returnedAddress.getStreet()).isNotNull();
            assertThat(returnedAddress.getComplement()).isNotNull();
            assertThat(returnedAddress.getUnit()).isNotNull();
            assertThat(returnedAddress.getNumber()).isNull();
            assertThat(returnedAddress.getNeighborhood()).isNotNull();
            assertThat(returnedAddress.getLocality()).isNotNull();
            assertThat(returnedAddress.getUf()).isNotNull();
            assertThat(returnedAddress.getState()).isNotNull();
            assertThat(returnedAddress.getRegion()).isNotNull();
            assertThat(returnedAddress.getIbge()).isNotNull();
            assertThat(returnedAddress.getGia()).isNotNull();
            assertThat(returnedAddress.getDdd()).isNotNull();
            assertThat(returnedAddress.getSiafi()).isNotNull();
            assertThat(returnedAddress.getClient()).isNull();
        }

        @Test
        void shouldThrowApiExceptionNotFoundWhenCepIsNotFound() {
            // Arrange
            String nonExistentCep = "99999-999";

            // Act & Assert
            assertThatThrownBy(() -> addressPortOut.getAddressByApi(nonExistentCep))
                    .isInstanceOf(AddressAdapterApiException.NotFound.class)
                    .hasMessage(nonExistentCep);
        }

        @Test
        void shouldThrowApiExceptionBadRequestWhenCepIsInvalid() {
            // Arrange
            String invalidCep = "999";

            // Act & Assert
            assertThatThrownBy(() -> addressPortOut.getAddressByApi(invalidCep))
                    .isInstanceOf(AddressAdapterApiException.BadRequest.class)
                    .hasMessage(invalidCep);
        }
    }
}
