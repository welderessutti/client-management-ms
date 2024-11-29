package br.com.fiap.client_management_ms.core.updater;

import br.com.fiap.client_management_ms.core.domain.Client;

import static java.util.Objects.nonNull;

public class ClientUpdater {

    private ClientUpdater() {
    }

    public static void updateOutdatedClient(Client outdated, Client updated) {
        if (nonNull(updated.getFullName())) {
            outdated.setFullName(updated.getFullName());
        }
        if (nonNull(updated.getEmail())) {
            outdated.setEmail(updated.getEmail());
        }
        if (nonNull(updated.getMobilePhoneNumber())) {
            outdated.setMobilePhoneNumber(updated.getMobilePhoneNumber());
        }
        if (nonNull(updated.getCpf())) {
            CpfUpdater.updateOutdatedCpf(outdated.getCpf(), updated.getCpf());
        }
        if (nonNull(updated.getAddress())) {
            AddressUpdater.updateOutdatedAddress(outdated.getAddress(), updated.getAddress());
        }
    }
}
