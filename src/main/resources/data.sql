INSERT INTO AUTENTICACAO (password, username) VALUES ('$2a$10$Q/Qr7nXkOucqa3YaDuvBNuacPWGny824eM/Sr06bPEzjGTe/cl7/a', 'admin');
INSERT INTO AUTENTICACAO (password, username) VALUES ('$2a$10$Q/Qr7nXkOucqa3YaDuvBNuacPWGny824eM/Sr06bPEzjGTe/cl7/a', 'teste');

INSERT INTO PERFIL (id, nomeperfil) VALUES (1, 'ROLE_ADMINISTRADOR');
INSERT INTO PERFIL (id, nomeperfil) VALUES (2, 'ROLE_TESTE');


INSERT INTO AUTENTICACAO_PERFIS (AUTENTICACAO_ID, PERFIS_ID) values (1,1);
INSERT INTO AUTENTICACAO_PERFIS (AUTENTICACAO_ID, PERFIS_ID) values (2,2);