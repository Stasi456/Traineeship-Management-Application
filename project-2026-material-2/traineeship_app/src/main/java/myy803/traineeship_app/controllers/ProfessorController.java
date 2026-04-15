package myy803.traineeship_app.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import myy803.traineeship_app.domain.Evaluation;
import myy803.traineeship_app.domain.Professor;
import myy803.traineeship_app.domain.TraineeshipPosition;
import myy803.traineeship_app.services.ProfessorService;

@Controller
public class ProfessorController {

    @Autowired
    private ProfessorService professorService;

    // ---------- Professor User Stories

    @RequestMapping("/professor/dashboard")
    public String getProfessorDashboard() {
        return "professor/dashboard";
    }

    @RequestMapping("/professor/profile")
    public String retrieveProfessorProfile(Model model) {
        String username = getLoggedUsername();

        Professor professor = professorService.getOrCreateProfessorProfile(username);
        model.addAttribute("professor", professor);

        return "professor/profile";
    }

    @RequestMapping("/professor/save_profile")
    public String saveProfile(@ModelAttribute("profile") Professor professor) {
        professorService.saveProfessorProfile(professor);
        return "professor/dashboard";
    }

    // US14
    @RequestMapping("/professor/list_traineeships")
    public String listSupervisedTraineeships(Model model) {
        String professorUsername = getLoggedUsername();

        List<TraineeshipPosition> supervisedPositions =
                professorService.getSupervisedTraineeships(professorUsername);

        model.addAttribute("positions", supervisedPositions);
        return "professor/supervised_traineeships";
    }

    // US15 (GET form)
    @RequestMapping("/professor/evaluate")
    public String showProfessorEvaluation(@RequestParam("positionId") Integer positionId,
                                          Model model) {

        String professorUsername = getLoggedUsername();
        TraineeshipPosition position = professorService.getPosition(positionId);

        if (position == null || !professorService.isSupervisorOfPosition(professorUsername, position)) {
            return "redirect:/professor/list_traineeships";
        }

        Evaluation evaluation = professorService.createProfessorEvaluation();

        model.addAttribute("position", position);
        model.addAttribute("evaluation", evaluation);

        return "professor/evaluation_form";
    }

    // US15 (POST save)
    @PostMapping("/professor/save_evaluation")
    public String saveProfessorEvaluation(@RequestParam("positionId") Integer positionId,
                                          @RequestParam("motivation") int motivation,
                                          @RequestParam("effectiveness") int effectiveness,
                                          @RequestParam("efficiency") int efficiency,
                                          @RequestParam("facilities") int facilities,
                                          @RequestParam("guidance") int guidance) {

        String professorUsername = getLoggedUsername();

        professorService.saveProfessorEvaluation(positionId, professorUsername,
                motivation, effectiveness, efficiency, facilities, guidance);

        return "redirect:/professor/list_traineeships";
    }

    private String getLoggedUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }
}
