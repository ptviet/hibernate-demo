package com.stevenp.hibernate.OneToMany.entity;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


public class GetInstructorsCourses {

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

            // start the transaction
            session.beginTransaction();

            // get the instructor from db
            System.out.println("\nGetting instructor from db...");
            int id = 1;
            Instructor instructor = session.get(Instructor.class, id);

            System.out.println("\nInstructor: " + instructor +"\n");

            //  get course for the instructor
            System.out.println("\nCourses: " + instructor.getCourseList() + "\n");


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
