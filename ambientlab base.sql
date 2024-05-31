-- Creación de la Base de Datos AmbientLab.
CREATE DATABASE ambientlab;
-- Uso de la BD AmbientLab.
USE ambientlab;

-- Creación de las tablas principales.

-- Creacion de la tabla para Usuario.
CREATE TABLE usuario (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255),
    apellidos VARCHAR(255),
    rol INT,
    email VARCHAR(255),
    contrasena VARCHAR(255)
);
-- Creacion de la tabla para la entidad Proyecto.
CREATE TABLE proyecto (
	id INT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(255),
    descripcion	VARCHAR(1000),
    estado VARCHAR(255) CHECK (estado IN ('A la espera', 'En proceso', 'Finalizado')),
    fechaInicio DATE,
    fechaFin DATE
);

-- Creacion de la tabla Muestra.
CREATE TABLE muestra (
    id INT AUTO_INCREMENT PRIMARY KEY,
    referencia VARCHAR(255),
    tipo VARCHAR(255) CHECK (tipo IN ('Biologica', 'Geologica', 'Suelo')),
    origen VARCHAR(255),
    estado VARCHAR(255) CHECK (estado IN ('Registrada', 'En Analisis', 'Analizada')),
    fechaRegistro DATE,
    idProyecto INT,
    FOREIGN KEY (idProyecto) REFERENCES proyecto(id)
);
-- Creacion de la tabla Analisis.
CREATE TABLE analisis (
    id INT AUTO_INCREMENT PRIMARY KEY,
    observaciones VARCHAR(255),
    tipo VARCHAR(255) CHECK (tipo IN ('ICP-MS', 'GC-MS', 'HgTotal')),
    fecha DATE,
    estado VARCHAR(255) CHECK (estado IN ('A la espera', 'En realizacion', 'Completado')),
    idMuestra INT,
    idUsuario INT,
    idProyecto INT,
    FOREIGN KEY (idMuestra) REFERENCES muestra(id),
    FOREIGN KEY (idUsuario) REFERENCES usuario(id),
    FOREIGN KEY (idProyecto) REFERENCES proyecto(id)
);

CREATE TABLE resultado (
	id INT AUTO_INCREMENT PRIMARY KEY,
    parametro VARCHAR(255),
    valor FLOAT,
    unidad VARCHAR(255),
    idAnalisis INT,
    FOREIGN KEY (idAnalisis) REFERENCES analisis(id)
);

-- Creacion de la tabla para la entidad Tarea.
CREATE TABLE tarea ( 
	id INT PRIMARY KEY,
	titulo VARCHAR(255),
	observaciones VARCHAR(255),
	estado VARCHAR(255) CHECK (estado IN ('A la espera', 'En proceso', 'Finalizado')),
	idProyecto INT,
	idUsuario INT,
	FOREIGN KEY (idProyecto) REFERENCES proyecto(id),
    FOREIGN KEY (idUsuario) REFERENCES usuario(id)
);

-- Creacion de la tabla Informe.
CREATE TABLE informe (
	id INT PRIMARY KEY,
	contenido VARCHAR(255),
	fechaEmision DATE,
	idProyecto INT,
	idUsuario INT,
	FOREIGN KEY (idUsuario) REFERENCES usuario(id),
    FOREIGN KEY (idProyecto) REFERENCES proyecto(id)
);

-- Creamos las tablas intermedias. Lo hacemos entre aquellas entidades o tablas que tienen relaciones N - N.

CREATE TABLE usuario_proyecto (
	id INT AUTO_INCREMENT PRIMARY KEY,
    idUsuario INT,
    idProyecto INT,
    FOREIGN KEY (idUsuario) REFERENCES usuario(id),
    FOREIGN KEY (idProyecto) REFERENCES proyecto(id)
);

CREATE TABLE usuario_tarea (
	id INT AUTO_INCREMENT PRIMARY KEY,
    idUsuario INT,
    idTarea INT,
    FOREIGN KEY (idUsuario) REFERENCES usuario(id),
    FOREIGN KEY (idTarea) REFERENCES tarea(id)
);
-- Aqui

CREATE TABLE proyecto_tarea (
	id INT AUTO_INCREMENT PRIMARY KEY,
    idProyecto INT,
    idTarea INT,
    FOREIGN KEY (idProyecto) REFERENCES proyecto(id),
    FOREIGN KEY (idTarea) REFERENCES tarea(id)
    );
    

-- Creamos la tabla para manejar las cookies de sesion.

