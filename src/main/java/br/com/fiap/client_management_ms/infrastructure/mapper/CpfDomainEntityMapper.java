package br.com.fiap.client_management_ms.infrastructure.mapper;

import br.com.fiap.client_management_ms.core.domain.Cpf;
import br.com.fiap.client_management_ms.infrastructure.entity.CpfEntity;

public class CpfDomainEntityMapper {

    private CpfDomainEntityMapper() {
    }

    public static CpfEntity toCpfEntity(Cpf cpf) {
        CpfEntity cpfEntity = new CpfEntity();
        cpfEntity.setDocumentNumber(cpf.getDocumentNumber());
        return cpfEntity;
    }

    public static Cpf toCpf(CpfEntity cpfEntity) {
        Cpf cpf = new Cpf();
        cpf.setDocumentNumber(cpfEntity.getDocumentNumber());
        return cpf;
    }
}
