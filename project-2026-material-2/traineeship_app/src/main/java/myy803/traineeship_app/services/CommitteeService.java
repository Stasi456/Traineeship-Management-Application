package myy803.traineeship_app.services;

import java.util.List;
import myy803.traineeship_app.domain.Student;
import myy803.traineeship_app.domain.TraineeshipPosition;

public interface CommitteeService {
    List<Student> getTraineeshipApplications();

    List<TraineeshipPosition> findPositionsForStudent(String studentUsername, String strategy);

    Integer assignPositionToStudent(Integer positionId, String studentUsername);

    void assignSupervisor(Integer positionId, String strategy);
}
