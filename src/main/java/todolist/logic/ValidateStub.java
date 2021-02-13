package todolist.logic;

import todolist.memory.MemoryStore;
import todolist.memory.Store;
import todolist.models.Category;
import todolist.models.Item;
import todolist.models.User;

import java.util.ArrayList;
import java.util.List;

public class ValidateStub implements Validate {

    private final Store store = MemoryStore.getInstance();

    @Override
    public Item addItem(Item item, String[] ids) {
        item.setId(1);
        for (String id : ids) {
            item.addCategory(store.findById(Integer.parseInt(id)));
        }
        return store.addItem(item);
    }

    @Override
    public void updateItem(Item item) {
        store.updateItem(item);
    }

    @Override
    public List<Item> showFilterItems() {
        List<Item> result = new ArrayList<>();
        for (Item item : store.findAll()) {
            if (!item.isDone()) {
                result.add(item);
            }
        }
        return result;
    }

    @Override
    public List<Item> findAll() {
        return store.findAll();
    }

    @Override
    public void addUser(User user) {
        store.addUser(user);
    }

    @Override
    public User findByEmail(String email) {
        return store.findByEmail(email);
    }

    @Override
    public void addCategory(Category category) {
        store.addCategory(category);
    }
}
