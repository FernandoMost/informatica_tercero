CREATE DATABASE bd_alumnos;
USE bd_alumnos;

CREATE TABLE altadaw (
    id INT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(40) NOT NULL,
    apellidos VARCHAR(80) NOT NULL,
    email VARCHAR(50) NOT NULL,

    PRIMARY KEY (id)
);

ALTER TABLE altadaw AUTO_INCREMENT=1000;

-- -----------------------------------------------------------

CREATE DATABASE mosteiroDelPilar;
USE mosteiroDelPilar;

CREATE TABLE usuario (
    id INT NOT NULL,
    nombre VARCHAR(40) NOT NULL,
    apellidos VARCHAR(80) NOT NULL,
    email VARCHAR(50) NOT NULL,
    dni CHAR(9),
    contrasena VARCHAR(32) NOT NULL,

    PRIMARY KEY (id),
    UNIQUE (email)
);

CREATE TABLE direccionEnvio (
    id INT NOT NULL AUTO_INCREMENT,
    usuario INT NOT NULL,
    
    calle VARCHAR(100) NOT NULL,
    num INTEGER NOT NULL,
    piso VARCHAR(10),
    ciudad VARCHAR(50) NOT NULL,
    provincia VARCHAR(30) NOT NULL,
    codigoPostal CHAR(5),

    PRIMARY KEY (id, usuario)
);

CREATE TABLE metodoPago (
    id INT NOT NULL AUTO_INCREMENT,
    usuario INT NOT NULL,

    facturacionIgualEnvio BOOLEAN NOT NULL DEFAULT true,
    metodoPago VARCHAR(15) DEFAULT 'tarjeta',
    tarjeta CHAR(16),
    caducidadTarjeta CHAR(5),
    codSeguridadTarjeta CHAR(3),

    PRIMARY KEY (id, usuario),
    CHECK (metodoPago IN ('paypal', 'tarjeta', 'contrarrembolso'))
);

CREATE TABLE articulo (
    id INT NOT NULL AUTO_INCREMENT,
    imagen VARCHAR(100),
    nombre VARCHAR(100),
    precio DECIMAL(7,2),
    descripcion VARCHAR(300),
    stock INT NOT NULL,

    PRIMARY KEY (id)
);

CREATE TABLE carrito (
    usuario INT NOT NULL,
    articulo INT NOT NULL,

    cantidad INT NOT NULL,

    PRIMARY KEY (usuario, articulo)
);

CREATE TABLE pedido (
    id INT NOT NULL AUTO_INCREMENT,
    usuario INT NOT NULL,
    fecha DATE NOT NULL,
    direccionEnvio INT NOT NULL,
    metodoPago INT NOT NULL,
    total DECIMAL(7,2) NOT NULL,

    PRIMARY KEY (id, usuario)
);

CREATE TABLE articuloEnPedido (
    pedido INT NOT NULL,
    articulo INT NOT NULL,

    cantidad INT NOT NULL,

    PRIMARY KEY (pedido, articulo)
);

ALTER TABLE direccionEnvio AUTO_INCREMENT=1000;
ALTER TABLE metodoPago AUTO_INCREMENT=1000;
ALTER TABLE articulo AUTO_INCREMENT=1000;
ALTER TABLE pedido AUTO_INCREMENT=1000;

-- -----------------------------------------------------------

INSERT INTO altadaw (nombre, apellidos, email) VALUES ('Fernando', 'Mosteiro del Pilar', 'fernandomosteiro@gmail.com');
INSERT INTO altadaw (nombre, apellidos, email) VALUES ('Miguel', 'Rodriguez Diaz', 'miguelwww@gmail.com');
INSERT INTO altadaw (nombre, apellidos, email) VALUES ('Álvaro', 'Goldar Dieste', 'alvarogoldar@gmail.com');
INSERT INTO altadaw (nombre, apellidos, email) VALUES ('Nicolás', 'Santos Camaño', 'nicolasUglyBastard@gmail.com');
INSERT INTO altadaw (nombre, apellidos, email) VALUES ('Francisco Javier', 'Cardama Santiago', 'franco@gmail.com');
INSERT INTO altadaw (nombre, apellidos, email) VALUES ('Manuel', 'Lestón Lestón', 'manuelll@gmail.com');
INSERT INTO altadaw (nombre, apellidos, email) VALUES ('Test', 'Test Test', 'test@test.com');


