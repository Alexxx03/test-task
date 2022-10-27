--
-- Table structure for table `access_role`
--

CREATE TABLE IF NOT EXISTS `access_role` (
                                            `access_role_id` bigint(10) unsigned NOT NULL AUTO_INCREMENT,
                                            `name` varchar(255) NOT NULL,
                                            PRIMARY KEY (`access_role_id`)
                                         ) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

--
-- Table structure for table `permission`
--

CREATE TABLE IF NOT EXISTS `permission` (
                                           `permission_id` bigint(10) unsigned NOT NULL AUTO_INCREMENT,
                                           `permission_code` varchar(50) NOT NULL,
                                           `description` varchar(250) DEFAULT NULL,
                                           PRIMARY KEY (`permission_id`),
                                           UNIQUE KEY `IDX_PERMISSION_CODE` (`permission_code`)
                                        ) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

--
-- Table structure for table `role_permission`
--

CREATE TABLE IF NOT EXISTS `role_permission` (
                                                `access_role_id` bigint(10) unsigned NOT NULL,
                                                `permission_id` bigint(10) unsigned NOT NULL,
                                                PRIMARY KEY (`access_role_id`,`permission_id`),
                                                KEY `FK_RolePermission_Role` (`access_role_id`),
                                                KEY `FK_r9n3d4e7gtp6tyu35re8a848d` (`permission_id`),
                                                CONSTRAINT `FK_h8wdxi8j2kqopb560khqu1tlm` FOREIGN KEY (`access_role_id`) REFERENCES `access_role` (`access_role_id`),
                                                CONSTRAINT `FK_r9n3d4e7gtp6tyu35re8a848d` FOREIGN KEY (`permission_id`) REFERENCES `permission` (`permission_id`)
                                             ) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

--
-- Table structure for table `user`
--

CREATE TABLE IF NOT EXISTS `user` (
                                     `id` bigint(10) unsigned NOT NULL AUTO_INCREMENT,
                                     `password` varchar(255) NOT NULL,
                                     `username` varchar(50),
                                     `first_name` varchar(50) NOT NULL,
                                     `last_name` varchar(50) NOT NULL,
                                     PRIMARY KEY (`id`),
                                     UNIQUE KEY `IDX_username` (`username`)
                                  ) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

--
-- Table structure for table `user_role`
--


CREATE TABLE IF NOT EXISTS `user_role` (
                                           `user_id` bigint(10) unsigned NOT NULL,
                                           `access_role_id` bigint(10) unsigned NOT NULL,
                                           PRIMARY KEY (`user_id`,`access_role_id`),
                                           KEY `FK_user_role_user` (`user_id`),
                                           KEY `FK_7lhdkgkagcqeq1e1xhwnq4xj4` (`access_role_id`),
                                           CONSTRAINT `FK_7lhdkgkagcqeq1e1xhwnq4xj4` FOREIGN KEY (`access_role_id`) REFERENCES `access_role` (`access_role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

--
-- Table structure for table `room`
--

CREATE TABLE IF NOT EXISTS `room` (
                                      `id` bigint(10) unsigned NOT NULL AUTO_INCREMENT,
                                      `number` bigint(10) unsigned NOT NULL,
                                      PRIMARY KEY (`id`),
                                      UNIQUE KEY `IDX_number` (`number`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

--
-- Table structure for table `booking`
--

CREATE TABLE IF NOT EXISTS `booking` (
                                        `id` bigint(10) unsigned NOT NULL  AUTO_INCREMENT,
                                        `weekday` enum('MONDAY','TUESDAY','WEDNESDAY','THURSDAY','FRIDAY') NOT NULL,
                                        `start_time` int unsigned NOT NULL,
                                        `end_time` int unsigned NOT NULL,
                                        `date` date NOT NULL,
                                        `user_id` bigint(10) unsigned,
                                        `room_id` bigint(10) unsigned,
                                        PRIMARY KEY (`id`),
                                        CONSTRAINT `FK_userId` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
                                        CONSTRAINT `FK_roomId` FOREIGN KEY (`room_id`) REFERENCES `room` (`id`)
                                      ) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;