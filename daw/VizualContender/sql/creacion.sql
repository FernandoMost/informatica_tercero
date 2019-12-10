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
    descripcion VARCHAR(650),
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

USE bd_alumnos;

INSERT INTO altadaw (nombre, apellidos, email) VALUES ('Fernando',  'Mosteiro del Pilar', 'fernandomosteiro@gmail.com');
INSERT INTO altadaw (nombre, apellidos, email) VALUES ('Miguel',    'Rodriguez Diaz',     'miguelwww@gmail.com');
INSERT INTO altadaw (nombre, apellidos, email) VALUES ('Álvaro',    'Goldar Dieste',      'alvarogoldar@gmail.com');
INSERT INTO altadaw (nombre, apellidos, email) VALUES ('Nicolás',   'Santos Camaño',      'nicolasUglyBastard@gmail.com');
INSERT INTO altadaw (nombre, apellidos, email) VALUES ('Francisco', 'Cardama Santiago',   'franco@gmail.com');
INSERT INTO altadaw (nombre, apellidos, email) VALUES ('Manuel',    'Lestón Lestón',      'manuelll@gmail.com');
INSERT INTO altadaw (nombre, apellidos, email) VALUES ('Test',      'Test Test',          'test@test.com');

USE mosteiroDelPilar;

INSERT INTO usuario (id, nombre, apellidos, email, dni, contrasena) VALUES (1000, 'Fernando', 'Mosteiro del Pilar', 'fernandomosteiro@gmail.com', '12345678A', '1234');
INSERT INTO usuario (id, nombre, apellidos, email, dni, contrasena) VALUES (1001, 'Miguel', 'Rodriguez Diaz', 'miguelwww@gmail.com', '12345678A', '1234');
INSERT INTO usuario (id, nombre, apellidos, email, dni, contrasena) VALUES (1002, 'Álvaro', 'Goldar Dieste', 'alvarogoldar@gmail.com', '12345678A', '1234');
INSERT INTO usuario (id, nombre, apellidos, email, dni, contrasena) VALUES (1003, 'Nicolás', 'Santos Camaño', 'nicolasUglyBastard@gmail.com', '12345678A', '1234');
INSERT INTO usuario (id, nombre, apellidos, email, dni, contrasena) VALUES (1006, 'Test', 'Test Test', 'test@test.com', '12345678A', '1234');

INSERT INTO direccionEnvio (usuario, calle, num, piso, ciudad, provincia, codigoPostal) VALUES (1000, 'Outid de Arriba', 17, '1ºB', 'Llorenç del Penedès', 'Tarragona', '43712');
INSERT INTO direccionEnvio (usuario, calle, num, piso, ciudad, provincia, codigoPostal) VALUES (1000, 'Rio Segura', 45, NULL, 'Cabanillas', 'Navarre', '31511');
INSERT INTO direccionEnvio (usuario, calle, num, piso, ciudad, provincia, codigoPostal) VALUES (1001, 'Atamaria', 32, '5ºA', 'Redondela', 'Pontevedra', '36800');
INSERT INTO direccionEnvio (usuario, calle, num, piso, ciudad, provincia, codigoPostal) VALUES (1002, 'Calle Carril de la Fuente', 38, NULL, 'Valenzuela de Calatrava', 'Ciudad Real', '13279');
INSERT INTO direccionEnvio (usuario, calle, num, piso, ciudad, provincia, codigoPostal) VALUES (1003, 'Reiseñor', 80, '7ºE', 'Almàssera', 'Valencia', '46132');
INSERT INTO direccionEnvio (usuario, calle, num, piso, ciudad, provincia, codigoPostal) VALUES (1006, 'Avda. Andalucía', 43, NULL, 'Soto en Cameros', 'La Rioja', '26132');
INSERT INTO direccionEnvio (usuario, calle, num, piso, ciudad, provincia, codigoPostal) VALUES (1006, 'C/ Cuesta del  Álamo', 60, '2ºC', 'Jubrique', 'Málaga', '29492');
INSERT INTO direccionEnvio (usuario, calle, num, piso, ciudad, provincia, codigoPostal) VALUES (1006, 'C/ Benito Guinea', 58, '3ºE', 'Malgrat de Mar', 'Barcelona', '08380');