CREATE TABLE sesion (
idUsuario INT,
cookie VARCHAR(255) PRIMARY KEY,
fechaCreacion Date,
FOREIGN KEY (idUsuario) REFERENCES usuario(id)
);

-- Creamos una serie de datos para rellenar la tabla y tener material con el que trabajar.

-- Insertar usuarios de ejemplo de cada tipo.
INSERT INTO usuario (nombre, apellidos, rol, email, contrasena) VALUES
('Juan', 'Pérez', 1, 'juan@emilio.com', 'password123'), -- Usuario con rol Admin.
('María', 'González', 2, 'maria@emilio.com', 'securepassord'), -- Usuario con rol Supervisor.
('Pedro', 'Martínez', 3, 'pedro@emilio.com', 'test123'), -- Usuario con rol Analista.
('Estefania', 'Aller', 4, 'estefania@emilio.com', 'prueba123'), -- Usuario con rol Registro.
('Isabel', 'Suarez', 9, 'isabel@emilio.com', 'contrasenya'); -- Usuario con rol Cliente.


-- Insertar proyectos de ejemplo
INSERT INTO proyecto (titulo, descripcion, estado, fechaInicio, fechaFin) VALUES
('Proyecto Alpha', 'Descripción del proyecto Alpha', 'En proceso', '2024-01-01', '2024-12-31'),
('Proyecto Beta', 'Descripción del proyecto Beta', 'A la espera', '2024-02-15', '2024-11-30'),
('Proyecto Gamma', 'Descripción del proyecto Gamma', 'Finalizado', '2024-03-10', '2024-08-20');


-- Insertar muestras de ejemplo para cada proyecto.

-- Insertar muestras de ejemplo para el Proyecto Alpha.
INSERT INTO muestra (tipo, origen, estado, fechaRegistro, idProyecto) VALUES
('P1 - 1','Biologica', 'Asturias', 'Registrada', '2024-04-01', 1),
('P1 - 2','Geologica', 'Asturias', 'En Analisis', '2024-04-05', 1),
('P1 - 3','Suelo', 'Asturias', 'Analizada', '2024-04-10', 1);

-- Insertar muestras de ejemplo para el Proyecto Beta.
INSERT INTO muestra (tipo, origen, estado, fechaRegistro, idProyecto) VALUES
('P2 - 1','Biologica', 'Madrid', 'Registrada', '2024-04-02', 2),
('P2 - 2','Geologica', 'Madrid', 'En Analisis', '2024-04-06', 2),
('P2 - 3','Suelo', 'Madrid', 'Analizada', '2024-04-11', 2);

-- Insertar muestras de ejemplo para el Proyecto Gamma.
INSERT INTO muestra (tipo, origen, estado, fechaRegistro, idProyecto) VALUES
('P3 - 1','Biologica', 'Canarias', 'Registrada', '2024-04-03', 3),
('P3 - 2','Geologica', 'Canarias', 'En Analisis', '2024-04-07', 3),
('P3 - 3','Suelo', 'Canarias', 'Analizada', '2024-04-12', 3);

-- Insertamos analisis para aquellas muestras de ejemplo que esten 'En Analisis' o 'Analizada'.
INSERT INTO analisis (observaciones, tipo, fecha, estado, idMuestra, idUsuario, idProyecto) VALUES
('Observacion 1', 'ICP-MS', '2024-04-15', 'En realizacion', 2, 3, 1),
('Observacion 2', 'ICP-MS', '2024-04-15', 'Completado', 3, 3, 1),
('Observacion 3', 'GC-MS', '2024-04-15', 'En realizacion', 4, 3, 2),
('Observacion 4', 'GC-MS', '2024-04-15', 'Completado', 5, 3, 2),
('Observacion 5', 'HgTotal', '2024-04-15', 'En realizacion', 8, 3, 3),
('Observacion 6', 'HgTotal', '2024-04-15', 'Completado', 9, 3, 3);

-- Insertamos los resultados para aquellos analisis que han sido completados.

INSERT INTO resultado (parametro, valor, unidad, idAnalisis) VALUES
('Ag', 10.2, 'ppm', 2),
('Au', 11.2, 'ppm', 2),
('Cu', 10.0, 'ppm', 2),
('Acy', 9.2, 'mg/kg', 4),
('Flu', 8.0, 'mg/kg', 4),
('Phen', 10.4, 'mg/kg', 4),
('Pyr', 0.6, 'mg/kg', 4),
('Hg', 6.0, 'ppm', 6);

-- Insertamos los dartos para 






