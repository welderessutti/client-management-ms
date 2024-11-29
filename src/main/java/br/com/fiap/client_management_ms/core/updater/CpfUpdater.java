package br.com.fiap.client_management_ms.core.updater;

import br.com.fiap.client_management_ms.core.domain.Cpf;

import static java.util.Objects.nonNull;

public class CpfUpdater {

    private CpfUpdater() {
    }

    public static void updateOutdatedCpf(Cpf outdated, Cpf updated) {
        if (nonNull(updated.getDocumentNumber())) {
            outdated.setDocumentNumber(updated.getDocumentNumber());
        }
    }
}
