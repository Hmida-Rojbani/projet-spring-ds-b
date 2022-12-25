package de.tekup.studentsabsence.controllers;


import de.tekup.studentsabsence.entities.Group;
import de.tekup.studentsabsence.entities.Student;
import de.tekup.studentsabsence.entities.Subject;
import de.tekup.studentsabsence.services.GroupSubjectService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import de.tekup.studentsabsence.services.GroupService;
import de.tekup.studentsabsence.services.StudentService;
import de.tekup.studentsabsence.services.SubjectService;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/statistics")
@AllArgsConstructor
public class StatisticsController {

    private final SubjectService subjectService;
    private final GroupSubjectService groupSubjectService;
    private final GroupService groupService;
    private final StudentService studentService;
    private final SubjectController subjectController;
    private final StudentController studentController;

    @GetMapping({"", "/"})
    public String index(Model model) {
        List<Subject> subjects = subjectService.getAllSubjects();
        List<Student> students = studentService.getAllStudents();
        List<Group> groups = groupService.getAllGroups();
        model.addAttribute("subjects", subjects.size());
        model.addAttribute("students", students.size());
        model.addAttribute("groups", groups.size());
        return "statistics/index";
    }

    @GetMapping("/statistic-absences")
    public String statisticAbsence(Model model) {
        List<Map<String, String>> data = subjectController.statTest();

        model.addAttribute("subjects", data);

        return "statistics/absences";
    }


    @GetMapping("/statistic-student")
    public String statisticStudent(Model model) {
        List<Subject> subjects = subjectService.getAllSubjects();
        model.addAttribute("subject", new Subject());
        model.addAttribute("subjects", subjects);

        return "statistics/students";
    }

    @PostMapping("/get-eliminated-student")
    public String getEliminatedStudent(BindingResult bindingResult, @Valid Subject subject, Model model) {
        System.out.println(subject);
        if(bindingResult.hasErrors()) {
            List<Subject> subjects = subjectService.getAllSubjects();
            model.addAttribute("subject", new Subject());
            model.addAttribute("subjects", subjects);
            return "statistics/students";
        }

        List<Student> studentList = studentController.getEliminatedStudent(subject.getId());
        System.out.println(studentList);

        return "redirect:/statistic-student";
    }
}
