-- DA IMPLEMENTARE, è SOLO UN PLACEHOLDER

INSERT INTO "Admin" (email, password)
VALUES ('elion','123'), ('lorenzo', '123');


--Insert default data into the "Pianta" table
--INSERT INTO "Pianta" (tipo) VALUES
--    ('Pianta A'),
--    ('Pianta B'),
--    ('Pianta C');

-- Insert default data into the "Cliente" table
INSERT INTO "Cliente" (nome, cognome, email, password) VALUES
    ('Mario', 'Rossi', 'mario@email.it', '123'),
    ('Luigi', 'Verdi', 'luigi@email.it', '123'),
    ('Giovanna', 'Bianchi', 'giovanna@email.it', '123');

-- Insert default data into the "Operatore" table
INSERT INTO "Operatore" (occupato) VALUES
    (false),
    (false),
    (false);

INSERT into "Termometro" (id) values
    (1),
    (2);

INSERT into "IgrometroAria" (id) values
                                  (1),
                                  (2);
INSERT into "IgrometroTerreno" (id) values
                                  (1),
                                  (2),
                                  (3);
INSERT into "Fotosensore" (id) values
                                  (1),
                                  (2);
INSERT into "Lampada" (id, acceso) values
                                  (1, false),
                                  (2, false);
INSERT into "Irrigatore" (id, acceso) values
                                       (1, false),
                                       (2, false),
                                       (3, false);
INSERT into "Climatizzatore" (id, acceso) values
                                       (1, false),
                                       (2, false);

INSERT INTO "Spazio" (id, nome, descrizione, nambientimax) values
          (1, 'primo', 'primo spazio', 4),
          (2, 'secondo', 'secondo spazio', 4);

INSERT INTO "Ambiente" (spazio_id, nposizionimax, termometro, fotosensore,
                      climatizzazione, lampada, igrometroaria)
values (1, 5, 1, 1, 1, 1, 1);

INSERT INTO "Posizione" (assegnata, ambiente, irriatore, igrometroterreno)
values (false, 1, 1, 1), (false, 1, 2, 2), (false, 1, 3, 3);

-- Insert default data into the "Ambiente" table
--INSERT INTO "Posizione" (assegnata) VALUES
  --  (false),
    --(false),
    --(true);

-- You can add more INSERT statements for other tables as needed
