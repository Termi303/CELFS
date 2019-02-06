package uk.ac.bris.celfs.database;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import org.jasypt.util.password.*;

@Entity
@Table(name = "keyword")
@Data
public class Keyword {
    @Id
    @Column(name = "keyword_key")
    private String key;

    @Column(name = "keyword_value")
    @NotEmpty
    private String value;

    public Keyword(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}