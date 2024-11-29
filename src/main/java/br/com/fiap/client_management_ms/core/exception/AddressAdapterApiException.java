package br.com.fiap.client_management_ms.core.exception;

public class AddressAdapterApiException extends RuntimeException {

    public AddressAdapterApiException(String message) {
        super(message);
    }

    public static class BadRequest extends AddressAdapterApiException {
        public BadRequest(String message) {
            super(message);
        }
    }

    public static class NotFound extends AddressAdapterApiException {
        public NotFound(String message) {
            super(message);
        }
    }

    public static class InternalError extends AddressAdapterApiException {
        public InternalError(String message) {
            super(message);
        }
    }
}
