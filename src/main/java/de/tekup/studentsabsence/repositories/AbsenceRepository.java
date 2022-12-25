package de.tekup.studentsabsence.repositories;

import de.tekup.studentsabsence.entities.Absence;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AbsenceRepository extends CrudRepository<Absence, Long> {
    List<Absence> findAllByStudent_Group_Id(Long id);
    List<Absence> findAllByStudent_Sid(Long sid);
    List<Absence> findAllByStudent_SidAndSubject_Id(Long sid, Long id);
    List<Absence> findAllByStudent_Group_IdAndSubject_Id(Long gid, Long id);
    @Query(value = "SELECT subject_id FROM(SELECT _group.id as grp,absence.subject_id,SUM(absence.hours)as nbh from absence ,_group ,student\n" +
            "            WHERE student.sid=absence.student_sid and _group.id=student.group_id  AND _group.id=?1 \n" +
            "            GROUP BY absence.subject_id)as h WHERE nbh=(SELECT Max(nbh) From(SELECT _group.id as grp,absence.subject_id,SUM(absence.hours)as nbh from absence ,_group ,student  WHERE student.sid=absence.student_sid and" +
            " _group.id=student.group_id AND _group.id=?1 GROUP BY absence.subject_id)as h  ) LIMIT 1",nativeQuery = true)
    Long findSumhourBySubject(Long gid);
    @Query(value = "SELECT subject_id FROM(SELECT _group.id as grp,absence.subject_id,SUM(absence.hours)as nbh from absence ,_group ,student\n" +
            "            WHERE student.sid=absence.student_sid and _group.id=student.group_id  AND _group.id=?1 \n" +
            "            GROUP BY absence.subject_id)as h WHERE nbh=(SELECT MIN(nbh) From(SELECT _group.id as grp,absence.subject_id,SUM(absence.hours)as nbh from absence ,_group ,student  WHERE student.sid=absence.student_sid and" +
            " _group.id=student.group_id AND _group.id=?1 GROUP BY absence.subject_id)as h  )LIMIT 1 ",nativeQuery = true)
    Long findSumMinhourBySubject(Long gid);
}
