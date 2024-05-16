-- table generation

CREATE TABLE IF NOT EXISTS "Pianta" (
    id SERIAL PRIMARY KEY,
    tipo VARCHAR(50) PRIMARY KEY,
    );

CREATE TABLE IF NOT EXISTS "Commissione" (
    id SERIAL PRIMARY KEY,
    FOREIGN KEY (pianta) REFERENCES "Pianta"(id),
    FOREIGN KEY (cliente) REFERENCEs "Cliente"(id),
    creazione VARCHAR(100),
    );

CREATE TABLE IF NOT EXISTS "Richiesta"(
    id SERIAL PRIMARY KEY,
    FOREIGN KEY (cliente) REFERENCES "Cliente"(id),
    FOREIGN KEY (pianta) REFERENCES "Pianta"(tipo),

)

CREATE TABLE IF NOT EXISTS "Cliente" (
    id SERIAL PRIMARY KEY,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    creditCard VARCHAR(50) UNIQUE,
    payPal INTEGER UNIQUE,
    FOREIGN KEY (creditCard) REFERENCES "CreditCard"(cardNumber),
    FOREIGN KEY (payPal) REFERENCES "PayPal"(uniqueCode),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );

CREATE TABLE IF NOT EXISTS "Posizione" (
    id SERIAL PRIMARY KEY,
    FOREIGN KEY (area) REFERENCES "Spazio"(id),
    FOREIGN KEY ()
)

CREATE TABLE IF NOT EXIST "Spazio" (
    id SERIAL PRIMARY KEY
    FOREIGN KEY (temperatura) REFERENCES "Termometro"(temp),

)

CREATE TABLE IF NOT EXISTS "Posizionamento" (
    code SERIAL PRIMARY KEY,
    FOREIGN KEY (pianta) REFERENCES "Pianta"(id),
    FOREIGN KEY (commissione) REFERENCES "Commissione"(id),
    FOREIGN KEY (posizione) REFERENCES "Posizione"(id)
    );
