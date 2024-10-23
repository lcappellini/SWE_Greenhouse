-- table generation

CREATE TABLE IF NOT EXISTS "Cliente" (
                                         id SERIAL PRIMARY KEY,
                                         nome VARCHAR(50),
                                         cognome VARCHAR(50),
                                         email VARCHAR(50),
                                         password varchar(50)
);
CREATE TABLE IF NOT EXISTS "Ordine" (
                                        id SERIAL PRIMARY KEY,
                                        cliente INT,
                                        dataConsegna VARCHAR(50),
                                        piante VARCHAR(300),
                                        totale DECIMAL(6,2),
                                        stato VARCHAR(50),
                                        FOREIGN KEY (cliente) REFERENCES "Cliente"(id)
);
CREATE TABLE IF NOT EXISTS "Pianta" (
                                        id SERIAL PRIMARY KEY ,
                                        tipo VARCHAR(50),
                                        descrizione VARCHAR(200),
                                        dataInizio VARCHAR(50),
                                        stato VARCHAR(100),
                                        costo DECIMAL(10, 2),
                                        ordine int references "Ordine"(id)
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
CREATE TABLE IF NOT EXISTS "Sensore"(
                                        id INT PRIMARY KEY,
                                        tipo VARCHAR(50),
                                        valore DECIMAL(6,1),
                                        data VARCHAR(50)
);
CREATE TABLE IF NOT EXISTS "Attuatore"(
                                          id INT PRIMARY KEY,
                                          tipo VARCHAR(50),
                                          working BOOLEAN
);
CREATE TABLE IF NOT EXISTS "Settore" (
                                         id INT PRIMARY KEY,
                                         spazio_id INT,
                                         termometro INT,
                                         igrometroAria INT,
                                         fotosensore INT,
                                         lampada INT,
                                         climatizzatore INT,
                                         FOREIGN KEY (spazio_id) REFERENCES "Spazio"(id),
                                         FOREIGN KEY (termometro) REFERENCES  "Sensore"(id),
                                         FOREIGN KEY (fotosensore) REFERENCES "Sensore"(id),
                                         FOREIGN KEY (igrometroAria) REFERENCES "Sensore"(id),
                                         FOREIGN KEY (climatizzatore) REFERENCES  "Attuatore"(id),
                                         FOREIGN KEY (lampada) REFERENCES "Attuatore"(id)
);
CREATE TABLE IF NOT EXISTS "Posizione" (
                                           id INT PRIMARY KEY,
                                           assegnata BOOLEAN,
                                           occupata BOOLEAN,
                                           settore INT,
                                           irrigatore INT,
                                           igrometroterra INT,
                                           FOREIGN KEY (settore) REFERENCES  "Settore"(id),
                                           FOREIGN KEY (igrometroterra) REFERENCES  "Sensore"(id),
                                           FOREIGN KEY (irrigatore) REFERENCES  "Attuatore"(id)
);
CREATE TABLE IF NOT EXISTS "Posizionamento" (
                                                id SERIAL PRIMARY KEY,
                                                pianta INT,
                                                posizione INT,
                                                ordine INT,
                                                FOREIGN KEY  (pianta) REFERENCES "Pianta"(id),
                                                FOREIGN KEY (posizione) REFERENCES  "Posizione"(id),
                                                FOREIGN KEY (ordine) REFERENCES  "Ordine"(id)
);
CREATE TABLE IF NOT EXISTS "Operazione" (
                                            id SERIAL PRIMARY KEY,
                                            tipoAttuatore VARCHAR(50),
                                            idAttuatore INT,
                                            descrizione VARCHAR(100),
                                            data VARCHAR(50)
);
CREATE TABLE IF NOT EXISTS "Admin"(
                                    id SERIAL PRIMARY KEY,
                                    email VARCHAR(50),
                                    password VARCHAR(100),
                                    nome VARCHAR(50),
                                    cognome VARCHAR(50)
);
