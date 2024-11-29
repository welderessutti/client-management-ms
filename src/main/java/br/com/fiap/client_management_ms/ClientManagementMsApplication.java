package br.com.fiap.client_management_ms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ClientManagementMsApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClientManagementMsApplication.class, args);
    }

}
