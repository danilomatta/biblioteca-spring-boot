CREATE TABLE livros(
id BIGINT not null auto_increment,
titulo VARCHAR(300) not null,
isbn INT not null,
descricao VARCHAR(1000) NOT NULL,
ativo INT not null,
primary key(id)
);