package br.com.auttar.common.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

    private static SessionFactory sessionFactory;

    private static void build(){
        try {
            sessionFactory = new Configuration().configure()
                    .buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("SessionFactory creation failed" + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {

        if(sessionFactory == null)
            build();

        return sessionFactory;
    }
}