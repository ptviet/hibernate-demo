package com.stevenp.hibernate.ManyToMany;

import com.stevenp.hibernate.ManyToMany.entity.*;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


public class DeleteStudent {

    private static final SessionFactory factory;

    //create session factory
    static {
        try {
            Configuration configuration = new Configuration();
            configuration.configure("hibernate.cfg.xml");

            factory = configuration.addAnnotatedClass(Instructor.class)
                                    .addAnnotatedClass(InstructorDetail.class)
                                    .addAnnotatedClass(Course.class)
                                    .addAnnotatedClass(Review.class)
                                    .addAnnotatedClass(Student.class)
                                    .buildSessionFactory();
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

            // start the transaction
            session.beginTransaction();

            // get a student from db
            int id = 2;
            Student student = session.get(Student.class, id);

            // delete the course
            System.out.println("\nDeleting the student: " + student);
            session.delete(student);

            // commit transaction
            session.getTransaction().commit();

            // close session
            session.close();


        } finally {
            factory.close();
            System.out.println("\nDone.\n");
        }

    }
}
