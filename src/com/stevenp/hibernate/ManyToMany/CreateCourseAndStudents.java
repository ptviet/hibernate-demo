package com.stevenp.hibernate.ManyToMany;

import com.stevenp.hibernate.ManyToMany.entity.*;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


public class CreateCourseAndStudents {

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
                                    .addAnnotatedClass(Student.class)
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
            System.out.println("\nSaving course and reviews to db...\n");
            session.save(course);

            // create the students
            System.out.println("\n Create students...\n");
            Student student1 = new Student("John", "Doe", "johndoe@website.com");
            Student student2 = new Student("Marry", "Public", "marrypublic@website.com");

            // add students to the course
            System.out.println("\n Adding students to the course...\n");
            course.addStudent(student1);
            course.addStudent(student2);

            // save the students
            System.out.println("\n Saving students...\n");
            session.save(student1);
            session.save(student2);
            System.out.println("\n Saved students:" + course.getStudentList() + "\n");

            // commit transaction
            session.getTransaction().commit();

            // close session
            session.close();


        } finally {
            factory.close();
            System.out.println("\nDone.\n");
        }

    }
}
