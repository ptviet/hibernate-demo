package com.stevenp.hibernate.demo;

import com.stevenp.hibernate.entity.StudentEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.HibernateException;


public class CreateStudent {

    private static final SessionFactory factory;

    //create session factory
    static {
        try {
            Configuration configuration = new Configuration();
            configuration.configure("hibernate.cfg.xml");

            factory = configuration.addAnnotatedClass(StudentEntity.class).buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static Session getSession() throws HibernateException {
        return factory.openSession();
    }

    public static void main(String[] args) {

        //create session
        Session session = getSession();


        try {
            System.out.println("Creating new student object...");
            // create a student object
            StudentEntity student = new StudentEntity("RandomF",
                                            "RandomL",
                                        "randomrandom@website.com");

            // start the transaction
            session.beginTransaction();

            // save the student object
            System.out.println("Saving the student...");
            session.save(student);

            // commit transaction
            session.getTransaction().commit();
            System.out.println("Done.");

        } finally {
            session.close();
            factory.close();
        }

    }
}
