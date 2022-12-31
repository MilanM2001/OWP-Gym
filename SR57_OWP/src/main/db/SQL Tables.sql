DROP SCHEMA sr57owp;
CREATE SCHEMA sr57owp DEFAULT CHARACTER SET utf8;
USE sr57owp;

CREATE TABLE users (
  id bigint NOT NULL AUTO_INCREMENT,
  userName varchar(20) NOT NULL,
  password varchar(20) NOT NULL,
  email varchar(20) NOT NULL,
  firstName varchar(20) NOT NULL,
  lastName varchar(20) NOT NULL,
  dateOfBirth date DEFAULT NULL,
  address varchar(20) NOT NULL,
  phoneNumber varchar(20) NOT NULL,
  dateOfRegistration date DEFAULT NULL,
  userRole enum('Administrator','Member') NOT NULL,
  active tinyint(1) NOT NULL,
  PRIMARY KEY (id)
);
CREATE TABLE comments (
  id bigint NOT NULL AUTO_INCREMENT,
  commentText varchar(50) NOT NULL,
  commentGrade int NOT NULL,
  dateOfCreation date DEFAULT NULL,
  username varchar(45) NOT NULL,
  workoutId bigint NOT NULL,
  commentStatus enum('Waiting','Accepted','Denied') NOT NULL,
  anonymous tinyint(1) NOT NULL,
  PRIMARY KEY (id),
  KEY workoutID (workoutId),
  CONSTRAINT comments_ibfk_1 FOREIGN KEY (workoutId) REFERENCES workouts (id) ON DELETE CASCADE
);
CREATE TABLE halls (
  id bigint NOT NULL AUTO_INCREMENT,
  hallLabel varchar(20) NOT NULL,
  capacity int NOT NULL,
  PRIMARY KEY (id)
);
CREATE TABLE membershipcards (
  id bigint NOT NULL AUTO_INCREMENT,
  discount int NOT NULL,
  cardPoints double NOT NULL,
  cardUsername varchar(20) NOT NULL,
  cardStatus enum('Waiting','Accepted','Denied') NOT NULL,
  PRIMARY KEY (id)
);
CREATE TABLE trainers (
  id bigint NOT NULL AUTO_INCREMENT,
  trainerName varchar(20) NOT NULL,
  PRIMARY KEY (id)
);
CREATE TABLE wishlists (
  id bigint NOT NULL AUTO_INCREMENT,
  username varchar(25) NOT NULL,
  userId bigint NOT NULL,
  PRIMARY KEY (id),
  KEY userId (userId),
  CONSTRAINT wishLists_ibfk_1 FOREIGN KEY (userId) REFERENCES users (id) ON DELETE CASCADE
);
CREATE TABLE workoutappointments (
  id bigint NOT NULL AUTO_INCREMENT,
  hallId bigint DEFAULT NULL,
  workoutId bigint DEFAULT NULL,
  dateOfWorkout datetime DEFAULT NULL,
  PRIMARY KEY (id),
  KEY workoutappointments_ibfk_1 (hallId),
  KEY workoutappointments_ibfk_2 (workoutId),
  CONSTRAINT workoutappointments_ibfk_1 FOREIGN KEY (hallId) REFERENCES halls (id) ON DELETE CASCADE,
  CONSTRAINT workoutappointments_ibfk_2 FOREIGN KEY (workoutId) REFERENCES workouts (id) ON DELETE CASCADE
);
CREATE TABLE workouts (
  id bigint NOT NULL AUTO_INCREMENT,
  workoutName varchar(20) NOT NULL,
  workoutDescription varchar(40) NOT NULL,
  picture varchar(20) NOT NULL,
  price int NOT NULL,
  workoutGrouping enum('Individually','Group') NOT NULL DEFAULT 'Individually',
  workoutLevel enum('Amateur','Medium','Advanced') NOT NULL DEFAULT 'Amateur',
  workoutLength double NOT NULL,
  averageGrade int NOT NULL,
  PRIMARY KEY (id)
);
CREATE TABLE workoutswishlists (
  workoutId bigint NOT NULL,
  wishListId bigint NOT NULL,
  PRIMARY KEY (workoutId,wishListId),
  KEY wishListId (wishListId),
  CONSTRAINT workoutsWishLists_ibfk_1 FOREIGN KEY (workoutId) REFERENCES workouts (id) ON DELETE CASCADE,
  CONSTRAINT workoutsWishLists_ibfk_2 FOREIGN KEY (wishListId) REFERENCES wishlists (id) ON DELETE CASCADE
);
CREATE TABLE workouttrainer (
  workoutTrainerId bigint NOT NULL,
  trainerId bigint NOT NULL,
  PRIMARY KEY (workoutTrainerId,trainerId),
  KEY trainerId (trainerId),
  CONSTRAINT workouttrainer_ibfk_1 FOREIGN KEY (workoutTrainerId) REFERENCES workouts (id) ON DELETE CASCADE,
  CONSTRAINT workouttrainer_ibfk_2 FOREIGN KEY (trainerId) REFERENCES trainers (id) ON DELETE CASCADE
);
CREATE TABLE workouttype (
  id bigint NOT NULL AUTO_INCREMENT,
  workoutTypeName varchar(20) NOT NULL,
  workoutTypeDescription varchar(40) NOT NULL,
  PRIMARY KEY (id)
);
CREATE TABLE workoutworkouttype (
  workoutId bigint NOT NULL,
  workoutTypeId bigint NOT NULL,
  PRIMARY KEY (workoutId,workoutTypeId),
  KEY workoutTypeId (workoutTypeId),
  CONSTRAINT workoutworkouttype_ibfk_1 FOREIGN KEY (workoutId) REFERENCES workouts (id) ON DELETE CASCADE,
  CONSTRAINT workoutworkouttype_ibfk_2 FOREIGN KEY (workoutTypeId) REFERENCES workouttype (id) ON DELETE CASCADE
)