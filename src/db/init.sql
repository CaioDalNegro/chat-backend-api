CREATE TABLE Users (
    id_user UUID PRIMARY KEY,
    nome VARCHAR(50) UNIQUE NOT NULL,
    senha VARCHAR(50) NOT NULL
);

CREATE TABLE Message (
    id_message UUID PRIMARY KEY,
    id_destinatario UUID REFERENCES Users(id_user),
    id_remetente UUID REFERENCES Users(id_user),
    descricao VARCHAR(100) NOT NULL,
    datahora TIMESTAMP NOT NULL
);

CREATE TABLE Contact (
    id_contact UUID PRIMARY KEY,
    id_usuarioInterno UUID REFERENCES Users(id_user),
    id_usuarioExterno UUID REFERENCES Users(id_user)
);
