-- Drop existing tables
DROP TABLE IF EXISTS "Posizionamento";
DROP TABLE IF EXISTS "Posizione";
DROP TABLE IF EXISTS "Spazio";
DROP TABLE IF EXISTS "Cliente";
DROP TABLE IF EXISTS "Ordine";
DROP TABLE IF EXISTS "Pianta";

-- Recreate tables based on schema
CREATE TABLE IF NOT EXISTS "Pianta" (
    id SERIAL PRIMARY KEY,
    tipo VARCHAR(50)
    );

CREATE TABLE IF NOT EXISTS "Ordine" (
    id SERIAL PRIMARY KEY,
    cliente VARCHAR(50),
    dataConsegna VARCHAR(50),
    tipoPianta VARCHAR(50),
    quantità VARCHAR(50),
    descrizione VARCHAR(100),
    totale DECIMAL(6,2)
    );

CREATE TABLE IF NOT EXISTS "Cliente" (
    nome VARCHAR(50) PRIMARY KEY,
    cognome VARCHAR(50)
    );

CREATE TABLE IF NOT EXISTS "Spazio" (
    id SERIAL PRIMARY KEY,
    posizione INT
);

CREATE TABLE IF NOT EXISTS "Posizione" (
    id SERIAL PRIMARY KEY,
    assegnata BOOLEAN
);

CREATE TABLE IF NOT EXISTS "Posizionamento" (
    pianta INT,
    commissione INT,
    posizione INT,
    FOREIGN KEY (pianta) REFERENCES "Pianta"(id),
    FOREIGN KEY (commissione) REFERENCES "Ordine"(id),
    FOREIGN KEY (posizione) REFERENCES "Posizione"(id)
    );
