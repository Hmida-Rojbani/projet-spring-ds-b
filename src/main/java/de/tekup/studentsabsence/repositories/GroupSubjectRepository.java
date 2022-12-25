package de.tekup.studentsabsence.repositories;

import de.tekup.studentsabsence.entities.Group;
import de.tekup.studentsabsence.entities.GroupSubject;
import de.tekup.studentsabsence.entities.GroupSubjectKey;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface GroupSubjectRepository extends CrudRepository<GroupSubject, GroupSubjectKey> {
    List<GroupSubject> findAllByGroup(Group id);

    @Query("select g from GroupSubject g where g.id.groupId = ?1 and g.id.subjectId = ?2")
    GroupSubject findById_GroupIdAndId_SubjectId(Long groupId, Long subjectId);

    @Query("select g from GroupSubject g where g.id.subjectId = ?1")
    List<GroupSubject> findById_SubjectId(Long subjectId);






}
