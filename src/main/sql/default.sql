-- DA IMPLEMENTARE, è SOLO UN PLACEHOLDER

INSERT INTO "Admin" (email, password, nome, cognome)
VALUES ('elion','123', 'elion', 'karaboja'), ('lorenzo', '123', 'lorenzo', 'cappelletti');

INSERT INTO "Cliente" (nome, cognome, email, password) VALUES
    ('Mario', 'Rossi', 'mario@email.it', '123'),
    ('Luigi', 'Verdi', 'luigi@email.it', '123'),
    ('Giovanna', 'Bianchi', 'giovanna@email.it', '123');

INSERT INTO "Operatore" (nome, cognome, email, password, working)
VALUES ('Alessandro', 'Ferrari', 'ferrari@email.it', '123', false),
       ('Francesca', 'Romano', 'romano@email.it', '123', false),
       ('Matteo', 'Greco', 'greco@email.it', '123', false);

INSERT INTO "Spazio" (id) values (1),(2),(3);

INSERT into "Sensore" (id, tipo, valore, data)
values (1, 'Termometro', 0, ''),
       (2, 'Termometro', 0, ''),
       (3, 'Termometro', 0, ''),
       (4, 'Termometro', 0, ''),
       (5, 'Termometro', 0, ''),
       (6, 'Termometro', 0, ''),
       (7, 'Termometro', 0, ''),
       (8, 'Termometro', 0, ''),
       (9, 'Termometro', 0, ''),
       (10, 'Termometro', 0, ''),
       (11, 'Termometro', 0, ''),
       (12, 'Termometro', 0, ''),
       (13, 'IgrometroAria', 0, ''),
       (14, 'IgrometroAria', 0, ''),
       (15, 'IgrometroAria', 0, ''),
       (16, 'IgrometroAria', 0, ''),
       (17, 'IgrometroAria', 0, ''),
       (18, 'IgrometroAria', 0, ''),
       (19, 'IgrometroAria', 0, ''),
       (20, 'IgrometroAria', 0, ''),
       (21, 'IgrometroAria', 0, ''),
       (22, 'IgrometroAria', 0, ''),
       (23, 'IgrometroAria', 0, ''),
       (24, 'IgrometroAria', 0, ''),
       (25, 'Fotosensore', 0, ''),
       (26, 'Fotosensore', 0, ''),
       (27, 'Fotosensore', 0, ''),
       (28, 'Fotosensore', 0, ''),
       (29, 'Fotosensore', 0, ''),
       (30, 'Fotosensore', 0, ''),
       (31, 'Fotosensore', 0, ''),
       (32, 'Fotosensore', 0, ''),
       (33, 'Fotosensore', 0, ''),
       (34, 'Fotosensore', 0, ''),
       (35, 'Fotosensore', 0, ''),
       (36, 'Fotosensore', 0, ''),
       (37, 'IgrometroTerra', 0, ''),
       (38, 'IgrometroTerra', 0, ''),
       (39, 'IgrometroTerra', 0, ''),
       (40, 'IgrometroTerra', 0, ''),
       (41, 'IgrometroTerra', 0, ''),
       (42, 'IgrometroTerra', 0, ''),
       (43, 'IgrometroTerra', 0, ''),
       (44, 'IgrometroTerra', 0, ''),
       (45, 'IgrometroTerra', 0, ''),
       (46, 'IgrometroTerra', 0, ''),
       (47, 'IgrometroTerra', 0, ''),
       (48, 'IgrometroTerra', 0, ''),
       (49, 'IgrometroTerra', 0, ''),
       (50, 'IgrometroTerra', 0, ''),
       (51, 'IgrometroTerra', 0, ''),
       (52, 'IgrometroTerra', 0, ''),
       (53, 'IgrometroTerra', 0, ''),
       (54, 'IgrometroTerra', 0, ''),
       (55, 'IgrometroTerra', 0, ''),
       (56, 'IgrometroTerra', 0, ''),
       (57, 'IgrometroTerra', 0, ''),
       (58, 'IgrometroTerra', 0, ''),
       (59, 'IgrometroTerra', 0, ''),
       (60, 'IgrometroTerra', 0, ''),
       (61, 'IgrometroTerra', 0, ''),
       (62, 'IgrometroTerra', 0, ''),
       (63, 'IgrometroTerra', 0, ''),
       (64, 'IgrometroTerra', 0, ''),
       (65, 'IgrometroTerra', 0, ''),
       (66, 'IgrometroTerra', 0, ''),
       (67, 'IgrometroTerra', 0, ''),
       (68, 'IgrometroTerra', 0, ''),
       (69, 'IgrometroTerra', 0, ''),
       (70, 'IgrometroTerra', 0, ''),
       (71, 'IgrometroTerra', 0, ''),
       (72, 'IgrometroTerra', 0, ''),
       (73, 'IgrometroTerra', 0, ''),
       (74, 'IgrometroTerra', 0, ''),
       (75, 'IgrometroTerra', 0, ''),
       (76, 'IgrometroTerra', 0, ''),
       (77, 'IgrometroTerra', 0, ''),
       (78, 'IgrometroTerra', 0, ''),
       (79, 'IgrometroTerra', 0, ''),
       (80, 'IgrometroTerra', 0, ''),
       (81, 'IgrometroTerra', 0, ''),
       (82, 'IgrometroTerra', 0, ''),
       (83, 'IgrometroTerra', 0, ''),
       (84, 'IgrometroTerra', 0, ''),
       (85, 'IgrometroTerra', 0, ''),
       (86, 'IgrometroTerra', 0, ''),
       (87, 'IgrometroTerra', 0, ''),
       (88, 'IgrometroTerra', 0, ''),
       (89, 'IgrometroTerra', 0, ''),
       (90, 'IgrometroTerra', 0, ''),
       (91, 'IgrometroTerra', 0, ''),
       (92, 'IgrometroTerra', 0, ''),
       (93, 'IgrometroTerra', 0, ''),
       (94, 'IgrometroTerra', 0, ''),
       (95, 'IgrometroTerra', 0, ''),
       (96, 'IgrometroTerra', 0, '');

