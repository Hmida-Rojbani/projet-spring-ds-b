package de.tekup.studentsabsence.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    //TODO Complete Validations of fields (done)

    @Id
    @Column(unique = true)
    private Long sid;
    @Column(length = 50)
    @NotBlank(message="this field is required")
    @Size(min=3,max = 50)
    private String firstName;
    @Column(length = 50)
    @NotBlank(message="this field is required")
    @Size(min=3,max = 50)
    private String lastName;

    @NotBlank(message="this field is required")
    @Email(message = "not a valid email")
    private String email;

    @NotBlank(message="this field is required")
    @Pattern(regexp = "^[0-9]{8}$", message = "phone must contains exactly 8 digits")
    @Column
    private String phone;
    @DateTimeFormat(pattern = "dd-MM-yyyy")
//    @JsonFormat(pattern = "dd-MM-yyyy")
    @Past(message = "not a valid date of birth")
    private LocalDate dob;

    //TODO Complete Relations with other entities (done)

    @NotNull(message = "this filed is required")
    @ManyToOne
    public Group group;

    @OneToMany
    public List<Absence> absences;

    @OneToOne
    public Image image;

}
