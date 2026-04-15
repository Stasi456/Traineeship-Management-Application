package myy803.traineeship_app.controllers.supervisorsearchstrategies;

import java.util.List;

import org.springframework.stereotype.Component;

import myy803.traineeship_app.domain.Professor;
import myy803.traineeship_app.domain.TraineeshipPosition;


@Component
public class AssignmentBasedOnInterests extends AbstractSupervisorAssignmentStrategy {


	@Override
	protected Professor findCandidateSupervisor(TraineeshipPosition position,
												List<Professor> professors) {
		String[] topics = parseTopics(position.getTopics());

		for (Professor professor : professors) {
			if (professor.match(topics)) {
				return professor;
			}
		}

		return null;
	}

	private String[] parseTopics(String topicsString) {
		return topicsString.split("[,\\s+\\.]");
	}
}