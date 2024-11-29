package br.com.fiap.client_management_ms.infrastructure.entity;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CpfEntity {

    @Column(name = "document_number")
    private String documentNumber;
}
