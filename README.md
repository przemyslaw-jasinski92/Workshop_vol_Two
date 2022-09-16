# Workshop vol. Two
Next workshop on course Coderslab - DAO.
I created program, which helps user to modify database from IDE:
- DbUtli (for creating connection to database in MySQL)
- User (keeping data for each row from database)
- UserDao (excluding all methods needs to add new object to database, edit or deleting existing and getting all objects from database to User array)

UserDao has CRUD - create new user, remove existing user, update existing user and delete existing user. I decided to create 2 methods for update object:
1. update only user name and email. Password does not change.
2. update user name, email and password.
