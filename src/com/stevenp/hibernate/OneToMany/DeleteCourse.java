package com.stevenp.hibernate.OneToMany;

import com.stevenp.hibernate.OneToMany.entity.Course;
import com.stevenp.hibernate.OneToMany.entity.Instructor;
import com.stevenp.hibernate.OneToMany.entity.InstructorDetail;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


public class DeleteCourse {

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

            // get the course from db
            System.out.println("\nGetting course from db...");
            int id = 11;
            Course course = session.get(Course.class, id);

            System.out.println("\nCourse: " + course +"\n");

            // delete the course
            if (course != null) {
                System.out.println("\nDeleting\n");
                session.delete(course);
            }
            else
                System.out.println("\nNo result found.\n");


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
