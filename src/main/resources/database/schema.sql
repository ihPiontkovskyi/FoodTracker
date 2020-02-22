DROP TABLE IF EXISTS `meals`;
DROP TABLE IF EXISTS `users`;
DROP TABLE IF EXISTS `records`;
DROP TABLE IF EXISTS `user_goals`;

CREATE TABLE `users`
(
    `id`           int(11)      NOT NULL AUTO_INCREMENT,
    `email`        varchar(320) NOT NULL,
    `password`     char(60)     NOT NULL,
    `first_name`   varchar(32)  NOT NULL,
    `last_name`    varchar(32) DEFAULT NULL,
    `height`       int(11)      NOT NULL,
    `weight`       int(11)      NOT NULL,
    `birthday`     date         NOT NULL,
    `gender`       varchar(255) NOT NULL,
    `user_goal_id` int(11)      NOT NULL,
    `lifestyle`    varchar(255) NOT NULL,
    `role`         varchar(255) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `users_email_uindex` (`email`),
    KEY `users_user_goals_id_fk` (`user_goal_id`),
    CONSTRAINT `users_user_goals_id_fk` FOREIGN KEY (`user_goal_id`) REFERENCES `user_goals` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE `user_goals`
(
    `id`                 int(11) NOT NULL AUTO_INCREMENT,
    `daily_energy`       int(11) NOT NULL,
    `daily_fat`          int(11) NOT NULL,
    `daily_protein`      int(11) NOT NULL,
    `daily_water`        int(11) NOT NULL,
    `daily_carbohydrate` int(11) NOT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE `meals`
(
    `id`           int(11)     NOT NULL AUTO_INCREMENT,
    `user_id`      int(11) DEFAULT NULL,
    `fat`          int(11)     NOT NULL,
    `protein`      int(11) DEFAULT NULL,
    `carbohydrate` int(11)     NOT NULL,
    `water`        int(11)     NOT NULL,
    `weight`       int(11)     NOT NULL,
    `name`         varchar(32) NOT NULL,
    PRIMARY KEY (`id`),
    KEY `meals_users_id_fk` (`user_id`),
    CONSTRAINT `meals_users_id_fk` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE `records`
(
    `id`      int(11) NOT NULL AUTO_INCREMENT,
    `meal_id` int(11) NOT NULL,
    `date`    date    NOT NULL,
    `user_id` int(11) NOT NULL,
    `weight`  int(11) NOT NULL,
    PRIMARY KEY (`id`),
    KEY `records_meals_id_fk` (`meal_id`),
    KEY `records_users_id_fk` (`user_id`),
    CONSTRAINT `records_meals_id_fk` FOREIGN KEY (`meal_id`) REFERENCES `meals` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT `records_users_id_fk` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
);
