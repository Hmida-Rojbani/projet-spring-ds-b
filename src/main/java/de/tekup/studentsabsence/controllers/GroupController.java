package de.tekup.studentsabsence.controllers;


import de.tekup.studentsabsence.entities.Absence;
import de.tekup.studentsabsence.entities.Group;
import de.tekup.studentsabsence.entities.Student;
import de.tekup.studentsabsence.enums.LevelEnum;
import de.tekup.studentsabsence.enums.SpecialityEnum;
import de.tekup.studentsabsence.repositories.StudentRepository;
import de.tekup.studentsabsence.services.*;
import de.tekup.studentsabsence.services.holders.GroupSubjectHolder;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/groups")
@AllArgsConstructor
public class GroupController {
    private final GroupService groupService;
    private final SubjectService subjectService;
    private final GroupSubjectService groupSubjectService;
    private final StudentRepository studentRepository;
    private final StudentService studentService;
    private final AbsenceService absenceService;

    @GetMapping({"", "/"})
    public String index(Model model) {
        List<Group> groups = groupService.getAllGroups();
        model.addAttribute("groups", groups);
        return "groups/index";
    }

    @GetMapping("/add")
    public String addView(Model model) {
        model.addAttribute("levels", LevelEnum.values());
        model.addAttribute("specialities", SpecialityEnum.values());
        model.addAttribute("group", new Group());
        return "groups/add";
    }

    @PostMapping("/add")
    public String add(@Valid Group group, BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()) {
            model.addAttribute("levels", LevelEnum.values());
            model.addAttribute("specialities", SpecialityEnum.values());
            return "groups/add";
        }

        groupService.addGroup(group);
        return "redirect:/groups";
    }

    @GetMapping("/{id}/update")
    public String updateView(@PathVariable long id,  Model model) {
        model.addAttribute("levels", LevelEnum.values());
        model.addAttribute("specialities", SpecialityEnum.values());
        model.addAttribute("group", groupService.getGroupById(id));
        return "groups/update";
    }

    @PostMapping("/{id}/update")
    public String update(@PathVariable long id, @Valid Group group, BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()) {
            model.addAttribute("levels", LevelEnum.values());
            model.addAttribute("specialities", SpecialityEnum.values());
            return "groups/update";
        }
        groupService.updateGroup(group);
        return "redirect:/groups";
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable long id) {
        groupService.deleteGroup(id);
        return "redirect:/groups";
    }

    @GetMapping("/{id}/show")
    public String show(@PathVariable long id, Model model) {
        Group group = groupService.getGroupById(id);
        System.out.println(group);
        List<Student> studentList = studentRepository.findByGroup_Id(id); // add this also for more precisions on list students group

        model.addAttribute("group", group);
        model.addAttribute("groupSubjects",groupSubjectService.getSubjectsByGroupId(id));
        model.addAttribute("students",studentList);
        model.addAttribute("absenceService", absenceService);

        group.getStudent().forEach(student -> {

        });

        return "groups/show";
    }

    @GetMapping("/{id}/add-subject")
    public String addSubjectView(Model model , @PathVariable Long id){
        model.addAttribute("groupSubjectHolder", new GroupSubjectHolder());
        model.addAttribute("group",groupService.getGroupById(id));
        model.addAttribute("subjects",subjectService.getAllSubjects());
        return "groups/add-subject";

    }

    @PostMapping("/{id}/add-subject")
    public String addSubject(@PathVariable Long id, @Valid GroupSubjectHolder groupSubjectHolder, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()) {
            model.addAttribute("group",groupService.getGroupById(id));
            model.addAttribute("subjects",subjectService.getAllSubjects());
            return "groups/add-subject";
        }

        Group group = groupService.getGroupById(id);
        groupSubjectService.addSubjectToGroup(group, groupSubjectHolder.getSubject(), groupSubjectHolder.getHours());
        return "redirect:/groups/"+id+"/add-subject";
    }

    @GetMapping("/{gid}/subject/{sid}/delete")
    public String deleteSubject(@PathVariable Long gid, @PathVariable Long sid){
        groupSubjectService.deleteSubjectFromGroup(gid, sid);
        return "redirect:/groups/"+gid+"/show";
    }

    @GetMapping("/{id}/add-absences")
    public String addAbsenceView(@PathVariable long id, Model model) {
        Group group = groupService.getGroupById(id);
        List<Student> students = studentRepository.findByGroup_Id(id); // Add this because that was failed !

        // System.out.println(students); // to look !!
        // System.out.println(groupSubjectService.getSubjectsByGroupId(id));
        model.addAttribute("group", group);
        model.addAttribute("absence", new Absence());
        model.addAttribute("groupSubjects", groupSubjectService.getSubjectsByGroupId(id));
        model.addAttribute("students", students);

        return "groups/add-absences";
    }

    @PostMapping("/{id}/add-absences")
    public String addAbsence(@PathVariable long id, @Valid Absence absence, BindingResult bindingResult, @RequestParam(value = "students", required = false) Student students, Model model) {

        System.out.println("-------------");
        System.out.println(students);
        if(bindingResult.hasErrors()) {
            model.addAttribute("group",groupService.getGroupById(id));
            model.addAttribute("subjects",subjectService.getAllSubjects());
            model.addAttribute("groupSubjects", groupSubjectService.getSubjectsByGroupId(id));
            model.addAttribute("students", students);

            return "groups/add-absences";
        }

        // All this things because they was errors ! Thats incredible !
        Absence absence1 = absence;
        Student student = students;
        absence1.setStudent(student);
        absenceService.addAbsence(absence1);

        return "redirect:/groups/"+id+"/add-absences";
    }

}
