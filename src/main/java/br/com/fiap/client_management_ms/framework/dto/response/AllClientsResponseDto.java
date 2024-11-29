package br.com.fiap.client_management_ms.framework.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AllClientsResponseDto {

    List<ClientResponseDto> clients;
}
