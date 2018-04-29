package com.stevenp.hibernate.OneToMany;

import com.stevenp.hibernate.OneToMany.entity.*;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


public class CreateInstructor {

    private static final SessionFactory factory;

    //create session factory
    static {
        try {
            Configuration configuration = new Configuration();
            configuration.configure("hibernate.cfg.xml");

            factory = configuration.addAnnotatedClass(Instructor.class)
                                    .addAnnotatedClass(InstructorDetail.class)
                                    .addAnnotatedClass(Course.class)
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

            // create objects

            System.out.println("\nCreating new instructor object...");
            Instructor instructor = new Instructor("Susan", "Public", "susanpublic@luv2code.com");

            System.out.println("\nCreating new instructordetail object...");
            InstructorDetail instructorDetail = new InstructorDetail("http://youtube.com", "Video Games");

            // associate objects
            instructor.setInstructorDetail(instructorDetail);

            // start the transaction
            session.beginTransaction();


            // save the instructor
            System.out.println("Saving instructor: " + instructor);
            session.save(instructor);

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