INSERT INTO metodoPago (usuario, facturacionIgualEnvio, metodoPago) VALUES (1000, false, 'paypal');
INSERT INTO metodoPago (usuario, metodoPago, tarjeta, caducidadTarjeta, codSeguridadTarjeta) VALUES (1000, 'tarjeta', '4532038964613391', '03/23', '278');
INSERT INTO metodoPago (usuario, facturacionIgualEnvio, metodoPago) VALUES (1001, true, 'contrarrembolso');
INSERT INTO metodoPago (usuario, facturacionIgualEnvio, metodoPago, tarjeta, caducidadTarjeta, codSeguridadTarjeta) VALUES (1002, true, 'tarjeta', '4539364578459523', '03/24', '090');
INSERT INTO metodoPago (usuario, facturacionIgualEnvio, metodoPago, tarjeta, caducidadTarjeta, codSeguridadTarjeta) VALUES (1003, false, 'tarjeta', '5212397659313137', '12/21', '195');
INSERT INTO metodoPago (usuario, metodoPago) VALUES (1006, 'paypal');
INSERT INTO metodoPago (usuario, facturacionIgualEnvio, metodoPago, tarjeta, caducidadTarjeta, codSeguridadTarjeta) VALUES (1006, false, 'tarjeta', '4556338683318962', '10/21', '125');
INSERT INTO metodoPago (usuario, metodoPago, tarjeta, caducidadTarjeta, codSeguridadTarjeta) VALUES (1006, 'tarjeta', '5297638650643024', '11/22', '731');

INSERT INTO articulo (nombre, descripcion, precio, stock, imagen) VALUES (
    'Nike LD Waffle sacai Black',
    'After making their debut on the Fall / Winter 2019 runway in Paris Fashion Week, Nike and Sacai deliver a familiar silhouette in new fashion with the Sacai Nike LD Waffle Black, now available on StockX. The LD Waffle took the sneaker world by storm when they originally debuted in Sacai’s Spring / Summer 2019 show alongside a Nike Blazer collaboration. Out of the two sneakers they worked on together, the Waffle stood out most and acquired an aftermarket hype that hasn’t showed any signs of slowing down. It only made sense for the two companies to keep this model alive, but present it in new colorways.',
    440.00,
    105,
    'waffleblack.jpg');

INSERT INTO articulo (nombre, descripcion, precio, stock, imagen) VALUES (
    'Nike LD Waffle sacai Pine Green',
    'After making their debut on the Fall / Winter 2019 runway in Paris Fashion Week, Nike and Sacai deliver a familiar silhouette in new fashion with the Sacai Nike LD Waffle Pine Green, now available on StockX. The LD Waffle took the sneaker world by storm when they originally debuted in Sacai’s Spring / Summer 2019 show alongside a Nike Blazer collaboration. Out of the two sneakers they worked on together, the Waffle stood out most and acquired an aftermarket hype that hasn’t showed any signs of slowing down. It only made sense for the two companies to keep this model alive, but present it in new colorways.',
    271.00,
    95,
    'wafflePineGreen.jpg');

INSERT INTO articulo (nombre, descripcion, precio, stock, imagen) VALUES (
    'Nike Waffle Racer Off-White Vivid Sky',
    'Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry\'s standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.',
    185.00,
    200,
    'waffleRacerVividSky.jpg');

INSERT INTO articulo (nombre, descripcion, precio, stock, imagen) VALUES (
    'Nike Waffle Racer Off-White Black',
    'Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry\'s standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.',
    212.00,
    10,
    'waffleRacerBlack.jpg');

INSERT INTO articulo (nombre, descripcion, precio, stock, imagen) VALUES (
    'adidas Yeezy Boost 380 Alien',
    'Yeezy has introduced a new silhouette to their product line with the adidas Yeezy Boost 380 Alien, now available on StockX. This model was originally known to be the Yeezy Boost 350 V3, but upon release it was given its own name. ',
    458.00,
    63,
    'alien.jpg');

INSERT INTO articulo (nombre, descripcion, precio, stock, imagen) VALUES (
    'Nike React Element 55 Kendrick Lamar',
    'Kendrick Lamar and Nike expanded their collaborative efforts once again with the Nike React Element 55 Kendrick Lamar, now available on StockX. In 2018, Kendrick announced his partnership with Nike via Instagram by previewing his first signature Cortez. Two years and several sneakers later, it’s full steam ahead for this dominant partnership.',
    135.00,
    74,
    'kendrick.jpg');

INSERT INTO articulo (nombre, descripcion, precio, stock, imagen) VALUES (
    'adidas Yeezy Boost 700 V2 Vanta',
    'Make a statement with the adidas Yeezy Boost 700 V2 Vanta on your feet. This Yeezy Boost 700 V2 comes with a black upper, black midsole, and a black sole. These sneakers released in June 2019 and retailed for $300. Provide the world with some Yeezy vibes after grabbing these on StockX.',
    347.00,
    50,
    'vanta.jpg');

INSERT INTO articulo (nombre, descripcion, precio, stock, imagen) VALUES (
    'Air Fear Of God 1 Frosted Spruce',
    'Note: Only select retailers included a dust bag with the Air Fear of God 1s during their initial release. As such we can not guarantee one will be included with your purchase.',
    522.00,
    42,
    'frostedSpruce.jpg');

