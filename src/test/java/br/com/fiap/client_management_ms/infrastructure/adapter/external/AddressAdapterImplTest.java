package br.com.fiap.client_management_ms.infrastructure.adapter.external;

import br.com.fiap.client_management_ms.core.domain.Address;
import br.com.fiap.client_management_ms.core.exception.AddressAdapterApiException;
import br.com.fiap.client_management_ms.core.port.out.AddressPortOut;
import br.com.fiap.client_management_ms.core.updater.AddressUpdater;
import br.com.fiap.client_management_ms.infrastructure.api.ViaCepApiClient;
import br.com.fiap.client_management_ms.infrastructure.dto.ViaCepApiResponseDto;
import br.com.fiap.client_management_ms.utils.ClientHelper;
import feign.FeignException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AddressAdapterImplTest {

    AddressPortOut addressPortOut;

    @Mock
    ViaCepApiClient viaCepApiClient;

    @Mock
    AddressUpdater addressUpdater;

    AutoCloseable openMocks;

    @BeforeEach
    public void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
        addressPortOut = new AddressAdapterImpl(viaCepApiClient);
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
            String cep = "01001-000";
            ViaCepApiResponseDto viaCepApiResponseDto = ClientHelper.createViaCepApiResponseDtoObject();

            when(viaCepApiClient.getAddressByCep(any(String.class))).thenReturn(viaCepApiResponseDto);

            // Act
            Address returnedAddress = addressPortOut.getAddressByApi(cep);

            // Assert
            assertThat(returnedAddress).isNotNull().isInstanceOf(Address.class);
            assertThat(returnedAddress.getId()).isNull();
            assertThat(returnedAddress.getCep()).isNotNull();
            assertThat(returnedAddress.getStreet()).isNotNull().isEqualTo(viaCepApiResponseDto.getLogradouro());
            assertThat(returnedAddress.getComplement()).isNotNull().isEqualTo(viaCepApiResponseDto.getComplemento());
            assertThat(returnedAddress.getUnit()).isNotNull().isEqualTo(viaCepApiResponseDto.getUnidade());
            assertThat(returnedAddress.getNumber()).isNull();
            assertThat(returnedAddress.getNeighborhood()).isNotNull().isEqualTo(viaCepApiResponseDto.getBairro());
            assertThat(returnedAddress.getLocality()).isNotNull().isEqualTo(viaCepApiResponseDto.getLocalidade());
            assertThat(returnedAddress.getUf()).isNotNull().isEqualTo(viaCepApiResponseDto.getUf());
            assertThat(returnedAddress.getState()).isNotNull().isEqualTo(viaCepApiResponseDto.getEstado());
            assertThat(returnedAddress.getRegion()).isNotNull().isEqualTo(viaCepApiResponseDto.getRegiao());
            assertThat(returnedAddress.getIbge()).isNotNull().isEqualTo(viaCepApiResponseDto.getIbge());
            assertThat(returnedAddress.getGia()).isNotNull().isEqualTo(viaCepApiResponseDto.getGia());
            assertThat(returnedAddress.getDdd()).isNotNull().isEqualTo(viaCepApiResponseDto.getDdd());
            assertThat(returnedAddress.getSiafi()).isNotNull().isEqualTo(viaCepApiResponseDto.getSiafi());
            assertThat(returnedAddress.getClient()).isNull();
            verify(viaCepApiClient, times(1)).getAddressByCep(any(String.class));
        }

        @Test
        void shouldThrowApiExceptionNotFoundWhenCepIsNotFound() {
            // Arrange
            String nonExistentCep = "99999-999";
            ViaCepApiResponseDto viaCepApiResponseDto = ClientHelper.createViaCepApiResponseDtoWithErrorFieldTrueObject();

            when(viaCepApiClient.getAddressByCep(any(String.class))).thenReturn(viaCepApiResponseDto);

            // Act & Assert
            assertThatThrownBy(() -> addressPortOut.getAddressByApi(nonExistentCep))
                    .isInstanceOf(AddressAdapterApiException.NotFound.class)
                    .hasMessage(nonExistentCep);
            verify(viaCepApiClient, times(1)).getAddressByCep(any(String.class));
        }

        @Test
        void shouldThrowApiExceptionBadRequestWhenCepIsInvalid() {
            // Arrange
            String invalidCep = "999";

            when(viaCepApiClient.getAddressByCep(any(String.class))).thenThrow(FeignException.BadRequest.class);

            // Act & Assert
            assertThatThrownBy(() -> addressPortOut.getAddressByApi(invalidCep))
                    .isInstanceOf(AddressAdapterApiException.BadRequest.class)
                    .hasMessage(invalidCep);
            verify(viaCepApiClient, times(1)).getAddressByCep(any(String.class));
        }
    }
}
