package com.stevenp.hibernate.OneToOneBi;

import com.stevenp.hibernate.OneToOneBi.entity.*;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


public class GetInstructorDetail {

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

            // get the instructor detail object
            int id = 4;
            InstructorDetail instructorDetail = session.get(InstructorDetail.class, id);


            // print the instructor detail
            System.out.println("\nInstructor Detail: " + instructorDetail);


            // print the associated instructor
            System.out.println("\nAssociated Instructor: " + instructorDetail.getInstructor());

            // commit transaction
            session.getTransaction().commit();
            System.out.println("\nDone.\n");

            // close session
            session.close();


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            factory.close();
        }

    }
}
