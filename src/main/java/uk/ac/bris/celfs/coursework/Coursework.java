package uk.ac.bris.celfs.coursework;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "coursework")
@Data
public class Coursework {
    @Id
    @GeneratedValue
    @Column(name = "coursework_id")
    private Long id;

    @Column(name = "coursework_name")
    private String name;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}