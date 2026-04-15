package myy803.traineeship_app.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import myy803.traineeship_app.domain.Company;
import myy803.traineeship_app.domain.Evaluation;
import myy803.traineeship_app.domain.TraineeshipPosition;
import myy803.traineeship_app.services.CompanyService;

@Controller
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    // ---------- Company User Stories

    @RequestMapping("/company/dashboard")
    public String getCompanyDashboard() {
        return "company/dashboard";
    }

    @RequestMapping("/company/profile")
    public String retrieveCompanyProfile(Model model) {
        String username = getLoggedUsername();

        Company company = companyService.getOrCreateCompanyProfile(username);
        model.addAttribute("company", company);

        return "company/profile";
    }

    @RequestMapping("/company/save_profile")
    public String saveProfile(@ModelAttribute("profile") Company company) {
        companyService.saveCompanyProfile(company);
        return "company/dashboard";
    }

    @RequestMapping("/company/list_available_positions")
    public String listAvailablePositions(Model model) {
        String username = getLoggedUsername();

        List<TraineeshipPosition> positions = companyService.getAvailablePositionsForCompany(username);
        model.addAttribute("positions", positions);

        return "company/available_positions";
    }

    // US9
    @RequestMapping("/company/list_assigned_positions")
    public String listAssignedPositions(Model model) {
        String username = getLoggedUsername();

        List<TraineeshipPosition> positions = companyService.getAssignedPositionsForCompany(username);
        model.addAttribute("positions", positions);

        return "company/assigned_positions";
    }

    // US11
    @RequestMapping("/company/delete_position")
    public String deletePosition(@RequestParam("position_id") Integer positionId) {
        String username = getLoggedUsername();

        companyService.deletePosition(username, positionId);
        return "redirect:/company/list_available_positions";
    }

    // US12 (GET form)
    @RequestMapping("/company/evaluate")
    public String showEvaluationForm(@RequestParam("positionId") Integer positionId, Model model) {

        TraineeshipPosition position = companyService.getPosition(positionId);
        if (position == null) {
            return "redirect:/company/list_assigned_positions";
        }

        model.addAttribute("position", position);
        model.addAttribute("evaluation", new Evaluation());

        return "company/evaluation_form";
    }

    // US12 (POST save)
    @RequestMapping(value = "/company/save_evaluation", method = RequestMethod.POST)
    public String saveEvaluation(@RequestParam("positionId") Integer positionId,
                                 @ModelAttribute("evaluation") Evaluation evaluation) {

        String username = getLoggedUsername();
        companyService.saveCompanyEvaluation(username, positionId, evaluation);

        return "redirect:/company/list_assigned_positions";
    }

    @RequestMapping("/company/show_position_form")
    public String showPositionForm(Model model) {
        model.addAttribute("position", new TraineeshipPosition());
        return "company/position";
    }

    @RequestMapping("/company/save_position")
    public String savePosition(@ModelAttribute("position") TraineeshipPosition position) {

        String username = getLoggedUsername();
        companyService.createPositionForCompany(username, position);

        return "redirect:/company/dashboard";
    }

    private String getLoggedUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }
}
