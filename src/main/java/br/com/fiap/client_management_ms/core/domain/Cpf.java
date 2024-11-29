package br.com.fiap.client_management_ms.core.domain;

import br.com.fiap.client_management_ms.core.exception.InvalidCpfDocumentNumberException;

import java.util.Objects;

public class Cpf {

    private String documentNumber;

    public Cpf() {
    }

    public Cpf(String documentNumber) {
        documentNumber = removeCpfEspecialCharacters(documentNumber);

        if (!isDocumentNumberValid(documentNumber)) {
            throw new InvalidCpfDocumentNumberException("Invalid CPF document number: " + documentNumber);
        }

        this.documentNumber = documentNumber;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        documentNumber = removeCpfEspecialCharacters(documentNumber);

        if (!isDocumentNumberValid(documentNumber)) {
            throw new InvalidCpfDocumentNumberException("Invalid CPF document number: " + documentNumber);
        }
        this.documentNumber = documentNumber;
    }

    private String removeCpfEspecialCharacters(String documentNumber) {
        // Remove non-numeric characters
        return documentNumber.replaceAll("[^\\d]", "");
    }

    private boolean isDocumentNumberValid(String documentNumber) {
        // Verifies if CPF has 11 digits or if CPF has all equal digits
        if (documentNumber.length() != 11 || documentNumber.matches("(\\d)\\1{10}")) {
            return false;
        }

        // Calculates both check digits
        return calculateVerificationDigits(documentNumber);
    }

    private boolean calculateVerificationDigits(String documentNumber) {
        int sum = 0;
        int weight = 10;

        // First check digit
        for (int i = 0; i < 9; i++) {
            sum += (documentNumber.charAt(i) - '0') * weight--;
        }
        int firstDigit = (sum * 10) % 11;
        if (firstDigit == 10) firstDigit = 0;

        // Verifies the first digit
        if (firstDigit != (documentNumber.charAt(9) - '0')) {
            return false;
        }

        // Resume the sum and the weight to the second digit
        sum = 0;
        weight = 11;

        // Second check digit
        for (int i = 0; i < 10; i++) {
            sum += (documentNumber.charAt(i) - '0') * weight--;
        }
        int secondDigit = (sum * 10) % 11;
        if (secondDigit == 10) secondDigit = 0;

        // Verifies the second digit
        return secondDigit == (documentNumber.charAt(10) - '0');
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cpf cpf = (Cpf) o;
        return Objects.equals(documentNumber, cpf.documentNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(documentNumber);
    }

    @Override
    public String toString() {
        return "Cpf{" +
                "documentNumber='" + documentNumber + '\'' +
                '}';
    }
}
