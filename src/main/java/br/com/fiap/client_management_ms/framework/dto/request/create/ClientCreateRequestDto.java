package br.com.fiap.client_management_ms.framework.dto.request.create;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientCreateRequestDto {

    @Pattern(
            regexp = "^[A-Za-zÀ-ÖØ-öø-ÿ ]+$",
            message = "The name must contain only alphabetic characters and spaces."
    )
    @NotBlank(message = "The full name field is mandatory!")
    private String fullName;

    @Email(message = "E-mail not valid!")
    @NotBlank(message = "The e-mail field is mandatory!")
    private String email;

    @Pattern(
            regexp = "^(\\d{2}-\\d{9}|\\d{11})$",
            message = "The mobile phone number must be in a valid format (either 11-111111111 or 11111111111)."
    )
    @NotBlank(message = "The mobile phone number field is mandatory!")
    private String mobilePhoneNumber;

    @Valid
    @NotNull(message = "The CPF field is mandatory!")
    private CpfCreateRequestDto cpf;

    @Valid
    @NotNull(message = "The address field is mandatory!")
    private AddressCreateRequestDto address;
}
