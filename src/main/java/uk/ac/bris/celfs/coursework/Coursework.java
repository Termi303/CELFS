package uk.ac.bris.celfs.coursework;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Table(name = "coursework")
@Data
public class Coursework {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "coursework_id")
    private Long id;

    @NotNull
    @Column(name = "coursework_name")
    private String name;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Coursework(String name) {
        this.name = name;
    }

    private Coursework() {}
}