INSERT INTO articulo (nombre, descripcion, precio, stock, imagen) VALUES (
    'Air Fear Of God 1 Orange Pulse',
    'Note: Only select retailers included a dust bag with the Air Fear of God 1s during their initial release. As such we can not guarantee one will be included with your purchase.',
    546.00,
    39,
    'orangePulse.jpg');

INSERT INTO articulo (nombre, descripcion, precio, stock, imagen) VALUES (
    'Nike Daybreak Undercover Blue Jay',
    'Take a break from destroying the sneaker community and buy the Nike Daybreak Undercover Blue Jay. This Nike Daybreak comes with a blue upper plus white and black accents, white Nike “Swoosh”, grey midsole, and a black sole. These sneakers released in June 2019 and retailed for $160. Grab these on StockX today.',
    153.00,
    72,
    'undercoverBlue.jpg');

INSERT INTO articulo (nombre, descripcion, precio, stock, imagen) VALUES (
    'Air Presto Off-White',
    'Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry\'s standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.',
    1622.00,
    69,
    'prestos.jpg');

INSERT INTO articulo (nombre, descripcion, precio, stock, imagen) VALUES (
    'adidas Yeezy 700 V2 Static',
    'Ignore the static, adidas and Kanye are teaming up to release the adidas Yeezy 700 V2 Static. This Yeezy 700 comes with a grey upper with white accents, white midsole, and a black sole. These sneakers released in December 2018 and retailed for $300. The 700’s have been one of the hottest adidas silhouette this year so place a Bid on these today.',
    478.00,
    182,
    '700v2static.jpg');

INSERT INTO articulo (nombre, descripcion, precio, stock, imagen) VALUES (
    'Air Fear Of God 1 Light Bone',
    'Step into the bright lights while wearing the Air Fear Of God 1 Light Bone. This FOG1 comes with a grey upper, black Nike “Swoosh”, white midsole, and a translucent sole.',
    635.00,
    129,
    'lightBone.jpg');



INSERT INTO pedido (usuario, fecha, direccionEnvio, metodoPago, total) VALUES (1000, 'fecha', direccionEnvio, metodoPago, total);

INSERT INTO articuloEnPedido VALUES (1000, 1006, 1);
INSERT INTO articuloEnPedido VALUES (1000, 1000, 2);
INSERT INTO articuloEnPedido VALUES (1000, 1000, 1);
INSERT INTO articuloEnPedido VALUES (1000, 1000, 1);
INSERT INTO articuloEnPedido VALUES (1000, 1000, 3);

INSERT INTO pedido (usuario, fecha, direccionEnvio, metodoPago, total) VALUES (1000, 'fecha', direccionEnvio, metodoPago, total);

INSERT INTO articuloEnPedido VALUES (1001, 1006, 1);
INSERT INTO articuloEnPedido VALUES (1001, 1000, 1);

INSERT INTO pedido (usuario, fecha, direccionEnvio, metodoPago, total) VALUES (1001, 'fecha', direccionEnvio, metodoPago, total);

INSERT INTO articuloEnPedido VALUES (1002, 1006, 1);

INSERT INTO pedido (usuario, fecha, direccionEnvio, metodoPago, total) VALUES (1002, 'fecha', direccionEnvio, metodoPago, total);

INSERT INTO articuloEnPedido VALUES (1003, 1006, 1);
INSERT INTO articuloEnPedido VALUES (1003, 1000, 2);
INSERT INTO articuloEnPedido VALUES (1003, 1000, 1);

INSERT INTO pedido (usuario, fecha, direccionEnvio, metodoPago, total) VALUES (1006, 'fecha', direccionEnvio, metodoPago, total);

INSERT INTO articuloEnPedido VALUES (1004, 1006, 1);
INSERT INTO articuloEnPedido VALUES (1004, 1000, 2);

INSERT INTO pedido (usuario, fecha, direccionEnvio, metodoPago, total) VALUES (1006, 'fecha', direccionEnvio, metodoPago, total);

INSERT INTO articuloEnPedido VALUES (1005, 1006, 1);

INSERT INTO pedido (usuario, fecha, direccionEnvio, metodoPago, total) VALUES (1006, 'fecha', direccionEnvio, metodoPago, total);

INSERT INTO articuloEnPedido VALUES (1006, 1006, 1);
INSERT INTO articuloEnPedido VALUES (1006, 1000, 2);
INSERT INTO articuloEnPedido VALUES (1006, 1000, 1);
INSERT INTO articuloEnPedido VALUES (1006, 1000, 1);
INSERT INTO articuloEnPedido VALUES (1006, 1000, 3);
INSERT INTO articuloEnPedido VALUES (1006, 1006, 1);
INSERT INTO articuloEnPedido VALUES (1006, 1000, 2);
INSERT INTO articuloEnPedido VALUES (1006, 1000, 1);
INSERT INTO articuloEnPedido VALUES (1006, 1000, 1);