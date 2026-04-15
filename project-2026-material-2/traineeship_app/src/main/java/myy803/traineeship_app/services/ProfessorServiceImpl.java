package myy803.traineeship_app.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import myy803.traineeship_app.domain.Evaluation;
import myy803.traineeship_app.domain.EvaluationType;
import myy803.traineeship_app.domain.Professor;
import myy803.traineeship_app.domain.TraineeshipPosition;
import myy803.traineeship_app.mappers.ProfessorMapper;
import myy803.traineeship_app.mappers.TraineeshipPositionsMapper;

@Service
public class ProfessorServiceImpl implements ProfessorService {

	@Autowired
	private ProfessorMapper professorMapper;
	@Autowired
	private TraineeshipPositionsMapper positionsMapper;

	@Override
	public Professor getOrCreateProfessorProfile(String username) {
		Professor professor = professorMapper.findByUsername(username);
		return (professor != null) ? professor : new Professor(username);
	}

	@Override
	public void saveProfessorProfile(Professor professor) {
		professorMapper.save(professor);
	}

	@Override
	public List<TraineeshipPosition> getSupervisedTraineeships(String professorUsername) {

		List<TraineeshipPosition> result = new ArrayList<>();

		for (TraineeshipPosition p : positionsMapper.findAll()) {
			if (p.getSupervisor() != null &&
					professorUsername.equals(p.getSupervisor().getUsername())) {
				result.add(p);
			}
		}

		return result;
	}

	@Override
	public TraineeshipPosition getPosition(Integer positionId) {
		return positionsMapper.findById(positionId).orElse(null);
	}

	@Override
	public boolean isSupervisorOfPosition(String professorUsername, TraineeshipPosition position) {
		return position != null
				&& position.getSupervisor() != null
				&& professorUsername.equals(position.getSupervisor().getUsername());
	}

	@Override
	public Evaluation createProfessorEvaluation() {
		Evaluation evaluation = new Evaluation();
		evaluation.setEvaluationType(EvaluationType.PROFESSOR_EVALUATION);
		return evaluation;
	}

	@Override
	@Transactional
	public void saveProfessorEvaluation(Integer positionId,
										String professorUsername,
										int motivation,
										int effectiveness,
										int efficiency,
										int facilities,
										int guidance) {

		TraineeshipPosition position = getPosition(positionId);
		if (position == null) {
			throw new IllegalStateException("Position not found: " + positionId);
		}

		if (!isSupervisorOfPosition(professorUsername, position)) {
			throw new IllegalStateException("Not supervisor of position " + positionId);
		}

		Evaluation evaluation = createProfessorEvaluation();

		evaluation.setMotivation(motivation);
		evaluation.setEffectiveness(effectiveness);
		evaluation.setEfficiency(efficiency);
		evaluation.setFacilities(facilities);
		evaluation.setGuidance(guidance);

		if (position.getEvaluations() == null) {
			position.setEvaluations(new ArrayList<>());
		}

		position.getEvaluations().add(evaluation);
		positionsMapper.save(position);
	}
}
