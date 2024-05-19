-- table generation

CREATE TABLE IF NOT EXISTS "Pianta" (
    tipo VARCHAR(50) PRIMARY KEY,
    descrizione VARCHAR(200)
    );

CREATE TABLE IF NOT EXISTS "Ordine" (
    id SERIAL PRIMARY KEY,
    cliente INT,
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

CREATE TABLE IF NOT EXISTS "Operatore"(
    id SERIAL PRIMARY KEY
    );

CREATE TABLE IF NOT EXISTS "Posizionamento" (
    id SERIAL PRIMARY KEY,
    pianta VARCHAR(50),
    posizione INT,
    ordine INT,
    operatore INT,
    FOREIGN KEY (pianta) REFERENCES "Pianta"(tipo),
    FOREIGN KEY (posizione) REFERENCES  "Posizione"(id),
    FOREIGN KEY (ordine) REFERENCES  "Ordine"(id)
    );

CREATE TABLE IF NOT EXISTS "Ambiente" (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    descrizione VARCHAR(200),
    nSpaziMax INT NOT NULL
);

CREATE TABLE IF NOT EXISTS "Spazio" (
    id SERIAL PRIMARY KEY,
    ambiente INT,
    nPosizioniMax INT NOT NULL,
    FOREIGN KEY (ambiente) REFERENCES "Ambiente"(id)
);

CREATE TABLE IF NOT EXISTS "Posizione" (
    id SERIAL PRIMARY KEY,
    assegnata BOOLEAN,
    spazio INT,
    FOREIGN KEY (spazio) REFERENCES  "Spazio"(id)
);
