package com.stevenp.hibernate.demo;

import com.stevenp.hibernate.entity.Student;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.HibernateException;


public class PrimaryKey {
    private static final SessionFactory factory;

    //create session factory
    static {
        try {
            Configuration configuration = new Configuration();
            configuration.configure("hibernate.cfg.xml");

            factory = configuration.addAnnotatedClass(Student.class).buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static Session getSession() throws HibernateException {
        return factory.getCurrentSession();
    }

    public static void main(String[] args) {

        try {
            //create session
            Session session = getSession();

            // create 3 student objects
            System.out.println("\nCreating 3 student objects...");
            Student student1 = new Student("John",
                    "Doe",
                    "johndoe@website.com");
            Student student2 = new Student("Marry",
                    "Public",
                    "marrypublic@website.com");
            Student student3 = new Student("Bonita",
                    "Applebum",
                    "bonitaapplebum@website.com");

            // start the transaction
            session.beginTransaction();

            // save the student object
            System.out.println("Saving the students...");
            session.save(student1);
            session.save(student2);
            session.save(student3);

            // commit transaction
            session.getTransaction().commit();
            System.out.println("\nDone.\n");

            // close session
            session.close();

        } finally {

            factory.close();
        }

    }

}
