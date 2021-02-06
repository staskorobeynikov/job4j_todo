package todolist.logic;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import todolist.memory.DBStore;
import todolist.memory.MemoryStore;
import todolist.memory.Store;
import todolist.models.Item;

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@PowerMockIgnore({"com.sun.org.apache.xerces.*", "javax.xml.*", "org.xml.*", "org.hibernate.*"})
@RunWith(PowerMockRunner.class)
@PrepareForTest(DBStore.class)
public class ValidateServiceTest {

    @Test
    public void whenTestAddNewItemAndMethodFindAll11() {
        Store store = new MemoryStore();
        Item item = new Item();
        item.setDesc("Learn English");
        item.setDone(false);
        item.setId(1);
        Item update = new Item();
        update.setId(1);
        update.setDone(true);
        PowerMockito.mockStatic(DBStore.class);

        when(DBStore.getInstance()).thenReturn(store);

        // new ValidateService().addItem(item);

        new ValidateService().updateItem(update);

        List<Item> list = new ValidateService().findAll();

        List<Item> filter = new ValidateService().showFilterItems();

        assertThat(filter.size(), is(0));
        assertThat(list.iterator().next().getDesc(), is("Learn English"));
        assertThat(list.iterator().next().isDone(), is(true));
    }
}