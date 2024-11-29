package br.com.fiap.client_management_ms.utils;

import br.com.fiap.client_management_ms.core.domain.Address;
import br.com.fiap.client_management_ms.core.domain.Client;
import br.com.fiap.client_management_ms.core.domain.Cpf;
import br.com.fiap.client_management_ms.framework.dto.request.create.AddressCreateRequestDto;
import br.com.fiap.client_management_ms.framework.dto.request.create.ClientCreateRequestDto;
import br.com.fiap.client_management_ms.framework.dto.request.create.CpfCreateRequestDto;
import br.com.fiap.client_management_ms.framework.dto.request.update.AddressUpdateRequestDto;
import br.com.fiap.client_management_ms.framework.dto.request.update.ClientUpdateRequestDto;
import br.com.fiap.client_management_ms.framework.dto.request.update.CpfUpdateRequestDto;
import br.com.fiap.client_management_ms.framework.dto.response.*;
import br.com.fiap.client_management_ms.infrastructure.dto.ViaCepApiResponseDto;
import br.com.fiap.client_management_ms.infrastructure.entity.AddressEntity;
import br.com.fiap.client_management_ms.infrastructure.entity.ClientEntity;
import br.com.fiap.client_management_ms.infrastructure.entity.CpfEntity;

import java.util.List;

public class ClientHelper {

    private ClientHelper() {
    }

    public static Client createClientObject() {
        Cpf cpf = new Cpf(
                "91202612253"
        );
        Address address = new Address(
                null,
                "43156-070",
                "Rua Osvaldo Vargas",
                "de 999/2332 ao fim",
                "",
                "999",
                "Vila Nova",
                "São Paulo",
                "SP",
                "São Paulo",
                "Sudeste",
                "0000000",
                "0000",
                "11",
                "0000",
                null
        );
        return new Client(
                null,
                "Marcos da Silva",
                "marcos@silva.com",
                "99999999999",
                cpf,
                address
        );
    }

    public static ClientEntity createClientEntityObject() {
        CpfEntity cpfEntity = new CpfEntity(
                "91202612253"
        );
        AddressEntity addressEntity = new AddressEntity(
                null,
                "43156-070",
                "Rua Osvaldo Vargas",
                "de 999/2332 ao fim",
                "",
                "999",
                "Vila Nova",
                "São Paulo",
                "SP",
                "São Paulo",
                "Sudeste",
                "0000000",
                "0000",
                "11",
                "0000",
                null
        );
        return new ClientEntity(
                null,
                "Marcos da Silva",
                "marcos@silva.com",
                "99999999999",
                cpfEntity,
                addressEntity
        );
    }

    public static Client createClientResponseObject() {
        return new Client();
    }

    public static ClientCreateRequestDto createClientRequestDtoObject() {
        CpfCreateRequestDto cpfCreateRequestDto = new CpfCreateRequestDto(
                "91202612253"
        );
        AddressCreateRequestDto addressCreateRequestDto = new AddressCreateRequestDto(
                "01001-000",
                "999"
        );
        return new ClientCreateRequestDto(
                "Marcos da Silva",
                "marcos@silva.com",
                "99999999999",
                cpfCreateRequestDto,
                addressCreateRequestDto
        );
    }

    public static Address createAddressObject() {
        return new Address(
                null,
                "01001-000",
                null,
                null,
                null,
                "999",
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null
        );
    }

    public static Address createFilledAddressObject() {
        return new Address(
                null,
                "01001-000",
                "Praça da Sé",
                "lado ímpar",
                "",
                "999",
                "Sé",
                "São Paulo",
                "SP",
                "São Paulo",
                "Sudeste",
                "3550308",
                "1004",
                "11",
                "7107",
                null
        );
    }

    public static Address createUpdatedAddressObject() {
        return new Address(
                null,
                "01310-930",
                "Avenida Paulista",
                "2100",
                "Banco Safra S.A",
                "555",
                "Bela Vista",
                "São Paulo",
                "SP",
                "São Paulo",
                "Sudeste",
                "3550308",
                "1004",
                "11",
                "7107",
                null
        );
    }

    public static ViaCepApiResponseDto createViaCepApiResponseDtoObject() {
        return new ViaCepApiResponseDto(
                "01001-000",
                "Praça da Sé",
                "lado ímpar",
                "",
                "Sé",
                "São Paulo",
                "SP",
                "São Paulo",
                "Sudeste",
                "3550308",
                "1004",
                "11",
                "7107",
                null
        );
    }

    public static ViaCepApiResponseDto createViaCepApiResponseDtoWithErrorFieldTrueObject() {
        return new ViaCepApiResponseDto(
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                true
        );
    }

    public static CreateClientResponseDto createCreateClientResponseDtoObject() {
        return new CreateClientResponseDto(1L);
    }

    public static ClientResponseDto createClientResponseDtoObject() {
        CpfResponseDto cpfResponseDto = new CpfResponseDto(
                "91202612253"
        );
        AddressResponseDto addressResponseDto = new AddressResponseDto(
                1L,
                "01001-000",
                "Praça da Sé",
                "lado ímpar",
                "",
                "999",
                "Sé",
                "São Paulo",
                "SP",
                "São Paulo",
                "Sudeste",
                "3550308",
                "1004",
                "11",
                "7107"
        );
        return new ClientResponseDto(
                1L,
                "Marcos da Silva",
                "marcos@silva.com",
                "99999999999",
                cpfResponseDto,
                addressResponseDto
        );
    }

    public static AllClientsResponseDto createAllClientsResponseDtoObject() {
        ClientResponseDto clientResponseDto1 = createClientResponseDtoObject();
        ClientResponseDto clientResponseDto2 = createClientResponseDtoObject();
        List<ClientResponseDto> clientResponseDtoList = List.of(clientResponseDto1, clientResponseDto2);
        return new AllClientsResponseDto(clientResponseDtoList);
    }

    public static ClientUpdateRequestDto createClientUpdateRequestDtoObject() {
        CpfUpdateRequestDto cpfUpdateRequestDto = new CpfUpdateRequestDto(
                "29399354083"
        );
        AddressUpdateRequestDto addressUpdateRequestDto = new AddressUpdateRequestDto(
                "01310-930",
                "555"
        );
        return new ClientUpdateRequestDto(
                "Updated name",
                "email@updated.com",
                "55555555555",
                cpfUpdateRequestDto,
                addressUpdateRequestDto
        );
    }

    public static Client createUpdatedClientObject() {
        Cpf cpf = new Cpf(
                "29399354083"
        );
        Address address = new Address(
                null,
                "01310-930",
                "Avenida Paulista",
                "2100",
                "Banco Safra S.A",
                "555",
                "Bela Vista",
                "São Paulo",
                "SP",
                "São Paulo",
                "Sudeste",
                "3550308",
                "1004",
                "11",
                "7107",
                null
        );
        return new Client(
                null,
                "Updated name",
                "email@updated.com",
                "55555555555",
                cpf,
                address
        );
    }
}
