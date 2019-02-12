# User Interface
1. Bootstrap - used to make easy UIs without much coding

# Back end
1. Database
    1. List of tables
        1. Marks
            * Primary Key:
                * student + coursework
            * Foreign Key:
                * Lecturer.id
                * Student.id
        1. Lecturer
            * Primary Key: id
            * Foreign Key: None
        1. Student
            * Primary Key: id
            * Foreign Key: None
    1. Types of connections
        1. Marks and Lecturer:
            Many-to-Many
        1. Student and Marks:
            One-to-Many
        1. Lecturer and Student:
            No connection
