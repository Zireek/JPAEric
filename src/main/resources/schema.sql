CREATE TABLE equipos
(
  nombre_equipos character varying(30) NOT NULL PRIMARY KEY
);

CREATE TABLE jugadores
(
  id_jugadores serial NOT NULL PRIMARY KEY,
  nom character varying(30) NOT NULL,
  any_naixement integer,
  nacionalitat character varying(12),
  equipo_juegan character varying(30) REFERENCES equipos(nombre_equipos)
);



