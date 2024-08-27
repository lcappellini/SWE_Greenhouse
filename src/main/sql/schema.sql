-- table generation

CREATE TABLE IF NOT EXISTS "Pianta" (
    id INT PRIMARY KEY ,
    tipo VARCHAR(50),
    descrizione VARCHAR(200)
    );


CREATE TABLE IF NOT EXISTS "Termometro" (
    id INT PRIMARY KEY ,
    temperatura INT,
    data VARCHAR(50)
    );

CREATE TABLE IF NOT EXISTS "IgrometroAria" (
    id INT PRIMARY KEY ,
    perc_acqua INT,
    data VARCHAR(50)

);
CREATE TABLE IF NOT EXISTS "IgrometroTerreno" (
    id INT PRIMARY KEY,
    perc_acqua INT,
    data VARCHAR(50)

);
CREATE TABLE IF NOT EXISTS "Irrigatore" (
    id INT PRIMARY KEY,
    acceso BOOLEAN
);

CREATE TABLE IF NOT EXISTS "Fotosensore" (
    id INT PRIMARY KEY,
    perc_luce INT,
    data VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS "Lampada" (
    id INT PRIMARY KEY,
    acceso BOOLEAN
);

CREATE TABLE IF NOT EXISTS "Climatizzatore" (
    id INT PRIMARY KEY,
    acceso BOOLEAN,
    temperaturaRichiesta INT
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
    nome VARCHAR(50),
    cognome VARCHAR(50),
    email VARCHAR(50),
    password varchar(50)
    );

CREATE TABLE IF NOT EXISTS "Operatore"(
    id SERIAL PRIMARY KEY,
    occupato BOOLEAN
    );


CREATE TABLE IF NOT EXISTS "Spazio" (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    descrizione VARCHAR(200),
    nAmbientiMax INT NOT NULL
);

CREATE TABLE IF NOT EXISTS "Ambiente" (
    id SERIAL PRIMARY KEY,
    spazio_id INT,
    nPosizioniMax INT NOT NULL,
    termometro INT,
    fotosensore INT,
    climatizzazione INT,
    lampada INT,
    igrometroAria INT,
    FOREIGN KEY (spazio_id) REFERENCES "Spazio"(id),
    FOREIGN KEY (termometro) REFERENCES  "Termometro"(id),
    FOREIGN KEY (fotosensore) REFERENCES "Fotosensore"(id),
    FOREIGN KEY (climatizzazione) REFERENCES  "Climatizzatore"(id),
    FOREIGN KEY (lampada) REFERENCES "Lampada"(id),
    FOREIGN KEY (igrometroAria) REFERENCES "IgrometroAria"(id)
);

CREATE TABLE IF NOT EXISTS "Posizione" (
    id SERIAL PRIMARY KEY,
    assegnata BOOLEAN,
    ambiente INT,
    irriatore INT,
    igrometroTerreno INT,
    FOREIGN KEY (ambiente) REFERENCES  "Ambiente"(id),
    FOREIGN KEY (irriatore) REFERENCES  "Irrigatore"(id),
    FOREIGN KEY (igrometroTerreno) REFERENCES  "IgrometroTerreno"(id)
);

CREATE TABLE IF NOT EXISTS "Posizionamento" (
    id SERIAL PRIMARY KEY,
    pianta VARCHAR(50),
    posizione INT,
    ordine INT,
    operatore int,
    FOREIGN KEY (posizione) REFERENCES  "Posizione"(id),
    FOREIGN KEY (ordine) REFERENCES  "Ordine"(id),
    FOREIGN KEY (operatore) REFERENCES  "Operatore"(id)
);

CREATE TABLE IF NOT EXISTS "Operazione" (
    id SERIAL PRIMARY KEY,
    tipoAttuatore VARCHAR(50),
    idAttuatore INT,
    descrizione VARCHAR(100),
    data VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS "Admin"(
    email VARCHAR(50) PRIMARY KEY,
    password VARCHAR(100)
)