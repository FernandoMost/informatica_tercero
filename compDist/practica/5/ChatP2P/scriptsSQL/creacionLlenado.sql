CREATE TABLE usuario (
    nombre VARCHAR(30) NOT NULL,
    contrasena VARCHAR(20) NOT NULL,

    PRIMARY KEY (nombre)
);

CREATE TABLE solicitudPendiente (
    origen VARCHAR(30) NOT NULL,
    destino VARCHAR(20) NOT NULL,

    PRIMARY KEY (origen, destino),
    FOREIGN KEY (origen) REFERENCES usuario(nombre) ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY (destino) REFERENCES usuario(nombre) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE amistad (
    amigo1 VARCHAR(30) NOT NULL,
    amigo2 VARCHAR(20) NOT NULL,

    PRIMARY KEY (amigo1, amigo2),
    FOREIGN KEY (amigo1) REFERENCES usuario(nombre) ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY (amigo2) REFERENCES usuario(nombre) ON UPDATE CASCADE ON DELETE CASCADE
);

INSERT INTO usuario VALUES ('root', 'root');
INSERT INTO usuario VALUES ('fernando', '1234');
INSERT INTO usuario VALUES ('mario_lopez', 'asdfgh');
INSERT INTO usuario VALUES ('dr_strange', 'xyz');
INSERT INTO usuario VALUES ('SamReimi', 'abcd');

INSERT INTO amistad VALUES ('fernando', 'mario_lopez');
INSERT INTO amistad VALUES ('mario_lopez', 'fernando');

INSERT INTO solicitudPendiente VALUES ('fernando', 'SamReimi');


