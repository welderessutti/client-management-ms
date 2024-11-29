package br.com.fiap.client_management_ms.core.port.out;

import br.com.fiap.client_management_ms.core.domain.Address;

public interface AddressAdapter {

    Address getAddressByApi(String cep);
}
