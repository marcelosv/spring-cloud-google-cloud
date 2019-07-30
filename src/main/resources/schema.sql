drop table if exists clientes;

create table clientes (
 id bigint not null auto_increment primary key,
 nome varchar(255)
)