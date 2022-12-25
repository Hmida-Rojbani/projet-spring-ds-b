package de.tekup.studentsabsence.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupSubject implements Serializable {
    @EmbeddedId
    private GroupSubjectKey id;

    @ManyToOne
    //mapsid : y5alik taaml field part of primary key and it's foreign key at the same time
    @MapsId("groupId")
    @JoinColumn(name = "group_id")
    private Group group;

    @ManyToOne
    @MapsId("subjectId")
    @JoinColumn(name = "subject_id")
    private Subject subject;

    private float hours;
}
