package br.com.fiap.client_management_ms.core.domain;

import java.util.Objects;

public class Client {

    private Long id;
    private String fullName;
    private String email;
    private String mobilePhoneNumber;
    private Cpf cpf;
    private Address address;

    public Client() {
    }

    public Client(
            Long id, String fullName, String email,
            String mobilePhoneNumber, Cpf cpf, Address address
    ) {
        this.id = id;
        this.fullName = fullName.trim();
        this.email = email;
        this.mobilePhoneNumber = removeMobilePhoneNumberSpecialCharacters(mobilePhoneNumber);
        this.cpf = cpf;
        this.address = address;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobilePhoneNumber() {
        return mobilePhoneNumber;
    }

    public void setMobilePhoneNumber(String mobilePhoneNumber) {
        this.mobilePhoneNumber = removeMobilePhoneNumberSpecialCharacters(mobilePhoneNumber);
    }

    private String removeMobilePhoneNumberSpecialCharacters(String mobilePhoneNumber) {
        // Remove non-numeric characters
        return mobilePhoneNumber.replaceAll("[^\\d]", "");
    }

    public Cpf getCpf() {
        return cpf;
    }

    public void setCpf(Cpf cpf) {
        this.cpf = cpf;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return Objects.equals(id, client.id)
                && Objects.equals(fullName, client.fullName)
                && Objects.equals(email, client.email)
                && Objects.equals(mobilePhoneNumber, client.mobilePhoneNumber)
                && Objects.equals(cpf, client.cpf)
                && Objects.equals(address, client.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fullName, email, mobilePhoneNumber, cpf, address);
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", mobilePhoneNumber='" + mobilePhoneNumber + '\'' +
                ", cpf=" + cpf +
                ", address=" + address +
                '}';
    }
}
