package myy803.traineeship_app.controllers.supervisorsearchstrategies;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import myy803.traineeship_app.domain.Professor;
import myy803.traineeship_app.domain.TraineeshipPosition;
import myy803.traineeship_app.mappers.ProfessorMapper;
import myy803.traineeship_app.mappers.TraineeshipPositionsMapper;


public abstract class AbstractSupervisorAssignmentStrategy implements SupervisorAssignmentStrategy {

    @Autowired
    protected TraineeshipPositionsMapper positionsMapper;

    @Autowired
    protected ProfessorMapper professorMapper;

    @Override
    public final void assign(Integer positionId) {
        TraineeshipPosition position = getPosition(positionId);

        List<Professor> professors = getAllProfessors();

        Professor candidateSupervisor = findCandidateSupervisor(position, professors);

        performAssignment(position, candidateSupervisor);

        saveAssignment(position);
    }

    protected TraineeshipPosition getPosition(Integer positionId) {
        return positionsMapper.findById(positionId).get();
    }

    protected List<Professor> getAllProfessors() {
        return professorMapper.findAll();
    }

    protected abstract Professor findCandidateSupervisor(TraineeshipPosition position,
                                                         List<Professor> professors);

    protected void performAssignment(TraineeshipPosition position, Professor candidateSupervisor) {
        position.setSupervisor(candidateSupervisor);
        if (candidateSupervisor != null) {
            candidateSupervisor.addPosition(position);
        }
    }


    protected void saveAssignment(TraineeshipPosition position) {
        positionsMapper.save(position);
    }
}