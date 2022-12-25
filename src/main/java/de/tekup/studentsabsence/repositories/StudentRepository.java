package de.tekup.studentsabsence.repositories;

import de.tekup.studentsabsence.entities.Student;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface StudentRepository extends CrudRepository<Student, Long> {
    @Query("select s from Student s where s.group.id = ?1")
    List<Student> findByGroup_Id(Long id);
}
