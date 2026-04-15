package myy803.traineeship_app.controllers.searchstrategies;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import myy803.traineeship_app.domain.Student;
import myy803.traineeship_app.domain.TraineeshipPosition;
import myy803.traineeship_app.mappers.TraineeshipPositionsMapper;


@Component
public class SearchBasedOnInterests extends AbstractPositionsSearchStrategy {

	@Autowired
	private TraineeshipPositionsMapper positionsMapper;


	@Override
	protected void findMatchingPositions(Student applicant,
										 Set<TraineeshipPosition> matchingPositionsSet) {
		String[] interests = parseInterests(applicant.getInterests());

		for (String interest : interests) {
			List<TraineeshipPosition> positions =
					positionsMapper.findByTopicsContainingAndIsAssignedFalse(interest);
			matchingPositionsSet.addAll(positions);
		}
	}

	private String[] parseInterests(String interestsString) {
		return interestsString.split("[,\\s+\\.]");
	}
}