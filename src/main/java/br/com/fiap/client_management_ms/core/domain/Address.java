package br.com.fiap.client_management_ms.core.domain;

import java.util.Objects;

public class Address {

    private Long id;
    private String cep;
    private String street;
    private String complement;
    private String unit;
    private String number;
    private String neighborhood;
    private String locality;
    private String uf;
    private String state;
    private String region;
    private String ibge;
    private String gia;
    private String ddd;
    private String siafi;
    private Client client;

    public Address() {
    }

    public Address(
            Long id,
            String cep,
            String street,
            String complement,
            String unit,
            String number,
            String neighborhood,
            String locality,
            String uf,
            String state,
            String region,
            String ibge,
            String gia,
            String ddd,
            String siafi,
            Client client
    ) {
        this.id = id;
        this.cep = removeCepSpecialCharacters(cep);
        this.street = street;
        this.complement = complement;
        this.unit = unit;
        this.number = number.trim();
        this.neighborhood = neighborhood;
        this.locality = locality;
        this.uf = uf;
        this.state = state;
        this.region = region;
        this.ibge = ibge;
        this.gia = gia;
        this.ddd = ddd;
        this.siafi = siafi;
        this.client = client;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = removeCepSpecialCharacters(cep);
    }

    private String removeCepSpecialCharacters(String cep) {
        // Remove caracteres não numéricos
        return cep.replaceAll("[^\\d]", "");
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getComplement() {
        return complement;
    }

    public void setComplement(String complement) {
        this.complement = complement;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number.trim();
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getIbge() {
        return ibge;
    }

    public void setIbge(String ibge) {
        this.ibge = ibge;
    }

    public String getGia() {
        return gia;
    }

    public void setGia(String gia) {
        this.gia = gia;
    }

    public String getDdd() {
        return ddd;
    }

    public void setDdd(String ddd) {
        this.ddd = ddd;
    }

    public String getSiafi() {
        return siafi;
    }

    public void setSiafi(String siafi) {
        this.siafi = siafi;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return Objects.equals(id, address.id)
                && Objects.equals(cep, address.cep)
                && Objects.equals(street, address.street)
                && Objects.equals(complement, address.complement)
                && Objects.equals(unit, address.unit)
                && Objects.equals(number, address.number)
                && Objects.equals(neighborhood, address.neighborhood)
                && Objects.equals(locality, address.locality)
                && Objects.equals(uf, address.uf)
                && Objects.equals(state, address.state)
                && Objects.equals(region, address.region)
                && Objects.equals(ibge, address.ibge)
                && Objects.equals(gia, address.gia)
                && Objects.equals(ddd, address.ddd)
                && Objects.equals(siafi, address.siafi)
                && Objects.equals(client, address.client);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                id, cep, street, complement,
                unit, number, neighborhood, locality,
                uf, state, region, ibge,
                gia, ddd, siafi, client);
    }

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", cep='" + cep + '\'' +
                ", street='" + street + '\'' +
                ", complement='" + complement + '\'' +
                ", unit='" + unit + '\'' +
                ", number='" + number + '\'' +
                ", neighborhood='" + neighborhood + '\'' +
                ", locality='" + locality + '\'' +
                ", uf='" + uf + '\'' +
                ", state='" + state + '\'' +
                ", region='" + region + '\'' +
                ", ibge='" + ibge + '\'' +
                ", gia='" + gia + '\'' +
                ", ddd='" + ddd + '\'' +
                ", siafi='" + siafi + '\'' +
                ", client=" + client +
                '}';
    }
}
