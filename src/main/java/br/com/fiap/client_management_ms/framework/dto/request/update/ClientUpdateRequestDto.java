package br.com.fiap.client_management_ms.framework.dto.request.update;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientUpdateRequestDto {

    @Pattern(
            regexp = ".*\\S.*",
            message = "The name cannot be empty or contain only spaces."
    )
    @Pattern(
            regexp = "^[A-Za-zÀ-ÖØ-öø-ÿ ]+$",
            message = "The name must contain only alphabetic characters and spaces."
    )
    private String fullName;

    @Pattern(
            regexp = ".*\\S.*",
            message = "The e-mail cannot be empty or contain only spaces."
    )
    @Email(message = "E-mail not valid!")
    private String email;

    @Pattern(
            regexp = "^(\\d{2}-\\d{9}|\\d{11})$",
            message = "The mobile phone number must be in a valid format (either 11-111111111 or 11111111111)."
    )
    private String mobilePhoneNumber;

    @Valid
    private CpfUpdateRequestDto cpf;

    @Valid
    private AddressUpdateRequestDto address;
}
