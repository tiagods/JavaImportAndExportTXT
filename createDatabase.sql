create database municipio;

CREATE TABLE public.cad_municipio
(
  codigo integer NOT NULL,
  nome character varying(40) NOT NULL,
  estado character varying(2) NOT NULL,
  CONSTRAINT cad_municipio_pkey PRIMARY KEY (codigo)
)