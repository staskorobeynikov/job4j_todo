package todolist.models;

import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class ItemTest {

    @Test
    public void whenTestMethodEqualsHashcode() {
        Set<Item> store = new HashSet<>();
        Item item = new Item();
        item.setId(1);
        item.setDesc("Learn SOLID principles");
        item.setDone(true);

        Item item1 = new Item();
        item1.setId(1);
        item1.setDone(false);
        item.setDesc("Learn OOD");

        store.add(item);
        store.add(item1);

        item = null;
        item1 = null;

        store.add(item);
        store.add(item1);

        assertThat(store.size(), is(2));
    }
}