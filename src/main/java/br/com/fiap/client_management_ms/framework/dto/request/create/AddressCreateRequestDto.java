package br.com.fiap.client_management_ms.framework.dto.request.create;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressCreateRequestDto {

    @Pattern(
            regexp = "^(?:\\d{5}-\\d{3}|\\d{8})$",
            message = "The CEP must be in the format (either 11111-111 or 11111111)."
    )
    @NotBlank(message = "The CEP field is mandatory!")
    private String cep;

    @NotBlank(message = "The number field is mandatory!")
    private String number;
}
