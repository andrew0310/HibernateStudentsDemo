package com.java17.students;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity         // <-- Hibernate
@Data           // <-- Lombok
@ToString(exclude = {"oceny"})
@AllArgsConstructor
@NoArgsConstructor

public class Student extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    //identity - pobiera id, przypisuje wartość do obiektu i zapisuje obiekt
    //sequence - zapisuje obiekt, pobiera go z powrotem i sprawdza id
    private Long id;

    private String imie;
    private String nazwisko;
    private String indeks;

    //one (this class object) to many (some class Objects below)
    @OneToMany(mappedBy = "student", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    private List<Ocena> oceny;

    @ManyToMany(mappedBy = "listaStudentow", fetch = FetchType.LAZY)
    private List<Teacher> listaNauczycieli;

    @CreationTimestamp
    private LocalDate date;
}
