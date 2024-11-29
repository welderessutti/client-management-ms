package br.com.fiap.client_management_ms.infrastructure.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_address")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @OneToOne
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    private ClientEntity client;
}
