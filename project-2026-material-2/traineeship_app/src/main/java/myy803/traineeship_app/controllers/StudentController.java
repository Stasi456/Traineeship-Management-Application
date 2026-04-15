package myy803.traineeship_app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import myy803.traineeship_app.domain.Student;
import myy803.traineeship_app.domain.TraineeshipPosition;
import myy803.traineeship_app.services.StudentService;

@Controller
public class StudentController {

    @Autowired
    private StudentService studentService;

    // ---------- Student User Stories

    @RequestMapping("/student/dashboard")
    public String getStudentDashboard() {
        return "student/dashboard";
    }

    @RequestMapping("/student/profile")
    public String retrieveStudentProfile(Model model) {
        String username = getLoggedUsername();

        Student student = studentService.getOrCreateStudentProfile(username);
        model.addAttribute("student", student);

        return "student/profile";
    }

    @RequestMapping("/student/save_profile")
    public String saveProfile(@ModelAttribute("student") Student student) {
        studentService.saveStudentProfile(student);
        return "student/dashboard";
    }

    @RequestMapping("/student/logbook")
    public String getStudentLogbook(Model model) {
        String username = getLoggedUsername();

        TraineeshipPosition position = studentService.getAssignedTraineeshipForStudent(username);

        // if student profile missing, redirect like before
        if (position == null) {
            // could be "no assigned traineeship" OR "no student", but your view handles null too
            // If you want the old behavior "no student -> profile", you can add a separate check in service.
        }

        model.addAttribute("position", position);
        return "student/logbook";
    }

    @PostMapping("/student/logbook/save")
    public String saveLogbookEntry(@RequestParam("entryDate") String entryDate,
                                   @RequestParam("description") String description,
                                   @RequestParam(value = "hours", required = false) Double hours) {

        String username = getLoggedUsername();
        studentService.addLogbookEntry(username, entryDate, description, hours);

        return "redirect:/student/dashboard";
    }

    private String getLoggedUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }
}
