INSERT INTO Users (id, userName, password, email, firstName, lastName, dateOfBirth, address, phoneNumber, dateOfRegistration, userRole, active)
			VALUES (1, 'milan2001', 'milan123', 'milan@gmail.com', 'Milan', 'Miljus', '2021-12-12', 'Skolska', '06520', '2021-12-12', 'Administrator', false);
SELECT * FROM Users;
DELETE FROM Users WHERE ID = ?;


INSERT INTO WorkoutType(id, workoutTypeName, workoutTypeDescription) 
			VALUES (1, "Type 1", "Fitness");
SELECT * FROM WorkoutType;
DELETE FROM WorkoutType WHERE ID = ?;


INSERT INTO Halls(id, hallLabel, capacity) 
			VALUES (1, "First Hall", 15);
SELECT * FROM Halls;
DELETE FROM Halls WHERE ID = ?;