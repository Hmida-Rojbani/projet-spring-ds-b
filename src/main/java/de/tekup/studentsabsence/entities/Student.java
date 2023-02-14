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

    @NotBlank(message = "FirstName is required")
    private String firstName;

    @NotBlank(message = "LastName is required")
    private String lastName;

    @Email
    @NotBlank(message = "Email is required" )
    private String email;

    private String phone;
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @NotNull(message = "date is required")
    private LocalDate dob;

    //TODO Complete Relations with other entities
    @OneToMany(mappedBy = "student")
    private List<Absence> absences;

    @ManyToOne
    private  Group group;

    @OneToOne(mappedBy = "student")
    private Image image;

}
