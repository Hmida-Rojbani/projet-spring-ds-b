package de.tekup.studentsabsence.repositories;

import de.tekup.studentsabsence.entities.Absence;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AbsenceRepository extends CrudRepository<Absence, Long> {
    List<Absence> findAllByStudent_Group_Id(Long id);
    List<Absence> findAllByStudent_Sid(Long sid);
    List<Absence> findAllByStudent_SidAndSubject_Id(Long sid, Long id);
    List<Absence> findAllByStudent_Group_IdAndSubject_Id(Long gid, Long id);

    @Query("select a from Absence a where a.subject.id = ?1")
    List<Absence> findBySubject_Id(Long id);
}
