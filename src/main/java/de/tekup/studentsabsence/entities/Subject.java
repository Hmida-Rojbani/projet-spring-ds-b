package de.tekup.studentsabsence.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Subject implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Name is required")
    private String name;

    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="absence_id", referencedColumnName="id")
    private Absence absence;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", referencedColumnName = "id")
    private Set<Group> group = new HashSet<>();


    /*@OneToMany(targetEntity = GroupSubject.class,orphanRemoval = true, mappedBy = "id", fetch = FetchType.LAZY)
    private Set<GroupSubject> groupSubject = new HashSet<>();*/


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

    public Absence getAbsence() {
        return absence;
    }

    public void setAbsence(Absence absence) {
        this.absence = absence;
    }

    public Set<Group> getGroup() {
        return group;
    }

    public void setGroup(Set<Group> group) {
        this.group = group;
    }

    /*public Set<GroupSubject> getGroupSubject() {
        return groupSubject;
    }

    public void setGroupSubject(Set<GroupSubject> groupSubject) {
        this.groupSubject = groupSubject;
    }*/
}
