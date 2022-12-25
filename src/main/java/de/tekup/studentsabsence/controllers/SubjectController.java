package de.tekup.studentsabsence.controllers;

import de.tekup.studentsabsence.entities.*;
import de.tekup.studentsabsence.repositories.StudentRepository;
import de.tekup.studentsabsence.services.*;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.*;


@Controller
@RequestMapping("/subjects")
@AllArgsConstructor
public class SubjectController {
    private final SubjectService subjectService;
    private final GroupSubjectService groupSubjectService;
    private final GroupService groupService;
    private final StudentController studentController;
    private final AbsenceService absenceService;


    @GetMapping({"", "/"})
    public String index(Model model) {
        List<Subject> subjects = subjectService.getAllSubjects();
        model.addAttribute("subjects", subjects);
        return "subjects/index";
    }

    // Get home statistic page
    /*@GetMapping("/homeStatistic")
    public String statisticHome(Model model) {
        List<Subject> subjects = subjectService.getAllSubjects();
        List<Student> students = studentService.getAllStudents();
        List<Group> groups = groupService.getAllGroups();

        model.addAttribute("subjects", subjects.size());
        model.addAttribute("students", students.size());
        model.addAttribute("groups", groups.size());

        return "subjects/statistic";
    }*/

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

        List<Student> studentList = studentController.getEliminatedStudent(id);
        System.out.println(studentList);
        model.addAttribute("students", studentList);
        return "subjects/show";
    }






    // Return min, max subject by group
    @GetMapping("/stat-test")
    public List<Map<String,String>> statTest() {
        List<Group> group = groupService.getAllGroups();

        List<Map<String,String >> s = new ArrayList<>();



        for (int i = 0; i < group.size(); i++) {
            float max = 0;
            float min = 0;
            Subject maxSubject = null;
            Subject minSubject = null;

            List<GroupSubject> groupSubjects = groupSubjectService.getSubjectsByGroupId(group.get(i).getId());
            System.out.println("------------------");
            //System.out.println(groupSubjects);
            //System.out.println(groupSubjects.size());

            for (int j = 0; j < groupSubjects.size(); j++) {
                //System.out.println(groupSubjects.get(j).getSubject());
                //System.out.println(groupSubjects.get(j).getSubject().getId());

                Subject subject = groupSubjects.get(j).getSubject();
                System.out.println(subject);
                System.out.println(subject.getAbsence());
                List<Absence> absence = absenceService.getAllAbsencesByGroupIdAndSubjectId(group.get(i).getId(),subject.getId());
                System.out.println(absence);
                minSubject = subject;
                maxSubject = subject;

                for (Absence ab: absence
                     ) {
                    if (max < ab.getHours()) {
                        max = ab.getHours();
                        maxSubject = subject;
                    }

                    if (min > ab.getHours()) {
                        min = ab.getHours();
                        minSubject = subject;
                    }
                }


            }


            List<Subject> data = new ArrayList<>();
            data.add(minSubject);
            data.add(maxSubject);

            String hourMin = String.valueOf(absenceService.hoursCountByGroupAndSubject(group.get(i).getId(), minSubject.getId()));
            String hourMax = String.valueOf(absenceService.hoursCountByGroupAndSubject(group.get(i).getId(), maxSubject.getId()));

            Map<String, String> map = new HashMap<>();
            map.put("Module", group.get(i).getName());

            if (maxSubject != null) {
                map.put("subject_avec_taux_absence_plus_eleve", maxSubject.getName());
            } else {
                map.put("subject_avec_taux_absence_plus_eleve", "Non défini");
            }

            if (minSubject != null) {
                map.put("subject_avec_taux_absence_moins_eleve", minSubject.getName());
            } else {
                map.put("subject_avec_taux_absence_moins_eleve", "Non défini");
            }

            map.put("taux_absence_min", hourMin);


            map.put("taux_absence_max", hourMax);


            s.add(map);


        }

        System.out.println(s);
        return s;
    }



}
