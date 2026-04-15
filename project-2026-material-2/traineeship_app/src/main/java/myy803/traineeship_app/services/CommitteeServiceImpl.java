package myy803.traineeship_app.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import myy803.traineeship_app.controllers.searchstrategies.PositionsSearchFactory;
import myy803.traineeship_app.controllers.searchstrategies.PositionsSearchStrategy;
import myy803.traineeship_app.controllers.supervisorsearchstrategies.SupervisorAssigmentFactory;
import myy803.traineeship_app.controllers.supervisorsearchstrategies.SupervisorAssignmentStrategy;
import myy803.traineeship_app.domain.Student;
import myy803.traineeship_app.domain.TraineeshipPosition;
import myy803.traineeship_app.mappers.StudentMapper;
import myy803.traineeship_app.mappers.TraineeshipPositionsMapper;

@Service
public class CommitteeServiceImpl implements CommitteeService {

	@Autowired
	private StudentMapper studentMapper;
	@Autowired
	private TraineeshipPositionsMapper positionsMapper;
	@Autowired
	private PositionsSearchFactory positionsSearchFactory;
	@Autowired
	private SupervisorAssigmentFactory supervisorAssigmentFactory;

	@Override
	public List<Student> getTraineeshipApplications() {
		return studentMapper.findByLookingForTraineeshipTrue();
	}

	@Override
	public List<TraineeshipPosition> findPositionsForStudent(String studentUsername, String strategy) {
		PositionsSearchStrategy searchStrategy = positionsSearchFactory.create(strategy);
		return searchStrategy.search(studentUsername);
	}

	@Override
	@Transactional
	public Integer assignPositionToStudent(Integer positionId, String studentUsername) {

		Student student = studentMapper.findByUsername(studentUsername);
		TraineeshipPosition position = positionsMapper.findById(positionId).orElseThrow();

		position.setAssigned(true);
		position.setStudent(student);

		student.setAssignedTraineeship(position);
		student.setLookingForTraineeship(false);

		positionsMapper.save(position);

		return positionId;
	}

	@Override
	public void assignSupervisor(Integer positionId, String strategy) {
		SupervisorAssignmentStrategy assignmentStrategy = supervisorAssigmentFactory.create(strategy);
		assignmentStrategy.assign(positionId);
	}
}
