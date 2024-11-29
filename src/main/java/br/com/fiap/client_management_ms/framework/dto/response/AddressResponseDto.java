package br.com.fiap.client_management_ms.framework.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressResponseDto {

    private Long id;
    private String cep;
    private String street;
    private String complement;
    private String unit;
    private String number;
    private String neighborhood;
    private String locality;
    private String uf;
    private String state;
    private String region;
    private String ibge;
    private String gia;
    private String ddd;
    private String siafi;
}
