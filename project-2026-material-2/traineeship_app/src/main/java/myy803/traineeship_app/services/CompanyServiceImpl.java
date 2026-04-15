package myy803.traineeship_app.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import myy803.traineeship_app.domain.Company;
import myy803.traineeship_app.domain.Evaluation;
import myy803.traineeship_app.domain.EvaluationType;
import myy803.traineeship_app.domain.TraineeshipPosition;
import myy803.traineeship_app.mappers.CompanyMapper;
import myy803.traineeship_app.mappers.TraineeshipPositionsMapper;

@Service
public class CompanyServiceImpl implements CompanyService {

	@Autowired
	private CompanyMapper companyMapper;
	@Autowired
	private TraineeshipPositionsMapper positionsMapper;

	@Override
	public Company getOrCreateCompanyProfile(String username) {
		Company company = companyMapper.findByUsername(username);
		return (company != null) ? company : new Company(username);
	}

	@Override
	public void saveCompanyProfile(Company company) {
		companyMapper.save(company);
	}

	@Override
	public List<TraineeshipPosition> getAvailablePositionsForCompany(String username) {
		Company company = companyMapper.findByUsername(username);
		if (company == null) return List.of();
		return company.getAvailablePositions();
	}

	@Override
	public List<TraineeshipPosition> getAssignedPositionsForCompany(String username) {
		// you already used this query method in your mapper:
		return positionsMapper.findByCompanyUsernameAndIsAssignedTrue(username);
	}

	@Override
	@Transactional
	public void createPositionForCompany(String username, TraineeshipPosition position) {

		Company company = companyMapper.findByUsername(username);
		if (company == null) {
			throw new IllegalStateException("Company not found: " + username);
		}

		position.setCompany(company);
		company.addPosition(position);

		companyMapper.save(company);
	}

	@Override
	@Transactional
	public void deletePosition(String username, Integer positionId) {

		TraineeshipPosition position = positionsMapper.findById(positionId).orElse(null);
		if (position == null) return;

		// must belong to this company
		if (position.getCompany() == null || !username.equals(position.getCompany().getUsername())) return;

		// cannot delete assigned positions
		if (position.isAssigned()) return;

		positionsMapper.deleteById(positionId);
	}

	@Override
	public TraineeshipPosition getPosition(Integer positionId) {
		return positionsMapper.findById(positionId).orElse(null);
	}

	@Override
	@Transactional
	public void saveCompanyEvaluation(String username, Integer positionId, Evaluation evaluation) {

		TraineeshipPosition position = getPosition(positionId);
		if (position == null) return;

		if (position.getCompany() == null || !username.equals(position.getCompany().getUsername())) return;

		if (!position.isAssigned()) return;

		evaluation.setEvaluationType(EvaluationType.COMPANY_EVALUATION);

		if (position.getEvaluations() == null) {
			position.setEvaluations(new java.util.ArrayList<>());
		}

		position.getEvaluations().add(evaluation);
		positionsMapper.save(position);
	}
}
