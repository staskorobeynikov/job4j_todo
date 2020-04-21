package todolist.memory;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateFactory {

    public static final SessionFactory FACTORY = new Configuration()
            .configure("Items.cfg.xml")
            .buildSessionFactory();

    public static SessionFactory getFactory() {
        return FACTORY;
    }
}
