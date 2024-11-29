package br.com.fiap.client_management_ms.infrastructure.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_client")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "full_name")
    private String fullName;

    private String email;

    @Column(name = "mobile_phone_number")
    private String mobilePhoneNumber;

    @Embedded
    private CpfEntity cpf;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "client")
    private AddressEntity address;
}
