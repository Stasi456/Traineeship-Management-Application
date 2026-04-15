package myy803.traineeship_app.controllers;

import java.util.List;

import myy803.traineeship_app.services.CommitteeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import myy803.traineeship_app.controllers.searchstrategies.PositionsSearchFactory;
import myy803.traineeship_app.controllers.searchstrategies.PositionsSearchStrategy;
import myy803.traineeship_app.controllers.supervisorsearchstrategies.SupervisorAssigmentFactory;
import myy803.traineeship_app.controllers.supervisorsearchstrategies.SupervisorAssignmentStrategy;
import myy803.traineeship_app.domain.Student;
import myy803.traineeship_app.domain.TraineeshipPosition;
import myy803.traineeship_app.mappers.StudentMapper;
import myy803.traineeship_app.mappers.TraineeshipPositionsMapper;

@Controller
public class CommitteeController {

    @Autowired
    private PositionsSearchFactory positionsSearchFactory;

    @Autowired
    private SupervisorAssigmentFactory supervisorAssigmentFactory;

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private TraineeshipPositionsMapper positionsMapper;

    @Autowired  // ← ADDED THIS!
    private CommitteeService committeeService;

    // ---------- Committee User Stories

    @RequestMapping("/committee/dashboard")
    public String getCommitteeDashboard() {
        return "committee/dashboard";
    }

    @RequestMapping("/committee/list_traineeship_applications")
    public String listTraineeshipApplications(Model model) {
        model.addAttribute("traineeship_applications", committeeService.getTraineeshipApplications());
        return "committee/traineeship_applications";
    }

    @RequestMapping("/committee/find_positions")
    public String findPositions(@RequestParam("selected_student_id") String studentUsername,
                                @RequestParam("strategy") String strategy,
                                Model model) {

        model.addAttribute("positions", committeeService.findPositionsForStudent(studentUsername, strategy));
        model.addAttribute("student_username", studentUsername);
        return "committee/available_positions";
    }

    @RequestMapping("/committee/assign_position")
    public String assignPosition(@RequestParam("selected_position_id") Integer positionId,
                                 @RequestParam("applicant_username") String studentUsername,
                                 Model model) {

        Integer id = committeeService.assignPositionToStudent(positionId, studentUsername);
        model.addAttribute("position_id", id);
        return "committee/supervisor_assignment";
    }

    @RequestMapping("/committee/assign_supervisor")
    public String assignSupervisor(@RequestParam("selected_position_id") Integer positionId,
                                   @RequestParam("strategy") String strategy) {
        committeeService.assignSupervisor(positionId, strategy);
        return "committee/dashboard";
    }

    // ← ADDED THIS NEW METHOD!
    @RequestMapping("/committee/list_assigned_traineeships")
    public String listAssignedTraineeships(Model model) {
        List<TraineeshipPosition> assignedPositions = positionsMapper.findByIsAssignedTrue();
        model.addAttribute("positions", assignedPositions);
        return "committee/assigned_traineeships";
    }
}