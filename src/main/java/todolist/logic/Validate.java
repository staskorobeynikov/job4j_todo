package todolist.logic;

import todolist.models.Category;
import todolist.models.Item;
import todolist.models.User;

import java.util.List;

public interface Validate {

    Item addItem(Item item, String[] ids);

    void updateItem(Item item);

    List<Item> showFilterItems();

    List<Item> findAll();

    void addUser(User user);

    User findByEmail(String email);

    void addCategory(Category category);
}
