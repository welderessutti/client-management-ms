package br.com.fiap.client_management_ms.framework.dto.request.update;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressUpdateRequestDto {

    @Pattern(
            regexp = "^(?:\\d{5}-\\d{3}|\\d{8})$",
            message = "The CEP must be in the format (either 11111-111 or 11111111)."
    )
    private String cep;

    @Pattern(
            regexp = ".*\\S.*",
            message = "The number cannot be empty or contain only spaces."
    )
    private String number;
}
