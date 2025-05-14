CREATE DATABASE IF NOT EXISTS proyectoPrimero;
USE proyectoPrimero;


CREATE TABLE equipo(

id_equipo int auto_increment primary key,
nombreEquipo varchar (20),
anioCreacion int 

);


CREATE TABLE participantes (

id_participante  int auto_increment primary key,
nombre varchar (20),
apellido varchar (20),
id_equipo int, 
foreign key(id_equipo) references equipo(id_equipo) on delete cascade
);

CREATE TABLE Fase(

id_fase int auto_increment primary key,
nombrefase varchar (20) not null

);


# Insertamos los datos de la tabla equipo

INSERT INTO equipo ( nombreEquipo, anioCreacion) VALUES ( 'Equipo 1', 2025);
INSERT INTO equipo ( nombreEquipo, anioCreacion) VALUES ( 'Equipo 2', 2025);
INSERT INTO equipo ( nombreEquipo, anioCreacion) VALUES ( 'Equipo 3', 2025);
INSERT INTO equipo ( nombreEquipo, anioCreacion) VALUES ( 'Equipo 4', 2025);
INSERT INTO equipo ( nombreEquipo, anioCreacion) VALUES ( 'Equipo 5', 2025);
INSERT INTO equipo ( nombreEquipo, anioCreacion) VALUES ( 'Equipo 6', 2025);
INSERT INTO equipo (nombreEquipo, anioCreacion) VALUES ( 'Equipo 7', 2025);
INSERT INTO equipo ( nombreEquipo, anioCreacion) VALUES ( 'Equipo 8', 2025);
INSERT INTO equipo ( nombreEquipo, anioCreacion) VALUES ( 'Equipo 9', 2025);
INSERT INTO equipo ( nombreEquipo, anioCreacion) VALUES ( 'Equipo 10', 2025);
INSERT INTO equipo ( nombreEquipo, anioCreacion) VALUES ( 'Equipo 11', 2025);
INSERT INTO equipo (nombreEquipo, anioCreacion) VALUES ( 'Equipo 12', 2025);
INSERT INTO equipo ( nombreEquipo, anioCreacion) VALUES ( 'Equipo 13', 2025);
INSERT INTO equipo ( nombreEquipo, anioCreacion) VALUES ( 'Equipo 14', 2025);
INSERT INTO equipo ( nombreEquipo, anioCreacion) VALUES ( 'Equipo 15', 2025);
INSERT INTO equipo ( nombreEquipo, anioCreacion) VALUES ( 'Equipo 16', 2025);


# Inserto participantes para un mismo equipo
INSERT INTO participantes (nombre, id_equipo) VALUES ('Particpante 1' , 1);
INSERT INTO participantes (nombre, id_equipo) VALUES ('Particpante 2' , 1);
INSERT INTO participantes (nombre, id_equipo) VALUES ('Particpante 3' , 1);
INSERT INTO participantes (nombre, id_equipo) VALUES ('Particpante 4' , 1);

