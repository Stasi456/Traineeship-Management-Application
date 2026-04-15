package myy803.traineeship_app.services;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import myy803.traineeship_app.domain.Student;
import myy803.traineeship_app.domain.TraineeshipPosition;
import myy803.traineeship_app.mappers.StudentMapper;
import myy803.traineeship_app.mappers.TraineeshipPositionsMapper;

@Service
public class StudentServiceImpl implements StudentService {

	@Autowired
	private StudentMapper studentMapper;
	@Autowired
	private TraineeshipPositionsMapper positionsMapper;

	@Override
	public Student getOrCreateStudentProfile(String username) {
		Student student = studentMapper.findByUsername(username);
		return (student != null) ? student : new Student(username);
	}

	@Override
	public void saveStudentProfile(Student student) {
		student.setLookingForTraineeship(true);
		studentMapper.save(student);
	}

	@Override
	public TraineeshipPosition getAssignedTraineeshipForStudent(String username) {
		Student student = studentMapper.findByUsername(username);
		if (student == null) return null;
		return student.getAssignedTraineeship();
	}

	@Override
	@Transactional
	public void addLogbookEntry(String username, String entryDate, String description, Double hours) {

		Student student = studentMapper.findByUsername(username);
		if (student == null) {
			throw new IllegalStateException("Student not found: " + username);
		}

		TraineeshipPosition position = student.getAssignedTraineeship();
		if (position == null) {
			throw new IllegalStateException("Student has no assigned traineeship.");
		}

		// Build one log entry line (simple, stored in student_log_book column)
		LocalDate date = LocalDate.parse(entryDate);
		String hoursText = (hours == null) ? "" : " | hours: " + hours;

		String newEntry =
				"=== " + date + hoursText + " ===\n" +
						description + "\n\n";

		String existing = position.getStudentLogbook();
		if (existing == null) existing = "";

		position.setStudentLogbook(existing + newEntry);

		positionsMapper.save(position);
	}
}
