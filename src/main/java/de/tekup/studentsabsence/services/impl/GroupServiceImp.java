package de.tekup.studentsabsence.services.impl;

import de.tekup.studentsabsence.entities.Absence;
import de.tekup.studentsabsence.entities.Group;
import de.tekup.studentsabsence.entities.GroupSubject;
import de.tekup.studentsabsence.entities.Subject;
import de.tekup.studentsabsence.repositories.AbsenceRepository;
import de.tekup.studentsabsence.repositories.GroupRepository;
import de.tekup.studentsabsence.services.GroupService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.*;

import static java.util.Comparator.comparingDouble;

@Service
@AllArgsConstructor
public class GroupServiceImp implements GroupService {
    private final GroupRepository groupRepository;
    private final AbsenceRepository absenceRepository;

    @Override
    public List<Group> getAllGroups() {
        List<Group> groups = new ArrayList<>();
        groupRepository.findAll().forEach(groups::add);
        return groups;
    }

    @Override
    public Group getGroupById(Long id) {
        return groupRepository.findById(id).orElseThrow(() -> new NoSuchElementException("No Group With ID: " + id));
    }

    @Override
    public Group addGroup(Group group) {
        return groupRepository.save(group);
    }

    @Override
    public Group updateGroup(Group group) {
        if (!groupRepository.existsById(group.getId())) {
            throw new NoSuchElementException("No Group With ID: " + group.getId());
        }
        return groupRepository.save(group);
    }

    @Override
    public Group deleteGroup(Long id) {
        Group group = getGroupById(id);
        groupRepository.delete(group);
        return group;
    }
    public Group TauxAbsence_Plus_et_Moins_Eleve(Long gid)
    {
        Group group = groupRepository.findById(gid).orElseThrow(()-> new EntityNotFoundException());

      //  group.getGroupSubjects().sort(comparingDouble(Absence::getHours));

        return group;
    }
}
