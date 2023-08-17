package org.example.developer.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
public class University {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "university")
    private List<Department> departments = new ArrayList<>();

    public void setName(String universityName) {
        this.name = universityName;
    }

    public String getName() {
        return name;
    }
}


