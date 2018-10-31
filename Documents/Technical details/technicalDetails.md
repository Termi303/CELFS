# User Interface
## Bootstrap - used to make easy UIs without much coding

# Back end
## Database
### List of tables
1. Marks
    ..* Primary Key:
        ..* student + coursework
    ..* Foreign Key:
        ..* Lecturer.id
        ..* Student.id
1. Lecturer
    ..* Primary Key: id
    ..* Foreign Key: None
    ..* Password (salted and hashed)
1. Student
    ..* Primary Key: id
    ..* Foreign Key: None
