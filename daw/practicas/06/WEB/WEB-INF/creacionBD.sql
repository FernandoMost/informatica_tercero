CREATE DATABASE bd_alumnos;
USE bd_alumnos;

CREATE TABLE altadaw (
    -- IDENTIFICACION
    id INT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(40) NOT NULL,
    apellidos VARCHAR(80) NOT NULL,
    dni CHAR(9) NOT NULL,
    email VARCHAR(50) NOT NULL,
    contrasena VARCHAR(32) NOT NULL,
    
    -- DIRECCION
    calle VARCHAR(100),
    num INTEGER,
    piso VARCHAR(10),
    ciudad VARCHAR(50),
    provincia VARCHAR(30),
    codigoPostal CHAR(5),
    
    -- PAGOS
    facturacionIgualEnvio BOOLEAN DEFAULT true,
    metodoPago VARCHAR(15) DEFAULT 'tarjeta',
    tarjeta CHAR(16),
    caducidadTarjeta CHAR(5),
    codSeguridadTarjeta CHAR(3),

    PRIMARY KEY (id),
    CHECK (metodoPago IN ('paypal', 'tarjeta', 'contrarrembolso'))
);


INSERT INTO altadaw (nombre, apellidos, dni, email, contrasena) VALUES (
    'Fernando',
    'Mosteiro del Pilar',
    '49402968A',
    'fernandomosteiro@gmail.com',
    '1234'
);

-- CREATE DATABASE mosteiroDelPilar;