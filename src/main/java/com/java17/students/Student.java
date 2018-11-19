package com.java17.students;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    //identity - pobiera id, przypisuje wartość do obiektu i zapisuje obiekt
    //sequence - zapisuje obiekt, pobiera go z powrotem i sprawdza id
    private Long id;

    private String imie;
    private String nazwisko;
    private String indeks;
}
