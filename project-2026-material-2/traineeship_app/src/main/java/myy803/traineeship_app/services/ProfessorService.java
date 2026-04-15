package myy803.traineeship_app.services;

import java.util.List;

import myy803.traineeship_app.domain.Evaluation;
import myy803.traineeship_app.domain.Professor;
import myy803.traineeship_app.domain.TraineeshipPosition;

public interface ProfessorService {

    Professor getOrCreateProfessorProfile(String username);

    void saveProfessorProfile(Professor professor);

    List<TraineeshipPosition> getSupervisedTraineeships(String professorUsername);

    TraineeshipPosition getPosition(Integer positionId);

    boolean isSupervisorOfPosition(String professorUsername, TraineeshipPosition position);

    void saveProfessorEvaluation(Integer positionId,
                                 String professorUsername,
                                 int motivation,
                                 int effectiveness,
                                 int efficiency,
                                 int facilities,
                                 int guidance);

    Evaluation createProfessorEvaluation();
}
