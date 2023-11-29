CREATE TABLE `roles` (
  `role_id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  PRIMARY KEY (`role_id`)) ;
INSERT INTO `supercoding_1st`.`roles` (`role_id`, `name`) VALUES ('1', 'ROLE_ADMIN');
INSERT INTO `supercoding_1st`.`roles` (`role_id`, `name`) VALUES ('2', 'ROLE_USER');

CREATE TABLE `supercoding_1st`.`user_roles` (
  `user_role_id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NOT NULL,
  `role_id` INT NOT NULL,
  PRIMARY KEY (`user_role_id`),
  FOREIGN KEY (user_id) REFERENCES users(user_id),
  FOREIGN KEY (role_id) REFERENCES roles(role_id)
  );