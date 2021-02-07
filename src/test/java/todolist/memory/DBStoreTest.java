package todolist.memory;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Test;
import todolist.models.Category;
import todolist.models.Item;

import java.sql.Timestamp;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class DBStoreTest {

    @Test
    public void whenAddItemMethodTest() {
        SessionFactory sessionFactory = ConnectionRollback.create(HibernateFactory.FACTORY);
        Session session = sessionFactory.openSession();
        DBStore store = new DBStore(sessionFactory);

        Category learning = store.addCategory(Category.of("Learning"));

        Item item = new Item();
        item.setCreated(new Timestamp(1585047600000L));
        item.setDesc("Learn Hibernate");
        item.setDone(false);
        item.setCategories(List.of(learning));

        store.addItem(item);

        assertThat(store.findAll().get(0).getDesc(), is("Learn Hibernate"));

        session.clear();
        sessionFactory.close();
    }

    @Test
    public void whenTestMethodUpdate() {
        SessionFactory sessionFactory = ConnectionRollback.create(HibernateFactory.FACTORY);
        Session session = sessionFactory.openSession();
        DBStore store = new DBStore(sessionFactory);

        Category learning = store.addCategory(Category.of("Learning"));

        Item first = new Item();
        first.setCreated(new Timestamp(1585047600000L));
        first.setDesc("Learn Hibernate");
        first.setDone(false);
        first.setCategories(List.of(learning));

        store.addItem(first);

        int id = store.findAll().get(0).getId();
        first.setId(id);
        first.setDone(true);

        store.updateItem(first);

        List<Item> result = store.findAll();

        assertThat(result.get(0).isDone(), is(true));
        assertThat(result.get(0).getDesc(), is("Learn Hibernate"));

        session.clear();
        sessionFactory.close();
    }

    @Test
    public void whenTestMethodShowFilterItems() {
        SessionFactory sessionFactory = ConnectionRollback.create(HibernateFactory.FACTORY);
        Session session = sessionFactory.openSession();
        DBStore store = new DBStore(sessionFactory);

        Item first = new Item();
        first.setCreated(new Timestamp(1585047600000L));
        first.setDesc("Learn Hibernate");
        first.setDone(true);
        Item second = new Item();
        second.setCreated(new Timestamp(1585054800000L));
        second.setDesc("Learn Spring");
        second.setDone(false);

        store.addItem(first);
        store.addItem(second);

        List<Item> list = store.showFilterItems();

        assertThat(list.get(0).getDesc(), is("Learn Spring"));

        session.clear();
        sessionFactory.close();
    }
}