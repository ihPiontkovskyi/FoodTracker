INSERT INTO `user_goals`
VALUES (21, 2277, 42, 94, 2000, 379),
       (22, 2681, 49, 111, 2000, 446),
       (23, 3243, 60, 135, 2000, 540),
       (24, 2138, 39, 89, 2000, 356);

INSERT INTO `users`
VALUES (7, 'admin@mail.com', '$2a$10$NxW3cyRxP33QWbEeAUu2b.QSShHLyYHKtUHrkG5vyISuZzLXksMTa', 'Ivan', 'Ivanov',
        183, 78, '1999-12-11', 'MALE', 21, 'SEDENTARY', 'ADMIN'),
       (8, 'user@mail.com', '$2a$10$DeAVHwkwXGfxi0v1z0owN.b/otxt2iSyYwunnieDXQ.r42RrKWtg.', ' Петр', 'Петров',
        190, 78, '2001-02-01', 'OTHER', 22, 'ACTIVE', 'USER'),
       (10, 'mail@mail.com', '$2a$10$5T4IV94jpleRYOgXP880/.qAH7dSNHSf/y2O0.WKQ46AQ6GYSpggS', 'Maxim', 'Maximov',
        180, 70, '2001-01-26', 'MALE', 24, 'SEDENTARY', 'USER');

INSERT INTO `meals`
VALUES (95, NULL, 6, 10, 60, 0, 100, 'pizza'),
       (96, NULL, 6, 4, 12, 20, 100, 'soup'),
       (97, NULL, 7, 0, 76, 10, 100, 'гречка'),
       (98, NULL, 8, 6, 32, 0, 100, 'блины'),
       (99, 8, 10, 3, 60, 5, 100, 'cake'),
       (102, 8, 1, 3, 15, 0, 100, 'fried chicken'),
       (103, NULL, 2, 5, 17, 0, 100, 'fried potato'),
       (104, 8, 3, 7, 13, 0, 100, 'boiled potato'),
       (105, NULL, 4, 9, 14, 0, 100, 'Солянка'),
       (106, NULL, 5, 11, 15, 0, 100, 'Вареники'),
       (107, NULL, 7, 2, 7, 0, 100, 'Овсянка'),
       (112, NULL, 6, 10, 60, 0, 100, 'peperoni');

INSERT INTO `records`
VALUES (7, 95, '2020-02-01', 7, 100),
       (8, 98, '2020-02-02', 7, 100),
       (9, 96, '2019-01-31', 7, 200),
       (10, 97, '2020-02-01', 7, 300),
       (11, 96, '2020-02-02', 7, 100),
       (12, 95, '2020-02-03', 7, 200),
       (14, 97, '2020-02-03', 7, 100),
       (17, 96, '2020-02-03', 7, 100),
       (25, 95, '2020-02-17', 7, 350),
       (26, 95, '2020-02-17', 7, 200),
       (27, 96, '2020-02-17', 7, 100),
       (28, 96, '2020-02-17', 7, 200),
       (30, 95, '2020-02-19', 7, 500),
       (31, 96, '2020-02-19', 7, 200),
       (32, 95, '2020-02-20', 7, 1200);