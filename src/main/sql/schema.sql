-- table generation



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
    working BOOLEAN
);

CREATE TABLE IF NOT EXISTS "Fotosensore" (
    id INT PRIMARY KEY,
    perc_luce INT,
    data VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS "Lampada" (
    id INT PRIMARY KEY,
    working BOOLEAN
);

CREATE TABLE IF NOT EXISTS "Climatizzazione" (
    id INT PRIMARY KEY,
    working BOOLEAN,
    temperaturaRichiesta INT
);

CREATE TABLE IF NOT EXISTS "Ordine" (
    id SERIAL PRIMARY KEY,
    cliente INT,
    dataConsegna VARCHAR(50),
    piante VARCHAR(300),
    totale DECIMAL(6,2),
    stato VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS "Pianta" (
    id SERIAL PRIMARY KEY ,
    tipo VARCHAR(50),
    descrizione VARCHAR(200),
    dataInizio VARCHAR(50),
    stato VARCHAR(100),
    costo DECIMAL(10, 2),
    ordine INT REFERENCES "Ordine"(id)
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
    nome VARCHAR(50),
    cognome VARCHAR(50),
    email VARCHAR(50),
    password varchar(50),
    working BOOLEAN,
    ruolo VARCHAR(50)
                                      );


CREATE TABLE IF NOT EXISTS "Spazio" (
    id INT PRIMARY KEY
                                    );

CREATE TABLE IF NOT EXISTS "Settore" (
    id INT PRIMARY KEY,
    spazio_id INT,
    termometro INT,
    fotosensore INT,
    climatizzazione INT,
    lampada INT,
    igrometroAria INT,
    FOREIGN KEY (spazio_id) REFERENCES "Spazio"(id),
    FOREIGN KEY (termometro) REFERENCES  "Termometro"(id),
    FOREIGN KEY (fotosensore) REFERENCES "Fotosensore"(id),
    FOREIGN KEY (climatizzazione) REFERENCES  "Climatizzazione"(id),
    FOREIGN KEY (lampada) REFERENCES "Lampada"(id),
    FOREIGN KEY (igrometroAria) REFERENCES "IgrometroAria"(id)
);

CREATE TABLE IF NOT EXISTS "Posizione" (
    id INT PRIMARY KEY,
    assegnata BOOLEAN,
    occupata BOOLEAN,
    settore INT,
    irrigatore INT,
    igrometroTerreno INT,
    FOREIGN KEY (settore) REFERENCES  "Settore"(id),
    FOREIGN KEY (irrigatore) REFERENCES  "Irrigatore"(id),
    FOREIGN KEY (igrometroTerreno) REFERENCES  "IgrometroTerreno"(id)
);

CREATE TABLE IF NOT EXISTS "Posizionamento" (
    id SERIAL PRIMARY KEY,
    pianta INT,
    posizione INT,
    ordine INT,
    operatore int,
    FOREIGN KEY  (pianta) REFERENCES "Pianta"(id),
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
    password VARCHAR(100),
    nome VARCHAR(50),
    cognome VARCHAR(50)
)