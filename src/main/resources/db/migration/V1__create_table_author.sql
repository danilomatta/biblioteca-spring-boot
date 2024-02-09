CREATE TABLE author(
id BIGINT not null auto_increment,
name VARCHAR(300) not null,
biography VARCHAR(1000) NOT NULL,
ativo INT not null,
primary key(id)
);