UPDATE "Sensore"
SET valore = NULL, data=NULL
WHERE data = '';

INSERT into "Attuatore" (id, tipo, working)
values (1, 'Lampada',  false),
       (2, 'Lampada',  false),
       (3, 'Lampada',  false),
       (4, 'Lampada',  false),
       (5, 'Lampada',  false),
       (6, 'Lampada',  false),
       (7, 'Lampada',  false),
       (8, 'Lampada',  false),
       (9, 'Lampada',  false),
       (10, 'Lampada',  false),
       (11, 'Lampada',  false),
       (12, 'Lampada',  false),
       (13, 'Climatizzatore',  false),
       (14, 'Climatizzatore',  false),
       (15, 'Climatizzatore',  false),
       (16, 'Climatizzatore',  false),
       (17, 'Climatizzatore',  false),
       (18, 'Climatizzatore',  false),
       (19, 'Climatizzatore',  false),
       (20, 'Climatizzatore',  false),
       (21, 'Climatizzatore',  false),
       (22, 'Climatizzatore',  false),
       (23, 'Climatizzatore',  false),
       (24, 'Climatizzatore',  false),
       (25, 'Irrigatore', false),
       (26, 'Irrigatore', false),
       (27, 'Irrigatore', false),
       (28, 'Irrigatore', false),
       (29, 'Irrigatore', false),
       (30, 'Irrigatore', false),
       (31, 'Irrigatore', false),
       (32, 'Irrigatore', false),
       (33, 'Irrigatore', false),
       (34, 'Irrigatore', false),
       (35, 'Irrigatore', false),
       (36, 'Irrigatore', false),
       (37, 'Irrigatore', false),
       (38, 'Irrigatore', false),
       (39, 'Irrigatore', false),
       (40, 'Irrigatore', false),
       (41, 'Irrigatore', false),
       (42, 'Irrigatore', false),
       (43, 'Irrigatore', false),
       (44, 'Irrigatore', false),
       (45, 'Irrigatore', false),
       (46, 'Irrigatore', false),
       (47, 'Irrigatore', false),
       (48, 'Irrigatore', false),
       (49, 'Irrigatore', false),
       (50, 'Irrigatore', false),
       (51, 'Irrigatore', false),
       (52, 'Irrigatore', false),
       (53, 'Irrigatore', false),
       (54, 'Irrigatore', false),
       (55, 'Irrigatore', false),
       (56, 'Irrigatore', false),
       (57, 'Irrigatore', false),
       (58, 'Irrigatore', false),
       (59, 'Irrigatore', false),
       (60, 'Irrigatore', false),
       (61, 'Irrigatore', false),
       (62, 'Irrigatore', false),
       (63, 'Irrigatore', false),
       (64, 'Irrigatore', false),
       (65, 'Irrigatore', false),
       (66, 'Irrigatore', false),
       (67, 'Irrigatore', false),
       (68, 'Irrigatore', false),
       (69, 'Irrigatore', false),
       (70, 'Irrigatore', false),
       (71, 'Irrigatore', false),
       (72, 'Irrigatore', false),
       (73, 'Irrigatore', false),
       (74, 'Irrigatore', false),
       (75, 'Irrigatore', false),
       (76, 'Irrigatore', false),
       (77, 'Irrigatore', false),
       (78, 'Irrigatore', false),
       (79, 'Irrigatore', false),
       (80, 'Irrigatore', false),
       (81, 'Irrigatore', false),
       (82, 'Irrigatore', false),
       (83, 'Irrigatore', false),
       (84, 'Irrigatore', false);

