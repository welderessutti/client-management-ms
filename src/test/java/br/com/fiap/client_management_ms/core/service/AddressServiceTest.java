package br.com.fiap.client_management_ms.core.service;

import br.com.fiap.client_management_ms.core.domain.Address;
import br.com.fiap.client_management_ms.core.exception.AddressAdapterApiException;
import br.com.fiap.client_management_ms.core.exception.CepAddressNotFoundException;
import br.com.fiap.client_management_ms.core.exception.InvalidCepException;
import br.com.fiap.client_management_ms.core.port.out.AddressAdapter;
import br.com.fiap.client_management_ms.utils.ClientHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class AddressServiceTest {

    AddressService addressService;

    @Mock
    AddressAdapter addressAdapter;

    AutoCloseable openMocks;

    @BeforeEach
    public void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
        addressService = new AddressService(addressAdapter);
    }

    @AfterEach
    public void tearDown() throws Exception {
        openMocks.close();
    }

    @Nested
    class GetAddressByApi {

        @Test
        void shouldGetAddressByApi() {
            // Arrange
            Address address = ClientHelper.createAddressObject();
            String cep = address.getCep();
            Address filledAddress = ClientHelper.createFilledAddressObject();

            when(addressAdapter.getAddressByApi(cep)).thenReturn(filledAddress);

            // Act
            Address returnedAddress = addressService.getAddressByApi(address);

            // Assert
            assertThat(returnedAddress).isNotNull().isInstanceOf(Address.class);
            assertThat(returnedAddress.getId()).isNull();
            assertThat(returnedAddress.getCep()).isNotNull().isEqualTo(cep);
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
            verify(addressAdapter, times(1)).getAddressByApi(any(String.class));
        }

        @Test
        void shouldThrowCepAddressNotFoundExceptionWhenCepIsNotFound() {
            // Arrange
            String invalidCep = "99999-999";
            Address address = ClientHelper.createAddressObject();
            address.setCep(invalidCep);

            when(addressAdapter.getAddressByApi(any(String.class)))
                    .thenThrow(new AddressAdapterApiException.NotFound(address.getCep()));

            // Act & Assert
            assertThatThrownBy(() -> addressService.getAddressByApi(address))
                    .isInstanceOf(CepAddressNotFoundException.class)
                    .hasMessage("CEP not found: " + address.getCep());
            verify(addressAdapter, times(1)).getAddressByApi(any(String.class));
        }

        @Test
        void shouldThrowInvalidCepFormatExceptionWhenCepIsInvalid() {
            // Arrange
            String invalidCep = "999";
            Address address = ClientHelper.createAddressObject();
            address.setCep(invalidCep);

            when(addressAdapter.getAddressByApi(any(String.class)))
                    .thenThrow(new AddressAdapterApiException.BadRequest(address.getCep()));

            // Act & Assert
            assertThatThrownBy(() -> addressService.getAddressByApi(address))
                    .isInstanceOf(InvalidCepException.class)
                    .hasMessage("CEP format is invalid: " + address.getCep());
            verify(addressAdapter, times(1)).getAddressByApi(any(String.class));
        }
    }
}
