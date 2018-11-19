package com.java17.students;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Student st = new Student(null, "Marian", "Kowalski", "123");
        Student st1 = new Student(null, "Krzysiek", "Malinowski", "255");
        Student st2 = new Student(null, "Marek", "Markowski", "475");
        Student st3 = new Student(null, "Robert", "Korzeniowski", "269");

        StudentDao studentDao = new StudentDao();
        studentDao.savesStudentIntoDatabase(st);
        studentDao.savesStudentIntoDatabase(st1);
        studentDao.savesStudentIntoDatabase(st2);
        studentDao.savesStudentIntoDatabase(st3);

        Scanner sc = new Scanner(System.in);
        System.out.println("Podaj operację jaką chcesz wykonać:\nSAVE - zapisuje do bazy danych,\nLIST - wypisuje listę,\nEXIT - wyjście:");

        String odczyt;
        do {
            odczyt = sc.nextLine();
            if (odczyt.equals("LIST")) {
                studentDao.getAllStudentsFromDatabase().stream().forEach(System.out::println);
            } else if (odczyt.equals("SAVE")) {
                studentDao.savesStudentIntoDatabase(st);
            }
        } while (!odczyt.equals("EXIT"));
    }
}
