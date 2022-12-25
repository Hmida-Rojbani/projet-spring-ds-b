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
import java.util.ArrayList;
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
    @NotBlank(message = "firstName is required")
    private String firstName;
    @NotBlank(message = "lasttName is required")
    private String lastName;
    @NotBlank(message = "email is required")
    private String email;
    @NotBlank(message = "phone is required")
    private String phone;
    @NotNull(message = "Date of Birth is required")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate dob;



    //TODO Complete Relations with other entities
    @ManyToOne
    private Group group;
    @OneToOne(mappedBy = "student")
    private Image image;
    @OneToMany(mappedBy = "student",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Absence> absences;
}
