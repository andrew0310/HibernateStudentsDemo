package com.java17.students;

import org.hibernate.Session;
import org.hibernate.SessionException;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.persistence.PersistenceException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

//Data Access Object
public class StudentDao {

    public boolean saveStudentWithGradesIntoDb(Student student) {
        // pobieramy session factory (fabryka połączenia z bazą)
        SessionFactory sesssionFactory = HibernateUtil.getSessionFactory();
        Transaction transaction = null;

        try (Session session = sesssionFactory.openSession()) {
            // otwieram transakcję
            transaction = session.beginTransaction();

            for (Ocena oc : student.getOceny()) {
                session.save(oc);
            }

            session.save(student); // dokonujemy zapisu na bazie

            // zamykam transakcję i zatwierdzam zmiany
            transaction.commit();
        } catch (SessionException se) {
            // w razie błędu przywróć stan sprzed transakcji
            if (transaction != null) {
                transaction.rollback();
            }
            return false;
        }
        return true;
    }

    public boolean savesIntoDatabase(BaseEntity entity) {
        //pobieram session factory (fabryka połączenia z bazą)
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {

            //otwieram transakcję
            transaction = session.beginTransaction();
            session.save(entity);  //dokonujemy zapisu w bazie
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

    public Optional<Student> getById(Long id) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

        try (Session session = sessionFactory.openSession()) {

            Query<Student> query = session.createQuery("from Student where id = : id", Student.class);  //stwórz zapytanie
            query.setParameter("id", id);

            return Optional.ofNullable(query.getSingleResult());   //zwróć wynik

        } catch (PersistenceException se) {
            //jeśli coś pójdzie nie tak - wypiszemy komunikat loggerem:
            System.err.println("Nie udało się pobrać z bazy!");
        }
        //jeśli nie uda się znaleźć zwraca pustą listę
        return Optional.empty();
    }

    public List<Student> getById(List<Long> ids) {

        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        try (Session session = sessionFactory.openSession()) {

            Query<Student> query = session.createQuery("from Student where id IN :ids", Student.class);  //stwórz zapytanie
            query.setParameterList("ids", ids);

            return query.list();   //zwróć wynik

        } catch (PersistenceException se) {
            //jeśli coś pójdzie nie tak - wypiszemy komunikat loggerem:
            System.err.println("Nie udało się pobrać z bazy!");
        }
        //jeśli nie uda się znaleźć zwraca pustą listę
        return new ArrayList<>();
    }

    public boolean removeById(Long id) {

        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Transaction transaction = null;

        Optional<Student> studentOptional = getById(id);
        if (studentOptional.isPresent()) {
            Student student = studentOptional.get();

            try (Session session = sessionFactory.openSession()) {
                transaction = session.beginTransaction();
/*
                for (Ocena ocena : student.getOceny()) {
                    session.delete(ocena);
                }*/

                session.delete(student);
                transaction.commit();
                return true;
            } catch (SessionException se) {
                if (transaction != null) {
                    transaction.rollback();
                }
            }
        }
        return false;
    }

    public boolean addTeacherToStudent(Long teacherId, Long studentId) {
        Transaction transaction = null;
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        try (Session session = sessionFactory.openSession()) {

            Query<Teacher> query = session.createQuery("from Teacher where id = :id", Teacher.class);
            query.setParameter("id", teacherId);
            Teacher teacher = query.getSingleResult();

            Query<Student> studentQuery = session.createQuery("from Student where id =:id", Student.class);
            Student student = studentQuery.getSingleResult();

            student.getListaNauczycieli().add(teacher);
            teacher.getListaStudentow().add(student);

            session.save(teacher);
            session.save(student);

            transaction.commit();
            return true;
        } catch (PersistenceException se) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.err.println("Nie udało się pobrać z bazy!");
        }
        return false;
    }


    public List<Student> getAllStudents_fromTeacher(Long teacherId) {

        SessionFactory sesssionFactory = HibernateUtil.getSessionFactory();
        try (Session session = sesssionFactory.openSession()) {

            Query<Teacher> query = session.createQuery("from Teacher where id = :id", Teacher.class);
            query.setParameter("id", teacherId);

            Teacher teacher = query.getSingleResult();

            return teacher.getListaStudentow().stream().collect(Collectors.toList());

        } catch (PersistenceException se) {
            System.err.println("Nie udało się pobrać z bazy!");
        }
        return new ArrayList<>();
    }
}

