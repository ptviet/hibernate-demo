package com.stevenp.hibernate.CRUDdemo;

import com.stevenp.hibernate.CRUDdemo.entity.Student;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;


public class QueryStudent {

    private static final SessionFactory factory;

    //create session factory
    static {
        try {
            Configuration configuration = new Configuration();
            configuration.configure("hibernate.cfg.xml");

            factory = configuration.addAnnotatedClass(Student.class).buildSessionFactory();
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

            // query students
            List<Student> studentList = session.createQuery("from Student").list();

            // display the students
            displayStudents(studentList);

            // query students: lastName='Doe'
            studentList = session.createQuery("from Student s where s.lastName='Doe'").list();

            // display the students
            System.out.println("\nStudents who have last name of Doe: ");
            displayStudents(studentList);

            // commit transaction
            session.getTransaction().commit();
            System.out.println("\nDone.\n");

            // close session
            session.close();

        } finally {

            factory.close();
        }

    }

    private static void displayStudents(List<Student> studentList) {
        System.out.println("\nList of students: \n");
        for(Student student: studentList) {
            System.out.println(student);
        }
        System.out.println();
    }
}
