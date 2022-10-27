INSERT INTO `access_role`(`name`) VALUES ('ROLE_USER'),('ROLE_ADMIN');

INSERT INTO `permission`(`permission_code`, `description`) VALUES ('PERMISSION_READ', 'allows request your data'),
                                                                 ('PERMISSION_CREATE', 'allows insert records with your data into database'),
                                                                 ('PERMISSION_UPDATE', 'allows update your data in database'),
                                                                 ('PERMISSION_DELETE', 'allows delete records with your data from database'),
                                                                 ('PERMISSION_ADMIN_READ', 'allows request all date'),
                                                                 ('PERMISSION_ADMIN_CREATE', 'allows insert records with any data into database'),
                                                                 ('PERMISSION_ADMIN_UPDATE', 'allows update any data in database'),
                                                                 ('PERMISSION_ADMIN_DELETE', 'allows delete records with any data from database');

INSERT INTO `role_permission` VALUES (1,1),(1,2),(1,3),(1,4),(2,5),(2,6),(2,7),(2,8);

INSERT INTO `user`(`password`,`username`,`first_name`,`last_name`) VALUES ('$2y$12$5MSuzsSGphzGWXo2DecK.u8tgVfYcWs2wHcwg.CbqUDwM.jwaNrFG', 'superAdmin','Alex', 'Danylin'),
                                                                        ('$2y$12$kl1HBYDmGJW05yDN5pC.7.J75SsvDgAIRESU0Qa2igaCksQcBos0y', 'superUser', 'Volodymyr', 'Zelensky');

INSERT INTO `user_role` VALUES (1,2),(2,1);

INSERT INTO `room`(`number`) VALUES (1),(2),(3);
