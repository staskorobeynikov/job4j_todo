package todolist.memory;

import org.junit.Test;
import todolist.models.Item;

import java.sql.Timestamp;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class DBStoreTest {

    private final Store store = DBStore.getInstance();

    @Test
    public void whenAddItemMethodTest() {
        Item item = new Item();
        item.setCreated(new Timestamp(System.currentTimeMillis()));
        item.setDesc("Learn Hibernate");
        item.setDone(false);

        store.addItem(item);

        List<Item> list = store.findAll();
        assertThat(list.iterator().next().getDesc(), is("Learn Hibernate"));
    }

    @Test
    public void whenTestMethodUpdate() {
        Item first = new Item();
        first.setCreated(new Timestamp(System.currentTimeMillis()));
        first.setDesc("Learn Hibernate");
        first.setDone(false);

        Item second = new Item();
        second.setCreated(new Timestamp(System.currentTimeMillis()));
        second.setDesc("Learn Spring");
        second.setDone(false);

        store.addItem(first);
        store.addItem(second);

        Item update = new Item();
        update.setId(2);
        update.setDone(true);
        store.updateItem(update);

        List<Item> list = store.findAll();

        assertThat(list.get(1).isDone(), is(true));
        assertThat(list.iterator().next().isDone(), is(false));
        assertThat(list.get(1).getDesc(), is("Learn Spring"));
    }

    @Test
    public void whenTestMethodShowFilterItems() {
        Item first = new Item();
        first.setCreated(new Timestamp(System.currentTimeMillis()));
        first.setDesc("Learn Hibernate");
        first.setDone(false);

        Item second = new Item();
        second.setCreated(new Timestamp(System.currentTimeMillis()));
        second.setDesc("Learn Spring");
        second.setDone(false);

        store.addItem(first);
        store.addItem(second);

        Item update = new Item();
        update.setId(2);
        update.setDone(true);
        store.updateItem(update);

        List<Item> list = store.showFilterItems();

        assertThat(list.iterator().next().getDesc(), is("Learn Hibernate"));
    }
}