INSERT INTO usuario (id, nombre, apellidos, email, dni, contrasena) VALUES (1, 'Fernando', 'Mosteiro del Pilar', 'fernandomosteiro@gmail.com', '12345678A', '1234');
INSERT INTO usuario (id, nombre, apellidos, email, dni, contrasena) VALUES (2, 'Miguel', 'Rodriguez Diaz', 'miguelwww@gmail.com', '12345678A', '1234');
INSERT INTO usuario (id, nombre, apellidos, email, dni, contrasena) VALUES (3, 'Álvaro', 'Goldar Dieste', 'alvarogoldar@gmail.com', '12345678A', '1234');
INSERT INTO usuario (id, nombre, apellidos, email, dni, contrasena) VALUES (4, 'Nicolás', 'Santos Camaño', 'nicolasUglyBastard@gmail.com', '12345678A', '1234');
INSERT INTO usuario (id, nombre, apellidos, email, dni, contrasena) VALUES (7, 'Test', 'Test Test', 'test@test.com', '12345678A', '1234');

INSERT INTO direccionEnvio (usuario, calle, num, piso, ciudad, provincia, codigoPostal) VALUES (1);
INSERT INTO direccionEnvio (usuario, calle, num, piso, ciudad, provincia, codigoPostal) VALUES (1);
INSERT INTO direccionEnvio (usuario, calle, num, piso, ciudad, provincia, codigoPostal) VALUES (2);
INSERT INTO direccionEnvio (usuario, calle, num, piso, ciudad, provincia, codigoPostal) VALUES (3);
INSERT INTO direccionEnvio (usuario, calle, num, piso, ciudad, provincia, codigoPostal) VALUES (4);
INSERT INTO direccionEnvio (usuario, calle, num, piso, ciudad, provincia, codigoPostal) VALUES (7);
INSERT INTO direccionEnvio (usuario, calle, num, piso, ciudad, provincia, codigoPostal) VALUES (7);
INSERT INTO direccionEnvio (usuario, calle, num, piso, ciudad, provincia, codigoPostal) VALUES (7);

INSERT INTO metodoPago (usuario, facturacionIgualEnvio, metodoPago) VALUES ();
INSERT INTO metodoPago (usuario, facturacionIgualEnvio, metodoPago) VALUES ();
INSERT INTO metodoPago (usuario, facturacionIgualEnvio, metodoPago) VALUES ();
INSERT INTO metodoPago (usuario, facturacionIgualEnvio, metodoPago) VALUES ();
INSERT INTO metodoPago (usuario, facturacionIgualEnvio, metodoPago) VALUES ();
INSERT INTO metodoPago (usuario, facturacionIgualEnvio, metodoPago, tarjeta, caducidadTarjeta, codSeguridadTarjeta) VALUES ();
INSERT INTO metodoPago (usuario, facturacionIgualEnvio, metodoPago, tarjeta, caducidadTarjeta, codSeguridadTarjeta) VALUES ();
INSERT INTO metodoPago (usuario, facturacionIgualEnvio, metodoPago, tarjeta, caducidadTarjeta, codSeguridadTarjeta) VALUES ();
INSERT INTO metodoPago (usuario, facturacionIgualEnvio, metodoPago, tarjeta, caducidadTarjeta, codSeguridadTarjeta) VALUES ();
INSERT INTO metodoPago (usuario, facturacionIgualEnvio, metodoPago, tarjeta, caducidadTarjeta, codSeguridadTarjeta) VALUES ();
INSERT INTO metodoPago (usuario, facturacionIgualEnvio, metodoPago, tarjeta, caducidadTarjeta, codSeguridadTarjeta) VALUES ();

INSERT INTO articulo (nombre, descripcion, precio, stock, imagen) VALUES ();
INSERT INTO articulo (nombre, descripcion, precio, stock, imagen) VALUES ();
INSERT INTO articulo (nombre, descripcion, precio, stock, imagen) VALUES ();
INSERT INTO articulo (nombre, descripcion, precio, stock, imagen) VALUES ();
INSERT INTO articulo (nombre, descripcion, precio, stock, imagen) VALUES ();
INSERT INTO articulo (nombre, descripcion, precio, stock, imagen) VALUES ();
INSERT INTO articulo (nombre, descripcion, precio, stock, imagen) VALUES ();
INSERT INTO articulo (nombre, descripcion, precio, stock, imagen) VALUES ();
INSERT INTO articulo (nombre, descripcion, precio, stock, imagen) VALUES ();
INSERT INTO articulo (nombre, descripcion, precio, stock, imagen) VALUES ();
INSERT INTO articulo (nombre, descripcion, precio, stock, imagen) VALUES ();
INSERT INTO articulo (nombre, descripcion, precio, stock, imagen) VALUES ();
INSERT INTO articulo (nombre, descripcion, precio, stock, imagen) VALUES ();

INSERT INTO pedido

INSERT INTO articuloEnPedido