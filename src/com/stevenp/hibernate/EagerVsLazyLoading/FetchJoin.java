package com.stevenp.hibernate.EagerVsLazyLoading;

import com.stevenp.hibernate.EagerVsLazyLoading.entity.*;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;


public class FetchJoin {

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

            // Hibernate query with HQL

            // get the instructor from db
            System.out.println("\nGetting instructor from db...");
            int id = 1;

            Query<Instructor> query = session.createQuery("select i from Instructor i "
                                                            + "JOIN FETCH i.courseList "
                                                            + "where i.id=:theInstructorId",
                                                        Instructor.class);

            // set parameter on query

            query.setParameter("theInstructorId", id);

            // execute query and get the instructor

            Instructor instructor = query.getSingleResult();

            System.out.println("\nInstructor: " + instructor +"\n");


            // commit transaction
            session.getTransaction().commit();
            System.out.println("\nDone.\n");

            // close session
            session.close();

            //  get course for the instructor
            System.out.println("\nCourses: " + instructor.getCourseList() + "\n");


        } finally {
            factory.close();
        }

    }
}
