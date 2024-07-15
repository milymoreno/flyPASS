-- Creación de la tabla Cliente
CREATE TABLE cliente (
    id INT AUTO_INCREMENT PRIMARY KEY,
    tipo_identificacion VARCHAR(50) NOT NULL,
    numero_identificacion VARCHAR(50) NOT NULL,
    nombres VARCHAR(100) NOT NULL,
    apellidos VARCHAR(100) NOT NULL,
    correo_electronico VARCHAR(100) NOT NULL,
    fecha_nacimiento DATE NOT NULL,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_modificacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Creación de la tabla Estado
CREATE TABLE estado (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL
);

-- Creación de la tabla Producto (Cuenta)
CREATE TABLE producto (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_tipo_producto INT,
    numero_cuenta VARCHAR(20) UNIQUE NOT NULL,
    id_estado INT NOT NULL,
    saldo DECIMAL(10, 2) DEFAULT 0.00,
    exenta_gmf BOOLEAN NOT NULL DEFAULT FALSE,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_modificacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    id_cliente INT,
    FOREIGN KEY (id_cliente) REFERENCES cliente (id),
    FOREIGN KEY (id_estado) REFERENCES estado (id),
	FOREIGN KEY (id_tipo_producto) REFERENCES tipo_producto (id)
);
-- Creación de la tabla Tipo_Producto
CREATE TABLE tipo_producto (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL
);

-- Creación de la tabla Tipo_Transaccion
CREATE TABLE tipo_transaccion (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL
);

-- Creación de la tabla Transaccion
CREATE TABLE transaccion (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_tipo_transaccion INT NOT NULL,
    monto DECIMAL(10, 2) NOT NULL,
    saldo_anterior DECIMAL(10, 2) NOT NULL,
    saldo_actual DECIMAL(10, 2) NOT NULL,
    fecha_transaccion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    id_producto_origen INT,
    id_producto_destino INT,
    FOREIGN KEY (id_producto_origen) REFERENCES producto (id),
    FOREIGN KEY (id_producto_destino) REFERENCES producto (id),
    FOREIGN KEY (id_tipo_transaccion) REFERENCES tipo_transaccion (id)
);

-- Insertar valores iniciales en la tabla Tipo_Producto
INSERT INTO tipo_producto (nombre) VALUES ('Corriente'), ('Ahorro');

-- Insertar valores iniciales en la tabla Estado
INSERT INTO estado (nombre) VALUES ('Activa'), ('Inactiva'), ('Cancelada');

-- Insertar valores iniciales en la tabla Tipo_Transaccion
INSERT INTO tipo_transaccion (nombre) VALUES ('Consignación'), ('Retiro'), ('Transferencia');