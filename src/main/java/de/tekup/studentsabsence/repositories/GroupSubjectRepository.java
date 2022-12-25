package de.tekup.studentsabsence.repositories;

import de.tekup.studentsabsence.entities.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface GroupSubjectRepository extends CrudRepository<GroupSubject, GroupSubjectKey> {
    List<GroupSubject> findAllByGroup(Group id);


    ///TODO create a methode to find a groupSubject by Group Id and Subject Id

    //@Query("select GroupSubject g from GroupSubject gc ,Subject subject ,Group group where subject.id like :x and group.id=x2")
    //GroupSubject find_groupSubject_by_Group_Id_and_Subject_Id(Long gid, Long sid);
}
