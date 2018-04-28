package com.stevenp.hibernate.CrudDemo;

import com.stevenp.hibernate.CrudDemo.entity.Student;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.HibernateException;


public class ReadStudent {

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

            // create a student object
            System.out.println("\nCreating new student object...");
            Student student = new Student("Daffy",
                    "Duck",
                    "daffyduck@website.com");

            // start the transaction
            session.beginTransaction();

            // save the student object
            System.out.println("Saving the student: " + student);
            session.save(student);

            // commit transaction
            session.getTransaction().commit();
            System.out.println("Saved.");

            // find the student's id: primary key
            System.out.println("Generated id: " + student.getId());

            // close session
            session.close();

            // get a new session and start transaction
            session = getSession();
            session.beginTransaction();

            // retrieve student based on the id: primary key
            System.out.println("\nGetting student with id: " + student.getId());
            Student myStudent = session.get(Student.class, student.getId());
            System.out.println("Student retrieved: " + myStudent);

            // commit the transaction
            session.getTransaction().commit();

            // close session
            session.close();
            System.out.println("\nDone.\n");

        } finally {
            factory.close();
        }

    }
}
