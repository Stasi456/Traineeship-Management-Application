package myy803.traineeship_app.services;

import myy803.traineeship_app.domain.Student;
import myy803.traineeship_app.domain.TraineeshipPosition;

public interface StudentService {

    Student getOrCreateStudentProfile(String username);

    void saveStudentProfile(Student student);

    TraineeshipPosition getAssignedTraineeshipForStudent(String username);

    void addLogbookEntry(String username, String entryDate, String description, Double hours);
}
