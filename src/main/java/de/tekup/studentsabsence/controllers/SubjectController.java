package de.tekup.studentsabsence.controllers;

import de.tekup.studentsabsence.entities.Absence;
import de.tekup.studentsabsence.entities.GroupSubject;
import de.tekup.studentsabsence.entities.Student;
import de.tekup.studentsabsence.entities.Subject;
import de.tekup.studentsabsence.services.AbsenceService;
import de.tekup.studentsabsence.services.GroupSubjectService;
import de.tekup.studentsabsence.services.StudentService;
import de.tekup.studentsabsence.services.SubjectService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;


@Controller
@RequestMapping("/subjects")
@AllArgsConstructor
public class SubjectController {
    private final SubjectService subjectService;
    private final StudentService studentService;
    private final AbsenceService absenceService;
    private final GroupSubjectService groupSubjectService;

    @GetMapping({"", "/"})
    public String index(Model model) {
        List<Subject> subjects = subjectService.getAllSubjects();
        model.addAttribute("subjects", subjects);
        return "subjects/index";
    }

    @GetMapping("/add")
    public String addView(Model model) {
        model.addAttribute("subject", new Subject());
        return "subjects/add";
    }

    @PostMapping("/add")
    public String add(@Valid Subject subject, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return "subjects/add";
        }

        subjectService.addSubject(subject);
        return "redirect:/subjects";
    }

    @GetMapping("/{id}/update")
    public String updateView(@PathVariable Long id, Model model) {
        model.addAttribute("subject", subjectService.getSubjectById(id));
        return "subjects/update";
    }

    @PostMapping("/{id}/update")
    public String update(@PathVariable Long id, @Valid Subject subject, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return "subjects/update";
        }

        subjectService.updateSubject(subject);
        return "redirect:/subjects";
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {

        subjectService.deleteSubject(id);
        return "redirect:/subjects";
    }

    @GetMapping("/{id}/show")
    public String show(@PathVariable Long id, Model model) {
        model.addAttribute("subject", subjectService.getSubjectById(id));
        return "subjects/show";
    }

    @GetMapping("/Subshow/{gid}/{sid}")
    public String subjectAbsence(@PathVariable Long gid,@PathVariable Long sid,Model model) {
        HashSet<Student> filtredList=new HashSet<Student>();
        List <Absence>l=absenceService.getAllAbsencesByGroupIdAndSubjectId(gid,sid);
        GroupSubject gs=groupSubjectService.getSubjectByIdAndGroupId(sid,gid);

        for (Absence ab:l){
            if(absenceService.hoursCountByStudentAndSubject(ab.getStudent().getSid(),sid)> gs.getHours()/2){
                filtredList.add(ab.getStudent());
            }
        }
        model.addAttribute("students",filtredList );
        model.addAttribute("subjectId",sid );
        model.addAttribute("subjectName",gs.getSubject().getName() );

        return "subjects/subjectsAbsence";
    }
}
