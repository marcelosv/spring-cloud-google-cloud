package com.example.gcpservice;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gcp.data.spanner.core.mapping.Column;
import org.springframework.cloud.gcp.data.spanner.core.mapping.PrimaryKey;
import org.springframework.cloud.gcp.data.spanner.core.mapping.Table;
import org.springframework.data.annotation.Id;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class GcpServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(GcpServiceApplication.class, args);
    }
}

// #### Passo 2

@Component
class JdbcRunner implements ApplicationRunner {

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        clienteRepository.deleteAll();
        Stream.of("Marcelo", "Souza", "Vieira")
                .map(name -> Cliente.builder().id(UUID.randomUUID().toString()).nome(name).build())
                .map(clienteRepository::save)
                .forEach(System.out::println);
    }
}

interface ClienteRepository extends PagingAndSortingRepository<Cliente, Long> {

}


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table
class Cliente {
    @Id
    @PrimaryKey
    private String id;

    @Column(name = "nome")
    private String nome;
}
// #### fim Passo 2


// #### Passo 1
/*@Component
class JdbcRunner implements ApplicationRunner {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        List<Cliente> query = jdbcTemplate.query("select * from clientes", new RowMapper<Cliente>() {
            @Override
            public Cliente mapRow(ResultSet resultSet, int i) throws SQLException {
                return Cliente
                        .builder()
                        .id(resultSet.getLong("id"))
                        .nome(resultSet.getString("nome"))
                        .build();
            }
        });
        query.forEach(System.out::println);
    }
}*/

/*@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
class Cliente {
    private Long id;
    private String nome;
}*/
//### fim passo 1