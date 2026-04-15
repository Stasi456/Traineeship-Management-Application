package myy803.traineeship_app.services;

import java.util.List;

import myy803.traineeship_app.domain.Company;
import myy803.traineeship_app.domain.Evaluation;
import myy803.traineeship_app.domain.TraineeshipPosition;

public interface CompanyService {

    Company getOrCreateCompanyProfile(String username);

    void saveCompanyProfile(Company company);

    List<TraineeshipPosition> getAvailablePositionsForCompany(String username);

    List<TraineeshipPosition> getAssignedPositionsForCompany(String username);

    void createPositionForCompany(String username, TraineeshipPosition position);

    void deletePosition(String username, Integer positionId);

    TraineeshipPosition getPosition(Integer positionId);

    void saveCompanyEvaluation(String username, Integer positionId, Evaluation evaluation);
}
