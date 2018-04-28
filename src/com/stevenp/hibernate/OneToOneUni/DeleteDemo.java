package com.stevenp.hibernate.OneToOneUni;

import com.stevenp.hibernate.entity.Instructor;
import com.stevenp.hibernate.entity.InstructorDetail;
import com.stevenp.hibernate.entity.Student;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


public class DeleteDemo {

    private static final SessionFactory factory;

    //create session factory
    static {
        try {
            Configuration configuration = new Configuration();
            configuration.configure("hibernate.cfg.xml");

            factory = configuration.addAnnotatedClass(Instructor.class)
                    .addAnnotatedClass(InstructorDetail.class)
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

            // get instructor by primary key: id
            int id = 3;
            Instructor instructor = session.get(Instructor.class, id);

            // delete the instructor
            if (instructor != null) {
                System.out.println("\nFound: " + instructor);
                System.out.println("\nDeleting...");
                session.delete(instructor);
                // instructor detail also deleted

            } else {
                System.out.println("\nNo result found.");
            }

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
