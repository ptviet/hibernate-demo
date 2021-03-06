package com.stevenp.hibernate.CRUDdemo;

import com.stevenp.hibernate.CRUDdemo.entity.Student;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


public class DeleteStudent {

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

            int tempStuId = 1;

            //create session
            Session session = getSession();

            // start the transaction
            session.beginTransaction();

            // retrieve student based on the id: primary key
            System.out.println("\nGetting student with id: " + tempStuId);
            Student myStudent = session.get(Student.class, tempStuId);

            // delete the student
            System.out.println("\nDeleting the student: " + myStudent);
            session.delete(myStudent);
            //alternative
            //session.createQuery("delete from Student where id=1").executeUpdate();

            // commit the transaction
            session.getTransaction().commit();

            // close session
            session.close();

            System.out.println("\nDone.\n");

        } finally {
            factory.close();
        }

    }
}
