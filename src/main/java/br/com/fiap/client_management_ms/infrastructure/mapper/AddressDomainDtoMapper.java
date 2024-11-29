package br.com.fiap.client_management_ms.infrastructure.mapper;

import br.com.fiap.client_management_ms.core.domain.Address;
import br.com.fiap.client_management_ms.infrastructure.dto.ViaCepApiResponseDto;

public class AddressDomainDtoMapper {

    private AddressDomainDtoMapper() {
    }

    public static Address toAddress(ViaCepApiResponseDto viaCepApiResponseDto) {
        Address address = new Address();
        address.setCep(viaCepApiResponseDto.getCep());
        address.setStreet(viaCepApiResponseDto.getLogradouro());
        address.setComplement(viaCepApiResponseDto.getComplemento());
        address.setUnit(viaCepApiResponseDto.getUnidade());
        address.setNeighborhood(viaCepApiResponseDto.getBairro());
        address.setLocality(viaCepApiResponseDto.getLocalidade());
        address.setUf(viaCepApiResponseDto.getUf());
        address.setState(viaCepApiResponseDto.getEstado());
        address.setRegion(viaCepApiResponseDto.getRegiao());
        address.setIbge(viaCepApiResponseDto.getIbge());
        address.setGia(viaCepApiResponseDto.getGia());
        address.setDdd(viaCepApiResponseDto.getDdd());
        address.setSiafi(viaCepApiResponseDto.getSiafi());
        return address;
    }
}
