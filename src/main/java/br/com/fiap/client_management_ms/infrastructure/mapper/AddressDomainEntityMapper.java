package br.com.fiap.client_management_ms.infrastructure.mapper;

import br.com.fiap.client_management_ms.core.domain.Address;
import br.com.fiap.client_management_ms.infrastructure.entity.AddressEntity;

public class AddressDomainEntityMapper {

    private AddressDomainEntityMapper() {
    }

    public static AddressEntity toAddressEntity(Address address) {
        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setId(address.getId());
        addressEntity.setCep(address.getCep());
        addressEntity.setStreet(address.getStreet());
        addressEntity.setComplement(address.getComplement());
        addressEntity.setUnit(address.getUnit());
        addressEntity.setNumber(address.getNumber());
        addressEntity.setNeighborhood(address.getNeighborhood());
        addressEntity.setLocality(address.getLocality());
        addressEntity.setUf(address.getUf());
        addressEntity.setState(address.getState());
        addressEntity.setRegion(address.getRegion());
        addressEntity.setIbge(address.getIbge());
        addressEntity.setGia(address.getGia());
        addressEntity.setDdd(address.getDdd());
        addressEntity.setSiafi(address.getSiafi());
        return addressEntity;
    }

    public static Address toAddress(AddressEntity addressEntity) {
        Address address = new Address();
        address.setId(addressEntity.getId());
        address.setCep(addressEntity.getCep());
        address.setStreet(addressEntity.getStreet());
        address.setComplement(addressEntity.getComplement());
        address.setUnit(addressEntity.getUnit());
        address.setNumber(addressEntity.getNumber());
        address.setNeighborhood(addressEntity.getNeighborhood());
        address.setLocality(addressEntity.getLocality());
        address.setUf(addressEntity.getUf());
        address.setState(addressEntity.getState());
        address.setRegion(addressEntity.getRegion());
        address.setIbge(addressEntity.getIbge());
        address.setGia(addressEntity.getGia());
        address.setDdd(addressEntity.getDdd());
        address.setSiafi(addressEntity.getSiafi());
        return address;
    }
}
