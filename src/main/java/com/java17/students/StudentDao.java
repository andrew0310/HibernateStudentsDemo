package com.java17.students;

import org.hibernate.Session;
import org.hibernate.SessionException;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

//Data Access Object
public class StudentDao {

    public boolean savesStudentIntoDatabase(Student student) {
        //pobieram session factory (fabryka połączenia z bazą)
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {

            //otwieram transakcję
            transaction = session.beginTransaction();
            session.save(student);  //dokonujemy zapisu w bazie
            transaction.commit();  //zamykam transakcję i zatwierdzam zmiany

        } catch (SessionException se) {
            //w razie błędu przywróć stan sprzed transakcji
            if (transaction != null) {
                transaction.rollback();
            }
            return false;
        }
        return true;
    }

    public List<Student> getAllStudentsFromDatabase() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

        try (Session session = sessionFactory.openSession()) {

            //stwórz zapytanie
            Query<Student> query = session.createQuery("from Student st", Student.class);
            //wywołaj zapytanie
            List<Student> students = query.list();
            //zwróć wynik
            return students;

        } catch (SessionException se) {
            //jeśli coś pójdzie nie tak - wypiszemy komunikat loggerem:
            System.err.println("Nie udało się pobrać z bazy!");
        }
        //jeśli nie uda się znaleźć zwraca pustą listę
        return new ArrayList<>();

    }
}
