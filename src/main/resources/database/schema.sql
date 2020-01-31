DROP TABLE IF EXISTS `meals`;
DROP TABLE IF EXISTS `records`;
DROP TABLE IF EXISTS `user_goals`;
DROP TABLE IF EXISTS `users`;

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
    `gender`       int(11)      NOT NULL,
    `user_goal_id` int(11)      NOT NULL,
    `lifestyle`    int(11)      NOT NULL,
    `role`         int(11)      NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `users_email_uindex` (`email`),
    CONSTRAINT `users_user_goals_id_fk` FOREIGN KEY (`user_goal_id`) REFERENCES `user_goals` (`id`) ON UPDATE CASCADE
);

CREATE TABLE `meals`
(
    `id`           int(11)     NOT NULL AUTO_INCREMENT,
    `user_id`      int(11)     NOT NULL,
    `fat`          int(11)     NOT NULL,
    `protein`      int(11)     NOT NULL,
    `carbohydrate` int(11)     NOT NULL,
    `water`        int(11)     NOT NULL,
    `weight`       int(11)     NOT NULL,
    `name`         varchar(32) NOT NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT `meals_users_id_fk` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE `records`
(
    `id`      int(11) NOT NULL AUTO_INCREMENT,
    `meal_id` int(11) NOT NULL,
    `date`    date    NOT NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT `records_meals_id_fk` FOREIGN KEY (`meal_id`) REFERENCES `meals` (`id`) ON DELETE CASCADE ON UPDATE CASCADE);


