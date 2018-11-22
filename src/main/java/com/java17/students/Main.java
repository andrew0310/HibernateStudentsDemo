package com.java17.students;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("Wybierz co chcesz zrobić: " +
                "\n1 - operacja na Studentach," +
                "\n2 - operacja na Nauczycielach: ");

        String wybor = sc.next();

        if (wybor.equals("1")) {
            studentOpeacja();
        } else if (wybor.equals("2")) {
            nauczycielOperacja();
        }
    }

    public static void studentOpeacja() {

        System.out.println("Podaj operację jaką chcesz wykonać:" +
                "\nSAVE - zapisuje do bazy danych," +
                "\nUSUN - usuwa z bazy dancyh," +
                "\nPOBIERZ - wypisuje dane (id)," +
                "\nZNAJDZ - znajduje studentów po id," +
                "\nEXIT - wyjście:");

        StudentDao studentDao = new StudentDao();
        Scanner sc = new Scanner(System.in);
        String odczyt;

        do {
            odczyt = sc.next();

            if (odczyt.equals("POBIERZ")) {

                System.out.print("Podaj id studenta: ");
                Long id = sc.nextLong();
                studentDao.getById(id).ifPresent(System.out::println);

            } else if (odczyt.equals("ZNAJDZ")) {
                System.out.print("Podaj ilość studentów: ");
                int ilosc = sc.nextInt();

                List<Long> ids = new ArrayList<>();
                for (int i = 0; i < ilosc; i++) {
                    System.out.println("Podaj id:");
                    Long id = sc.nextLong();
                    ids.add(id);
                }
                System.out.println(studentDao.getById(ids));

            } else if (odczyt.equals("USUN")) {
                System.out.print("Podaj id: ");
                long id = sc.nextLong();
                studentDao.removeById(id);

            } else if (odczyt.equals("SAVE")) {
                System.out.print("Podaj imię: ");
                String imie = sc.next();
                System.out.print("Podaj nazwisko: ");
                String nazwisko = sc.next();
                System.out.print("Podaj numer indeksu: ");
                String indeks = sc.next();

                Student student = new Student();
                System.out.println("Podaj ilość ocen: ");
                int iloscOcen = sc.nextInt();

                List<Ocena> ocenaList = new ArrayList<>();

                for (int i = 0; i < iloscOcen; i++) {
                    System.out.print("Podaj nazwę przedmiotu:");
                    Przedmiot przedmiot = Przedmiot.valueOf(sc.next().toUpperCase());

                    System.out.print("Podaj ocenę:");
                    int ocena = sc.nextInt();

                    ocenaList.add(new Ocena(null, ocena, przedmiot, student));
                }
                student.setImie(imie);
                student.setNazwisko(nazwisko);
                student.setIndeks(indeks);
                student.setOceny(ocenaList);

                studentDao.savesIntoDatabase(student);
            }
        } while (!odczyt.equals("EXIT"));

        HibernateUtil.getSessionFactory().close();   // <---- zamyka połączenie z bazą danych
    }

    public static void nauczycielOperacja() {

        System.out.println("Podaj operację jaką chcesz wykonać:" +
                "\nDODAJ - zapisuje do bazy danych," +
                "\nUSUN - usuwa z bazy dancyh," +
                "\nGET_STUDENTS - pobiera wszystkich studentów danego nauczyciela," +
                "\nPOWIAZ - zapisuje studentów do nauczyciela," +
                "\nEXIT - wyjście:");

        Scanner scanner = new Scanner(System.in);
        StudentDao studentDao = new StudentDao();

        String tekst = scanner.next();
        if (tekst.equals("DODAJ")) {

            String name = scanner.next();
            String surname = scanner.next();
            studentDao.savesIntoDatabase(new Teacher(null, name, surname, 0.0, null));

        } else if (tekst.equals("GET_STUDENTS")) {
            System.out.println("Podaj id nauczyciela:");
            Long id_nauczyciela = scanner.nextLong();
            List<Student> students = studentDao.getAllStudents_fromTeacher(id_nauczyciela);
            System.out.println("Jego studenci:");
            students.forEach(System.out::println);

        } else if (tekst.equals("POWIAZ")) {
            System.out.println("Podaj id studenta:");
            Long idStudent = scanner.nextLong();
            System.out.println("Podaj id nauczyciela:");
            Long idTeacher = scanner.nextLong();
            studentDao.addTeacherToStudent(idTeacher, idStudent);
        }

        HibernateUtil.getSessionFactory().close();
    }

}

