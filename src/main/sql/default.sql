-- DA IMPLEMENTARE, è SOLO UN PLACEHOLDER

INSERT INTO "Admin" (email, password)
VALUES ('elion','123'), ('lorenzo', '123');

INSERT INTO "Cliente" (nome, cognome, email, password) VALUES
    ('Mario', 'Rossi', 'mario@email.it', '123'),
    ('Luigi', 'Verdi', 'luigi@email.it', '123'),
    ('Giovanna', 'Bianchi', 'giovanna@email.it', '123');

INSERT INTO "Operatore" (nome, cognome, email, password, working)
VALUES ('Alessandro', 'Ferrari', 'ferrari@email.it', '123', false),
       ('Francesca', 'Romano', 'romano@email.it', '123', false),
       ('Matteo', 'Greco', 'greco@email.it', '123', false);

INSERT into "Termometro" (id)
values (1), (2),(3),(4),(5),(6),
       (7),(8),(9),(10),(11),(12);

INSERT into "IgrometroAria" (id)
values (1), (2),(3),(4),(5),(6),
       (7),(8),(9),(10),(11),(12);

INSERT into "Fotosensore" (id)
values (1), (2),(3),(4),(5),(6),
       (7),(8),(9),(10),(11),(12);

INSERT into "Lampada" (id, working)
values (1, false), (2, false), (3, false),
       (4, false), (5, false), (6, false),
       (7, false), (8, false), (9, false),
       (10, false), (11, false), (12, false);

INSERT into "Climatizzazione" (id, working)
values (1, false), (2, false), (3, false),
       (4, false), (5, false), (6, false),
       (7, false), (8, false), (9, false),
       (10, false), (11, false), (12, false);

INSERT INTO "Spazio" (id) values (1),(2),(3);

INSERT INTO "Settore" (id, spazio_id, termometro, fotosensore, climatizzazione, lampada, igrometroAria)
values (1, 1, 1, 1, 1, 1, 1),
       (2, 1, 2, 2, 2, 2, 2),
       (3, 1, 3, 3, 3, 3, 3),
       (4, 1, 4, 4, 4, 4, 4),
       (5, 2,5, 5, 5, 5, 5),
       (6, 2, 6, 6, 6, 6, 6),
       (7, 2, 7, 7, 7, 7, 7),
       (8, 2, 8, 8, 8, 8, 8),
       (9, 3, 9, 9, 9, 9, 9),
       (10, 3, 10, 10, 10, 10, 10),
       (11, 3, 11, 11, 11, 11, 11),
       (12, 3, 12,12 , 12, 12, 12);

INSERT into "IgrometroTerreno" (id)
values
    (1), (2), (3), (4), (5), (6), (7), (8), (9), (10),
    (11), (12), (13), (14), (15), (16), (17), (18), (19), (20),
    (21), (22), (23), (24), (25), (26), (27), (28), (29), (30),
    (31), (32), (33), (34), (35), (36), (37), (38), (39), (40),
    (41), (42), (43), (44), (45), (46), (47), (48), (49), (50),
    (51), (52), (53), (54), (55), (56), (57), (58), (59), (60);

INSERT into "Irrigatore" (id, working)
values
    (1, false), (2, false), (3, false), (4, false), (5, false), (6, false),
    (7, false), (8, false), (9, false), (10, false), (11, false), (12, false),
    (13, false), (14, false), (15, false), (16, false), (17, false), (18, false),
    (19, false), (20, false), (21, false), (22, false), (23, false), (24, false),
    (25, false), (26, false), (27, false), (28, false), (29, false), (30, false),
    (31, false), (32, false), (33, false), (34, false), (35, false), (36, false),
    (37, false), (38, false), (39, false), (40, false), (41, false), (42, false),
    (43, false), (44, false), (45, false), (46, false), (47, false), (48, false),
    (49, false), (50, false), (51, false), (52, false), (53, false), (54, false),
    (55, false), (56, false), (57, false), (58, false), (59, false), (60, false);


INSERT INTO "Posizione" (id, assegnata, occupata, settore, irrigatore, igrometroterreno)
values
    (1, false, false, 1, 1, 1),
    (2, false, false, 1, 2, 2),
    (3, false, false, 1, 3, 3),
    (4, false, false, 1, 4, 4),
    (5, false, false, 1, 5, 5),

    (6, false, false, 2, 6, 6),
    (7, false, false, 2, 7, 7),
    (8, false, false, 2, 8, 8),
    (9, false, false, 2, 9, 9),
    (10, false, false, 2, 10, 10),

    (11, false, false, 3, 11, 11),
    (12, false, false, 3, 12, 12),
    (13, false, false, 3, 13, 13),
    (14, false, false, 3, 14, 14),
    (15, false, false, 3, 15, 15),

    (16, false, false, 4, 16, 16),
    (17, false, false, 4, 17, 17),
    (18, false, false, 4, 18, 18),
    (19, false, false, 4, 19, 19),
    (20, false, false, 4, 20, 20),

    (21, false, false, 5, 21, 21),
    (22, false, false, 5, 22, 22),
    (23, false, false, 5, 23, 23),
    (24, false, false, 5, 24, 24),
    (25, false, false, 5, 25, 25),

    (26, false, false, 6, 26, 26),
    (27, false, false, 6, 27, 27),
    (28, false, false, 6, 28, 28),
    (29, false, false, 6, 29, 29),
    (30, false, false, 6, 30, 30),

    (31, false, false, 7, 31, 31),
    (32, false, false, 7, 32, 32),
    (33, false, false, 7, 33, 33),
    (34, false, false, 7, 34, 34),
    (35, false, false, 7, 35, 35),

    (36, false, false, 8, 36, 36),
    (37, false, false, 8, 37, 37),
    (38, false, false, 8, 38, 38),
    (39, false, false, 8, 39, 39),
    (40, false, false, 8, 40, 40),

    (41, false, false, 9, 41, 41),
    (42, false, false, 9, 42, 42),
    (43, false, false, 9, 43, 43),
    (44, false, false, 9, 44, 44),
    (45, false, false, 9, 45, 45),

    (46, false, false, 10, 46, 46),
    (47, false, false, 10, 47, 47),
    (48, false, false, 10, 48, 48),
    (49, false, false, 10, 49, 49),
    (50, false, false, 10, 50, 50),

    (51, false, false, 11, 51, 51),
    (52, false, false, 11, 52, 52),
    (53, false, false, 11, 53, 53),
    (54, false, false, 11, 54, 54),
    (55, false, false, 11, 55, 55),

    (56, false, false, 12, 56, 56),
    (57, false, false, 12, 57, 57),
    (58, false, false, 12, 58, 58),
    (59, false, false, 12, 59, 59),
    (60, false, false, 12, 60, 60);



