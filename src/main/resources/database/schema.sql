DROP TABLE IF EXISTS `genders`;
DROP TABLE IF EXISTS `lifestyles`;
DROP TABLE IF EXISTS `roles`;
DROP TABLE IF EXISTS `user_goals`;
DROP TABLE IF EXISTS `users`;
DROP TABLE IF EXISTS `meals`;
DROP TABLE IF EXISTS `records`;

CREATE TABLE `genders`
(
    `id`   int(11)     NOT NULL AUTO_INCREMENT,
    `name` varchar(32) NOT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE `lifestyles`
(
    `id`                 int(11)      NOT NULL AUTO_INCREMENT,
    `name`               varchar(32)  NOT NULL,
    `description`        varchar(128) NOT NULL,
    `energy_coefficient` double       NOT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE `roles`
(
    `id`   int(11)     NOT NULL AUTO_INCREMENT,
    `name` varchar(32) NOT NULL,
    PRIMARY KEY (`id`)
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

CREATE TABLE `users`
(
    `id`           int(11)      NOT NULL AUTO_INCREMENT,
    `email`        varchar(320) NOT NULL,
    `password`     varchar(255) NOT NULL,
    `first_name`   varchar(32)  NOT NULL,
    `last_name`    varchar(32)  NOT NULL,
    `height`       int(11)      NOT NULL,
    `weight`       int(11)      NOT NULL,
    `birthday`     date         NOT NULL,
    `gender_id`    int(11)      NOT NULL,
    `user_goal_id` int(11)      NOT NULL,
    `lifestyle_id` int(11)      NOT NULL,
    `role_id`      int(11)      NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `users_email_uindex` (`email`),
    KEY `users_genders_id_fk` (`gender_id`),
    KEY `users_lifestyles_id_fk` (`lifestyle_id`),
    KEY `users_roles_id_fk` (`role_id`),
    KEY `users_user_goals_id_fk` (`user_goal_id`),
    CONSTRAINT `users_genders_id_fk` FOREIGN KEY (`gender_id`) REFERENCES `genders` (`id`) ON UPDATE CASCADE,
    CONSTRAINT `users_lifestyles_id_fk` FOREIGN KEY (`lifestyle_id`) REFERENCES `lifestyles` (`id`) ON UPDATE CASCADE,
    CONSTRAINT `users_roles_id_fk` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`) ON UPDATE CASCADE,
    CONSTRAINT `users_user_goals_id_fk` FOREIGN KEY (`user_goal_id`) REFERENCES `user_goals` (`id`) ON UPDATE CASCADE
);

CREATE TABLE `meals`
(
    `id`           int(11) NOT NULL AUTO_INCREMENT,
    `user_id`      int(11) NOT NULL,
    `fat`          int(11) NOT NULL,
    `protein`      int(11) DEFAULT NULL,
    `carbohydrate` int(11) NOT NULL,
    `water`        int(11) NOT NULL,
    `weight`       int(11) NOT NULL,
    PRIMARY KEY (`id`),
    KEY `meals_users_id_fk` (`user_id`),
    CONSTRAINT `meals_users_id_fk` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE `records`
(
    `id`      int(11) NOT NULL AUTO_INCREMENT,
    `user_id` int(11) NOT NULL,
    `meal_id` int(11) NOT NULL,
    `date`    date    NOT NULL,
    PRIMARY KEY (`id`),
    KEY `records_meals_id_fk` (`meal_id`),
    KEY `records_users_id_fk` (`user_id`),
    CONSTRAINT `records_meals_id_fk` FOREIGN KEY (`meal_id`) REFERENCES `meals` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT `records_users_id_fk` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
);