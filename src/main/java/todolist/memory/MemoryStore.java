package todolist.memory;

import todolist.models.Item;
import todolist.models.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MemoryStore implements Store {

    private static final MemoryStore INSTANCE = new MemoryStore();

    private final Map<Integer, Item> store = new HashMap<>();

    public MemoryStore() {
    }

    public static MemoryStore getInstance() {
        return INSTANCE;
    }

    @Override
    public Item addItem(Item item) {
        store.put(item.getId(), item);
        return item;
    }

    @Override
    public void updateItem(Item item) {
        Item update = store.get(item.getId());
        update.setDone(item.isDone());
        store.put(item.getId(), update);
    }

    @Override
    public List<Item> showFilterItems() {
        return new ArrayList<>();
    }

    @Override
    public List<Item> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public void addUser(User user) {

    }

    @Override
    public User findByEmail(String email) {
        return null;
    }
}
