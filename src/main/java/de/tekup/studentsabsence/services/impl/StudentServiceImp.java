package de.tekup.studentsabsence.services.impl;

import de.tekup.studentsabsence.entities.Group;
import de.tekup.studentsabsence.entities.Student;
import de.tekup.studentsabsence.repositories.StudentRepository;
import de.tekup.studentsabsence.services.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@AllArgsConstructor
public class StudentServiceImp implements StudentService {
    private final StudentRepository studentRepository;

    @Override
    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        studentRepository.findAll().forEach(students::add);
        return students;
    }

    @Override
    public Student getStudentBySid(Long sid) {
        return studentRepository.findById(sid).
                orElseThrow(() -> new NoSuchElementException("No Student With SID: " + sid));
    }

    @Override
    public Student addStudent(Student student) {
        Student student1 = student;
        Group group = student.getGroup();
        student1.setGroup(group); // because we have an error when we saved a student (group_id was already null)
        System.out.println(group);
        return studentRepository.save(student1);

    }

    @Override
    public Student updateStudent(Student student) {
        if (!studentRepository.existsById(student.getSid())) {
            throw new NoSuchElementException("No Subject with ID : " + student.getSid());
        }
        return studentRepository.save(student);
    }

    @Override
    public Student deleteStudent(Long sid) {
        Student student = getStudentBySid(sid);
        studentRepository.delete(student);
        return student;
    }
}
