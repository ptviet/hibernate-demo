package com.stevenp.hibernate.OneToOneUni;

import com.stevenp.hibernate.entity.Instructor;
import com.stevenp.hibernate.entity.InstructorDetail;
import com.stevenp.hibernate.entity.Student;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


public class CreateDemo {

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

            // create objects

            System.out.println("\nCreating new instructor object...");
            Instructor instructor = new Instructor("Madhu", "Patel", "mahdu@luv2code.com");

            System.out.println("\nCreating new instructordetail object...");
            InstructorDetail instructorDetail = new InstructorDetail("http://youtube.com", "Guitar");

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