INSERT INTO "Settore" (id, spazio_id, termometro, igrometroAria, fotosensore, lampada, climatizzatore)
values (1, 1, 1, 13, 25, 1, 13),
       (2, 1, 2, 14, 26, 2, 14),
       (3, 1, 3, 15, 27, 3, 15),
       (4, 1, 4, 16, 28, 4, 16),
       (5, 2, 5, 17, 29, 5, 17),
       (6, 2, 6, 18, 30, 6, 18),
       (7, 2, 7, 19, 31, 7, 19),
       (8, 2, 8, 20, 32, 8, 20),
       (9, 3, 9, 21, 33, 9, 21),
       (10, 3, 10, 22, 34, 10, 22),
       (11, 3, 11, 23, 35, 11, 23),
       (12, 3, 12, 24, 36, 12, 24);


INSERT INTO "Posizione" (id, assegnata, occupata, settore, igrometroterra, irrigatore)
values (1, false, false, 1, 37, 25),
       (2, false, false, 1, 38, 26),
       (3, false, false, 1, 39, 27),
       (4, false, false, 1, 40, 28),
       (5, false, false, 1, 41, 29),

       (6, false, false, 2, 42, 30),
       (7, false, false, 2, 43, 31),
       (8, false, false, 2, 44, 32),
       (9, false, false, 2, 45, 33),
       (10, false, false, 2, 46, 34),

       (11, false, false, 3, 47, 35),
       (12, false, false, 3, 48, 36),
       (13, false, false, 3, 49, 37),
       (14, false, false, 3, 50, 38),
       (15, false, false, 3, 51, 39),

       (16, false, false, 4, 52, 40),
       (17, false, false, 4, 53, 41),
       (18, false, false, 4, 54, 42),
       (19, false, false, 4, 55, 43),
       (20, false, false, 4, 56, 44),

       (21, false, false, 5, 57, 45),
       (22, false, false, 5, 58, 46),
       (23, false, false, 5, 59, 47),
       (24, false, false, 5, 60, 48),
       (25, false, false, 5, 61, 49),

       (26, false, false, 6, 62, 50),
       (27, false, false, 6, 63, 51),
       (28, false, false, 6, 64, 52),
       (29, false, false, 6, 65, 53),
       (30, false, false, 6, 66, 54),

       (31, false, false, 7, 67, 55),
       (32, false, false, 7, 68, 56),
       (33, false, false, 7, 69, 57),
       (34, false, false, 7, 70, 58),
       (35, false, false, 7, 71, 59),

       (36, false, false, 8, 72, 60),
       (37, false, false, 8, 73, 61),
       (38, false, false, 8, 74, 62),
       (39, false, false, 8, 75, 63),
       (40, false, false, 8, 76, 64),

       (41, false, false, 9, 77, 65),
       (42, false, false, 9, 78, 66),
       (43, false, false, 9, 79, 67),
       (44, false, false, 9, 80, 68),
       (45, false, false, 9, 81, 69),

       (46, false, false, 10, 82, 70),
       (47, false, false, 10, 83, 71),
       (48, false, false, 10, 84, 72),
       (49, false, false, 10, 85, 73),
       (50, false, false, 10, 86, 74),

       (51, false, false, 11, 87, 75),
       (52, false, false, 11, 88, 76),
       (53, false, false, 11, 89, 77),
       (54, false, false, 11, 90, 78),
       (55, false, false, 11, 91, 79),

       (56, false, false, 12, 92, 80),
       (57, false, false, 12, 93, 81),
       (58, false, false, 12, 94, 82),
       (59, false, false, 12, 95, 83),
       (60, false, false, 12, 96, 84);
