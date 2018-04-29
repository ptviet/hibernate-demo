package com.stevenp.hibernate.OneToManyUni;

import com.stevenp.hibernate.OneToManyUni.entity.*;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


public class CreateCourseAndReviews {

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

            // create a course
            System.out.println("\nCreating a course\n");
            Course course = new Course("Pacman - How to score one million points");

            // add some reviews
            System.out.println("\nAdding reviews to the course\n");
            course.addReview(new Review("Great course! Love it!"));
            course.addReview(new Review("Cool course, well done!"));
            course.addReview(new Review("What a dumb course!"));

            // save the course and leverage the cascade all
            System.out.println("\nSaving to db...\n");
            session.save(course);


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
