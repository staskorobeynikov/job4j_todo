package todolist.logic;

import todolist.memory.DBStore;
import todolist.memory.Store;
import todolist.models.Item;
import todolist.models.User;

import java.util.List;

public class ValidateService implements Validate {

    private static final Validate INSTANCE = new ValidateService();

    private final Store store = DBStore.getInstance();

    public ValidateService() {
    }

    public static Validate getInstance() {
        return INSTANCE;
    }

    @Override
    public Item addItem(Item item, String[] ids) {
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
    public List<Item> findAll() {
        return store.findAll();
    }

    @Override
    public List<Item> showFilterItems() {
        return store.showFilterItems();
    }

    @Override
    public void addUser(User user) {
        store.addUser(user);
    }

    @Override
    public User findByEmail(String email) {
        return store.findByEmail(email);
    }
}
