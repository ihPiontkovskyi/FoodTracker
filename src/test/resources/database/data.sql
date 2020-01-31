INSERT INTO `user_goals`
VALUES (1, 2600, 40, 55, 2200, 180),
       (2, 2300, 40, 65, 2000, 160);

INSERT INTO `users`
VALUES (1, 'admin', '$2y$12$GNV6r.PPoouP9hZ354r3qu9TOPFJofCgN50toXf4ll9S.jyIyqrg6', 'Ihor', 'Piontkovskyi', 182, 78,
        '1999-12-11', 1, 2, 2, 2),
       (2, 'user@mail', '$2y$12$.obmNC3vIgT1XGBbfJnHeeD1A5aRw/JiTi.hzmAVuZbcj8X3dGr/6', 'Ivan', 'Ivanov', 190, 82,
        '1994-01-29', 1, 1, 1, 1);

INSERT INTO `meals`
VALUES (1, 1, 6, 8, 60, 0, 100, 'cereals'),
       (2, 1, 14, 20, 32, 0, 100, 'cheese'),
       (3, 2, 2, 1, 6, 30, 100, 'borshch'),
       (4, 2, 1, 2, 5, 35, 100, 'суп овочевий');
INSERT INTO `records`
VALUES (1, 1, '2020-01-28'),
       (2, 2, '2020-01-28'),
       (3, 3, '2020-01-27'),
       (4, 4, '2020-01-27'),
       (5, 2, '2020-01-29'),
       (6, 4, '2020-01-28');
