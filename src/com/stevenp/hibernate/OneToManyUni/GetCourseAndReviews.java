package com.stevenp.hibernate.OneToManyUni;

import com.stevenp.hibernate.OneToManyUni.entity.Course;
import com.stevenp.hibernate.OneToManyUni.entity.Instructor;
import com.stevenp.hibernate.OneToManyUni.entity.InstructorDetail;
import com.stevenp.hibernate.OneToManyUni.entity.Review;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


public class GetCourseAndReviews {

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

            // get the course
            int id = 10;

            Course course = session.get(Course.class, id);

            // print the course
            System.out.println("Course: " + course);

            // print the reviews
            System.out.println("Reviews: " + course.getReviewList());

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
