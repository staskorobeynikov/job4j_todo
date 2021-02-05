package todolist.memory;

import todolist.models.Category;
import todolist.models.Item;
import todolist.models.User;

import java.util.List;

public interface Store {

    Item addItem(Item item);

    void updateItem(Item item);

    List<Item> showFilterItems();

    List<Item> findAll();

    void addUser(User user);

    User findByEmail(String email);

    List<Category> findCategories();
}
