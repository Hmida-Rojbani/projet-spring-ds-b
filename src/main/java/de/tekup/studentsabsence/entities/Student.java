package de.tekup.studentsabsence.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"image","group","absences"})
public class Student implements Serializable {
    //TODO Complete Validations of fields


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sid;

    @Column(length = 50)
    @NotBlank
    @Size(min = 3,max = 50)
    private String firstName;

    @Column(length = 50)
    @NotBlank
    @Size(min = 3,max = 50)
    private String lastName;

    @Column(unique = true,nullable = false)
    @NotBlank
    @Email
    private String email;

    @Column(length = 8)
    @NotBlank
    private String phone;


    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate dob;

    //TODO Complete Relations with other entities

    @OneToOne
    private Image image;
    @ManyToOne
    private Group group;
    @OneToMany
    private List<Absence> absences;

}
