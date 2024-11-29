package br.com.fiap.client_management_ms.core.service;

import br.com.fiap.client_management_ms.core.domain.Address;
import br.com.fiap.client_management_ms.core.exception.CepAddressNotFoundException;
import br.com.fiap.client_management_ms.core.exception.InvalidCepException;
import br.com.fiap.client_management_ms.utils.ClientHelper;
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
class AddressServiceIT {

    @Autowired
    private AddressService addressService;

    @Nested
    class GetAddressByApi {

        @Test
        void shouldGetAddressByApi() {
            // Arrange
            Address address = ClientHelper.createAddressObject();

            // Act
            Address returnedAddress = addressService.getAddressByApi(address);

            // Assert
            assertThat(returnedAddress).isNotNull().isInstanceOf(Address.class);
            assertThat(returnedAddress.getId()).isNull();
            assertThat(returnedAddress.getCep()).isNotNull().isEqualTo(address.getCep());
            assertThat(returnedAddress.getStreet()).isNotNull();
            assertThat(returnedAddress.getComplement()).isNotNull();
            assertThat(returnedAddress.getUnit()).isNotNull();
            assertThat(returnedAddress.getNumber()).isNotNull();
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
        void shouldThrowCepAddressNotFoundExceptionWhenCepIsNotFound() {
            // Arrange
            String invalidCep = "99999-999";
            Address address = ClientHelper.createAddressObject();
            address.setCep(invalidCep);

            // Act & Assert
            assertThatThrownBy(() -> addressService.getAddressByApi(address))
                    .isInstanceOf(CepAddressNotFoundException.class)
                    .hasMessage("CEP not found: " + address.getCep());
        }

        @Test
        void shouldThrowInvalidCepFormatExceptionWhenCepIsInvalid() {
            // Arrange
            String invalidCep = "999";
            Address address = ClientHelper.createAddressObject();
            address.setCep(invalidCep);

            // Act & Assert
            assertThatThrownBy(() -> addressService.getAddressByApi(address))
                    .isInstanceOf(InvalidCepException.class)
                    .hasMessage("CEP format is invalid: " + address.getCep());
        }
    }
}
