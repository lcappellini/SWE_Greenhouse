-- table generation

CREATE TABLE IF NOT EXISTS "Pianta" (
    tipo VARCHAR(50) PRIMARY KEY,
    descrizione VARCHAR(200)
    );

CREATE TABLE IF NOT EXISTS "Ordine" (
    id SERIAL PRIMARY KEY,
    idCliente INT,
    dataConsegna VARCHAR(50),
    tipoPianta VARCHAR(50),
    quantità INT,
    descrizione VARCHAR(100),
    totale DECIMAL(6,2),
    stato VARCHAR(50)
    );


CREATE TABLE IF NOT EXISTS "Cliente" (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(100),
    cognome VARCHAR(100),
    email VARCHAR(100),
    password VARCHAR(100)
    );

CREATE TABLE IF NOT EXISTS "Spazio" (
    id SERIAL PRIMARY KEY,
    posizione INT
    );

CREATE TABLE IF NOT EXISTS "Posizione" (
    id SERIAL PRIMARY KEY,
    assegnata BOOLEAN
    );

CREATE TABLE IF NOT EXISTS "Operatore"(
    id SERIAL PRIMARY KEY
    );

CREATE TABLE IF NOT EXISTS "Posizionamento" (
    id SERIAL PRIMARY KEY,
    pianta VARCHAR(50),
    posizione INT,
    ordine INT,
    operatore INT
    );
