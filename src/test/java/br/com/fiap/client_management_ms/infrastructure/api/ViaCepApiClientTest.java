package br.com.fiap.client_management_ms.infrastructure.api;

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
import static org.mockito.Mockito.*;

class ViaCepApiClientTest {

    @Mock
    ViaCepApiClient viaCepApiClient;

    AutoCloseable openMocks;

    @BeforeEach
    public void openMocks() {
        openMocks = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    public void closeMocks() throws Exception {
        openMocks.close();
    }

    @Nested
    class GetAddressByCep {

        @Test
        void shouldGetAddressByCep() {
            // Arrange
            String cep = "01001-000";
            ViaCepApiResponseDto viaCepApiResponseDto = ClientHelper.createViaCepApiResponseDtoObject();

            when(viaCepApiClient.getAddressByCep(any(String.class))).thenReturn(viaCepApiResponseDto);

            // Act
            ViaCepApiResponseDto returnedViaCepResponseDto = viaCepApiClient.getAddressByCep(cep);

            // Assert
            assertThat(returnedViaCepResponseDto).isNotNull().isInstanceOf(ViaCepApiResponseDto.class);
            assertThat(returnedViaCepResponseDto).usingRecursiveComparison().isEqualTo(viaCepApiResponseDto);
            verify(viaCepApiClient, times(1)).getAddressByCep(any(String.class));
        }

        @Test
        void shouldReturnFieldErrorTrueWhenCepIsNotFound() {
            // Arrange
            String cep = "99999-999";
            ViaCepApiResponseDto viaCepApiResponseDto = ClientHelper.createViaCepApiResponseDtoWithErrorFieldTrueObject();

            when(viaCepApiClient.getAddressByCep(any(String.class))).thenReturn(viaCepApiResponseDto);

            // Act
            ViaCepApiResponseDto returnedViaCepResponseDto = viaCepApiClient.getAddressByCep(cep);

            // Assert
            assertThat(returnedViaCepResponseDto).isNotNull().isInstanceOf(ViaCepApiResponseDto.class);
            assertThat(returnedViaCepResponseDto.getErro()).isNotNull().isTrue();
            assertThat(returnedViaCepResponseDto).usingRecursiveComparison().isEqualTo(viaCepApiResponseDto);
            verify(viaCepApiClient, times(1)).getAddressByCep(any(String.class));
        }

        @Test
        void shouldThrowBadRequestOpenFeignExceptionWhenCepIsInvalid() {
            // Arrange
            String cep = "999";

            when(viaCepApiClient.getAddressByCep(any(String.class))).thenThrow(FeignException.BadRequest.class);

            // Act & Assert
            assertThatThrownBy(() -> viaCepApiClient.getAddressByCep(cep))
                    .isInstanceOf(FeignException.BadRequest.class);
            verify(viaCepApiClient, times(1)).getAddressByCep(any(String.class));
        }
    }
}
