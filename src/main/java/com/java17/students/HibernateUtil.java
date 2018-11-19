package com.java17.students;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

//niezbędna jako punkt konfiguracyjny połączenia z Hibernate
public class HibernateUtil {

    private static SessionFactory sessionFactory;

    static {
        //Tworzymy sobie obiekt, który pobiera konfigurację z pliku hibernate.cfg.xml
        StandardServiceRegistry standardServiceRegistry = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
        Metadata metadata = new MetadataSources(standardServiceRegistry).getMetadataBuilder().build();

        //tworzenie sesji z danych zawartych w pliku hibernate.cfg.xml
        sessionFactory = metadata.getSessionFactoryBuilder().build();
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
