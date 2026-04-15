package myy803.traineeship_app.controllers.supervisorsearchstrategies;

import java.util.List;

import org.springframework.stereotype.Component;

import myy803.traineeship_app.domain.Professor;
import myy803.traineeship_app.domain.TraineeshipPosition;

@Component
public class AssignmentBasedOnLoad extends AbstractSupervisorAssignmentStrategy {

	@Override
	protected Professor findCandidateSupervisor(TraineeshipPosition position,
												List<Professor> professors) {
		if (professors.isEmpty()) {
			return null;
		}

		Professor candidateSupervisor = professors.get(0);

		for (Professor professor : professors) {
			if (hasLowerLoad(professor, candidateSupervisor)) {
				candidateSupervisor = professor;
			}
		}

		return candidateSupervisor;
	}


	private boolean hasLowerLoad(Professor professor, Professor current) {
		return professor.compareLoad(current) >= 0;
	}
}