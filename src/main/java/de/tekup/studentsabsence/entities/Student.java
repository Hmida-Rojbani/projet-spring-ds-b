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
    @Column(unique = true)
    private Long sid;
    @Column(length = 80)
    @NotBlank(message="this field is required")
    @Size(min=3,max = 80)
    private String firstName;

    @Column(length = 80)
    @NotBlank(message="this field is required")
    @Size(min=3,max = 80)
    private String lastName;
    private String email;

    @NotBlank(message="this field is required")
    @Pattern(regexp = "^[0-9]{8}$", message = "phone must contains exactly 8 digits")
    private String phone;
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate dob;

    //TODO Complete Relations with other entities
    @NotNull(message = "this filed is required")
    @OneToOne
    public Image Images;
    @OneToMany(mappedBy = "Student")
    public List<Absence> Absences;
    @ManyToOne
    public Group Groups;


}
