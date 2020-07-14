package todolist.memory;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import todolist.models.Item;
import todolist.models.User;

import java.util.List;
import java.util.function.Function;

public class DBStore implements Store {

    private static final Store INSTANCE = new DBStore(HibernateFactory.getFactory());

    private final SessionFactory factory;

    public DBStore(SessionFactory factory) {
        this.factory = factory;
    }

    public static Store getInstance() {
        return INSTANCE;
    }

    private <T> T tx(final Function<Session, T> command) {
        final Session session = factory.openSession();
        final Transaction transaction = session.beginTransaction();
        try {
            T result = command.apply(session);
            transaction.commit();
            return result;
        } catch (final Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    @Override
    public void addItem(Item item) {
        this.tx(session -> session.save(item));
    }

    @Override
    public void updateItem(Item item) {
        this.tx(session ->
                        session.createQuery("UPDATE todolist.models.Item SET done = :done1 where id = :id")
                                .setParameter("done1", item.isDone())
                                .setParameter("id", item.getId())
                                .executeUpdate());
    }

    @Override
    public List<Item> findAll() {
        return this.tx(
                session ->
                        session.createQuery("FROM todolist.models.Item ORDER BY id").list()
        );
    }

    @Override
    public List<Item> showFilterItems() {
        return this.tx(
                session -> session.createQuery(
                        "FROM todolist.models.Item WHERE done = false ORDER BY id"
                ).list()
        );
    }

    @Override
    public void addUser(User user) {
        this.tx(session -> session.save(user));
    }

    @Override
    public User findByEmail(String email) {
        return (User) this.tx(
                session -> session
                        .createQuery("FROM todolist.models.User WHERE email = :email1")
                        .setParameter("email1", email)
                        .uniqueResult()
        );
    }
}
