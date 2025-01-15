package br.com.fiap.client_management_ms.core.service;

import br.com.fiap.client_management_ms.core.domain.Address;
import br.com.fiap.client_management_ms.core.exception.AddressAdapterApiException;
import br.com.fiap.client_management_ms.core.exception.CepAddressNotFoundException;
import br.com.fiap.client_management_ms.core.exception.ExternalApiException;
import br.com.fiap.client_management_ms.core.exception.InvalidCepException;
import br.com.fiap.client_management_ms.core.port.out.AddressPortOut;

import static java.util.Objects.nonNull;

public class AddressService {

    private final AddressPortOut addressPortOut;

    public AddressService(AddressPortOut addressPortOut) {
        this.addressPortOut = addressPortOut;
    }

    public Address getAddressByApi(Address address) {
        try {
            String cep = address.getCep();
            String number = address.getNumber();
            Address returnedAddress = addressPortOut.getAddressByApi(cep);
            if (nonNull(number)) {
                returnedAddress.setNumber(number);
            }
            return returnedAddress;
        } catch (AddressAdapterApiException.NotFound e) {
            throw new CepAddressNotFoundException("CEP not found: " + e.getMessage());
        } catch (AddressAdapterApiException.BadRequest e) {
            throw new InvalidCepException("CEP format is invalid: " + e.getMessage());
        } catch (AddressAdapterApiException.InternalError e) {
            throw new ExternalApiException("Internal ViaCep API service error: " + e.getMessage());
        }
    }
}
