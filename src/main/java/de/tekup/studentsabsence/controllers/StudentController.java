package de.tekup.studentsabsence.controllers;

import de.tekup.studentsabsence.entities.*;
import de.tekup.studentsabsence.repositories.AbsenceRepository;
import de.tekup.studentsabsence.repositories.GroupSubjectRepository;
import de.tekup.studentsabsence.repositories.StudentRepository;
import de.tekup.studentsabsence.services.*;

import lombok.AllArgsConstructor;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/students")
public class StudentController {
    private final StudentService studentService;
    private final StudentRepository studentRepository;
    private final GroupService groupService;
    private final GroupSubjectRepository groupSubjectRepository;
    private final ImageService imageService;
    private final SubjectService subjectService;
    private final AbsenceService absenceService;
    private final AbsenceRepository absenceRepository;



    // private final EmailService emailService;

    @GetMapping({"", "/"})
    public String index(Model model) {
        List<Student> students = studentService.getAllStudents();
        model.addAttribute("students", students);
        System.out.println(students.get(0).getGroup().getName());
        return "students/index";
    }

    @GetMapping("/add")
    public String addView(Model model) {
        model.addAttribute("student", new Student());
        model.addAttribute("groups", groupService.getAllGroups());
        return "students/add";
    }

    @PostMapping("/add")
    public String add(@Valid Student student, BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()) {
            model.addAttribute("groups", groupService.getAllGroups());
            return "students/add";
        }

        System.out.println(student.getGroup());

        studentService.addStudent(student);
        return "redirect:/students";
    }

    @GetMapping("/{sid}/update")
    public String updateView(@PathVariable Long sid, Model model) {
        model.addAttribute("student", studentService.getStudentBySid(sid));
        model.addAttribute("groups", groupService.getAllGroups());
        return "students/update";
    }

    @PostMapping("/{sid}/update")
    public String update(@PathVariable Long sid, @Valid Student student, BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()) {
            model.addAttribute("groups", groupService.getAllGroups());
            return "students/update";
        }

        studentService.updateStudent(student);
        return "redirect:/students";
    }

    @GetMapping("/{sid}/delete")
    public String delete(@PathVariable Long sid) {
        studentService.deleteStudent(sid);
        return "redirect:/students";
    }

    @GetMapping("/{sid}/show")
    public String show(Model model, @PathVariable Long sid) {
        model.addAttribute("student", studentService.getStudentBySid(sid));
        return "students/show";
    }

    @GetMapping("/{sid}/add-image")
    public String addImageView(@PathVariable Long sid, Model model) {
        model.addAttribute("student", studentService.getStudentBySid(sid));
        return "students/add-image";
    }

    @PostMapping("/{sid}/add-image")
    public String addImage(@PathVariable("sid") long sid, @Validated Image image) {
        Student student = studentService.getStudentBySid(sid);
        student.setImage(image);
        studentService.updateStudent(student);
        return "redirect:/students";
    }

    @RequestMapping(value = "/{sid}/display-image")
    public void getStudentPhoto(HttpServletResponse response, @PathVariable("sid") long sid) throws Exception {
        Student student = studentService.getStudentBySid(sid);
        Image image = student.getImage();

        if(image != null) {
            response.setContentType(image.getFileType());
            InputStream inputStream = new ByteArrayInputStream(image.getData());
            IOUtils.copy(inputStream, response.getOutputStream());
        }
    }


    @GetMapping("/student-eliminated/:idSubject")
    public List<Student> getEliminatedStudent(@PathVariable(value = "idSubject") long idSubject) {
        List<Student> students = studentService.getAllStudents();
        List<Student> data = new ArrayList<>();


        Subject subjects = subjectService.getSubjectById(idSubject);
        System.out.println(subjects);

        List<GroupSubject> groupList = groupSubjectRepository.findById_SubjectId(idSubject);
        for (GroupSubject groupSubj: groupList
             ) {
            List<Student> studentList = studentRepository.findByGroup_Id(groupSubj.getGroup().getId());
            // System.out.println(studentList);

            for (int i = 0; i < studentList.size(); i++) {

                List<Absence> a = (List<Absence>) studentList.get(i).getAbsence();

                System.out.println(a);
                float tt = 0;

                if (a.size() != 0) {
                    System.out.println(absenceService.countHours(a));

                    if (absenceService.countHours(a) > 24) {
                        if (!(data.contains(a.get(0).getStudent()))) {
                            data.add(a.get(0).getStudent());
                        }

                    }

                }

            }

        }

        return data;
    }



   /* @PostMapping("/send-mail/{idSubject}")
    public String sendMailToStudent(@Validated Student student, @PathVariable("idSubject") long idSubject) {
        Subject subject = subjectService.getSubjectById(idSubject);
        String text = "Bonjour " + student.getFirstName() + " " + student.getLastName() + "!";
        text += "\nVous avez dépasser le quota d'absences horaires de 24 heures pour la matière " + subject.getName();
        text += "\nSi vous ne justifiez pas dans 48 heures, vous serez éliminer !";
        text += "\nBest regard !";

        EmailDetails emailDetails = new EmailDetails(student.getEmail(), text, "Avis d'avertissements", "");

        String status
                = emailService.sendSimpleMail(emailDetails);

        return status;
    }*/

}
