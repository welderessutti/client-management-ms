package br.com.fiap.client_management_ms.core.mapper;

import br.com.fiap.client_management_ms.core.domain.Address;
import br.com.fiap.client_management_ms.framework.dto.request.create.AddressCreateRequestDto;
import br.com.fiap.client_management_ms.framework.dto.request.update.AddressUpdateRequestDto;
import br.com.fiap.client_management_ms.framework.dto.response.AddressResponseDto;

import static java.util.Objects.nonNull;

public class AddressDtoDomainMapper {

    private AddressDtoDomainMapper() {
    }

    public static Address toAddress(AddressCreateRequestDto request) {
        Address address = new Address();
        address.setCep(request.getCep());
        address.setNumber(request.getNumber());
        return address;
    }

    public static AddressResponseDto toAddressResponseDto(Address address) {
        AddressResponseDto addressResponseDTO = new AddressResponseDto();
        addressResponseDTO.setId(address.getId());
        addressResponseDTO.setCep(address.getCep());
        addressResponseDTO.setStreet(address.getStreet());
        addressResponseDTO.setComplement(address.getComplement());
        addressResponseDTO.setUnit(address.getUnit());
        addressResponseDTO.setNumber(address.getNumber());
        addressResponseDTO.setNeighborhood(address.getNeighborhood());
        addressResponseDTO.setLocality(address.getLocality());
        addressResponseDTO.setUf(address.getUf());
        addressResponseDTO.setState(address.getState());
        addressResponseDTO.setRegion(address.getRegion());
        addressResponseDTO.setIbge(address.getIbge());
        addressResponseDTO.setGia(address.getGia());
        addressResponseDTO.setDdd(address.getDdd());
        addressResponseDTO.setSiafi(address.getSiafi());
        return addressResponseDTO;
    }

    public static Address updateRequestToAddress(AddressUpdateRequestDto request) {
        Address address = new Address();
        if (nonNull(request.getCep())) {
            address.setCep(request.getCep());
        }
        if (nonNull(request.getNumber())) {
            address.setNumber(request.getNumber());
        }
        return address;
    }
}
