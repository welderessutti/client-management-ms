package br.com.fiap.client_management_ms.framework.dto.request.create;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CpfCreateRequestDto {

    @Pattern(
            regexp = "^(\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}|\\d{11})$",
            message = "The CPF must be in a valid format (either 111.111.111-11 or 11111111111)."
    )
    @NotBlank(message = "The document number field is mandatory!")
    private String documentNumber;
}
