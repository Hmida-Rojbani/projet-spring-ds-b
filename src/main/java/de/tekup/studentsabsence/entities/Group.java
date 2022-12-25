package de.tekup.studentsabsence.entities;

import de.tekup.studentsabsence.enums.LevelEnum;
import de.tekup.studentsabsence.enums.SpecialityEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@ToString(exclude = "students")
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "_group")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Name is required")
    private String name;
    @NotBlank(message = "Label is required")
    private String label;
    @Enumerated(EnumType.STRING)
    private LevelEnum level;
    @NotNull(message = "Speciality is required")
    @Enumerated(EnumType.STRING)
    private SpecialityEnum speciality;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id", referencedColumnName = "id")
    private Set<Subject> subject = new HashSet<>();


    @OneToMany(targetEntity = Student.class,orphanRemoval = true, mappedBy = "sid")
    private List<Student> student = new ArrayList<>();

    /*@OneToMany(targetEntity = GroupSubject.class,orphanRemoval = true, mappedBy = "id", fetch = FetchType.LAZY)
    private Set<GroupSubject> groupSubjects = new HashSet<>();*/

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public LevelEnum getLevel() {
        return level;
    }

    public void setLevel(LevelEnum level) {
        this.level = level;
    }

    public SpecialityEnum getSpeciality() {
        return speciality;
    }

    public void setSpeciality(SpecialityEnum speciality) {
        this.speciality = speciality;
    }

    public Set<Subject> getSubject() {
        return subject;
    }

    public void setSubject(Set<Subject> subject) {
        this.subject = subject;
    }

    public List<Student> getStudent() {
        return student;
    }

    public void setStudent(List<Student> student) {
        this.student = student;
    }

/*public Set<GroupSubject> getGroupSubjects() {
        return groupSubjects;
    }

    public void setGroupSubjects(Set<GroupSubject> groupSubjects) {
        this.groupSubjects = groupSubjects;
    }*/
}
