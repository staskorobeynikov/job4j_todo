package todolist.servlets;

import todolist.logic.ValidateService;
import todolist.models.Item;
import todolist.models.User;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddItemServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        String desc = req.getParameter("desc");
        Item item = new Item(desc);
        User user = (User) req.getSession().getAttribute("user");
        item.setUser(user);
        ValidateService.getInstance().addItem(item);
    }
}
