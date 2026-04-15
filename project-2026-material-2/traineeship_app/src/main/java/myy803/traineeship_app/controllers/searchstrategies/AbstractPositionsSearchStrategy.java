package myy803.traineeship_app.controllers.searchstrategies;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import myy803.traineeship_app.domain.Student;
import myy803.traineeship_app.domain.TraineeshipPosition;
import myy803.traineeship_app.mappers.StudentMapper;

public abstract class AbstractPositionsSearchStrategy implements PositionsSearchStrategy {

    @Autowired
    protected StudentMapper studentMapper;

    @Override
    public final List<TraineeshipPosition> search(String applicantUsername) {
        Student applicant = getApplicant(applicantUsername);

        Set<TraineeshipPosition> matchingPositionsSet = new HashSet<>();

        findMatchingPositions(applicant, matchingPositionsSet);

        return convertToList(matchingPositionsSet);
    }

    protected Student getApplicant(String applicantUsername) {
        return studentMapper.findByUsername(applicantUsername);
    }


    protected abstract void findMatchingPositions(Student applicant,
                                                  Set<TraineeshipPosition> matchingPositionsSet);


    protected List<TraineeshipPosition> convertToList(Set<TraineeshipPosition> matchingPositionsSet) {
        return new ArrayList<>(matchingPositionsSet);
    }
}