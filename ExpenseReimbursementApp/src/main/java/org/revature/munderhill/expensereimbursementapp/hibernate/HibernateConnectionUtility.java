package org.revature.munderhill.expensereimbursementapp.hibernate;

import lombok.extern.log4j.Log4j2;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

@Log4j2
public class HibernateConnectionUtility {

    private static HibernateConnectionUtility hibernateConnectionUtility;
    private static SessionFactory sessionFactory;

    private HibernateConnectionUtility() {};

    /*
        getInstance() factory method for this Singleton. This Singleton class
        (HibernateConnectionUtility) allows users to call HibernateConnectionUtility.
        getInstances().getDatabaseSession() to retrieve a Hibernate Session object
        without having to instantiate any class with the new keyword and store it
        in a variable.
     */
    public static HibernateConnectionUtility getInstance() {
        if(hibernateConnectionUtility != null) {
            return hibernateConnectionUtility;
        }
        else{
            // configures connection, builds session factory
            Configuration config = new Configuration();
            try {
                hibernateConnectionUtility = new HibernateConnectionUtility();
                config.configure();
                sessionFactory = config.buildSessionFactory();
            } catch (HibernateException h) {
                log.error("HibernateException - HibernateConnectionUtility\n", h);
            }
            return hibernateConnectionUtility;
        }
    }

    /*
       Returns a Session object to be used for Hibernate database access.
     */
    public Session getDatabaseSession() {
        return sessionFactory.openSession();
    }
}
