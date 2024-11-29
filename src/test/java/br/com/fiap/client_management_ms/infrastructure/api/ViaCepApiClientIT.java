package br.com.fiap.client_management_ms.infrastructure.api;

import br.com.fiap.client_management_ms.infrastructure.dto.ViaCepApiResponseDto;
import feign.FeignException;
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
class ViaCepApiClientIT {

    @Autowired
    private ViaCepApiClient viaCepApiClient;

    @Nested
    class GetAddressByCep {

        @Test
        void shouldGetAddressByCep() {
            // Arrange
            String cep = "01001-000";

            // Act
            ViaCepApiResponseDto returnedViaCepResponseDto = viaCepApiClient.getAddressByCep(cep);

            // Assert
            assertThat(returnedViaCepResponseDto).isNotNull().isInstanceOf(ViaCepApiResponseDto.class);
            assertThat(returnedViaCepResponseDto.getCep()).isNotNull().isEqualTo(cep);
            assertThat(returnedViaCepResponseDto.getErro()).isNull();
        }

        @Test
        void shouldReturnFieldErrorTrueWhenCepIsInvalid() {
            // Arrange
            String cep = "99999-999";

            // Act
            ViaCepApiResponseDto returnedViaCepResponseDto = viaCepApiClient.getAddressByCep(cep);

            // Assert
            assertThat(returnedViaCepResponseDto).isNotNull().isInstanceOf(ViaCepApiResponseDto.class);
            assertThat(returnedViaCepResponseDto.getCep()).isNull();
            assertThat(returnedViaCepResponseDto.getErro()).isNotNull().isTrue();
        }

        @Test
        void shouldThrowBadRequestFeignExceptionWhenCepIsInvalid() {
            // Arrange
            String cep = "999";

            // Act & Assert
            assertThatThrownBy(() -> viaCepApiClient.getAddressByCep(cep))
                    .isInstanceOf(FeignException.BadRequest.class);
        }
    }
}
