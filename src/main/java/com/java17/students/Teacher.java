package com.java17.students;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Teacher extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private Long id;
    private String imie;
    private String nazwisko;
    private double sredniaOcena;

    @ManyToMany(fetch = FetchType.LAZY)
    private List<Student> listaStudentow;
}
