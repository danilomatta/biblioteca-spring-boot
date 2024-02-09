CREATE TABLE usuario(
id BIGINT not null auto_increment,
nome VARCHAR(300) not null,
cpf INT not null,
ativo INT not null,
primary key(id)
);