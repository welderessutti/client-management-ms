package br.com.fiap.client_management_ms.core.updater;

import br.com.fiap.client_management_ms.core.domain.Address;

import static java.util.Objects.nonNull;

public class AddressUpdater {

    private AddressUpdater() {
    }

    public static void updateOutdatedAddress(Address outdated, Address updated) {
        if (nonNull(updated.getCep())) {
            outdated.setCep(updated.getCep());
        }
        if (nonNull(updated.getStreet())) {
            outdated.setStreet(updated.getStreet());
        }
        if (nonNull(updated.getComplement())) {
            outdated.setComplement(updated.getComplement());
        }
        if (nonNull(updated.getUnit())) {
            outdated.setUnit(updated.getUnit());
        }
        if (nonNull(updated.getNumber())) {
            outdated.setNumber(updated.getNumber());
        }
        if (nonNull(updated.getNeighborhood())) {
            outdated.setNeighborhood(updated.getNeighborhood());
        }
        if (nonNull(updated.getLocality())) {
            outdated.setLocality(updated.getLocality());
        }
        if (nonNull(updated.getUf())) {
            outdated.setUf(updated.getUf());
        }
        if (nonNull(updated.getState())) {
            outdated.setState(updated.getState());
        }
        if (nonNull(updated.getRegion())) {
            outdated.setRegion(updated.getRegion());
        }
        if (nonNull(updated.getIbge())) {
            outdated.setIbge(updated.getIbge());
        }
        if (nonNull(updated.getGia())) {
            outdated.setGia(updated.getGia());
        }
        if (nonNull(updated.getDdd())) {
            outdated.setDdd(updated.getDdd());
        }
        if (nonNull(updated.getSiafi())) {
            outdated.setSiafi(updated.getSiafi());
        }
    }
}
