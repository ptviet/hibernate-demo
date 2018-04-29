package com.stevenp.hibernate.OneToMany;

import com.stevenp.hibernate.OneToMany.entity.Course;
import com.stevenp.hibernate.OneToMany.entity.Instructor;
import com.stevenp.hibernate.OneToMany.entity.InstructorDetail;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


public class CreateCourse {

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


            // create courses
            System.out.println("\nCreating new courses...");
            Course course1 = new Course("Air Guitar - The Ultimate Guide");
            Course course2 = new Course("The Pinball Masterclass");


            // add courses to instructor
            System.out.println("Adding courses to instructor...");
            instructor.add(course1);
            instructor.add(course2);

            // save the courses
            System.out.println("Saving the courses to db...");
            session.save(course1);
            session.save(course2);

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
